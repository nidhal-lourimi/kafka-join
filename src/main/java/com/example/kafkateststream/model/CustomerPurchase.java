package com.example.kafkateststream.model;

import com.example.kafkateststream.serdes.SerdesFactory;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Getter
@Setter
@JsonSerialize
@JsonDeserialize
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class CustomerPurchase {

    public CustomerPurchase() {
    }

    @JsonCreator
    public CustomerPurchase(@JsonProperty("customerId") Long customerId,@JsonProperty("name") String name,@JsonProperty("email") String email,@JsonProperty("purchaseId") Long purchaseId,@JsonProperty("itemName") String itemName,@JsonProperty("itemsNumber") Integer itemsNumber) {
        super();
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.purchaseId = purchaseId;
        this.itemName = itemName;
        this.itemsNumber = itemsNumber;
    }


    private Long customerId;
    private String name;
    private String email;
    private Long purchaseId;
    private String itemName;
    private Integer itemsNumber;

    @Override
    public String toString() {
        return "CustomerPurchase{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", purchaseId=" + purchaseId +
                ", itemName='" + itemName + '\'' +
                ", itemsNumber=" + itemsNumber +
                '}';
    }
}
