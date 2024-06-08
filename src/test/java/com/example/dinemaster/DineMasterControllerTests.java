package com.example.dinemaster;

import com.example.dinemaster.model.Chef;
import com.example.dinemaster.model.Restaurant;
import com.example.dinemaster.repository.ChefJpaRepository;
import com.example.dinemaster.repository.RestaurantJpaRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.HashMap;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts = { "/schema.sql", "/data.sql" })
public class DineMasterControllerTests {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ChefJpaRepository chefJpaRepository;

        @Autowired
        private RestaurantJpaRepository restaurantJpaRepository;

        @Autowired
        private JdbcTemplate jdbcTemplate;

        private HashMap<Integer, Object[]> restaurants = new HashMap<>();
        {
                restaurants.put(1, new Object[] { "Fine Dining", "123 Main St", "European", 5 });
                restaurants.put(2, new Object[] { "Taco Bell", "456 Elm St", "Fast Food", 3 });
                restaurants.put(3, new Object[] { "Sushi Place", "789 Oak St", "Japanese", 4 });
                restaurants.put(4, new Object[] { "Pasta House", "222 Pasta St", "Italian", 4 }); // POST
                restaurants.put(5, new Object[] { "Burger Joint", "333 Burger Blvd", "American", 3 }); // PUT
        }

        private HashMap<Integer, Object[]> chefs = new HashMap<>();
        {
                chefs.put(1, new Object[] { "John", "Doe", "Sous Chef", 5, 1 });
                chefs.put(2, new Object[] { "Jane", "Doe", "Pastry Chef", 7, 1 });
                chefs.put(3, new Object[] { "Mike", "Smith", "Head Chef", 10, 2 });
                chefs.put(4, new Object[] { "Emily", "Johnson", "Sushi Chef", 8, 3 });
                chefs.put(5, new Object[] { "Anna", "Williams", "Pastry Chef", 6, 3 });
                chefs.put(6, new Object[] { "Mark", "Brown", "Sous Chef", 4, 2 });
                chefs.put(7, new Object[] { "Sara", "Lee", "Pastry Chef", 9, 4 }); // POST
                chefs.put(8, new Object[] { "Tom", "Gordon", "Grill Chef", 6, 4 }); // PUT
        }

        @Test
        @Order(1)
        public void testGetRestaurants() throws Exception {
                mockMvc.perform(get("/restaurants")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(3)))

                                .andExpect(jsonPath("$[0].id", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].name", Matchers.equalTo(restaurants.get(1)[0])))
                                .andExpect(jsonPath("$[0].address", Matchers.equalTo(restaurants.get(1)[1])))
                                .andExpect(jsonPath("$[0].cuisineType", Matchers.equalTo(restaurants.get(1)[2])))
                                .andExpect(jsonPath("$[0].rating", Matchers.equalTo(restaurants.get(1)[3])))

                                .andExpect(jsonPath("$[1].id", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].name", Matchers.equalTo(restaurants.get(2)[0])))
                                .andExpect(jsonPath("$[1].address", Matchers.equalTo(restaurants.get(2)[1])))
                                .andExpect(jsonPath("$[1].cuisineType", Matchers.equalTo(restaurants.get(2)[2])))
                                .andExpect(jsonPath("$[1].rating", Matchers.equalTo(restaurants.get(2)[3])))

                                .andExpect(jsonPath("$[2].id", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].name", Matchers.equalTo(restaurants.get(3)[0])))
                                .andExpect(jsonPath("$[2].address", Matchers.equalTo(restaurants.get(3)[1])))
                                .andExpect(jsonPath("$[2].cuisineType", Matchers.equalTo(restaurants.get(3)[2])))
                                .andExpect(jsonPath("$[2].rating", Matchers.equalTo(restaurants.get(3)[3])));
        }

        @Test
        @Order(2)
        public void testGetRestaurantNotFound() throws Exception {
                mockMvc.perform(get("/restaurants/48")).andExpect(status().isNotFound());
        }

        @Test
        @Order(3)
        public void testGetRestaurantById() throws Exception {
                for (int i = 0; i < 3; i++) {
                        mockMvc.perform(get("/restaurants/" + (i + 1))).andExpect(status().isOk())
                                        .andExpect(jsonPath("$", notNullValue()))
                                        .andExpect(jsonPath("$.id", Matchers.equalTo(i + 1)))
                                        .andExpect(jsonPath("$.name",
                                                        Matchers.equalTo(restaurants.get(i + 1)[0])))
                                        .andExpect(jsonPath("$.address",
                                                        Matchers.equalTo(restaurants.get(i + 1)[1])))
                                        .andExpect(jsonPath("$.cuisineType",
                                                        Matchers.equalTo(restaurants.get(i + 1)[2])))
                                        .andExpect(jsonPath("$.rating",
                                                        Matchers.equalTo(restaurants.get(i + 1)[3])));
                }
        }

        @Test
        @Order(4)
        public void testGetChefs() throws Exception {
                mockMvc.perform(get("/restaurants/chefs")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(6)))

                                .andExpect(jsonPath("$[0].id", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].firstName", Matchers.equalTo(chefs.get(1)[0])))
                                .andExpect(jsonPath("$[0].lastName", Matchers.equalTo(chefs.get(1)[1])))
                                .andExpect(jsonPath("$[0].expertise", Matchers.equalTo(chefs.get(1)[2])))
                                .andExpect(jsonPath("$[0].experienceYears", Matchers.equalTo(chefs.get(1)[3])))
                                .andExpect(jsonPath("$[0].restaurant.id", Matchers.equalTo(chefs.get(1)[4])))
                                .andExpect(jsonPath("$[0].restaurant.name", Matchers.equalTo(restaurants.get(1)[0])))
                                .andExpect(jsonPath("$[0].restaurant.address", Matchers.equalTo(restaurants.get(1)[1])))
                                .andExpect(jsonPath("$[0].restaurant.cuisineType",
                                                Matchers.equalTo(restaurants.get(1)[2])))
                                .andExpect(jsonPath("$[0].restaurant.rating", Matchers.equalTo(restaurants.get(1)[3])))

                                .andExpect(jsonPath("$[1].id", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].firstName", Matchers.equalTo(chefs.get(2)[0])))
                                .andExpect(jsonPath("$[1].lastName", Matchers.equalTo(chefs.get(2)[1])))
                                .andExpect(jsonPath("$[1].expertise", Matchers.equalTo(chefs.get(2)[2])))
                                .andExpect(jsonPath("$[1].experienceYears", Matchers.equalTo(chefs.get(2)[3])))
                                .andExpect(jsonPath("$[1].restaurant.id", Matchers.equalTo(chefs.get(2)[4])))
                                .andExpect(jsonPath("$[1].restaurant.name", Matchers.equalTo(restaurants.get(1)[0])))
                                .andExpect(jsonPath("$[1].restaurant.address", Matchers.equalTo(restaurants.get(1)[1])))
                                .andExpect(jsonPath("$[1].restaurant.cuisineType",
                                                Matchers.equalTo(restaurants.get(1)[2])))
                                .andExpect(jsonPath("$[1].restaurant.rating", Matchers.equalTo(restaurants.get(1)[3])))

                                .andExpect(jsonPath("$[2].id", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].firstName", Matchers.equalTo(chefs.get(3)[0])))
                                .andExpect(jsonPath("$[2].lastName", Matchers.equalTo(chefs.get(3)[1])))
                                .andExpect(jsonPath("$[2].expertise", Matchers.equalTo(chefs.get(3)[2])))
                                .andExpect(jsonPath("$[2].experienceYears", Matchers.equalTo(chefs.get(3)[3])))
                                .andExpect(jsonPath("$[2].restaurant.id", Matchers.equalTo(chefs.get(3)[4])))
                                .andExpect(jsonPath("$[2].restaurant.name", Matchers.equalTo(restaurants.get(2)[0])))
                                .andExpect(jsonPath("$[2].restaurant.address", Matchers.equalTo(restaurants.get(2)[1])))
                                .andExpect(jsonPath("$[2].restaurant.cuisineType",
                                                Matchers.equalTo(restaurants.get(2)[2])))
                                .andExpect(jsonPath("$[2].restaurant.rating", Matchers.equalTo(restaurants.get(2)[3])))

                                .andExpect(jsonPath("$[3].id", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[3].firstName", Matchers.equalTo(chefs.get(4)[0])))
                                .andExpect(jsonPath("$[3].lastName", Matchers.equalTo(chefs.get(4)[1])))
                                .andExpect(jsonPath("$[3].expertise", Matchers.equalTo(chefs.get(4)[2])))
                                .andExpect(jsonPath("$[3].experienceYears", Matchers.equalTo(chefs.get(4)[3])))
                                .andExpect(jsonPath("$[3].restaurant.id", Matchers.equalTo(chefs.get(4)[4])))
                                .andExpect(jsonPath("$[3].restaurant.name", Matchers.equalTo(restaurants.get(3)[0])))
                                .andExpect(jsonPath("$[3].restaurant.address", Matchers.equalTo(restaurants.get(3)[1])))
                                .andExpect(jsonPath("$[3].restaurant.cuisineType",
                                                Matchers.equalTo(restaurants.get(3)[2])))
                                .andExpect(jsonPath("$[3].restaurant.rating", Matchers.equalTo(restaurants.get(3)[3])))

                                .andExpect(jsonPath("$[4].id", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$[4].firstName", Matchers.equalTo(chefs.get(5)[0])))
                                .andExpect(jsonPath("$[4].lastName", Matchers.equalTo(chefs.get(5)[1])))
                                .andExpect(jsonPath("$[4].expertise", Matchers.equalTo(chefs.get(5)[2])))
                                .andExpect(jsonPath("$[4].experienceYears", Matchers.equalTo(chefs.get(5)[3])))
                                .andExpect(jsonPath("$[4].restaurant.id", Matchers.equalTo(chefs.get(5)[4])))
                                .andExpect(jsonPath("$[4].restaurant.name", Matchers.equalTo(restaurants.get(3)[0])))
                                .andExpect(jsonPath("$[4].restaurant.address", Matchers.equalTo(restaurants.get(3)[1])))
                                .andExpect(jsonPath("$[4].restaurant.cuisineType",
                                                Matchers.equalTo(restaurants.get(3)[2])))
                                .andExpect(jsonPath("$[4].restaurant.rating", Matchers.equalTo(restaurants.get(3)[3])))

                                .andExpect(jsonPath("$[5].id", Matchers.equalTo(6)))
                                .andExpect(jsonPath("$[5].firstName", Matchers.equalTo(chefs.get(6)[0])))
                                .andExpect(jsonPath("$[5].lastName", Matchers.equalTo(chefs.get(6)[1])))
                                .andExpect(jsonPath("$[5].expertise", Matchers.equalTo(chefs.get(6)[2])))
                                .andExpect(jsonPath("$[5].experienceYears", Matchers.equalTo(chefs.get(6)[3])))
                                .andExpect(jsonPath("$[5].restaurant.id", Matchers.equalTo(chefs.get(6)[4])))
                                .andExpect(jsonPath("$[5].restaurant.name", Matchers.equalTo(restaurants.get(2)[0])))
                                .andExpect(jsonPath("$[5].restaurant.address", Matchers.equalTo(restaurants.get(2)[1])))
                                .andExpect(jsonPath("$[5].restaurant.cuisineType",
                                                Matchers.equalTo(restaurants.get(2)[2])))
                                .andExpect(jsonPath("$[5].restaurant.rating", Matchers.equalTo(restaurants.get(2)[3])));
        }

        @Test
        @Order(5)
        public void testGetChefNotFound() throws Exception {
                mockMvc.perform(get("/restaurants/chefs/48")).andExpect(status().isNotFound());
        }

        @Test
        @Order(6)
        public void testGetChefById() throws Exception {
                for (int i = 0; i < 6; i++) {
                        mockMvc.perform(get("/restaurants/chefs/" + (i + 1))).andExpect(status().isOk())
                                        .andExpect(jsonPath("$", notNullValue()))
                                        .andExpect(jsonPath("$.id", Matchers.equalTo(i + 1)))
                                        .andExpect(jsonPath("$.firstName",
                                                        Matchers.equalTo(chefs.get(i + 1)[0])))
                                        .andExpect(jsonPath("$.lastName",
                                                        Matchers.equalTo(chefs.get(i + 1)[1])))
                                        .andExpect(jsonPath("$.expertise",
                                                        Matchers.equalTo(chefs.get(i + 1)[2])))
                                        .andExpect(jsonPath("$.experienceYears",
                                                        Matchers.equalTo(chefs.get(i + 1)[3])))
                                        .andExpect(jsonPath("$.restaurant.id",
                                                        Matchers.equalTo(chefs.get(i + 1)[4])))
                                        .andExpect(jsonPath("$.restaurant.name",
                                                        Matchers.equalTo(restaurants.get(chefs.get(i + 1)[4])[0])))
                                        .andExpect(jsonPath("$.restaurant.address",
                                                        Matchers.equalTo(restaurants.get(chefs.get(i + 1)[4])[1])))
                                        .andExpect(jsonPath("$.restaurant.cuisineType",
                                                        Matchers.equalTo(restaurants.get(chefs.get(i + 1)[4])[2])))
                                        .andExpect(jsonPath("$.restaurant.rating",
                                                        Matchers.equalTo(restaurants.get(chefs.get(i + 1)[4])[3])));
                }
        }

        @Test
        @Order(7)
        public void testPostRestaurant() throws Exception {
                String content = "{\"name\": \"" + restaurants.get(4)[0] + "\", \"address\": \""
                                + restaurants.get(4)[1]
                                + "\", \"cuisineType\": \"" + restaurants.get(4)[2] + "\", \"rating\": "
                                + restaurants.get(4)[3] + " }";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/restaurants")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.id", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.name", Matchers.equalTo(restaurants.get(4)[0])))
                                .andExpect(jsonPath("$.address", Matchers.equalTo(restaurants.get(4)[1])))
                                .andExpect(jsonPath("$.cuisineType", Matchers.equalTo(restaurants.get(4)[2])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(restaurants.get(4)[3])));
        }

        @Test
        @Order(8)
        public void testAfterPostRestaurant() throws Exception {
                mockMvc.perform(get("/restaurants/4")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.id", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.name", Matchers.equalTo(restaurants.get(4)[0])))
                                .andExpect(jsonPath("$.address", Matchers.equalTo(restaurants.get(4)[1])))
                                .andExpect(jsonPath("$.cuisineType", Matchers.equalTo(restaurants.get(4)[2])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(restaurants.get(4)[3])));
        }

        @Test
        @Order(9)
        public void testDbAfterPostRestaurant() throws Exception {
                Restaurant restaurant = restaurantJpaRepository.findById(4).get();

                assertEquals(restaurant.getId(), 4);
                assertEquals(restaurant.getName(), restaurants.get(4)[0]);
                assertEquals(restaurant.getAddress(), restaurants.get(4)[1]);
                assertEquals(restaurant.getCuisineType(), restaurants.get(4)[2]);
                assertEquals(restaurant.getRating(), restaurants.get(4)[3]);

        }

        @Test
        @Order(10)
        public void testPostChef() throws Exception {
                String content = "{\"firstName\": \"" + chefs.get(7)[0]
                                + "\", \"lastName\": \"" + chefs.get(7)[1]
                                + "\", \"expertise\": \"" + chefs.get(7)[2] + "\", \"experienceYears\": "
                                + chefs.get(7)[3] + ", \"restaurant\": {\"id\": " + chefs.get(7)[4] + "}}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/restaurants/chefs")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.id", Matchers.equalTo(7)))
                                .andExpect(jsonPath("$.firstName", Matchers.equalTo(chefs.get(7)[0])))
                                .andExpect(jsonPath("$.lastName", Matchers.equalTo(chefs.get(7)[1])))
                                .andExpect(jsonPath("$.expertise", Matchers.equalTo(chefs.get(7)[2])))
                                .andExpect(jsonPath("$.experienceYears", Matchers.equalTo(chefs.get(7)[3])))
                                .andExpect(jsonPath("$.restaurant.id", Matchers.equalTo(chefs.get(7)[4])))
                                .andExpect(jsonPath("$.restaurant.name", Matchers.equalTo(restaurants.get(4)[0])))
                                .andExpect(jsonPath("$.restaurant.address", Matchers.equalTo(restaurants.get(4)[1])))
                                .andExpect(jsonPath("$.restaurant.cuisineType",
                                                Matchers.equalTo(restaurants.get(4)[2])))
                                .andExpect(jsonPath("$.restaurant.rating", Matchers.equalTo(restaurants.get(4)[3])));
        }

        @Test
        @Order(11)
        public void testAfterPostChef() throws Exception {
                mockMvc.perform(get("/restaurants/chefs/7")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.id", Matchers.equalTo(7)))
                                .andExpect(jsonPath("$.firstName", Matchers.equalTo(chefs.get(7)[0])))
                                .andExpect(jsonPath("$.lastName", Matchers.equalTo(chefs.get(7)[1])))
                                .andExpect(jsonPath("$.expertise", Matchers.equalTo(chefs.get(7)[2])))
                                .andExpect(jsonPath("$.experienceYears", Matchers.equalTo(chefs.get(7)[3])))
                                .andExpect(jsonPath("$.restaurant.id", Matchers.equalTo(chefs.get(7)[4])))
                                .andExpect(jsonPath("$.restaurant.name", Matchers.equalTo(restaurants.get(4)[0])))
                                .andExpect(jsonPath("$.restaurant.address", Matchers.equalTo(restaurants.get(4)[1])))
                                .andExpect(jsonPath("$.restaurant.cuisineType",
                                                Matchers.equalTo(restaurants.get(4)[2])))
                                .andExpect(jsonPath("$.restaurant.rating", Matchers.equalTo(restaurants.get(4)[3])));
        }

        @Test
        @Order(12)
        public void testDbAfterPostChef() throws Exception {
                Chef chef = chefJpaRepository.findById(7).get();

                assertEquals(chef.getId(), 7);
                assertEquals(chef.getFirstName(), chefs.get(7)[0]);
                assertEquals(chef.getLastName(), chefs.get(7)[1]);
                assertEquals(chef.getExpertise(), chefs.get(7)[2]);
                assertEquals(chef.getExperienceYears(), chefs.get(7)[3]);
                assertEquals(chef.getRestaurant().getId(), chefs.get(7)[4]);
                assertEquals(chef.getRestaurant().getName(), restaurants.get(4)[0]);
                assertEquals(chef.getRestaurant().getAddress(), restaurants.get(4)[1]);
                assertEquals(chef.getRestaurant().getCuisineType(), restaurants.get(4)[2]);
                assertEquals(chef.getRestaurant().getRating(), restaurants.get(4)[3]);
        }

        @Test
        @Order(13)
        public void testPutRestaurantNotFound() throws Exception {
                String content = "{\"name\": \"" + restaurants.get(5)[0] + "\", \"address\": \""
                                + restaurants.get(5)[1]
                                + "\", \"cuisineType\": \"" + restaurants.get(5)[2] + "\", \"rating\": "
                                + restaurants.get(5)[3] + " }";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/restaurants/48")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isNotFound());

        }

        @Test
        @Order(14)
        public void testPutRestaurant() throws Exception {
                String content = "{\"name\": \"" + restaurants.get(5)[0] + "\", \"address\": \""
                                + restaurants.get(5)[1]
                                + "\", \"cuisineType\": \"" + restaurants.get(5)[2] + "\", \"rating\": "
                                + restaurants.get(5)[3] + " }";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/restaurants/4")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.id", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.name", Matchers.equalTo(restaurants.get(5)[0])))
                                .andExpect(jsonPath("$.address", Matchers.equalTo(restaurants.get(5)[1])))
                                .andExpect(jsonPath("$.cuisineType", Matchers.equalTo(restaurants.get(5)[2])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(restaurants.get(5)[3])));
        }

        @Test
        @Order(15)
        public void testAfterPutRestaurant() throws Exception {

                mockMvc.perform(get("/restaurants/4")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.id", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.name", Matchers.equalTo(restaurants.get(5)[0])))
                                .andExpect(jsonPath("$.address", Matchers.equalTo(restaurants.get(5)[1])))
                                .andExpect(jsonPath("$.cuisineType", Matchers.equalTo(restaurants.get(5)[2])))
                                .andExpect(jsonPath("$.rating", Matchers.equalTo(restaurants.get(5)[3])));
        }

        @Test
        @Order(16)
        public void testDbAfterPutRestaurant() throws Exception {
                Restaurant restaurant = restaurantJpaRepository.findById(4).get();

                assertEquals(restaurant.getId(), 4);
                assertEquals(restaurant.getName(), restaurants.get(5)[0]);
                assertEquals(restaurant.getAddress(), restaurants.get(5)[1]);
                assertEquals(restaurant.getCuisineType(), restaurants.get(5)[2]);
                assertEquals(restaurant.getRating(), restaurants.get(5)[3]);
        }

        @Test
        @Order(17)
        public void testPutChefNotFound() throws Exception {
                String content = "{\"firstName\": \"" + chefs.get(8)[0]
                                + "\", \"lastName\": \"" + chefs.get(8)[1]
                                + "\", \"expertise\": \"" + chefs.get(8)[2] + "\", \"experienceYears\": "
                                + chefs.get(8)[3] + ", \"restaurant\": {\"restaurantId\": " + chefs.get(8)[4] + "}}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/restaurants/chefs/48")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isNotFound());

        }

        @Test
        @Order(18)
        public void testPutChef() throws Exception {
                String content = "{\"firstName\": \"" + chefs.get(8)[0]
                                + "\", \"lastName\": \"" + chefs.get(8)[1]
                                + "\", \"expertise\": \"" + chefs.get(8)[2] + "\", \"experienceYears\": "
                                + chefs.get(8)[3] + ", \"restaurant\": {\"id\": " + chefs.get(8)[4] + "}}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/restaurants/chefs/7")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.id", Matchers.equalTo(7)))
                                .andExpect(jsonPath("$.firstName", Matchers.equalTo(chefs.get(8)[0])))
                                .andExpect(jsonPath("$.lastName", Matchers.equalTo(chefs.get(8)[1])))
                                .andExpect(jsonPath("$.expertise", Matchers.equalTo(chefs.get(8)[2])))
                                .andExpect(jsonPath("$.experienceYears", Matchers.equalTo(chefs.get(8)[3])))
                                .andExpect(jsonPath("$.restaurant.id", Matchers.equalTo(chefs.get(8)[4])))
                                .andExpect(jsonPath("$.restaurant.name", Matchers.equalTo(restaurants.get(5)[0])))
                                .andExpect(jsonPath("$.restaurant.address", Matchers.equalTo(restaurants.get(5)[1])))
                                .andExpect(jsonPath("$.restaurant.cuisineType",
                                                Matchers.equalTo(restaurants.get(5)[2])))
                                .andExpect(jsonPath("$.restaurant.rating", Matchers.equalTo(restaurants.get(5)[3])));
        }

        @Test
        @Order(19)
        public void testAfterPutChef() throws Exception {

                mockMvc.perform(get("/restaurants/chefs/7")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.id", Matchers.equalTo(7)))
                                .andExpect(jsonPath("$.firstName", Matchers.equalTo(chefs.get(8)[0])))
                                .andExpect(jsonPath("$.lastName", Matchers.equalTo(chefs.get(8)[1])))
                                .andExpect(jsonPath("$.expertise", Matchers.equalTo(chefs.get(8)[2])))
                                .andExpect(jsonPath("$.experienceYears", Matchers.equalTo(chefs.get(8)[3])))
                                .andExpect(jsonPath("$.restaurant.id", Matchers.equalTo(chefs.get(8)[4])))
                                .andExpect(jsonPath("$.restaurant.name", Matchers.equalTo(restaurants.get(5)[0])))
                                .andExpect(jsonPath("$.restaurant.address", Matchers.equalTo(restaurants.get(5)[1])))
                                .andExpect(jsonPath("$.restaurant.cuisineType",
                                                Matchers.equalTo(restaurants.get(5)[2])))
                                .andExpect(jsonPath("$.restaurant.rating", Matchers.equalTo(restaurants.get(5)[3])));
        }

        @Test
        @Order(20)
        public void testDbAfterPutChef() throws Exception {
                Chef chef = chefJpaRepository.findById(7).get();

                assertEquals(chef.getId(), 7);
                assertEquals(chef.getFirstName(), chefs.get(8)[0]);
                assertEquals(chef.getLastName(), chefs.get(8)[1]);
                assertEquals(chef.getExpertise(), chefs.get(8)[2]);
                assertEquals(chef.getExperienceYears(), chefs.get(8)[3]);
                assertEquals(chef.getRestaurant().getId(), chefs.get(8)[4]);
                assertEquals(chef.getRestaurant().getName(), restaurants.get(5)[0]);
                assertEquals(chef.getRestaurant().getAddress(), restaurants.get(5)[1]);
                assertEquals(chef.getRestaurant().getCuisineType(), restaurants.get(5)[2]);
                assertEquals(chef.getRestaurant().getRating(), restaurants.get(5)[3]);
        }

        @Test
        @Order(21)
        public void testDeleteChefNotFound() throws Exception {

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/restaurants/chefs/148")
                                .contentType(MediaType.APPLICATION_JSON);
                mockMvc.perform(mockRequest).andExpect(status().isNotFound());

        }

        @Test
        @Order(22)
        public void testDeleteChef() throws Exception {

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/restaurants/chefs/7")
                                .contentType(MediaType.APPLICATION_JSON);
                mockMvc.perform(mockRequest).andExpect(status().isNoContent());
        }

        @Test
        @Order(23)
        public void testAfterDeleteChef() throws Exception {
                mockMvc.perform(get("/restaurants/chefs")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(6)))

                                .andExpect(jsonPath("$[0].id", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].firstName", Matchers.equalTo(chefs.get(1)[0])))
                                .andExpect(jsonPath("$[0].lastName", Matchers.equalTo(chefs.get(1)[1])))
                                .andExpect(jsonPath("$[0].expertise", Matchers.equalTo(chefs.get(1)[2])))
                                .andExpect(jsonPath("$[0].experienceYears", Matchers.equalTo(chefs.get(1)[3])))
                                .andExpect(jsonPath("$[0].restaurant.id", Matchers.equalTo(chefs.get(1)[4])))
                                .andExpect(jsonPath("$[0].restaurant.name", Matchers.equalTo(restaurants.get(1)[0])))
                                .andExpect(jsonPath("$[0].restaurant.address", Matchers.equalTo(restaurants.get(1)[1])))
                                .andExpect(jsonPath("$[0].restaurant.cuisineType",
                                                Matchers.equalTo(restaurants.get(1)[2])))
                                .andExpect(jsonPath("$[0].restaurant.rating", Matchers.equalTo(restaurants.get(1)[3])))

                                .andExpect(jsonPath("$[1].id", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].firstName", Matchers.equalTo(chefs.get(2)[0])))
                                .andExpect(jsonPath("$[1].lastName", Matchers.equalTo(chefs.get(2)[1])))
                                .andExpect(jsonPath("$[1].expertise", Matchers.equalTo(chefs.get(2)[2])))
                                .andExpect(jsonPath("$[1].experienceYears", Matchers.equalTo(chefs.get(2)[3])))
                                .andExpect(jsonPath("$[1].restaurant.id", Matchers.equalTo(chefs.get(2)[4])))
                                .andExpect(jsonPath("$[1].restaurant.name", Matchers.equalTo(restaurants.get(1)[0])))
                                .andExpect(jsonPath("$[1].restaurant.address", Matchers.equalTo(restaurants.get(1)[1])))
                                .andExpect(jsonPath("$[1].restaurant.cuisineType",
                                                Matchers.equalTo(restaurants.get(1)[2])))
                                .andExpect(jsonPath("$[1].restaurant.rating", Matchers.equalTo(restaurants.get(1)[3])))

                                .andExpect(jsonPath("$[2].id", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].firstName", Matchers.equalTo(chefs.get(3)[0])))
                                .andExpect(jsonPath("$[2].lastName", Matchers.equalTo(chefs.get(3)[1])))
                                .andExpect(jsonPath("$[2].expertise", Matchers.equalTo(chefs.get(3)[2])))
                                .andExpect(jsonPath("$[2].experienceYears", Matchers.equalTo(chefs.get(3)[3])))
                                .andExpect(jsonPath("$[2].restaurant.id", Matchers.equalTo(chefs.get(3)[4])))
                                .andExpect(jsonPath("$[2].restaurant.name", Matchers.equalTo(restaurants.get(2)[0])))
                                .andExpect(jsonPath("$[2].restaurant.address", Matchers.equalTo(restaurants.get(2)[1])))
                                .andExpect(jsonPath("$[2].restaurant.cuisineType",
                                                Matchers.equalTo(restaurants.get(2)[2])))
                                .andExpect(jsonPath("$[2].restaurant.rating", Matchers.equalTo(restaurants.get(2)[3])))

                                .andExpect(jsonPath("$[3].id", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$[3].firstName", Matchers.equalTo(chefs.get(4)[0])))
                                .andExpect(jsonPath("$[3].lastName", Matchers.equalTo(chefs.get(4)[1])))
                                .andExpect(jsonPath("$[3].expertise", Matchers.equalTo(chefs.get(4)[2])))
                                .andExpect(jsonPath("$[3].experienceYears", Matchers.equalTo(chefs.get(4)[3])))
                                .andExpect(jsonPath("$[3].restaurant.id", Matchers.equalTo(chefs.get(4)[4])))
                                .andExpect(jsonPath("$[3].restaurant.name", Matchers.equalTo(restaurants.get(3)[0])))
                                .andExpect(jsonPath("$[3].restaurant.address", Matchers.equalTo(restaurants.get(3)[1])))
                                .andExpect(jsonPath("$[3].restaurant.cuisineType",
                                                Matchers.equalTo(restaurants.get(3)[2])))
                                .andExpect(jsonPath("$[3].restaurant.rating", Matchers.equalTo(restaurants.get(3)[3])))

                                .andExpect(jsonPath("$[4].id", Matchers.equalTo(5)))
                                .andExpect(jsonPath("$[4].firstName", Matchers.equalTo(chefs.get(5)[0])))
                                .andExpect(jsonPath("$[4].lastName", Matchers.equalTo(chefs.get(5)[1])))
                                .andExpect(jsonPath("$[4].expertise", Matchers.equalTo(chefs.get(5)[2])))
                                .andExpect(jsonPath("$[4].experienceYears", Matchers.equalTo(chefs.get(5)[3])))
                                .andExpect(jsonPath("$[4].restaurant.id", Matchers.equalTo(chefs.get(5)[4])))
                                .andExpect(jsonPath("$[4].restaurant.name", Matchers.equalTo(restaurants.get(3)[0])))
                                .andExpect(jsonPath("$[4].restaurant.address", Matchers.equalTo(restaurants.get(3)[1])))
                                .andExpect(jsonPath("$[4].restaurant.cuisineType",
                                                Matchers.equalTo(restaurants.get(3)[2])))
                                .andExpect(jsonPath("$[4].restaurant.rating", Matchers.equalTo(restaurants.get(3)[3])))

                                .andExpect(jsonPath("$[5].id", Matchers.equalTo(6)))
                                .andExpect(jsonPath("$[5].firstName", Matchers.equalTo(chefs.get(6)[0])))
                                .andExpect(jsonPath("$[5].lastName", Matchers.equalTo(chefs.get(6)[1])))
                                .andExpect(jsonPath("$[5].expertise", Matchers.equalTo(chefs.get(6)[2])))
                                .andExpect(jsonPath("$[5].experienceYears", Matchers.equalTo(chefs.get(6)[3])))
                                .andExpect(jsonPath("$[5].restaurant.id", Matchers.equalTo(chefs.get(6)[4])))
                                .andExpect(jsonPath("$[5].restaurant.name", Matchers.equalTo(restaurants.get(2)[0])))
                                .andExpect(jsonPath("$[5].restaurant.address", Matchers.equalTo(restaurants.get(2)[1])))
                                .andExpect(jsonPath("$[5].restaurant.cuisineType",
                                                Matchers.equalTo(restaurants.get(2)[2])))
                                .andExpect(jsonPath("$[5].restaurant.rating", Matchers.equalTo(restaurants.get(2)[3])));
        }

        @Test
        @Order(24)
        public void testDeleteRestaurantNotFound() throws Exception {

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/restaurants/148")
                                .contentType(MediaType.APPLICATION_JSON);
                mockMvc.perform(mockRequest).andExpect(status().isNotFound());

        }

        @Test
        @Order(25)
        public void testDeleteRestaurant() throws Exception {

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/restaurants/4")
                                .contentType(MediaType.APPLICATION_JSON);
                mockMvc.perform(mockRequest).andExpect(status().isNoContent());
        }

        @Test
        @Order(26)
        public void testAfterDeleteRestaurant() throws Exception {
                mockMvc.perform(get("/restaurants")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(3)))

                                .andExpect(jsonPath("$[0].id", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].name", Matchers.equalTo(restaurants.get(1)[0])))
                                .andExpect(jsonPath("$[0].address", Matchers.equalTo(restaurants.get(1)[1])))
                                .andExpect(jsonPath("$[0].cuisineType", Matchers.equalTo(restaurants.get(1)[2])))
                                .andExpect(jsonPath("$[0].rating", Matchers.equalTo(restaurants.get(1)[3])))

                                .andExpect(jsonPath("$[1].id", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].name", Matchers.equalTo(restaurants.get(2)[0])))
                                .andExpect(jsonPath("$[1].address", Matchers.equalTo(restaurants.get(2)[1])))
                                .andExpect(jsonPath("$[1].cuisineType", Matchers.equalTo(restaurants.get(2)[2])))
                                .andExpect(jsonPath("$[1].rating", Matchers.equalTo(restaurants.get(2)[3])))

                                .andExpect(jsonPath("$[2].id", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].name", Matchers.equalTo(restaurants.get(3)[0])))
                                .andExpect(jsonPath("$[2].address", Matchers.equalTo(restaurants.get(3)[1])))
                                .andExpect(jsonPath("$[2].cuisineType", Matchers.equalTo(restaurants.get(3)[2])))
                                .andExpect(jsonPath("$[2].rating", Matchers.equalTo(restaurants.get(3)[3])));
        }

        @Test
        @Order(27)
        public void testGetRestaurantByChefId() throws Exception {
                for (int i = 0; i < 6; i++) {
                        mockMvc.perform(get("/chefs/" + (i + 1) + "/restaurant")).andExpect(status().isOk())
                                        .andExpect(jsonPath("$", notNullValue()))
                                        .andExpect(jsonPath("$.id", Matchers.equalTo(chefs.get(i + 1)[4])))
                                        .andExpect(jsonPath("$.name",
                                                        Matchers.equalTo(restaurants.get(chefs.get(i + 1)[4])[0])))
                                        .andExpect(jsonPath("$.address",
                                                        Matchers.equalTo(restaurants.get(chefs.get(i + 1)[4])[1])))
                                        .andExpect(jsonPath("$.cuisineType",
                                                        Matchers.equalTo(restaurants.get(chefs.get(i + 1)[4])[2])))
                                        .andExpect(jsonPath("$.rating",
                                                        Matchers.equalTo(restaurants.get(chefs.get(i + 1)[4])[3])));
                }

        }

        @AfterAll
        public void cleanup() {
                jdbcTemplate.execute("drop table chef");
                jdbcTemplate.execute("drop table restaurant");
        }

}
