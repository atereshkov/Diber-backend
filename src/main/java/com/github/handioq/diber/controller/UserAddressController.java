package com.github.handioq.diber.controller;

import com.github.handioq.diber.model.dto.AddressDto;
import com.github.handioq.diber.model.dto.ErrorResponseDto;
import com.github.handioq.diber.model.entity.Address;
import com.github.handioq.diber.model.entity.Order;
import com.github.handioq.diber.model.entity.User;
import com.github.handioq.diber.service.AddressService;
import com.github.handioq.diber.service.UserService;
import com.github.handioq.diber.utils.Constants;
import com.github.handioq.diber.utils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.API_URL + Constants.URL_USER_ADDRESS)
public class UserAddressController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAddressController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @PreAuthorize("@securityServiceImpl.hasPermissions(#userPrincipal, #userId)")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAddresses(@AuthenticationPrincipal User userPrincipal,
                                          @PathVariable("user_id") long userId) {
        List<Address> addresses = addressService.findByUserId(userId);

        if (addresses.isEmpty()) {
            return new ResponseEntity<>("Empty", HttpStatus.NOT_FOUND);
        }

        List<AddressDto> addressesDtos = Converter.toAddressesDto(addresses);

        return new ResponseEntity<>(addressesDtos, HttpStatus.OK);
    }

    @PreAuthorize("@securityServiceImpl.hasPermissions(#userPrincipal, #userId)")
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addAddress(@AuthenticationPrincipal User userPrincipal,
                                        @PathVariable("user_id") long userId, @RequestBody AddressDto addressDto) {
        User user = userService.findOne(userId);

        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        Address existingAddress = addressService.findByNameAndUser(addressDto.getName(), user);

        if (existingAddress != null) {
            return new ResponseEntity<>(new ErrorResponseDto("internal", "Address with this name is already exists"),
                    HttpStatus.BAD_REQUEST);
        } else {
            Address address = Converter.toAddressEntity(addressDto);
            address.setUser(user);
            user.getAddresses().add(address);

            userService.saveOrUpdate(user);
            return new ResponseEntity<>(addressDto, HttpStatus.CREATED);
        }
    }

    @PreAuthorize("@securityServiceImpl.hasPermissions(#userPrincipal, #userId)")
    @RequestMapping(value = "/{address_id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAddress(@AuthenticationPrincipal User userPrincipal,
                                           @PathVariable("user_id") long userId,
                                           @PathVariable("address_id") long addressId) {
        Address address = addressService.findOne(addressId);

        if (address == null) {
            return new ResponseEntity<>("Address not found", HttpStatus.NOT_FOUND);
        }

        // todo add check for order status and if one of the orders has "In progress" status then don't delete address

        for (Order order : address.getOrders()) {
            order.setAddress(null);
        }

        addressService.delete(addressId);
        return new ResponseEntity<>(addressId, HttpStatus.NO_CONTENT);
    }

}
