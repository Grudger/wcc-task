package com.wcc.assessment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class UserCredentialsTest {


    @Test
    public void encodePassword() {

        String password = "myPassword";
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        System.out.println("Encoded password is :" + encodedPassword);

    }

}
