package com.nnk.springboot.dto;
import jakarta.validation.constraints.NotBlank;

public class RuleNameDto {

    @NotBlank(message = "description is required")
    private String description;

    @NotBlank(message = "json is required")
    private String json;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "sqlPart is required")
    private String sqlPart;

    @NotBlank(message = "sqlStr is required")
    private String sqlStr;

    @NotBlank(message = "template is required")
    private String template;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSqlPart() {
        return sqlPart;
    }

    public void setSqlPart(String sqlPart) {
        this.sqlPart = sqlPart;
    }

    public String getSqlStr() {
        return sqlStr;
    }

    public void setSqlStr(String sqlStr) {
        this.sqlStr = sqlStr;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
