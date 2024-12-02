
    @Test
    public void testGetCustomer_InvalidRequest_Returns5xx() {
        given()
            .baseUri(baseUrlOfSut)
            .when()
            .get("/customer?name=invalidName")
            .then()
            .statusCode(5xx);
    }

    @Test
    public void testCreateCustomer_InvalidRequest_Returns5xx() {
        given()
            .baseUri(baseUrlOfSut)
            .body("{\"email\":\"ivan.petrov@yandex.ru\",\"password\":\"petrov\",\"name\":\"Ivan Petrov\",\"phone\":\"+7 123 456 78 90\",\"address\":\"Riesstrasse 18\"}")
            .contentType("application/json")
            .when()
            .post("/register?invalidParam=invalidValue")
            .then()
            .statusCode(5xx);
    }
}
