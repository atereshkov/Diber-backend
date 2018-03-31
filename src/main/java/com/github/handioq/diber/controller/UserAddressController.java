package com.github.handioq.diber.controller;

import com.github.handioq.diber.model.dto.AddressDto;
import com.github.handioq.diber.model.dto.ErrorResponseDto;
import com.github.handioq.diber.model.entity.Address;
import com.github.handioq.diber.model.entity.Order;
import com.github.handioq.diber.model.entity.User;
import com.github.handioq.diber.service.AddressService;
import com.github.handioq.diber.service.UserService;
import com.github.handioq.diber.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    private final UserService userService;
    private final AddressService addressService;

    @Autowired
    public UserAddressController(UserService userService, AddressService addressService) {
        this.userService = userService;
        this.addressService = addressService;
    }

    @PreAuthorize("@securityServiceImpl.hasPermissions(#userPrincipal, #userId)")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAddresses(@AuthenticationPrincipal User userPrincipal,
                                          @PathVariable("user_id") long userId, Pageable pageable) {

        LOGGER.info("Start getAddresses user_id: {}", userId);
        Page<Address> addresses = addressService.findByUserId(userId, pageable);
        Page<AddressDto> addressesDtos = addresses.map(AddressDto::toDto);
        return new ResponseEntity<>(addressesDtos, HttpStatus.OK);
    }

    @PreAuthorize("@securityServiceImpl.hasPermissions(#userPrincipal, #userId)")
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> addAddress(@AuthenticationPrincipal User userPrincipal,
                                        @PathVariable("user_id") long userId, @RequestBody AddressDto addressDto) {
        LOGGER.info("Start addAddress user_id: {}", userId);
        User user = userService.findOne(userId);

        if (user == null) {
            LOGGER.error("User with id {} is not found", userId);
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        Address existingAddress = addressService.findByNameAndUser(addressDto.getName(), user);

        if (existingAddress == null) {
            Address address = Address.toEntity(addressDto);
            address.setUser(user);
            user.getAddresses().add(address);

            userService.saveOrUpdate(user);
            LOGGER.info("new address with name {} and user id {} is successfully created", address.getName(), user.getId());
            return new ResponseEntity<>(addressDto, HttpStatus.CREATED);
        } else {
            LOGGER.info("Address with name {} is already exists", existingAddress.getName());
            ErrorResponseDto error = new ErrorResponseDto("internal", "Address with this name is already exists");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("@securityServiceImpl.hasPermissions(#userPrincipal, #userId)")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> editAddress(@AuthenticationPrincipal User userPrincipal,
                                         @PathVariable("id") long id,
                                         @PathVariable("user_id") long userId,
                                         @RequestBody AddressDto addressDto) {
        Address address = addressService.findOne(id);

        if (address == null) {
            ErrorResponseDto error = new ErrorResponseDto("Not found", "Order not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        address.setCity(addressDto.getCity());
        address.setCountry(addressDto.getCountry());
        address.setAddress(addressDto.getAddress());
        address.setName(addressDto.getName());
        address.setPhone(addressDto.getPhone());
        address.setRegion(addressDto.getRegion());
        address.setPostalCode(addressDto.getPostalCode());
        //address.setLatitude(addressDto.getLatitude());
        //address.setLongitude(addressDto.getLongitude());

        addressService.saveOrUpdate(address);
        return new ResponseEntity<>(AddressDto.toDto(address), HttpStatus.OK);
    }

    @PreAuthorize("@securityServiceImpl.hasPermissions(#userPrincipal, #userId)")
    @RequestMapping(value = "/{address_id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAddress(@AuthenticationPrincipal User userPrincipal,
                                           @PathVariable("user_id") long userId,
                                           @PathVariable("address_id") long addressId) {
        LOGGER.info("Start deleteAddress addressId: {}", addressId);
        Address address = addressService.findOne(addressId);

        if (address == null) {
            LOGGER.error("Address with id {} is not found", addressId);
            return new ResponseEntity<>("Address not found", HttpStatus.NOT_FOUND);
        }

        // todo add check for order status and if one of the orders has "In progress" status then don't delete address

        for (Order order : address.getOrders()) {
            order.setAddressFrom(null);
        }

        addressService.delete(addressId);
        return new ResponseEntity<>(addressId, HttpStatus.NO_CONTENT);
    }

}
