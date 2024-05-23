package com.vikram.RewardsCalc.Services;

import com.vikram.RewardsCalc.Models.Rewards;
import com.vikram.RewardsCalc.Repos.RewardsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RewardsService {

    @Autowired
    RewardsRepo repo;

    public void insertData(Rewards rewards) {
        Rewards c = repo.save(rewards);
    }

    public List<Rewards> getAllRewards() {
        List<Rewards>  rewards =  repo.findAll();
        return rewards;
    }
}
