package com.example.smart_home_web;

import com.example.smart_home_web.enums.GenderEnum;
import com.example.smart_home_web.enums.RoleEnum;
import com.example.smart_home_web.pojo.User;
import com.example.smart_home_web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Slf4j
@SpringBootTest
class SmartHomeWebApplicationTests {
    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("333"));
        user.setRole(RoleEnum.ADMIN);
        user.setEmail("admin@123.com");
        user.setNickname("管理员");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        log.info("保存：{}", userService.save(user));
    }
}
