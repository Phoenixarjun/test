INSERT INTO restaurant (name, address, cuisineType, rating)
SELECT 'Fine Dining', '123 Main St', 'European', 5
WHERE NOT EXISTS(SELECT 1 FROM restaurant WHERE id = 1);

INSERT INTO restaurant (name, address, cuisineType, rating)
SELECT 'Taco Bell', '456 Elm St', 'Fast Food', 3
WHERE NOT EXISTS(SELECT 2 FROM restaurant WHERE id = 3);

INSERT INTO restaurant (name, address, cuisineType, rating)
SELECT 'Sushi Place', '789 Oak St', 'Japanese', 4
WHERE NOT EXISTS(SELECT 3 FROM restaurant WHERE id = 3);

INSERT INTO chef (firstName, lastName, expertise, experienceYears, restaurantId)
SELECT 'John', 'Doe', 'Sous Chef', 5, 1
WHERE NOT EXISTS(SELECT 1 FROM chef WHERE id = 1);

INSERT INTO chef (firstName, lastName, expertise, experienceYears, restaurantId)
SELECT 'Jane', 'Doe', 'Pastry Chef', 7, 1
WHERE NOT EXISTS(SELECT 2 FROM chef WHERE id = 2);

INSERT INTO chef (firstName, lastName, expertise, experienceYears, restaurantId)
SELECT 'Mike', 'Smith', 'Head Chef', 10, 2
WHERE NOT EXISTS(SELECT 3 FROM chef WHERE id = 3);

INSERT INTO chef (firstName, lastName, expertise, experienceYears, restaurantId)
SELECT 'Emily', 'Johnson', 'Sushi Chef', 8, 3
WHERE NOT EXISTS(SELECT 4 FROM chef WHERE id = 4);

INSERT INTO chef (firstName, lastName, expertise, experienceYears, restaurantId)
SELECT 'Anna', 'Williams', 'Pastry Chef', 6, 3
WHERE NOT EXISTS(SELECT 5 FROM chef WHERE id = 5);

INSERT INTO chef (firstName, lastName, expertise, experienceYears, restaurantId)
SELECT 'Mark', 'Brown', 'Sous Chef', 4, 2
WHERE NOT EXISTS(SELECT 6 FROM chef WHERE id = 6);