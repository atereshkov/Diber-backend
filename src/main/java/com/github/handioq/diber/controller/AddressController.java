package com.github.handioq.diber.controller;

import com.github.handioq.diber.model.dto.AddressDto;
import com.github.handioq.diber.model.entity.Address;
import com.github.handioq.diber.model.entity.Order;
import com.github.handioq.diber.model.entity.User;
import com.github.handioq.diber.service.AddressService;
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

@RestController
@RequestMapping(Constants.API_URL + Constants.URL_ADDRESSES)
public class AddressController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressController.class);

    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getAddressById(@PathVariable("id") long id) {
        LOGGER.info("Start getAddressById with id: {}", id);
        Address address = addressService.findOne(id);

        if (address == null) {
            LOGGER.info("Address with id: {} is NULL", id);
            return new ResponseEntity<>("Address not found", HttpStatus.NOT_FOUND);
        }

        AddressDto addressDto = AddressDto.toDto(address);
        return new ResponseEntity<>(addressDto, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAddresses(Pageable pageable) {
        Page<Address> addresses = addressService.findAllByPage(pageable);
        Page<AddressDto> addressesDtos = addresses.map(AddressDto::toDto);
        return new ResponseEntity<>(addressesDtos, HttpStatus.OK);
    }

    @PreAuthorize("@securityServiceImpl.hasAdminPermissions(#userPrincipal)")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAddress(@AuthenticationPrincipal User userPrincipal,
                                           @PathVariable("id") long id) {
        LOGGER.info("Start deleteAddress");
        Address address = addressService.findOne(id);

        if (address == null) {
            LOGGER.error("Address with id {} is not found", id);
            return new ResponseEntity<>("Address not found", HttpStatus.NOT_FOUND);
        }

        // todo also maybe only set "disabled/deleted" property to true and doesn't show to user instead of deleting
        // todo add check for order status and if one of the orders has "In progress" status then don't delete address

        for (Order order : address.getOrders()) {
            //order.setAddressFrom(null);
            // todo
        }

        addressService.delete(id);
        return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
    }

}
