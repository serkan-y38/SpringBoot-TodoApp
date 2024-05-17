## Register
##### @Post
This endpoint is used to register a new user for authentication.

## Sample Request

[http://localhost:8080/api/v1/auth/register](http://localhost:8080/api/v1/auth/register)

Request Body

``` json
{
    "email": "test2@mail",
    "password": "123456",
    "firstname": "test2 name",
    "lastname": "test2 lastname"
}

 ```

## Login
##### @Post

This endpoint is used to authenticate a user and generate a token for accessing protected resources.

## Sample Request - Response

[http://localhost:8080/api/v1/auth/login](http://localhost:8080/api/v1/auth/login)

Request Body

``` json
{
    "email": "test1@mail",
    "password": "123456"
}

 ```

Response Body

``` json
{
    {"role":"USER","id":1}
}

 ```

# Jwt Token
##### @Get
Authorization: Bearer Token

[http://localhost:8080/api/v1/todo](http://localhost:8080/api/v1/todo)

This endpoint is used to retrieve logged in user's jwt token. The request does not require a request body.

## Sample Response

``` json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MUBtYWlsIiwiaWF0IjoxNzE1ODg4NjEyLCJleHAiOjE3MTU5NzUwMTJ9.-oHqYyOsA3wTrkCbnxCcIgYByNUXxprLquPlCud2LMc"
}

 ```

## Create Todo
##### @Post
Authorization: Bearer Token

This endpoint is used to create a todo item.

## Sample Request

[http://localhost:8080/api/v1/todo/create-todo](http://localhost:8080/api/v1/todo/create-todo)

Request body

``` json
{
    "title": "Relaxation Time",
    "description": "Take a break, listen to calming music, or do some meditation for 15 minutes.",
    "due_date": "2025-10-11",
    "user_id": 1
}

 ```

## Update Todo
##### @Put
Authorization: Bearer Token

This endpoint is used to update a specific todo item.

## Request

### Path Parameters

- `1` (integer, required): The ID of the user.
    
- `2` (integer, required): The ID of the todo.
    

## Sample

[http://localhost:8080/api/v1/todo/update-todo/1/1](http://localhost:8080/api/v1/todo/update-todo/1/1)

Request body

``` json
{
    "title": "Read a Book updated",
    "description": " Spend 20 minutes reading a chapter from your current book. updated",
    "due_date": "2025-12-12"
}

 ```

## Delete Todo
##### @Delete
Authorization: Bearer Token

This endpoint sends an HTTP DELETE request to delete a specific todo item.

## Request

### Path Parameters

- `1` (integer, required): The ID of the user.
    
- `2` (integer, required): The ID of the todo.
    

## Sample

[http://localhost:8080/api/v1/todo/delete-todo/1/7](http://localhost:8080/api/v1/todo/delete-todo/1/7)

# Get Todo
##### @Get
Authorization: Bearer Token

This endpoint makes an HTTP GET request to retrieve a specific todo item.

## Request

### Path Parameters

- `1` (integer, required): The ID of the user.
    
- `2` (integer, required): The ID of the todo.
    

## Sample Response

[http://localhost:8080/api/v1/todo/get-todo/1/5](http://localhost:8080/api/v1/todo/get-todo/1/5)

The response will be a JSON object conforming to the following schema:

``` json
{
    "id": 5,
    "title": "Pay Bills",
    "description": "Settle electricity, internet, and phone bills for the month.",
    "due_date": "2025-10-11T00:00:00.000+00:00",
    "user_id": 1
}

 ```

# Get All Todos
##### @Get
This endpoint makes an HTTP GET request to retrieve all todos for a specific user.

### Path Parameters

- `userId` (integer, required): The ID of the user for whom the todos are being retrieved.
    

### Sample Response

The response will include an array of todo objects.

[http://localhost:8080/api/v1/todo/get-all-todos/1](http://localhost:8080/api/v1/todo/get-all-todos/1)

``` json
[
    {
        "id": 1,
        "title": "Read a Book updated",
        "description": " Spend 20 minutes reading a chapter from your current book. updated",
        "due_date": "2025-12-12T00:00:00.000+00:00",
        "user_id": 1
    },
    {
        "id": 2,
        "title": "Grocery Shopping",
        "description": "Buy milk, eggs, bread, and fruits",
        "due_date": "2025-10-11T00:00:00.000+00:00",
        "user_id": 1
    },
    {
        "id": 3,
        "title": "Clean Room",
        "description": "Tidy up the bedroom, vacuum the floor, and organize the closet.",
        "due_date": "2025-10-11T00:00:00.000+00:00",
        "user_id": 1
    },
    {
        "id": 4,
        "title": "Exercise",
        "description": "Go for a 30-minute walk or do a quick home workout routine.",
        "due_date": "2025-10-11T00:00:00.000+00:00",
        "user_id": 1
    },
    {
        "id": 5,
        "title": "Pay Bills",
        "description": "Settle electricity, internet, and phone bills for the month.",
        "due_date": "2025-10-11T00:00:00.000+00:00",
        "user_id": 1
    },
    {
        "id": 6,
        "title": "Relaxation Time",
        "description": "Take a break, listen to calming music, or do some meditation for 15 minutes.",
        "due_date": "2025-10-11T00:00:00.000+00:00",
        "user_id": 1
    }
]

 ```

# Paginate-todos
##### @Get
Authorization: Bearer Token

This endpoint retrieves a paginated list of to-do items. The request should include the page number, page size, order by field, and direction of sorting as query parameters. The response will be a JSON object representing the paginated list of to-do items, including the total number of pages, total number of items, and the to-do items for the requested page.

## Request

### Query Parameters

- `page` (integer, required): The page number.
    
- `size` (integer, required): The number of items per page.
    
- `orderBy` (string, required): The field to order the results by.
    
- `direction` (string, required): The direction of the ordering (ASC or DESC).
    

### Path Parameters

- `1` (integer, required): The ID of the todo.
    

## Sample Response

[http://localhost:8080/api/v1/todo/paginate-todos/1?page=0&amp;size=3&amp;orderBy=id&amp;direction=ASC](http://localhost:8080/api/v1/todo/paginate-todos/1?page=0&size=3&orderBy=id&direction=ASC)

``` json
[
    {
        "id": 1,
        "title": "Read a Book updated",
        "description": " Spend 20 minutes reading a chapter from your current book. updated",
        "due_date": "2025-12-12T00:00:00.000+00:00",
        "user_id": 1
    },
    {
        "id": 2,
        "title": "Grocery Shopping",
        "description": "Buy milk, eggs, bread, and fruits",
        "due_date": "2025-10-11T00:00:00.000+00:00",
        "user_id": 1
    },
    {
        "id": 3,
        "title": "Clean Room",
        "description": "Tidy up the bedroom, vacuum the floor, and organize the closet.",
        "due_date": "2025-10-11T00:00:00.000+00:00",
        "user_id": 1
    }
]

 ```

# Todo Search
##### @Get
Authorization: Bearer Token

This endpoint retrieves a list of todos based on the provided search string and query parameters.

## Request

### Query Parameters

- `query` (string, required): The search query.
    
- `page` (integer, required): The page number.
    
- `size` (integer, required): The number of items per page.
    
- `orderBy` (string, required): The field to order the results by.
    
- `direction` (string, required): The direction of the ordering (ASC or DESC).
    

### Path Parameters

- `1` (integer, required): The ID of the todo.
    

## Sample Response

[http://localhost:8080/api/v1/todo/search-todos/1?query=clean&amp;page=0&amp;size=2&amp;orderBy=id&amp;direction=ASC](http://localhost:8080/api/v1/todo/search-todos/1?query=clean&page=0&size=2&orderBy=id&direction=ASC)

The response will be a JSON object conforming to the following schema:

``` json
[
    {
        "id": 3,
        "title": "Clean Room",
        "description": "Tidy up the bedroom, vacuum the floor, and organize the closet.",
        "due_date": "2025-10-11T00:00:00.000+00:00",
        "user_id": 1
    }
]

 ```
<br/>

![Screenshot 2024-05-17 154334](https://github.com/serkan-y38/SpringBoot-TodoApp/assets/96957200/6ec901b9-12e6-4d9a-951c-1256cce28d2a)


