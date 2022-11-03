package base;

import org.junit.jupiter.api.BeforeEach;
import pages.Search4Page;
import pages.SearchPage;

public class Pages extends TestBase {

    public SearchPage searchPage;
    public Search4Page search4Page;

    @BeforeEach
    public void pageUp() {
        searchPage = new SearchPage(driver);
        search4Page = new Search4Page(driver);
    }
}
