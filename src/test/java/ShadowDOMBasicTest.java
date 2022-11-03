import base.TestBase;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

@Slf4j
public class ShadowDOMBasicTest extends TestBase {

    public String expectedText = "Learn Selenium";

    @Test
    @DisplayName("Testing search input in shadowDOM - JS")
    public void shouldSendTextIntoShadowDOMInputS3() {
        // Finding shadowHost
        WebElement shadowHost = driver.findElement(By.cssSelector("book-app"));

        // Using JS Script to return shadowRoot
        JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
        SearchContext shadowRoot = (SearchContext) jsDriver.executeScript("return arguments[0].shadowRoot", shadowHost);

        // Finding input in shadow root
        WebElement input = shadowRoot.findElement(By.cssSelector("#input"));
        input.sendKeys(expectedText);

        log.info(">>>> INPUT CONTENT IS: {} <<<<", input.getAttribute("value"));
        Assertions.assertThat(input.getAttribute("value")).isEqualTo(expectedText);
    }

    @Test
    @DisplayName("Testing search input in shadowDOM - S4")
    public void shouldSendTextIntoShadowDOMInputS4() {
        // Finding shadowHost
        WebElement shadowHost = driver.findElement(By.cssSelector("book-app"));

        // Getting shadowRoot
        SearchContext shadowRoot = shadowHost.getShadowRoot();

        // Finding input in shadow root
        WebElement input = shadowRoot.findElement(By.cssSelector("#input"));
        input.sendKeys(expectedText);

        Assertions.assertThat(input.getAttribute("value")).isEqualTo(expectedText);
    }

    @Test
    @DisplayName("Checking search result from nested shadowDOM - JS")
    public void shouldFindBookInNestedShadowDOMS3() {
        // Finding shadowHost
        WebElement shadowHost = driver.findElement(By.cssSelector("book-app"));

        // Using JS Script to return shadowRoot
        JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
        SearchContext shadowRoot = (SearchContext) jsDriver.executeScript("return arguments[0].shadowRoot", shadowHost);

        // Searching for book
        WebElement input = shadowRoot.findElement(By.cssSelector("#input"));
        input.sendKeys(expectedText + Keys.ENTER);

        // Getting host of nested shadow root
        wait.until(ExpectedConditions.visibilityOf(shadowRoot.findElement(By.cssSelector("book-explore"))));
        WebElement nestedShadowHost = shadowRoot.findElement(By.cssSelector("book-explore"));
        SearchContext nestedShadowRootOne = (SearchContext) jsDriver.executeScript("return arguments[0].shadowRoot", nestedShadowHost);

        // Getting first search result
        WebElement searchResultHost = nestedShadowRootOne.findElement(By.cssSelector("book-item"));
        SearchContext nestedShadowRootTwo = (SearchContext) jsDriver.executeScript("return arguments[0].shadowRoot", searchResultHost);
        WebElement firstBookTitle = nestedShadowRootTwo.findElement(By.cssSelector(".info .title"));
        wait.until(e -> !firstBookTitle.getText().isEmpty());

        Assertions.assertThat(firstBookTitle.getText()).contains(expectedText);
    }


    @Test
    @DisplayName("Checking search result from nested shadowDOM - S4")
    public void shouldClickOnBtnInNestedShadowDOMS4() {
        // Finding shadowHost
        WebElement shadowHost = driver.findElement(By.cssSelector("book-app"));

        // Getting shadowRoot
        SearchContext shadowRoot = shadowHost.getShadowRoot();

        // Finding input in shadow root
        WebElement input = shadowRoot.findElement(By.cssSelector("#input"));
        input.sendKeys(expectedText + Keys.ENTER);

        // Waiting for search result screen and finding result shadow root
        wait.until(ExpectedConditions.visibilityOf(shadowRoot.findElement(By.cssSelector("book-explore"))));
        SearchContext searchResultsShadowHost = shadowRoot
                .findElement(By.cssSelector("book-explore")).getShadowRoot()
                .findElement(By.cssSelector("book-item")).getShadowRoot();

        // Waiting until search results loads
        wait.until(e -> !searchResultsShadowHost.findElement(By.cssSelector(".title")).getText().isEmpty());

        Assertions.assertThat(searchResultsShadowHost.findElement(By.cssSelector(".title")).getText()).isEqualTo(expectedText);
    }
}
