package com.zhelandovskiy.user_subscriptions.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateUpdateDto {

    @NotBlank(message = "Name must not be null")
    @Size(min = 2, max = 20, message = "Name size has between 2 and 20")
    private String name;

    @Size(min = 6, max = 255, message = "Email size has between 6 and 255")
    @Email(message = "Email has not valid format")
    private String email;
}
