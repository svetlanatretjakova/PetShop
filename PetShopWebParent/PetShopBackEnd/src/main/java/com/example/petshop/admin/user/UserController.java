package com.example.petshop.admin.user;

import com.example.petshop.common.entity.Role;
import com.example.petshop.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    private static final String MESSAGE = "message";
    private static final String REDIRECT_USERS = "redirect:/users";

    @GetMapping("/users")
    public String listAll(Model model) {
        List<User> listUsers = service.listAll();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        List<Role> listRoles = service.listRoles();

        User user = new User();
        user.setEnabled(true);

        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("pageTitle", "Create new user");

        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes redirectAttributes) {
        service.save(user);

        redirectAttributes.addFlashAttribute(MESSAGE, "The user has been saved successfully.");
        return REDIRECT_USERS;
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable(name= "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        try {
            User user = service.get(id);
            List<Role> listRoles = service.listRoles();

            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Edit user (ID: " + id + ")");
            model.addAttribute("listRoles", listRoles);

            return "user_form";
        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute(MESSAGE, ex.getMessage());
            return REDIRECT_USERS;
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(name= "id") Integer id,
                             RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute(MESSAGE,
                    "The user ID " + id + " had been deleted successfully");
        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute(MESSAGE, ex.getMessage());
        }
        return REDIRECT_USERS;
    }

    @GetMapping("/users/{id}/enabled/{status}")
    public String updateUserEnabledStatus(@PathVariable("id") Integer id,
                                          @PathVariable("status") boolean enabled,
                                          RedirectAttributes redirectAttributes) {
        service.updateUserEnabledStatus(id, enabled);
        String status = enabled ? "enabled" : "disabled";
        String alert = "The user ID " + id + " has been " + status;
        redirectAttributes.addFlashAttribute(MESSAGE, alert);

        return REDIRECT_USERS;
    }
}

