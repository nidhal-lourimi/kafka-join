package com.example.kafkateststream.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

public class Customer {


    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("purchaseId")
    private Long purchaseId;

    public Customer() {


    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Long getPurchaseId() {
        return purchaseId;
    }


    @JsonCreator
    public Customer(@JsonProperty("id") Long id,@JsonProperty("name") String name,@JsonProperty("email") String email,@JsonProperty("purchaseId") Long purchaseId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.purchaseId = purchaseId;
    }


}
