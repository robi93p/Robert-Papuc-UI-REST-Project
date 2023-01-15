package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import methods.RestApiMethods;
import org.junit.Assert;

public class RestApiSteps {

   RestApiMethods restApiMethods = new RestApiMethods();

    @Given("^the (.*) is ready to be executed$")
    public void theDummmyAPIIsReadyToBeExecuted(String ApiName) {
        if ("DummmyAPI".equals(ApiName)) {
            restApiMethods.ApiReadyToBeExecuted();
        } else {
            Assert.fail("Unexpected API name");
        }
    }

    @When("^user performs a GET request to retrieve employees with age number higher than (.*)$")
    public void userPerformsAGETRequestToRetrieveAllEmployeesWithAgeNumberHigherThan(int value) {
        restApiMethods.getUserInfo(value);
        Assert.assertEquals("Unexpected status code", 200, restApiMethods.ResponseBody.getStatusCode());
    }

    @And("^user adds new employee with age higher than (.*)$")
    public void userAddsNewEmployeeWithAgeCompareThanValue(String value) throws InterruptedException {
        restApiMethods.requestBodyData(value);
        restApiMethods.userAddNewData(value);
        Assert.assertEquals("Unexpected status code", 200, restApiMethods.ResponseBody.getStatusCode());
        Assert.assertTrue("ID not found", Integer.parseInt(restApiMethods.getNewEmployeeId()) > 0);
        Assert.assertTrue("Age is not higher than " + value, Integer.parseInt(restApiMethods.getNewEmployeeAge()) > Integer.parseInt(value));
    }

    @And("user updates an employee")
    public void userUpdatesAnEmployee() throws InterruptedException {
       restApiMethods.updateEmployee();
        Assert.assertEquals("Unexpected status code", 200, restApiMethods.ResponseBody.getStatusCode());
    }

    @And("^user checks the number of employees higher than (.*)$")
    public void userChecksTheNumberOfEmployeesHigherThanValue(int value) throws InterruptedException {
        Assert.assertTrue("Number of employees with age number higher than " + value + " is not modified!", restApiMethods.getUpdatedListOfEmployees(value));
    }

    @Then("user deletes the employee")
    public void userDeletesTheEmployee() throws InterruptedException {
        restApiMethods.deleteEmployee();
        Assert.assertEquals("Unexpected status code", 200, restApiMethods.ResponseBody.getStatusCode());
    }
}
