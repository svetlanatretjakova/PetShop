package com.example.petshop.admin.user;

import com.example.petshop.common.entity.Role;
import com.example.petshop.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUserWithOneRole() {
        Role roleEditor = entityManager.find(Role.class, 1);
        User userSveta = new User("sveta@gmail.com", "sveta2020", "Sveta", "Tretjakova");
        userSveta.addRole(roleEditor);

        User savedUser = repo.save(userSveta);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateNewUserWithTwoRoles(){
        User userNikita = new User("nikita@gmail.com", "nikita2020", "Nikita", "Zarins");
        Role roleAssistant = new Role(2);
        Role roleSalesperson = new Role(3);

        userNikita.addRole(roleAssistant);
        userNikita.addRole(roleSalesperson);

        User savedUser = repo.save(userNikita);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers(){
       Iterable<User> listUsers =  repo.findAll();
       listUsers.forEach(user -> System.out.println(user));

    }

    @Test
    public void testGetUserById() {
         User userSveta = repo.findById(1).get();
         System.out.println(userSveta);
         assertThat(userSveta).isNotNull();
    }

    @Test
    public void testUpdateUserDetails() {
        User userSveta = repo.findById(1).get();
        userSveta.setEnabled(true);
        userSveta.setEmail("sveta.tretjakova@gmail.com");

        repo.save(userSveta);
    }

    @Test
    public void testUpdateUserRoles() {
        User userNikita = repo.findById(2).get();
        Role roleAssistant = new Role(2);
        Role roleShipper = new Role(4);

        userNikita.getRoles().remove(roleAssistant);
        userNikita.addRole(roleShipper);

        repo.save(userNikita);
    }

    @Test
    public void testDeleteUser() {
        Integer userId = 2;

        repo.deleteById(userId);
    }
}
