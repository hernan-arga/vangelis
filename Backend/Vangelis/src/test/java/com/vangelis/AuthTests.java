package com.vangelis;

import com.vangelis.controller.UserController;
import com.vangelis.domain.User;
import com.vangelis.doms.UserDom;
import com.vangelis.repository.GenreRepository;
import com.vangelis.repository.InstrumentRepository;
import com.vangelis.repository.UserRepository;
import com.vangelis.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.doReturn;

public class AuthTests
{
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final InstrumentRepository instrumentRepository = Mockito.mock(InstrumentRepository.class);
    private final GenreRepository genreRepository = Mockito.mock(GenreRepository.class);
    private final UserService userService = new UserService(userRepository, instrumentRepository, genreRepository);

    @InjectMocks
    private UserController userController;
    
    @Test
    @WithAnonymousUser
    public void validUsername()
    {
        String validUsername = "SecureUserTest";

        Boolean status = userService.validateUsername(validUsername);
        Assertions.assertTrue(status);
    }

    @Test
    @WithAnonymousUser
    public void invalidUsername()
    {
        String invalidUsername = "Secure__UserTest";

        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> userService.validateUsername(invalidUsername));

        Assertions.assertEquals("Invalid username", exception.getMessage());
    }

    @Test
    @WithAnonymousUser
    public void duplicatedUsername()
    {
        String invalidUsername = "SecureUserTest";

        Optional<User> user = Optional.of(new User("SecureUserTest", "password", "test_email@gmail.com"));
        doReturn(user).when(userRepository).findByUserName("SecureUserTest");

        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> userService.validateUsername(invalidUsername));

        Assertions.assertEquals("Username already exists", exception.getMessage());
    }

    @Test
    @WithAnonymousUser
    public void validPassword()
    {
        String validPassword = "ValidPassword_123";

        Boolean status = userService.validatePassword(validPassword);

        Assertions.assertTrue(status);
    }

    @Test
    @WithAnonymousUser
    public void invalidPassword()
    {
        String invalidPassword = "invalidpassword";

        Boolean status = userService.validatePassword(invalidPassword);

        Assertions.assertFalse(status);
    }

    @Test
    @WithAnonymousUser
    public void createUserWithInvalidPassword()
    {
        UserDom newUser = new UserDom("SecureUserTest", "invalidpassword", "test_email@gmail.com");

        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> userService.createUser(newUser));

        Assertions.assertEquals("Password does not comply with security standards", exception.getMessage());
    }

    @Test
    @WithAnonymousUser
    public void createValidUser()
    {
        UserDom newUser = new UserDom("SecureUserTest", "ValidPassword_123", "test_email@gmail.com");

        Assertions.assertEquals(User.class, userService.createUser(newUser).getClass());
    }
}
