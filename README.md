In this project, we will build a Spring Boot application to create a REST service for the 'DineMaster' application. Through the 'DineMaster' application, users can view the details of chefs working for specific restaurants.

For this application, we need to model two entities: `Chef` and `Restaurant`. The `Chef` entity has a Many-to-One relationship with the `Restaurant` entity.

<details>
<summary>**Implementation Files**</summary>

Use these files to complete the implementation:

- `ChefController.java`
- `ChefRepository.java`
- `ChefJpaService.java`
- `ChefJpaRepository.java`
- `Chef.java`
- `RestaurantController.java`
- `RestaurantRepository.java`
- `RestaurantJpaService.java`
- `RestaurantJpaRepository.java`
- `Restaurant.java`

</details>

Create a database that contains two tables `restaurant` and `chef` using the given database schema.

You can refer to this [session](https://learning.ccbp.in/course?c_id=e345dfa4-f5ce-406e-b19a-4ed720c54136&s_id=6a60610e-79c2-4e15-b675-45ddbd9bbe82&t_id=f880166e-2f51-4403-81a0-d2430694dae8), for creating a database.

_Create the SQL files and compose accurate queries to run the application. Inaccurate SQL files will result in test case failures._

<details>
<summary>**Database Schema**</summary>

#### Chef Table

|     Columns     |                 Type                  |
| :-------------: | :-----------------------------------: |
|       id        | INTEGER (Primary Key, Auto Increment) |
|    firstName    |                 TEXT                  |
|    lastName     |                 TEXT                  |
|    expertise    |                 TEXT                  |
| experienceYears |                INTEGER                |
|  restaurantId   |                INTEGER                |

#### Restaurant Table

|   Columns   |                 Type                  |
| :---------: | :-----------------------------------: |
|     id      | INTEGER (Primary Key, Auto Increment) |
|    name     |                 TEXT                  |
|   address   |                 TEXT                  |
| cuisineType |                 TEXT                  |
|   rating    |         INTEGER (Foreign Key)         |

You can use the given sample data to populate the tables.

<details>
<summary>**Sample Data**</summary>

#### Restaurant Data

|  id   |    name     |   address   | cuisineType | rating |
| :---: | :---------: | :---------: | :---------: | :----: |
|   1   | Fine Dining | 123 Main St |  European   |   5    |
|   2   |  Taco Bell  | 456 Elm St  |  Fast Food  |   3    |
|   3   | Sushi Place | 789 Oak St  |  Japanese   |   4    |

#### Chef Data

| firstName | lastName |  expertise  | experienceYears | restaurantId |
| :-------: | :------: | :---------: | :-------------: | :----------: |
|   John    |   Doe    |  Sous Chef  |        5        |      1       |
|   Jane    |   Doe    | Pastry Chef |        7        |      1       |
|   Mike    |  Smith   |  Head Chef  |       10        |      2       |
|   Emily   | Johnson  | Sushi Chef  |        8        |      3       |
|   Anna    | Williams | Pastry Chef |        6        |      3       |
|   Mark    |  Brown   |  Sous Chef  |        4        |      2       |

</details>

</details>

<MultiLineNote>

Use only `chef` and `restaurant` as the table names in your code.

</MultiLineNote>

### Completion Instructions

- `Chef.java`: The `Chef` class should contain the following attributes.

    |    Attribute    |    Type    |
    | :-------------: | :--------: |
    |       id        |    int     |
    |    firstName    |   String   |
    |    lastName     |   String   |
    |    expertise    |   String   |
    | experienceYears |    int     |
    |   restaurant    | Restaurant |

- `ChefRepository.java`: Create an `interface` containing the required methods.
- `ChefJpaService.java`: Update the service class with logic for managing chefs data.
- `ChefController.java`: Create the controller class to handle HTTP requests.
- `ChefJpaRepository.java`: Create an interface that implements the `JpaRepository` interface.
  
- `Restaurant.java`: The `Restaurant` class should contain the following attributes.

    |  Attribute  |  Type  |
    | :---------: | :----: |
    |     id      |  int   |
    |    name     | String |
    |   address   | String |
    | cuisineType | String |
    |   rating    |  int   |

- `RestaurantRepository.java`: Create an `interface` containing the required methods.
- `RestaurantJpaService.java`: Update the service class with logic for managing restaurant data.
- `RestaurantController.java`: Create the controller class to handle HTTP requests.
- `RestaurantJpaRepository.java`: Create an interface that implements the `JpaRepository` interface.

Implement the following APIs.

<details>
<summary>**API 1: GET /restaurants**</summary>

#### Path: `/restaurants`

#### Method: `GET`

#### Description:

Returns a list of all restaurants in the `restaurant` table.

#### Response

```json
[
    {
        "id": 1,
        "name": "Fine Dining",
        "address": "123 Main St",
        "cuisineType": "European",
        "rating": 5
    },
    ...
]
```

</details>

<details>
<summary>**API 2: POST /restaurants**</summary>

#### Path: `/restaurants`

#### Method: `POST`

#### Description:

Creates a new restaurant in the `restaurant` table. The `restaurantId` is auto-incremented.

#### Request

```json
{
    "name": "Pasta House",
    "address": "222 Pasta St",
    "cuisineType": "Italian",
    "rating": 4
}
```

#### Response

```json
{
    "id": 4,
    "name": "Pasta House",
    "address": "222 Pasta St",
    "cuisineType": "Italian",
    "rating": 4
}
```

</details>

<details>
<summary>**API 3: GET /restaurants/{restaurantId}**</summary>

#### Path: `/restaurants/{restaurantId}`

#### Method: `GET`

#### Description:

Returns a restaurant based on the `restaurantId`. If the given `restaurantId` is not found in the `restaurant` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.


#### Success Response

```json
{
    "id": 2,
    "name": "Taco Bell",
    "address": "456 Elm St",
    "cuisineType": "Fast Food",
    "rating": 3
}
```

</details>

<details>
<summary>**API 4: PUT /restaurants/{restaurantId}**</summary>

#### Path: `/restaurants/{restaurantId}`

#### Method: `PUT`

#### Description:

Updates the details of a Restaurant based on the `RestaurantId` and returns the updated Restaurant details. If the given `RestaurantId` is not found in the `Restaurant` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Request

```json
{
    "rating": 4
}
```

#### Success Response

```json
{
    "id": 2,
    "name": "Taco Bell",
    "address": "456 Elm St",
    "cuisineType": "Fast Food",
    "rating": 4
}
```

</details>

<details>
<summary>**API 5: DELETE /restaurants/{restaurantId}**</summary>

#### Path: `/restaurants/{restaurantId}`

#### Method: `DELETE`

#### Description:

Deletes a restaurant from the `restaurant` table based on the `restaurantId` and returns the status code `204`(raise `ResponseStatusException` with `HttpStatus.NO_CONTENT`). If the given `restaurantId` is not found in the `restaurant` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`. 

</details>

<details>
<summary>**API 6: GET /restaurants/chefs**</summary>

#### Path: `/restaurants/chefs`

#### Method: `GET`

#### Description:

Returns a list of all chefs in the `chef` table.

#### Response

```json
[
    {
        "id": 1,
        "firstName": "John",
        "lastName": "Doe",
        "expertise": "Sous Chef",
        "experienceYears": 5,
        "restaurant": {
            "id": 1,
            "name": "Fine Dining",
            "address": "123 Main St",
            "cuisineType": "European",
            "rating": 5
        }
    },
    ...
]
```

</details>

<details>
<summary>**API 7: POST /restaurants/chefs**</summary>

#### Path: `/restaurants/chefs`

#### Method: `POST`

#### Description:

Creates a new chef in the `chef` table and create an association between the chef and the restaurant based on the `id` of the `restaurant` field. The `chefId` is auto-incremented.

#### Request

```json
{
    "firstName": "Sara",
    "lastName": "Lee",
    "expertise": "Pastry Chef",
    "experienceYears": 9,
    "restaurant": {
        "id": 4
    }
}
```

#### Response

```json
{
    "id": 7,
    "firstName": "Sara",
    "lastName": "Lee",
    "expertise": "Pastry Chef",
    "experienceYears": 9,
    "restaurant": {
        "id": 4,
        "name": "Pasta House",
        "address": "222 Pasta St",
        "cuisineType": "Italian",
        "rating": 4
    }
}
```

</details>

<details>
<summary>**API 8: GET /restaurants/chefs/{chefId}**</summary>

#### Path: `/restaurants/chefs/{chefId}`

#### Method: `GET`

#### Description:

Returns a chef based on the `chefId`. If the given `chefId` is not found in the `chef` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.


#### Success Response

```json
{
    "id": 2,
    "firstName": "Jane",
    "lastName": "Doe",
    "expertise": "Pastry Chef",
    "experienceYears": 7,
    "restaurant": {
        "id": 1,
        "name": "Fine Dining",
        "address": "123 Main St",
        "cuisineType": "European",
        "rating": 5
    }
}
```

</details>

<details>
<summary>**API 9: PUT /restaurants/chefs/{chefId}**</summary>

#### Path: `/restaurants/chefs/{chefId}`

#### Method: `PUT`

#### Description:

Updates the details of a chef based on the `chefId` and returns the updated chef details. If the `id` in the `restaurant` field is provided, update the association between the chef and the restaurant based on the `id`. If the given `chefId` is not found in the `chef` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Request

```json
{
    "experienceYears": 8
}
```

#### Success Response

```json
{
    "id": 2,
    "firstName": "Jane",
    "lastName": "Doe",
    "expertise": "Pastry Chef",
    "experienceYears": 8,
    "restaurant": {
        "id": 1,
        "name": "Fine Dining",
        "address": "123 Main St",
        "cuisineType": "European",
        "rating": 5
    }
}
```

</details>

<details>
<summary>**API 10: DELETE /restaurants/chefs/{chefId}**</summary>

#### Path: `/restaurants/chefs/{chefId}`

#### Method: `DELETE`

#### Description:

Deletes a chef from the `chef` table based on the `chefId` and returns the status code `204`(raise `ResponseStatusException` with `HttpStatus.NO_CONTENT`). If the given `chefId` is not found in the `chef` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

</details>

<details>
<summary>**API 11: GET /chefs/{chefId}/restaurant**</summary>

#### Path: `/chefs/{chefId}/restaurant`

#### Method: `GET`

#### Description:

Returns a restaurant of the chef based on the `chefId`. If the given `chefId` is not found in the `chef` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Response

```json
{
    "id": 1,
    "name": "Fine Dining",
    "address": "123 Main St",
    "cuisineType": "European",
    "rating": 5
}
```

</details>

**Do not modify the code in `DineMasterApplication.java`**