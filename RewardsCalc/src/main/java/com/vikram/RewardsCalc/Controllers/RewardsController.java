package com.vikram.RewardsCalc.Controllers;

import com.vikram.RewardsCalc.Models.Rewards;
import com.vikram.RewardsCalc.Repos.RewardsRepo;
import com.vikram.RewardsCalc.Services.RewardsService;
import com.vikram.RewardsCalc.Utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/rewards")
public class RewardsController {

    @Autowired
    RewardsService rewardsService;

    @Autowired
    private RewardsRepo rewardsRepo;

    @PostMapping
    String insertData(@RequestBody Rewards rewards) {
        if (rewards == null)
            return "Data not inserted successfully";

        rewardsService.insertData(rewards);
        return "Data inserted successfully";
    }

    @PutMapping
    String updateData(@RequestBody Rewards rewards) {
        if (rewards == null) {
            return "Data not inserted successfully";
        }
        //  collageService.insertData(collage);
        if (rewards.getId() != null && rewardsRepo.findById(rewards.getId()).isPresent()) {
            rewardsService.insertData(rewards);
            return "Data Updated successfully";
        } else {
            return "Data not inserted successfully, Create before updating";
        }
    }

    @PatchMapping
    String updateDataPatch(@RequestBody Rewards rewards) {
        Optional<Rewards> rewardsOptional = rewardsRepo.findById(rewards.getId());

        if (!rewardsOptional.isPresent()) {
            return "Record not found";
        }
        try {
            Utility.rewardsPatcher(rewardsOptional.get(), rewards);
            rewardsService.insertData(rewardsOptional.get());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return "Data patched successfully";
    }

    @GetMapping
    List<Rewards> getRewardss() {
        List<Rewards> list = rewardsService.getAllRewards();
//        Rewards rewards = new Rewards();
//        rewards.setId(1233);
//        rewards.setRewardPoints(1000);
//        list.add(rewards);
        return list;
    }

    @RequestMapping(value = "/", method = RequestMethod.HEAD)
    String getRequest() {
        return "Success";
    }

    @GetMapping("/{customerId}")
    List<Rewards> getRewardsByCustomerId(@PathVariable int customerId) {
        return rewardsRepo.getRewardsByCustomerId(customerId);
    }

    @RequestMapping(value = "/", method = RequestMethod.OPTIONS)
    ResponseEntity<?> getOptions() {
        return ResponseEntity
                .ok()
                .allow(HttpMethod.GET, HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.POST, HttpMethod.PATCH, HttpMethod.OPTIONS, HttpMethod.HEAD)
                .build();
    }


    @DeleteMapping("/{id}")
    String deleteRewardsById(@PathVariable int id) {
        if (rewardsRepo.findById(id).isPresent()) {
            rewardsRepo.deleteById(id);
            return "Rewards deleted successfully";
        }
        return "Failed to Delete the rewards, Pls check the id";
    }
}