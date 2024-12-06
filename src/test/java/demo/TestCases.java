package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;
import net.bytebuddy.implementation.bytecode.StackSize;

public class TestCases {
    ChromeDriver driver;

    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */

     @Test
     public void testCase01() throws InterruptedException{
        
        Wrappers wrapMethods = new Wrappers(driver);
        SoftAssert sa = new SoftAssert();
        boolean status = false;

        //Navigate to https://forms.gle/wjPkzeSEk1CM7KgGA
        status = wrapMethods.NavigateToUrl("https://forms.gle/wjPkzeSEk1CM7KgGA");
        sa.assertTrue(status);

        //Enter Name
        WebElement nameTextbox = wrapMethods.findElementWithRetry(By.xpath("//div[@class='AgroKb']/div/div/div/div/input[@class='whsOnd zHQkBf']"), 3);
        status = wrapMethods.SendKeys(nameTextbox, "Crio Learner");
        sa.assertTrue(status);

        //Why are practicing Automation?
        WebElement whyAuto = wrapMethods.findElementWithRetry(By.xpath("//div[@class='RpC4Ne oJeWuf']/div[2]/textarea"), 3);
        String str1 = "I want to be the best QA Engineer! ";
        String str2 = wrapMethods.getEpochTime();
        String str = str1 + str2;
        status = wrapMethods.SendKeys(whyAuto, str);
        sa.assertTrue(status);

        //Enter your Automation Testing experience in the next radio button
        WebElement autoExp = wrapMethods.findElementWithRetry(By.xpath("//div[@role='radio' and @aria-label='0 - 2']"), 3);
        status = wrapMethods.Click(autoExp);
        sa.assertTrue(status);

        //Select Java, Selenium and TestNG from the next check-box
        WebElement javaCheckbox = wrapMethods.findElementWithRetry(By.xpath("//div[@aria-label='Java']"), 3);
        status = wrapMethods.Click(javaCheckbox);
        sa.assertTrue(status);
        WebElement seleniumCheckbox = wrapMethods.findElementWithRetry(By.xpath("//div[@aria-label='Selenium']"), 3);
        status = wrapMethods.Click(seleniumCheckbox);
        sa.assertTrue(status);
        WebElement testngCheckbox = wrapMethods.findElementWithRetry(By.xpath("//div[@aria-label='TestNG']"), 3);
        status = wrapMethods.Click(testngCheckbox);
        sa.assertTrue(status);

        //Provide how you would like to be addressed in the next dropdown
        WebElement dropdown = wrapMethods.findElementWithRetry(By.xpath("//div[@role='option']/span[text()='Choose']"), 3);
        status = wrapMethods.Click(dropdown);
        sa.assertTrue(status);
        WebElement dpOption1 = wrapMethods.findElementWithRetry(By.xpath("//div[@role='option']/span[text()='Mr']"), 3);
        status = wrapMethods.Click(dpOption1);
        sa.assertTrue(status);
        Thread.sleep(5000);

        //Provided the current date minus 7 days in the next date field, it should be dynamically calculated and not hardcoded.
        LocalDate currentDate = LocalDate.now();
        LocalDate dateMinus7Days = currentDate.minusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = dateMinus7Days.format(formatter);
        System.out.println("run8: "+formattedDate);
        
        WebElement inputDate = wrapMethods.findElementWithRetry(By.xpath("//div[@class='Qr7Oae']/div/div/div[2]/div/div/div/div/div/div/input[@type='date']"), 3);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value = arguments[1];", inputDate, formattedDate);
        Thread.sleep(5000);

        WebElement inputDate2 = wrapMethods.findElementWithRetry(By.xpath("//div[@class='Qr7Oae']/div/div/div[2]/div/div/div/div/div/div/input[@type='date']"), 3);
        String verifyDate = inputDate2.getAttribute("data-initial-value");
        System.out.println(verifyDate);

        //Provide the time 07:30 in the next field (Can also be in 24 hour clock)
        WebElement hour = wrapMethods.findElementWithRetry(By.xpath("//input[@aria-label='Hour']"), 3);
        status = wrapMethods.SendKeys(hour, "07");
        sa.assertTrue(status);
        WebElement minute = wrapMethods.findElementWithRetry(By.xpath("//input[@aria-label='Minute']"), 3);
        status = wrapMethods.SendKeys(minute, "30");
        sa.assertTrue(status);
        Thread.sleep(5000);

        //Submit the form
        WebElement submitButton = wrapMethods.findElementWithRetry(By.xpath("//span[text()='Submit']"), 3);
        status = wrapMethods.Click(submitButton);
        sa.assertTrue(status);
        Thread.sleep(5000);

        //You will see a success message on the website. Print the same message on the console upon successful completion
        WebElement successMsg = wrapMethods.findElementWithRetry(By.xpath("//div[text()='Thanks for your response, Automation Wizard!']"), 3);
        status = (successMsg.isDisplayed() && successMsg.getText().equals("Thanks for your response, Automation Wizard!"));
        sa.assertTrue(status);
        
        
     }

     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
}