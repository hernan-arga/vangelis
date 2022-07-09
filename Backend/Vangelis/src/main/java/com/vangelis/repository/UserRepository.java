package com.vangelis.repository;

import com.vangelis.domain.Employee;
import com.vangelis.domain.User;
import com.vangelis.utils.DBServiceEM;
import com.vangelis.utils.IDBService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserRepository implements IUserRepository {
    private IDBService dbService;
    public UserRepository() {
        this.dbService = new DBServiceEM();
    }

    public User prueba() throws Exception {
    return null;
    }

    public Employee SaveEmployee(){
        Employee emp = new Employee("Zara", "Ali", 2000);
        //User user = new User("a", "b", "c");
        this.dbService.Save(emp);

        return emp;
    }

}
