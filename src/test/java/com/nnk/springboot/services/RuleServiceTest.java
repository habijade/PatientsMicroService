package com.nnk.springboot.services;

import com.nnk.springboot.services.impl.RuleNameServiceImpl;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RuleServiceTest {

    @InjectMocks
    private RuleNameServiceImpl ruleNameServiceImpl;

    @Mock
    private RuleNameRepository ruleNameRepository;


    @Captor
    private ArgumentCaptor<RuleName> RuleNameArgumentCaptor;

    private RuleName createRuleName(int id) {
        return new RuleName("name", "description1", "json1", "template1", "sqlStr1", "sqlPart1");
    }


    @Test
    public void testGetRuleNameById() {
        Integer id = 1;
        RuleName expectedRuleName = createRuleName(id);
        when(ruleNameRepository.findById(id)).thenReturn(Optional.of(expectedRuleName));
        Optional<RuleName> result = ruleNameServiceImpl.getRuleNameById(id);
        verify(ruleNameRepository).findById(id);
        assertTrue(result.isPresent());
        assertEquals(expectedRuleName, result.get());
    }

    @Test
    public void testNotAddNewRuleName() {
        Integer id = 1;
        RuleName updateRuleName = createRuleName(id);

        when(ruleNameRepository.findById(id)).thenReturn(Optional.empty());

        Executable executable = () -> ruleNameServiceImpl.addNewRuleName(id, updateRuleName);
        assertThrows(RuntimeException.class, executable, "RuleName not found with id " + id);
        verify(ruleNameRepository).findById(id);
        verifyNoMoreInteractions(ruleNameRepository);
    }


    @Test
    public void testAddNewRuleName() {
        Integer id = 1;
        RuleName updateRuleName = createRuleName(id);
        when(ruleNameRepository.findById(id)).thenReturn(Optional.of(updateRuleName));

        RuleName result = ruleNameServiceImpl.addNewRuleName(id, updateRuleName);
        verify(ruleNameRepository).findById(1);
        verify(ruleNameRepository).save(RuleNameArgumentCaptor.capture());
    }

    @Test
    public void testSaveRuleName() {
        RuleName ruleName = createRuleName(1);

        ruleNameServiceImpl.createRuleName(ruleName);

        verify(ruleNameRepository).save(RuleNameArgumentCaptor.capture());
        RuleName capturedRuleName = RuleNameArgumentCaptor.getValue();
        assertThat(capturedRuleName).isEqualTo(ruleName);
    }

    @Test
    public void testListAllRuleName() {
        List<RuleName> expectedRuleName = new ArrayList<>();
        expectedRuleName.add(createRuleName(1));
        expectedRuleName.add(createRuleName(2));

        when(ruleNameRepository.findAll()).thenReturn(expectedRuleName);

        List<RuleName> actualUsers = ruleNameServiceImpl.getAllRuleName();

        verify(ruleNameRepository).findAll();
        assertThat(actualUsers).isEqualTo(expectedRuleName);
    }

    @Test
    public void testUpdateRuleName() {
        Integer id = 1;
        RuleName expectedRuleName = createRuleName(id);
        Optional<RuleName> expectedOptional = Optional.of(expectedRuleName);

        when(ruleNameRepository.findById(id)).thenReturn(expectedOptional);
        RuleName actualRuleName = ruleNameServiceImpl.updateRuleName(id);

        verify(ruleNameRepository).findById(id);
        assertThat(actualRuleName).isEqualTo(expectedRuleName);
    }

    @Test
    public void testDelete() {

        RuleName expectedRuleName = createRuleName(1);

        ruleNameServiceImpl.deleteRuleName(expectedRuleName.getId());

        verify(ruleNameRepository, times(1)).deleteById(expectedRuleName.getId());

    }
}
