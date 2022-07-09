package com.vangelis;

import com.vangelis.domain.Employee;
import com.vangelis.utils.DBServiceEM;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VangelisApplication {

    public VangelisApplication() {
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(VangelisApplication.class, args);
    }

}
