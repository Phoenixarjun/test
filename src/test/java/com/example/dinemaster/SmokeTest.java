package com.example.dinemaster;

import com.example.dinemaster.controller.ChefController;
import com.example.dinemaster.controller.RestaurantController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private ChefController chefController;

    @Autowired
    private RestaurantController restaurantController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(chefController).isNotNull();
        assertThat(restaurantController).isNotNull();
    }
}
