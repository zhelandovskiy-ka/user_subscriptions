package com.zhelandovskiy.user_subscriptions.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SubscriptionCreateDto {

    @NotBlank(message = "Code must not be null")
    @Size(min = 2, max = 20, message = "Code size has between 2 and 20")
    private String code;

    @NotBlank(message = "Name must not be null")
    @Size(min = 2, max = 20, message = "Name size has between 2 and 20")
    private String name;
}
