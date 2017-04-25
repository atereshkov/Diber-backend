package com.github.handioq.diber.utils;

import com.github.handioq.diber.model.dto.*;
import com.github.handioq.diber.model.entity.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Converter {

    public static User toUserEntity(UserDto userDto) {
        return new User(userDto.getEmail(), userDto.getUsername(),
                userDto.getPassword(), userDto.isEnabled(), userDto.getFullname());
        // todo complete this
    }

    public static Order toOrderEntity(OrderDto orderDto) {
        //Shop shop = toShopEntity(orderDto.getShop());
        Date date = DateUtil.getFromString(orderDto.getDate(), "yyyy-MM-dd HH:mm:ss");

        return new Order(date, orderDto.getDescription(), orderDto.getPrice(), orderDto.getStatus(), null, null);
    }

    public static Request toRequestEntity(RequestDto requestDto) {
        return new Request(); // todo implement
    }

    public static Address toAddressEntity(AddressDto addressDto) {
        return new Address(addressDto.getName(), addressDto.getPostalCode(), addressDto.getCountry(),
                addressDto.getCity(), addressDto.getRegion(), addressDto.getAddress(),
                addressDto.getPhone());
    }

    public static Shop toShopEntity(ShopDto shopDto) {
        return new Shop(shopDto.getName(), shopDto.getAddress());
    }

    public static OrderDto toOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();

        if (order.getShop() != null) {
            orderDto.setShop(toShopDto(order.getShop()));
        }

        if (order.getAddress() != null) {
            orderDto.setAddress(toAddressDto(order.getAddress()));
        }

        if (order.getCourier() != null) {
            orderDto.setCourier(toUserDto(order.getCourier()));
        }

        orderDto.setId(order.getId());
        orderDto.setDate(order.getDate().toString());
        orderDto.setDescription(order.getDescription());
        orderDto.setPrice(order.getPrice());
        orderDto.setStatus(order.getStatus());
        orderDto.setCustomer(toUserDto(order.getUser()));

        return orderDto;
    }

    public static AddressDto toAddressDto(Address address) {
        return new AddressDto(address.getId(), address.getName(), address.getPostalCode(), address.getCountry(),
                address.getCity(), address.getRegion(), address.getAddress(), address.getPhone());
    }

    public static ShopDto toShopDto(Shop shop) {
        return new ShopDto(shop.getId(), shop.getName(), shop.getAddress());
    }

    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getUsername(), user.getPassword(),
                user.isEnabled(), user.getFullname());
    }

    public static ReviewDto toReviewDto(Review review) {
        return new ReviewDto(review.getReview(), review.getRating(), toUserDto(review.getUser()));
    }

    public static List<ReviewDto> toReviewsDto(List<Review> reviews) {
        List<ReviewDto> reviewsDto = new ArrayList<>();

        for (Review review : reviews) {
            reviewsDto.add(toReviewDto(review));
        }

        return reviewsDto;
    }

    public static List<ShopDto> toShopsDto(List<Shop> shops) {
        List<ShopDto> shopDtos = new ArrayList<>();

        for (Shop shop : shops) {
            shopDtos.add(toShopDto(shop));
        }

        return shopDtos;
    }

    public static List<AddressDto> toAddressesDto(List<Address> addresses) {
        List<AddressDto> addressDtos = new ArrayList<>();

        for (Address address : addresses) {
            addressDtos.add(toAddressDto(address));
        }

        return addressDtos;
    }


    public static List<OrderDto> toOrdersDto(List<Order> orders) {
        List<OrderDto> ordersDtos = new ArrayList<>();

        for (Order order : orders) {
            ordersDtos.add(toOrderDto(order));
        }

        return ordersDtos;
    }

    private static RequestDto toRequestDto(Request request) {
        return new RequestDto(request.getId(), toOrderDto(request.getOrder()), toUserDto(request.getCourier()));
    }

    public static List<RequestDto> toRequestsDto(List<Request> requests) {
        List<RequestDto> requestsDtos = new ArrayList<>();

        for (Request request : requests) {
            requestsDtos.add(toRequestDto(request));
        }

        return requestsDtos;
    }

}