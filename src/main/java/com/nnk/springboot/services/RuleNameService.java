package com.nnk.springboot.services;


import com.nnk.springboot.domain.RuleName;

import java.util.List;
import java.util.Optional;

public interface RuleNameService {
    RuleName createRuleName(RuleName ruleName);
    Optional<RuleName> getRuleNameById(Integer id);
    List<RuleName> getAllRuleName();
    RuleName updateRuleName(Integer id);
    RuleName addNewRuleName(Integer id, RuleName updatedRuleName);
    void deleteRuleName(Integer id);
}
