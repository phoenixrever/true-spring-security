package com.phoenixhell.securityUaa;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author phoenixhell
 * @since 2021/4/9 0009-上午 11:36
 */

public class Password {
    @Test
    public void test(){
        String secret = new BCryptPasswordEncoder().encode("secret");
        System.out.println(secret);
    }
}
