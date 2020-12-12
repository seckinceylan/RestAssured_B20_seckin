package day3;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
public class GithubRestAPITest {




    @DisplayName("Test GitHub GET /users/{username}")
    @Test
    public void testGitHub(){
        String username1= "seckinceylan";
        String username2= "CybertekSchool";

        given()
                .pathParam("username",username1).
        when()
                .get("https://api.github.com/users/{username}").
        then()
                .assertThat()
                .statusCode(is (200))
                .contentType(ContentType.JSON)
                .header("server", "GitHub.com")
                .body("login",is ( username1))
                ;

    }

}
