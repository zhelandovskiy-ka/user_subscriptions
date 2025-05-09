package com.zhelandovskiy.user_subscriptions.controller;

import com.zhelandovskiy.user_subscriptions.dto.SubscriptionCreateDto;
import com.zhelandovskiy.user_subscriptions.dto.UserCreateUpdateDto;
import com.zhelandovskiy.user_subscriptions.dto.UserDto;
import com.zhelandovskiy.user_subscriptions.dto.UserNewDto;
import com.zhelandovskiy.user_subscriptions.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) {

        log.info("Receive user by id: {}", id);
        return userService.getById(id);
    }

    @PostMapping
    public UserNewDto create(@RequestBody @Valid UserCreateUpdateDto dto) {

        log.info("Create user, email: {}", dto.getEmail());
        return userService.create(dto);
    }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @RequestBody @Valid UserCreateUpdateDto dto) {

        log.info("Update user by id: {}", id);
        return userService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        log.info("Delete user by id: {}", id);
        userService.delete(id);
    }

    @PostMapping("/{id}/subscriptions")
    public UserDto addSubscriptions(@PathVariable Long id, @RequestBody @Valid SubscriptionCreateDto dto) {

        log.info("Add new subscription to user id: {}, subscription: {}", id, dto.getCode());
        return userService.addSubscription(id, dto);
    }

    @DeleteMapping("/{id}/subscriptions")
    public UserDto addSubscriptions(@PathVariable Long id, @RequestParam String code) {

        log.info("Remove subscription from user id: {}, subscription: {}", id, code);
        return userService.removeSubscription(id, code);
    }
}
