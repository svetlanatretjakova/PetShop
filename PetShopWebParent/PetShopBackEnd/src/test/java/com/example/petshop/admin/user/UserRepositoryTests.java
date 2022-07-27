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
class UserRepositoryTests {
    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testCreateUserWithOneRole() {
        Role roleEditor = entityManager.find(Role.class, 1);
        User userSveta = new User("katya@gmail.com", "sveta2020", "Katya", "Tretjakova");
        userSveta.addRole(roleEditor);

        User savedUser = repo.save(userSveta);
        assertThat(savedUser.getId()).isPositive();
    }

    @Test
    void testCreateNewUserWithTwoRoles(){
        User userNikita = new User("nikita@gmail.com", "nikita2020", "Nikita", "Zarins");
        Role roleAssistant = new Role(2);
        Role roleSalesperson = new Role(3);

        userNikita.addRole(roleAssistant);
        userNikita.addRole(roleSalesperson);

        User savedUser = repo.save(userNikita);
        assertThat(savedUser.getId()).isPositive();
    }

    @Test
    void testListAllUsers(){
       Iterable<User> listUsers =  repo.findAll();
       listUsers.forEach(user -> System.out.println(user));

    }

    @Test
    void testGetUserById() {
         User userSveta = repo.findById(1).get();
         System.out.println(userSveta);
         assertThat(userSveta).isNotNull();
    }

    @Test
    void testUpdateUserDetails() {
        User userSveta = repo.findById(1).get();
        userSveta.setEnabled(true);
        userSveta.setEmail("sveta.tretjakova@gmail.com");

        repo.save(userSveta);
    }

    @Test
    void testUpdateUserRoles() {
        User userNikita = repo.findById(2).get();
        Role roleAssistant = new Role(2);
        Role roleShipper = new Role(4);

        userNikita.getRoles().remove(roleAssistant);
        userNikita.addRole(roleShipper);

        repo.save(userNikita);
    }

    @Test
    void testDeleteUser() {
        Integer userId = 2;

        repo.deleteById(userId);
    }

    @Test
    void testGetUserByEmail() {
        String email = "sveta.tretjakova@gmail.com";
        User user = repo.getUserByEmail(email);

        assertThat(user).isNotNull();
    }

    @Test
    void testCountById() {
        Integer id = 1;
        Long countById = repo.countById(id);

        assertThat(countById).isNotNull().isPositive();
    }

    @Test
    void testDisableUser() {
        Integer id = 1;
        repo.updateEnabledStatus(id, false);
    }

    @Test
    void testEnableUser() {
        Integer id = 16;
        repo.updateEnabledStatus(id, true);
    }

}
