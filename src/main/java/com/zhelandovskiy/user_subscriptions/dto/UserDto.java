package com.zhelandovskiy.user_subscriptions.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private List<SubscriptionDto> subscriptionList;
}
