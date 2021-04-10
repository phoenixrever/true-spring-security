package com.phoenixhell.order.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author phoenixhell
 * @since 2021/4/8 0008-下午 3:31
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @GetMapping("/teacher")
    @PreAuthorize("hasAnyAuthority('teacher')")
    public String r1(){
        return "permission granted";
    }
}

