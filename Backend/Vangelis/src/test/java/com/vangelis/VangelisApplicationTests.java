package com.vangelis;

import com.vangelis.repository.InstrumentRepository;
import com.vangelis.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VangelisApplicationTests
{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private InstrumentRepository instrumentRepository;

	@Test
	void contextLoads() {
	}
}
