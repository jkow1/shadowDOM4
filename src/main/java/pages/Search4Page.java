package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Search4Page extends BasePage {

    public Search4Page(WebDriver driver) {
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

    @FindBy(css = ".title")
    private WebElement bookTitle;

    public void searchFor(String text) {
        shadowHost.getShadowRoot().findElement(By.cssSelector("#input")).sendKeys(text + Keys.ENTER);
    }

    public String getSearchInputText() {
        return shadowHost.getShadowRoot().findElement(By.cssSelector("#input")).getAttribute("value");
    }

    public String getFirstSearchResultTitle() {
        wait.until(e -> !getSearchResultsShadowHost().findElement(By.cssSelector(".title")).getText().isEmpty());
        return getSearchResultsShadowHost().findElement(By.cssSelector(".title")).getText();
    }

    public SearchContext getSearchResultsShadowHost() {
        wait.until(ExpectedConditions.visibilityOf(shadowHost.getShadowRoot().findElement(By.cssSelector("book-explore"))));
        return shadowHost.getShadowRoot()
                .findElement(By.cssSelector("book-explore")).getShadowRoot()
                .findElement(By.cssSelector("book-item")).getShadowRoot();
    }
}
