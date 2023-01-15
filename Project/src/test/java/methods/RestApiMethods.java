package methods;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;

public class RestApiMethods {

    private String baseUri;
    private String getEndpoint = "employees";
    private String postEndpoint = "create";
    private String putEndpoint = "update/";
    private String deleteEndpoint = "delete/";

    public Response ResponseBody;
    String response;

    HashMap<String, Object> employeeAttributes = new HashMap<>();
    ArrayList<Integer> employeeAge = new ArrayList<>();

    Random random = new Random();

    int count = 0;
    String newEmployeeId;
    int newEmployeeIdInt;

    public void ApiReadyToBeExecuted() {
        baseUri = "https://dummy.restapiexample.com/api/v1/";
    }

    public void getUserInfo(int value) {
        ResponseBody = given().baseUri(baseUri).contentType(ContentType.JSON).
                when().get(getEndpoint).
                then().extract().response();

        System.out.println("---User performs a GET request to retrieve employees with age number higher than " + value + "---");

        employeeAge = ResponseBody.path("data.employee_age");

        int size = 0;
        for (int age : employeeAge) {
            size = employeeAge.size();
            if (age > value) {
                count++;
            }
        }
        System.out.println("Total number of employees: " + size);
        System.out.println("Employees with age higher than " + value + ": " + count);
    }

    public void requestBodyData(String value) {

        int convertedAgetoInt = Integer.parseInt(value);

        int age = random.nextInt(convertedAgetoInt + 1, 65);
        value = Integer.toString(age);

        employeeAttributes.put("name", "Robert Tester");
        employeeAttributes.put("salary", "100");
        employeeAttributes.put("age", value);
    }

    public void userAddNewData(String value) throws InterruptedException {

        Thread.sleep(60000);

        ResponseBody = given().baseUri(baseUri).contentType(ContentType.JSON).
                body(employeeAttributes).
                when().post(postEndpoint).
                then().extract().response();

        System.out.println("---User adds new employee with age higher than " + value + "---");
        response = ResponseBody.getBody().asString();
        System.out.println(response);
    }

    public String getNewEmployeeId() {
        if (response.contains("id")) {
            String responseSplit = response.split("id")[1];
            String getEmployeeId = responseSplit.substring(responseSplit.indexOf(":") + 1, responseSplit.indexOf(","));
            newEmployeeId = getEmployeeId.replaceAll("[^0-9]", "");
        } else {
            Assert.fail("ID not found!");
        }
        System.out.println("The new employee ID is: " + newEmployeeId);
        return newEmployeeId;
    }

    public String getNewEmployeeAge() {
        String newEmployeeAge = "";
        if (response.contains("age")) {
            String ageSplit = response.split("age")[1];
            String getEmployeeAge = ageSplit.substring(ageSplit.indexOf(":") + 1, ageSplit.indexOf(","));
            newEmployeeAge = getEmployeeAge.replaceAll("[^0-9]", "");
        } else {
            Assert.fail("Age not found!");
        }
        System.out.println("The new employee age is: " + newEmployeeAge);
        return newEmployeeAge;
    }


    public void updateEmployee() throws InterruptedException {

        Thread.sleep(60000);

        newEmployeeIdInt = Integer.parseInt(newEmployeeId);

        employeeAttributes.replace("salary", 300);

        ResponseBody = given().baseUri(baseUri).contentType(ContentType.JSON).
                body(employeeAttributes).
                when().put(putEndpoint + newEmployeeIdInt).
                then().extract().response();

        System.out.println("---User updates the " + newEmployeeIdInt + " employee salary---");
        System.out.println(ResponseBody.getBody().asString());
    }

    public boolean getUpdatedListOfEmployees(int value) throws InterruptedException {

        Thread.sleep(60000);

        ResponseBody = given().baseUri(baseUri).contentType(ContentType.JSON).
                when().get(getEndpoint).
                then().
                assertThat().statusCode(200).
                extract().response();

        System.out.println(ResponseBody.getBody().asString());

        List<Integer> updatedEmployeesAgeList = ResponseBody.path("data.employee_age");

        int countEmployeeAgeAfterModif = 0;

        for (int age : updatedEmployeesAgeList) {
            if (age > value) {
                countEmployeeAgeAfterModif++;
            }
        }
        System.out.println("---user performs a GET request to retrieve employees with age number higher than " + value + "---");
        System.out.println("Now, the total number of employees with age number higher than " + value + " is: " + countEmployeeAgeAfterModif);

        return countEmployeeAgeAfterModif != count;
    }

    public void deleteEmployee() throws InterruptedException {
        Thread.sleep(60000);

        newEmployeeIdInt = Integer.parseInt(newEmployeeId);
        System.out.println(newEmployeeIdInt);

        ResponseBody = given().baseUri(baseUri).contentType(ContentType.JSON).
                when().delete(deleteEndpoint + newEmployeeIdInt).
                then().extract().response();

        System.out.println("---User deletes the employee---");
        System.out.println(ResponseBody.getBody().asString());
    }

}






