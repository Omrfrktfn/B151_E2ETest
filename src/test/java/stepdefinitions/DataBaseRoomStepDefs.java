package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pojos.RoomPojo;

import java.sql.*;

import static org.junit.Assert.assertEquals;
import static stepdefinitions.MedunnaRoomStepDefs.odaNo;

public class DataBaseRoomStepDefs {

    Statement statement;
    Connection connection;

    @Given("Database baglantisini kur")
    public void databaseBaglantisiniKur() throws SQLException {
        //1.adim connection olustur.
        connection = DriverManager.getConnection("jdbc:postgresql://medunna.com:5432/medunna_db_v2",
                "select_user", "Medunna_pass_@6");

        //2.adim statement olustur.
        statement = connection.createStatement();

        //3.adim

    }

    @Then("Oda bilgilerini dogrula")
    public void odaBilgileriniDogrula() throws SQLException {
        //3.adim query sorgu olustur.
        ResultSet resultSet = statement.executeQuery("select * from room where room_number = " + odaNo);
        resultSet.next();

        //room number kullanarak id no aldik;
        //  int roomId = resultSet.getInt("id");
        // System.out.println("roomId = " + roomId);

        RoomPojo expectedData = new RoomPojo(odaNo, "SUITE", true, 123, "End To End Test için oluşturulmuştur");
        assertEquals(expectedData.getRoomNumber(), resultSet.getInt("room_number"));
        assertEquals(expectedData.getRoomType(), resultSet.getString("room_type"));
        assertEquals(expectedData.isStatus(), resultSet.getBoolean("status"));
        assertEquals(expectedData.getPrice(), resultSet.getInt("price"));
        assertEquals(expectedData.getDescription(), resultSet.getString("description"));

    }
}
