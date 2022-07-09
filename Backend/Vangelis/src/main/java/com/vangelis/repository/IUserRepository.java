package com.vangelis.repository;

import com.vangelis.domain.Employee;
import com.vangelis.domain.User;

public interface IUserRepository {
    User prueba() throws Exception;
    Employee SaveEmployee();
}
