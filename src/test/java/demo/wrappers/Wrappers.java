package demo.wrappers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

     ChromeDriver driver;
     WebDriverWait wait;

     public Wrappers(ChromeDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
     } 

     public boolean SendKeys(WebElement wb, String st) throws InterruptedException{
        wait.until(ExpectedConditions.visibilityOf(wb));
        if(wb.isDisplayed()){
            wb.click();
            Thread.sleep(1000);
            wb.clear();
            Thread.sleep(1000);
            wb.sendKeys(st);
            return true;
        }
        return false;
     }

     public boolean Click(WebElement wb) throws InterruptedException{
        wait.until(ExpectedConditions.visibilityOf(wb));
        if(wb.isDisplayed()){
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollIntoView(true);", wb);
            Thread.sleep(1000);
            wb.click();
            return true;
        }
        return false;
     }


     public boolean NavigateToUrl(String url) throws InterruptedException{
        if(!(driver.getCurrentUrl().contains("docs.google.com/forms"))){
            driver.get(url);
            wait.until(ExpectedConditions.urlContains("docs.google.com/forms"));
            Thread.sleep(2000);
            return true;
        }
        return true;

     }

     public String getEpochTime(){

        long epochTime = Instant.now().getEpochSecond();

        // Format the message with the epoch time
        String message = "I want to be the best QA Engineer! " + epochTime;

        return message;
     }

     public WebElement findElementWithRetry(By locator, int retries) {
        WebElement element = null;
        int attempts = 0;
        while (attempts < retries) {
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                element = driver.findElement(locator);
                break; // Exit loop if found
            } catch (Exception e) {
                attempts++;
                if (attempts == retries) {
                    throw new RuntimeException("Element not found after " + retries + " retries: " + locator);
                }
            }
        }
        return element;
    }
    
        


    public List<WebElement> findElementsWithRetry(By locator, int retries) {
        List<WebElement> elements = null;
        int attempts = 0;

        while (attempts < retries) {
            try {
                // Wait for the elements to be visible
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
                elements = driver.findElements(locator);
            
                if (!elements.isEmpty()) {
                    break; // Exit loop if elements are found
                } else {
                    throw new RuntimeException("No elements found for locator: " + locator);
                }
            } catch (Exception e) {
                attempts++;
                System.out.println("Attempt " + attempts + " failed. Retrying...");

                if (attempts == retries) {
                    throw new RuntimeException("Elements not found after " + retries + " retries: " + locator, e);
                }
            }
        }
        return elements;
    }






}
