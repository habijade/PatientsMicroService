package com.nnk.springboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class BidListDto {
    @NotBlank(message = "id is mandatory")
    @Pattern(message = "id must be a number", regexp = "^\\d+$")
    private String bidListId;
    @NotBlank(message = "account is mandatory")
    private String account;
    @NotBlank(message = "type is mandatory")
    private String type;
    @NotBlank(message = "bidQuantity is mandatory")
    private String bidQuantity;

    public String getBidListId() {
        return bidListId;
    }

    public void setBidListId(String bidListId) {
        this.bidListId = bidListId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBidQuantity() {
        return bidQuantity;
    }

    public void setBidQuantity(String bidQuantity) {
        this.bidQuantity = bidQuantity;
    }
}
