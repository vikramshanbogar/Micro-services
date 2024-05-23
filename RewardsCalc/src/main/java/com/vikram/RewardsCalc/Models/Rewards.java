package com.vikram.RewardsCalc.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Rewards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String transactionEntityName;
    Double transactionAmount;
    Integer rewardPoints;
    Integer customerId;
}
