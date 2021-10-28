package petstore;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Data;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class Pet {
    // Atributes
    String url = "https://petstore.swagger.io/v2/pet";

    Data data;

    @BeforeMethod
    public void setup(){
        data = new Data();
    }

    // Incluir - Create - Post
    @Test(priority = 1)
    public void incluirPet() throws IOException {
        String jsonBody = data.lerJson("db/petPOST.json");

        // Sintaxe Gherkin
        // Given - When - Then

        given().contentType("application/json")
                .log().all().body(jsonBody)
        .when().post(url)
        .then().log().all()
                .statusCode(200)
                .body("name", is("Nina"))
                .body("status", is("available"))
                .body("category.name", is("dog"))
                .body("tags.name", contains("assertiva teste API"))
        ;
    }
    // Buscar por ID - Método GET
    @Test(priority = 2)
    public void consultarPet(){
        String petId = "9223372000001103320";
        String nameAnimal =
                given().contentType("application/json")
                        .log().all()
                .when().get(url + "/" +petId)
                .then().log().all()
                        .statusCode(200)
                        .body("id", is(9223372000001103320L))
                        .extract().path("name");

        System.out.println("Nome: " +nameAnimal);
    }
    // Editar PET - Método PUT
    @Test(priority = 3)
    public void editarPet() throws IOException {
        String jsonBody = data.lerJson("db/petPut.json");

        // Requisição PUT para editar o nome, status e categoria do animal
        given().contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when().put(url)
        .then().log().all()
                .statusCode(200)
                .body("name", is("Lunna"))
                .body("status", is("sold"))
                .body("category.name", is("cat"));
    }
    // Método para deletar PET pelo ID
    @Test(priority = 4)
    public void excluirPet(){
        String petId = "9223372000001103320";

        given().contentType("application/json")
                .log().all()
        .when().delete(url +"/" +petId)
        .then().log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(petId));
    }
}
