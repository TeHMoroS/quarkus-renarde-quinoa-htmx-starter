package io.quarkus.htmx;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.Header;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

@QuarkusTest
class PagesControllerTest {

    private static final String BODY_TAG = "<body class=\"min-vh-100 d-flex flex-column\">";
    private static final String PAGE_1_LINK_CONTENT = "Go to page 2";
    private static final String PAGE_2_LINK_CONTENT = "Go to page 1";
    private static final Header HTMX_VALID_HEADER = new Header("HX-Request", "true");
    private static final Header HTMX_INVALID_HEADER = new Header("HX-Request", "non-true");

    @Test
    void testIndexRedirectToPage1() {
        given()
            .when().get("/")
            .then()
            .statusCode(200)
            .body(
                containsString(BODY_TAG), // full template response
                containsString(PAGE_1_LINK_CONTENT)
            );
    }

    @Test
    void testPage1ReturningFullTemplateWithoutHtmxHeader() {
        given()
            .when().get("/page1")
            .then()
            .statusCode(200)
            .body(
                containsString(BODY_TAG), // full template response
                containsString(PAGE_1_LINK_CONTENT)
            );
    }

    @Test
    void testPage1ReturningFullTemplateWithNonTrueHtmxHeader() {
        given()
            .when()
            .header(HTMX_INVALID_HEADER)
            .get("/page1")
            .then()
            .statusCode(200)
            .body(
                containsString(BODY_TAG), // full template response
                containsString(PAGE_1_LINK_CONTENT)
            );
    }

    @Test
    void testPage1ReturningShortTemplateWithTrueHtmxHeader() {
        given()
            .when()
            .header(HTMX_VALID_HEADER)
            .get("/page1")
            .then()
            .statusCode(200)
            .body(
                not(containsString(BODY_TAG)), // short template response
                containsString(PAGE_1_LINK_CONTENT)
            );
    }

    @Test
    void testPage2ReturningShortTemplateWithTrueHtmxHeader() {
        given()
            .when()
            .header(HTMX_VALID_HEADER)
            .get("/page2")
            .then()
            .statusCode(200)
            .body(
                not(containsString(BODY_TAG)), // full template response
                containsString(PAGE_2_LINK_CONTENT)
            );
    }
}
