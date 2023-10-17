package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testViewHomePage() throws Exception {
        List<User> userList = new ArrayList<>();
        User users = new User(1, "u1", "p1", "1234", "Admin");
        userList.add(users);

        when(userService.getAllUser()).thenReturn(userList);

        mvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(view().name("indexUser"))
                .andExpect(model().attribute("listUsers", userList));

        verify(userService, times(1)).getAllUser();
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void testCreateUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setFullName("JohnDoe");
        userDto.setRole("ADMIN");
        userDto.setPassword("Password1@1112ha");
        userDto.setUsername("john");

        doNothing().when(userService).createUser(any(User.class));


        mvc.perform(post("/saveUser")
                        .with(csrf())
                        .param("fullName", userDto.getFullName())
                        .param("username", userDto.getUsername())
                        .param("password", userDto.getPassword())
                        .param("role", userDto.getRole()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user"));
    }



    @Test
    @WithMockUser(roles = "ADMIN")
    public void showFormForUserUpdateWhenUserExistsReturnsUpdateUserView() throws Exception {
        Integer id = 1;
        User existingUser = new User();
        existingUser.setFullName("existingUser");
        when(userService.findById(id)).thenReturn(Optional.of(existingUser));

        mvc.perform(get("/showFormForUpdate/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("updateUser"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateUserWhenUserDtoValidReturnsRedirectUserView() throws Exception {
        Integer id = 1;
        User updatedUser = new User();
        updatedUser.setFullName("updatedUser");
        when(userService.addNewUser(any(Integer.class), any(User.class))).thenReturn(updatedUser);

        mvc.perform(post("/updateUser/{id}", id)
                        .with(csrf())
                        .param("fullName", "jade")
                        .param("username", "updatedUser")
                        .param("password", "Password1@")
                        .param("role", "USER"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/user"));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(anyInt());

        mvc.perform(get("/deleteUser/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user"));
    }

}
