package com.zhelandovskiy.user_subscriptions.controller;

import com.zhelandovskiy.user_subscriptions.dto.SubscriptionTopDto;
import com.zhelandovskiy.user_subscriptions.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/top")
    public List<SubscriptionTopDto> getTop() {
        return subscriptionService.getTop();
    }
}
