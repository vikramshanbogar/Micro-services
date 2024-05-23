package com.vikram.customers.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Rewards {
    Integer id;
    String transactionEntityName;
    Double transactionAmount;
    Integer rewardPoints;
}
