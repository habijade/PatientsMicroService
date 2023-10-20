package com.nnk.springboot.services.impl;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.services.RuleNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RuleNameServiceImpl implements RuleNameService {

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @Override
    public RuleName createRuleName(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    @Override
    public Optional<RuleName> getRuleNameById(Integer id) {
        return ruleNameRepository.findById(id);
    }

    @Override
    public List<RuleName> getAllRuleName() {
        return ruleNameRepository.findAll();
    }

    @Override
    public RuleName updateRuleName(Integer id) {
        return ruleNameRepository.findById(id).get();

    }

    @Override
    public RuleName addNewRuleName(Integer id, RuleName updatedRuleName) {
        Optional<RuleName> existingRuleNameOpt = ruleNameRepository.findById(id);
        if (existingRuleNameOpt.isPresent()) {
            RuleName existingRuleName = existingRuleNameOpt.get();
            existingRuleName.setName(updatedRuleName.getName());
            existingRuleName.setJson(updatedRuleName.getJson());
            existingRuleName.setDescription(updatedRuleName.getDescription());
            existingRuleName.setTemplate(updatedRuleName.getTemplate());
            existingRuleName.setSqlPart(updatedRuleName.getSqlPart());
            existingRuleName.setSqlStr(updatedRuleName.getSqlStr());
            return ruleNameRepository.save(existingRuleName);
        } else {
            throw new RuntimeException("RuleName not found with id " + id);
        }
    }

    @Override
    public void deleteRuleName(Integer id) {
        ruleNameRepository.deleteById(id);
    }
}
