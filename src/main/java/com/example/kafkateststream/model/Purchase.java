package com.example.kafkateststream.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Purchase {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("itemName")
    private String itemName;
    @JsonProperty("itemsNumber")
    private Integer itemsNumber;

    public Purchase() {
    }

    @JsonCreator
    public Purchase( @JsonProperty("id") Long id,@JsonProperty("itemName") String itemName,@JsonProperty("itemsNumber") Integer itemsNumber) {
        this.id = id;
        this.itemName = itemName;
        this.itemsNumber = itemsNumber;
    }

    public Long getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public Integer getItemsNumber() {
        return itemsNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemsNumber(Integer itemsNumber) {
        this.itemsNumber = itemsNumber;
    }
}
