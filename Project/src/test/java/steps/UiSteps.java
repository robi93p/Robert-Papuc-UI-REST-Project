package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import methods.UiMethods;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class UiSteps {

    private UiMethods uiMethods = new UiMethods();

    @When("user navigate to website")
    public void userNavigateToWebsite() {
        uiMethods.userLaunchWebSite();
    }

    @And("user close modal")
    public void userCloseModal() throws InterruptedException {
        Assert.assertTrue(uiMethods.useCloseModal(), "Modal is not closed!");
    }

    @And("^user add (.+) products to Cart$")
    public void userAddProductsToCart(int numberOfItemsAdded) {
        Assert.assertTrue(uiMethods.userAddProductsToCart(numberOfItemsAdded));
    }

    @And("^user remove (.+) product from the Cart$")
    public void userRemoveNumberOfRemovedProductsProductFromTheCart(int numberOfItemsRemoved) throws Exception {
        for (int i = 1; i <= numberOfItemsRemoved; i++) {
            if (i == numberOfItemsRemoved && uiMethods.checkCart()) {
                Assert.assertFalse(uiMethods.removeUnexistingItem());
            } else {
                Assert.assertTrue(uiMethods.userRemovesProductsFromCart(numberOfItemsRemoved));
            }
        }
    }

}

