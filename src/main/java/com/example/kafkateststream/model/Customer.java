package com.example.kafkateststream.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer {

    @JsonProperty("id")
    public Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("purchaseId")
    private Long purchaseId;

    public Customer() {


    }

    @JsonCreator
    public Customer(@JsonProperty("id") Long id,@JsonProperty("name") String name,@JsonProperty("email") String email,@JsonProperty("purchaseId") Long purchaseId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.purchaseId = purchaseId;
    }


}
