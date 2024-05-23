package com.vikram.RewardsCalc.Utils;

import com.vikram.RewardsCalc.Models.Rewards;

import java.lang.reflect.Field;

public class Utility {


    public static void rewardsPatcher(Rewards existingRewards, Rewards incompleteRewards) throws IllegalAccessException {

        //GET THE COMPILED VERSION OF THE CLASS
        Class<?> rewardsClass= Rewards.class;
        Field[] CustomerFields=rewardsClass.getDeclaredFields();
        System.out.println(CustomerFields.length);
        for(Field field : CustomerFields){
            System.out.println(field.getName());
            //CANT ACCESS IF THE FIELD IS PRIVATE
            field.setAccessible(true);

            //CHECK IF THE VALUE OF THE FIELD IS NOT NULL, IF NOT UPDATE EXISTING Customer
            Object value=field.get(incompleteRewards);
            if(value!=null){
                field.set(existingRewards,value);
            }
            //MAKE THE FIELD PRIVATE AGAIN
            field.setAccessible(false);
        }
    }
}
