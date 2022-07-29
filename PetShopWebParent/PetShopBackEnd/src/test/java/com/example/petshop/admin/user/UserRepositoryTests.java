package com.example.petshop.admin.user;

import com.example.petshop.common.entity.Role;
import com.example.petshop.common.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.assertEquals;

@DataJpaTest
//Annotation for a JPA test that focuses only on JPA components.
//Using this annotation will disable full auto-configuration and instead apply only configuration relevant to JPA tests
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //Determines what type of existing DataSource bean can be replaced.
//Annotation that can be applied to a test class to configure
// a test database to use instead of the application-defined
// or auto-configured DataSource.
//@Rollback(false)
//test annotation that is used to indicate
// whether a test-managed transaction should be rolled back after the test method has completed.
class UserRepositoryTests {

    @Mock
    private UserRepository repo;

    @Test
    void testCreateUserWithOneRole() {
        User userSveta = new User(1, "katya@gmail.com", "sveta2020", "Katya", "Tretjakova");
        Role roleEditor = new Role(2);

        userSveta.addRole(roleEditor);
                        //Allow flexible verification or stubbing
        when(repo.save(ArgumentMatchers.any(User.class))).thenReturn(userSveta);

        User savedUser = repo.save(userSveta);

        assertThat(savedUser.getId()).isPositive();

        verify(repo).save(userSveta);
    }

    @Test
    void testCreateNewUserWithTwoRoles() {
        User userSveta = new User(1, "katya@gmail.com", "sveta2020", "Katya", "Tretjakova");
        Role roleEditor = new Role(2);
        Role roleSalesperson = new Role(3);
                        //Allow flexible verification or stubbing
        when(repo.save(ArgumentMatchers.any(User.class))).thenReturn(userSveta);

        userSveta.addRole(roleEditor);
        userSveta.addRole(roleSalesperson);

        User savedUser = repo.save(userSveta);

        assertThat(savedUser.getId()).isPositive();

    }

    @Test
    void testListAllUsers() {

        ArrayList users = new ArrayList();
        users.add(new User(1,"sveta@gmail.com", "password", "Sveta", "Tretjakova"));

        when(repo.findAll()).thenReturn(users);

        List<User> expected = (List<User>) repo.findAll();

        assertEquals(expected, users);
        verify(repo).findAll();
    }

    @Test
    void testGetUserById() {
        Optional<User> user = Optional.of(new User(1, "sveta@gmail.com", "password", "Sveta", "Tretjakova"));
        when(repo.findById(1)).thenReturn(user);

        Optional<User> expected = Optional.of(repo.findById(1).get());

        assertEquals(user, expected);

        verify(repo).findById(1);
    }

    @Test
    void testUpdateUserDetails() {
        User personToUpdate = new User(1, "sveta@gmail.com", "password", "Sveta", "Tretjakova");
        when(repo.findById(1)).thenReturn(Optional.of(personToUpdate));

        User foundPerson = repo.findById(1).get();
        foundPerson.setFirstName("Svetlana");

        assertThat(repo.findById(1)).map(User::getFirstName).contains(foundPerson.getFirstName());
    }

    //I don't know how to fix this
  /*  @Test
    void testUpdateUserRoles() {
        Optional<User> user = Optional.of(new User(1, "sveta@gmail.com", "password", "Sveta", "Tretjakova"));
        Optional<User> updatedUser = Optional.of(new User(2, "sveta@gmail.com", "password", "Sveta", "Tretjakova"));

        when(repo.findById(1)).thenReturn(user);

        User userSveta = repo.findById(1).get();

        Role roleAdmin = new Role(1);
        Role roleShipper = new Role(4);

        userSveta.getRoles().remove(roleAdmin);
        userSveta.addRole(roleShipper);

        repo.save(userSveta);

        assertEquals(user, updatedUser);
----------------------------------------------
        /*void testUpdateUserRoles() {

            User userNikita = repo.findById(2).get();
            Role roleAssistant = new Role(2);
            Role roleShipper = new Role(4);

            userNikita.getRoles().remove(roleAssistant);
            userNikita.addRole(roleShipper);

            repo.save(userNikita);
        }
        */

    @Test
    void testDeleteUser() {
        ArrayList users = new ArrayList();
        users.add(new User(1,"sveta@gmail.com", "password", "Sveta", "Tretjakova"));
        doNothing().when(repo).deleteById(1);

        repo.deleteById(1);

        verify(repo).deleteById(1);
    }

    @Test
    void testGetUserByEmail() {
        User user = new User(1, "sveta@gmail.com", "password", "Sveta", "Tretjakova");

        when(repo.getUserByEmail("sveta@gmail.com")).thenReturn(user);

        User expected = repo.getUserByEmail("sveta@gmail.com");

        assertEquals(user, expected);

        verify(repo).getUserByEmail("sveta@gmail.com");
    }

    /*@Test
    void testCountById() {
        Integer id = 1;
        Long countById = repo.countById(id);

        assertThat(countById).isNotNull().isPositive();
    }
*/

   @Test
    void testDisableUser() {
        Integer id = 1;
        repo.updateEnabledStatus(id, false);

        verify(repo).updateEnabledStatus(id, false);
    }

    @Test
    void testEnableUser() {
        Integer id = 16;
        repo.updateEnabledStatus(id, true);

        verify(repo).updateEnabledStatus(id, true);
    }
}
