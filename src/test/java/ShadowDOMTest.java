import base.Pages;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ShadowDOMTest extends Pages {

    public String expectedText = "Learn Selenium";

    @Test
    @DisplayName("Testing search input in shadowDOM - JS POP")
    public void shouldSendTextIntoShadowDOMInputS3POP() {
        searchPage.searchFor(expectedText);
        Assertions.assertThat(searchPage.getSearchInputText()).contains(expectedText);
    }

    @Test
    @DisplayName("Checking search result from nested shadowDOM - JS POP")
    public void shouldFindBookInNestedShadowDOMS3POP() {
        searchPage.searchFor(expectedText);
        Assertions.assertThat(searchPage.getFirstSearchResultTitle()).contains(expectedText);
    }

    @Test
    @DisplayName("Testing search input in shadowDOM - S4 POP")
    public void shouldSendTextIntoShadowDOMInputS4POP() {
        search4Page.searchFor(expectedText);
        Assertions.assertThat(search4Page.getSearchInputText()).isEqualTo(expectedText);
    }

    @Test
    @DisplayName("Checking search result from nested shadowDOM - S4 POP")
    public void shouldFindBookInNestedShadowDOMS4POP() {
        search4Page.searchFor(expectedText);
        Assertions.assertThat(search4Page.getFirstSearchResultTitle()).contains(expectedText);
    }
}
