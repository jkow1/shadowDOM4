package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchPage extends BasePage {

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "book-app")
    private WebElement shadowHost;

    @FindBy(css = "#input")
    private WebElement searchInput;

    @FindBy(css = "book-explore")
    private WebElement firstNestedShadowHost;

    @FindBy(css = "book-item")
    private WebElement secondNestedShadowHost;

    @FindBy(css = ".info .title")
    private WebElement bookTitle;

    public void searchFor(String text) {
        getShadowRootWithJS(shadowHost).findElement(By.cssSelector("#input")).sendKeys(text + Keys.ENTER);
    }

    public String getSearchInputText() {
        return getShadowRootWithJS(shadowHost).findElement(By.cssSelector("#input")).getAttribute("value");
    }

    public String getFirstSearchResultTitle() {
        wait.until(e -> !getSearchResultsShadowHost().findElement(By.cssSelector(".info .title")).getText().isEmpty());
        return getSearchResultsShadowHost().findElement(By.cssSelector(".title")).getText();
    }

    public SearchContext getSearchResultsShadowHost() {
        wait.until(ExpectedConditions.visibilityOf(getShadowRootWithJS(shadowHost).findElement(By.cssSelector("book-explore"))));
        return (SearchContext) ((JavascriptExecutor) driver).executeScript(
                "return document.querySelector('book-app').shadowRoot" +
                        ".querySelector('book-explore').shadowRoot" +
                        ".querySelector('book-item').shadowRoot");
    }

    public SearchContext getShadowRootWithJS(WebElement shadowHost) {
        return (SearchContext) ((JavascriptExecutor) driver).executeScript("return arguments[0].shadowRoot", shadowHost);
    }
}
