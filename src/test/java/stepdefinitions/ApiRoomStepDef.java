package stepdefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import pojos.RoomPojo;

import java.util.List;

import static base_url.MedunnaBaseUrl.spec;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static stepdefinitions.MedunnaRoomStepDefs.odaId;
import static stepdefinitions.MedunnaRoomStepDefs.odaNo;

public class ApiRoomStepDef {
    Response response;
    RoomPojo expectedData;

    @Given("Get Request gonderilir")
    public void getRequestGonderilir() {

        // Oda ID'sini alma --> https://medunna.com/api/rooms?sort=createdDate%2Cdesc
        spec.pathParams("first", "api", "second", "rooms")
                .queryParam("sort", "createdDate,desc");
        Response response1 = given(spec).when().get("{first}/{second}");
        Object roomId = response1.jsonPath().getList("findAll{it.roomNumber==" + odaNo + "}.id").get(0);
        System.out.println("Room ID: " + roomId);


        System.out.println("*****************************");

        // Set the URL --> https://medunna.com/api/rooms/72086
        spec.pathParams("first", "api", "second", "rooms", "third", roomId);

        // Set the expected data
        expectedData = new RoomPojo(odaNo, "SUITE", true, 123.00, "End To End Test için oluşturulmuştur");
        System.out.println(expectedData);

        // Send the request and get the response
        response = given(spec).when().get("{first}/{second}/{third}");
        //parentez icine kac tane parametre varsa eklenir.

    }

    @Then("Body dogrulanir")
    public void body_dogrulanir() throws JsonProcessingException {

        //objectMapper jackson databind olandan secilir
        //redvalue da exception firlatilacak
        //ve daha sonra assertion'lar yazilacak

        RoomPojo actualData = new ObjectMapper().readValue(response.asString(), RoomPojo.class);
        assertEquals(200, response.statusCode());
        assertEquals(expectedData.getRoomNumber(), actualData.getRoomNumber());
        assertEquals(expectedData.getRoomType(), actualData.getRoomType());
        assertEquals(expectedData.isStatus(), actualData.isStatus());
        assertEquals(expectedData.getPrice(), actualData.getPrice());
        assertEquals(expectedData.getDescription(), actualData.getDescription());
    }

}