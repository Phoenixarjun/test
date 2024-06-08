package com.example.dinemaster.service;

import com.example.dinemaster.model.Chef;
import com.example.dinemaster.model.Restaurant;
import com.example.dinemaster.repository.ChefJpaRepository;
import com.example.dinemaster.repository.ChefRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChefJpaService implements ChefRepository {
    @Autowired
    private ChefJpaRepository chefJpaRepository;

    @Autowired
    private RestaurantJpaService restaurantJpaService;

    @Override
    public ArrayList<Chef> getChefs() {
        List<Chef> chefsList = chefJpaRepository.findAll();
        return new ArrayList<>(chefsList);
    }

    @Override
    public Chef getChefById(int chefId) {
        try {
            return chefJpaRepository.findById(chefId).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Chef addChef(Chef chef) {
        int restaurantId = chef.getRestaurant().getId();
        Restaurant restaurant = restaurantJpaService.getRestaurantById(restaurantId);
        chef.setRestaurant(restaurant);
        chefJpaRepository.save(chef);
        return chef;
    }

@Override
public Chef updateChef(int chefId, Chef chef) {
    try {
        Chef updatedChef = chefJpaRepository.findById(chefId).get();

        if (chef.getFirstName() != null) {
            updatedChef.setFirstName(chef.getFirstName());
        }
        if (chef.getLastName() != null) {
            updatedChef.setLastName(chef.getLastName());
        }
        if (chef.getExpertise() != null) {
            updatedChef.setExpertise(chef.getExpertise());
        }
        if (chef.getExperienceYears() != 0) {
            updatedChef.setExperienceYears(chef.getExperienceYears());
        }

        chefJpaRepository.save(updatedChef);
        return updatedChef;
    } catch (Exception e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}



    @Override
    public void deleteChef(int chefId) {
        try {
            chefJpaRepository.deleteById(chefId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public Restaurant getChefRestaurant(int chefId) {
        try {
            Chef chef = chefJpaRepository.findById(chefId).get();
            return chef.getRestaurant();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
