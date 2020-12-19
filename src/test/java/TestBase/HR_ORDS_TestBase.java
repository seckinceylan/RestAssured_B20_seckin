package TestBase;

import Utility.ConfigurationReader;
import Utility.DB_Utility;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Utility.DB_Utility;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pojo.Country;
import java.util.List;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public abstract class HR_ORDS_TestBase {
    @BeforeAll
    public static void setUp(){
        baseURI = ConfigurationReader.getProperty("ords.baseURL");
        basePath = ConfigurationReader.getProperty("ords.basePath");
        //create DB connection here
        DB_Utility.createConnection( ConfigurationReader.getProperty("hr.database.url"),
                ConfigurationReader.getProperty("hr.database.username") ,
                ConfigurationReader.getProperty("hr.database.password")
        );
    }
    @AfterAll
    public static void tearDown(){
        reset();
        // Destroy DB connection here
        DB_Utility.destroy();
    }

}
