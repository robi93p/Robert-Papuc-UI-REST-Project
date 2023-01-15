package methods;

import common.DriverSet;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.interactions.Actions;
import io.qameta.allure.Allure;

public class UiMethods {

    public WebDriver driver;

    private String url = "https://www.libris.ro/";
    private String closeModal = "//*[contains(@class,'modal-open')]//a[contains(@class,'close modal')]";
    private String addToCart = "(//a[contains(@class,'adauga-in-cos')])";
    private String itemsInCart = "//div[contains(@class,'pr-section-desk')]";
    private String cartSize = "//section[contains(@class,'product-details')]";
    private String removeItem = "//span[contains(text(),'Sterge')]";
    private String emptyCart = "//*[contains(text(),'Coşul tău de cumpărături nu are momentan produse')]";

    int itemsAdded = 0;
    int itemsRemoved= 0;

    public void userLaunchWebSite() {
        driver = DriverSet.openBrowser();
        driver.get(url);
    }

    public boolean useCloseModal() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(closeModal)));
        Thread.sleep(1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(closeModal))).click();
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(closeModal)));
    }

    public boolean userAddProductsToCart(int numberOfItemsAdded) {
        itemsAdded = numberOfItemsAdded;

        int itemsOnPage = driver.findElements(By.xpath(addToCart)).size();

        if (itemsOnPage >= numberOfItemsAdded) {
            for (int i = 1; i <= numberOfItemsAdded + numberOfItemsAdded; i++) {
                try {
                    Thread.sleep(1000);
                    WebElement element = driver.findElement(By.xpath(addToCart + "[" + i + "]"));
                    Actions actions = new Actions(driver);
                    actions.moveToElement(element).click().build().perform();
                } catch (ElementClickInterceptedException | InterruptedException ignored) {
                }
            }
            int sizeOfCart = driver.findElements(By.xpath(cartSize)).size() - 1;
            return numberOfItemsAdded == sizeOfCart;
        } else {
            return false;
        }
    }

    public boolean isDisplayed(By obj) {
        try {
            WebElement ele = driver.findElement(obj);
            ele.isDisplayed();
            return true;
        } catch (Exception e) {
            Allure.addAttachment("Attachment", e.getMessage());
            return false;
        }
    }

    public boolean userRemovesProductsFromCart(int numbeOfItemsRemoved) throws Exception {
        itemsRemoved = numbeOfItemsRemoved;

        WebDriverWait wait = new WebDriverWait(driver, 30);

        int initialCartSize = driver.findElements(By.xpath(cartSize)).size() - 1;
        int newCartSize = 0;

        if (initialCartSize >= 1) {
                try {
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(removeItem))).click();
                } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                    System.out.println("error is " + e);
                }
                Thread.sleep(1000);
                if (isDisplayed(By.xpath(itemsInCart))) {
                    newCartSize = driver.findElements(By.xpath(cartSize)).size() - 1;
                    return initialCartSize - 1 == newCartSize;
                } else if (isDisplayed(By.xpath(emptyCart))) {
                    return true;
                } else {
                    return false;
                }
            }
        return userAddProductsToCart(numbeOfItemsRemoved);
    }

    public boolean checkCart() {
        return itemsRemoved > itemsAdded;
    }

    public boolean removeUnexistingItem() {
        WebDriverWait wait = new WebDriverWait(driver, 30);

        if (isDisplayed(By.xpath(emptyCart))) {
            if (isDisplayed(By.xpath(removeItem))) {
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(removeItem))).click();
            } else {
                return false;
            }
        }
        return removeUnexistingItem();
    }


}
