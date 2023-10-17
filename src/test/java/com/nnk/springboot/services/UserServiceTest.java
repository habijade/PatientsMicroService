package com.nnk.springboot.services;

import com.nnk.springboot.services.impl.UserServiceImpl;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userServiceImpl;
    @Mock
    private UserRepository userRepository;
    @Captor
    private ArgumentCaptor<User> UserArgumentCaptor;
    @Mock
    private PasswordEncoder passwordEncoder;

    private User createUser(int id) {
        return new User(1, "user1", "1234", "ADMIN", "ADMIN");
    }

    @Test
    public void testNotAddNewUser() {
        Integer id = 1;
        User updateUser = createUser(id);

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        Executable executable = () -> userServiceImpl.addNewUser(id, updateUser);
        assertThrows(RuntimeException.class, executable, "User not found with id " + id);
        verify(userRepository).findById(id);
        verifyNoMoreInteractions(userRepository);
    }
    
    @Test
    public void testGetUserById() {
        Integer id = 1;
        User expectedUser = createUser (id);
        when(userRepository.findById(id)).thenReturn(Optional.of(expectedUser));
        Optional<User> result = userServiceImpl.findById(id);
        verify(userRepository).findById(id);
        assertTrue(result.isPresent());
        assertEquals(expectedUser, result.get());
    }


    @Test
    public void testAddNewUser() {
        Integer id = 1;
        User updateUser = createUser(id);
        when(userRepository.findById(id)).thenReturn(Optional.of(updateUser));
        User result = userServiceImpl.addNewUser(id, updateUser);
        verify(userRepository).findById(1);
        verify(userRepository).save(UserArgumentCaptor.capture());
    }


    @Test
    public void testSaveUser() {
        User user = createUser(1);
        userServiceImpl.createUser(user);

        verify(userRepository).save(UserArgumentCaptor.capture());
        User capturedUser = UserArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);
    }

    @Test
    public void testListAllUsers() {
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(createUser(1));
        expectedUsers.add(createUser(2));

        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> actualUsers = userServiceImpl.getAllUser();

        verify(userRepository).findAll();
        assertThat(actualUsers).isEqualTo(expectedUsers);
    }

    @Test
    public void testFindUserByUsername() {
        String username = "user1";
        User expectedUser = createUser(1);
        Optional<User> expectedOptional = Optional.of(expectedUser);

        when(userRepository.findByUsername(username)).thenReturn(expectedOptional);

        Optional<User> actualOptional = userServiceImpl.findUserByUsername(username);

        verify(userRepository).findByUsername(username);
        assertThat(actualOptional).isEqualTo(expectedOptional);
    }

    @Test
    public void testUpdateUser() {
        Integer id = 1;
        User expectedUser = createUser(id);
        Optional<User> expectedOptional = Optional.of(expectedUser);

        when(userRepository.findById(id)).thenReturn(expectedOptional);

        User actualUser = userServiceImpl.updateUser(id);

        verify(userRepository).findById(id);
        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    public void testDeleteUser() {
        Integer id = 1;

        userServiceImpl.deleteUser(id);

        verify(userRepository, times(1)).deleteById(id);

    }
}
