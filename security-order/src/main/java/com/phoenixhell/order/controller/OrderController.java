package com.phoenixhell.order.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author phoenixhell
 * @since 2021/4/8 0008-下午 3:31
 */
@RestController
public class OrderController {
    @GetMapping("/r1")
    @PreAuthorize(value = "hasAuthority('p1')")
    public String r1(){
        return "permission granted";
    }
}
