package com.example.petshop.admin.user;

import com.example.petshop.common.entity.Role;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DataJpaTest
//Annotation for a JPA test that focuses only on JPA components
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//Determines what type of existing DataSource bean can be replaced.
//@Rollback(value = false)
class RoleRepositoryTests {

    @Mock
    private RoleRepository repo;

    @Test
    void testCreateFirstRole(){

        Role roleAdmin = new Role("Admin", "manage everything");

        when(repo.save(roleAdmin)).thenReturn(roleAdmin);

        Role result = repo.save(roleAdmin);

        assertEquals(result, roleAdmin);
    }

    @Test
    void testCreateRestRoles(){
        Role roleSalesperson = new Role("Salesperson", "manage prices," +
                " customers, shipping, orders and sales report");
        Role roleEditor = new Role("Editor", "manage categories," +
                " brands, products, articles and menus");
        Role roleShipper = new Role("Shipper", "view products, view orders" +
                "and update order status");
        Role roleAssistant = new Role("Assistant", "manage questions and reviews" +
                "of products");

        List<Role> roles = Arrays.asList((new Role("Salesperson", "manage prices," +
                " customers, shipping, orders and sales report")), new Role("Editor", "manage categories," +
                " brands, products, articles and menus"),new Role("Shipper", "view products, view orders" +
                "and update order status"));

        when(repo.saveAll(List.of(roleEditor, roleAssistant, roleSalesperson, roleShipper))).thenReturn(roles);

        repo.saveAll(List.of(roleEditor, roleAssistant, roleSalesperson, roleShipper));

        assertEquals(roles, repo.saveAll(List.of(roleEditor, roleAssistant, roleSalesperson, roleShipper)));
    }
}
