package com.vangelis;

import com.vangelis.domain.Instrument;
import com.vangelis.domain.User;
import com.vangelis.repository.InstrumentReporitory;
import com.vangelis.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

@SpringBootTest
class VangelisApplicationTests
{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private InstrumentReporitory instrumentReporitory;

	@Test
	void contextLoads() {
	}
}
