package com.nnk.springboot.controllers;

import com.nnk.springboot.services.UserService;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class UserController {
   @Autowired
    private UserService userService;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user")
    public String viewHomePage(Model model) {
        model.addAttribute("listUsers", userService.getAllUser());
        return "indexUser";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/showNewUserForm")
    public String showNewUserForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "newUser";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/saveUser")
    public String saveUser(@Valid @ModelAttribute("userDto") UserDto userDto,
                           BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "newUser";
        }

        try {
            User users = new User();
            users.setFullName(userDto.getFullName());
            users.setRole(userDto.getRole());
            users.setUsername(userDto.getUsername());
            users.setPassword(userDto.getPassword());

            userService.createUser(users);

            return "redirect:/user";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "newUser";
        }
    }

   @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUserUpdate(@PathVariable(value = "id") Integer id, Model model) {
        try {
            Optional<User> userOpt = userService.findById(id);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                model.addAttribute("userDto", user);
                return "updateUser";
            } else {
                return "redirect:/user";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "redirect:/user";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/updateUser/{id}")
    public String updateUser(@PathVariable(value = "id") Integer id, @Valid @ModelAttribute("userDto") UserDto userDto, BindingResult result) {
        if (result.hasErrors()) {
            return "updateUser";
        }
        try {
            User updatedUser = new User();
            updatedUser.setFullName(userDto.getFullName());
            updatedUser.setRole(userDto.getRole());
            updatedUser.setUsername(userDto.getUsername());
            updatedUser.setPassword(userDto.getPassword());
            userService.addNewUser(id, updatedUser);
            return "redirect:/user";
        } catch (Exception exception) {
            result.rejectValue("username", "", "error : " + exception.getMessage());
            return "updateUser";
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/deleteUser/{id}")
    public String deleteEmployee(@PathVariable(value = "id") Integer id) {
        this.userService.deleteUser(id);
        return "redirect:/user";
    }
}
