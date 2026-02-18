package project;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    @Parameters("browser")
    @BeforeClass
    public void setUp(String browser) {
    	if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        }
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Navigate + login once for the suite
        driver.get("https://qeaskillhub.cognizant.com/LoginPage");

        WebElement name = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtUsername")));
        name.sendKeys("admin_user");
        WebElement pass = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtPassword")));
        pass.sendKeys("testadmin");
        driver.findElement(By.id("BtnLogin")).click();

        // Optional: wait until dashboard/home indicator is visible
        // wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("some-dashboard-selector")));
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
            wait = null;
        }
    }
}
 