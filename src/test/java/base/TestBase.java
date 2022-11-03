package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.logging.Level;

@Slf4j
public class TestBase {

    public WebDriver driver;
    public WebDriverWait wait;

    @BeforeAll
    public static void setDriver() {
        WebDriverManager.chromedriver().setup();
        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
    }

    @BeforeEach
    public void setUP() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://books-pwakit.appspot.com/");
        log.info("Going on site: https://books-pwakit.appspot.com/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    @AfterEach
    public void tearDown() {
        driver.quit();
    }

}
