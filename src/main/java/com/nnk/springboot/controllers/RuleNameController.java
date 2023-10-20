package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.dto.RuleNameDto;
import com.nnk.springboot.services.RuleNameService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class RuleNameController {

    @Autowired
    private RuleNameService ruleNameService;

    @GetMapping("/ruleNameHomePage")
    public String viewHomePage(Model model) {
        model.addAttribute("ruleNameList", ruleNameService.getAllRuleName());
        return "ruleNameIndex"; // Cela renvoie vers la vue "ratings" pour afficher les résultats
    }

    @GetMapping("/showNewRuleNameForm")
    public String showNewRuleNameForm(Model model) {
        RuleNameDto ruleNameDto = new RuleNameDto();
        model.addAttribute("ruleNameDto", ruleNameDto);
        return "newRuleName";
    }

    @PostMapping("/saveRuleName")
    public String saveRuleName(@Valid @ModelAttribute("ruleNameDto") RuleNameDto ruleNameDto,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "newRuleName";
        }

        try {
            RuleName ruleName = new RuleName();
            ruleName.setName(ruleNameDto.getName());
            ruleName.setJson(ruleNameDto.getJson());
            ruleName.setDescription(ruleNameDto.getDescription());
            ruleName.setTemplate(ruleNameDto.getTemplate());
            ruleName.setSqlPart(ruleNameDto.getSqlPart());
            ruleName.setSqlStr(ruleNameDto.getSqlStr());

            ruleNameService.createRuleName(ruleName);

            return "redirect:/ruleNameHomePage";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "newRuleName";
        }
    }

    @GetMapping("/showFormForRuleNameUpdate/{id}")
    public String showFormForRuleNameUpdate(@PathVariable(value = "id") Integer id, Model model) {
        try {
            Optional<RuleName> ruleNameOpt = ruleNameService.getRuleNameById(id);
            if (ruleNameOpt.isPresent()) {
                RuleName ruleName = ruleNameOpt.get();
                model.addAttribute("ruleNameDto", ruleName);
                return "updateRuleName";
            } else {
                return "redirect:/ruleNameHomePage";
            }
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred: " + e.getMessage());
            return "redirect:/ruleNameHomePage";
        }
    }

    @PostMapping("/updateRuleName/{id}")
    public String updateRuleName(@PathVariable(value = "id") Integer id, @Valid @ModelAttribute("ruleNameDto") RuleNameDto ruleNameDto, BindingResult result) {
        if (result.hasErrors()) {
            return "updateRuleName";
        }
        try {
            RuleName updatedRuleName = new RuleName();
            updatedRuleName.setName(ruleNameDto.getName());
            updatedRuleName.setJson(ruleNameDto.getJson());
            updatedRuleName.setDescription(ruleNameDto.getDescription());
            updatedRuleName.setTemplate(ruleNameDto.getTemplate());
            updatedRuleName.setSqlPart(ruleNameDto.getSqlPart());
            updatedRuleName.setSqlStr(ruleNameDto.getSqlStr());

            ruleNameService.addNewRuleName(id, updatedRuleName);
            return "redirect:/ruleNameHomePage";
        } catch (Exception exception) {
            result.rejectValue("name", "", "error : " + exception.getMessage());
            return "updateRuleName";
        }
    }


    @GetMapping("/deleteRuleName/{id}")
    public String deleteRuleName(@PathVariable(value = "id") Integer id) {
        this.ruleNameService.deleteRuleName(id);
        return "redirect:/ruleNameHomePage";
    }
}
