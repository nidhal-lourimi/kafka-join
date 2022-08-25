package com.example.kafkateststream.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Purchase {
    private Long id;
    private String itemName;
    private Integer itemsNumber;

    public Purchase() {
    }

    public Purchase(Long id, String itemName, Integer itemsNumber) {
        this.id = id;
        this.itemName = itemName;
        this.itemsNumber = itemsNumber;
    }
}
