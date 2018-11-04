import lib.CoreTestCase;
import lib.ui.*;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class FirstTest extends CoreTestCase {

    private MainPageObject MainPageObject;

    protected void setUp() throws Exception
    {
        super.setUp();
        MainPageObject = new MainPageObject(driver);
    }

    @Test
    public void testPlaceholderInSearchInput()
    {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        WebElement search_input_element = MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot search input",
                5
        );

        String search_placeholder = search_input_element.getAttribute("text");

        assertEquals(
                "Placeholder is not 'Search…'!",
                "Search…",
                search_placeholder
        );
    }

    @Test
    public void testSearchResult()
    {
        String request = "Java";

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                request,
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/page_list_item_container"),
                "There is no results by '" + request + "",
                5);

        assertTrue(
                "Wikipedia found less than 2 articles by 'Junit'",
                MainPageObject.getAmountOfElements(By.id("org.wikipedia:id/page_list_item_container")) > 1
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5);

        assertTrue(
                "Search not canceled",
                MainPageObject.getAmountOfElements(By.id("org.wikipedia:id/page_list_item_container")) == 0);
    }

    @Test
    public void testSearchResultMatch()
    {
        String request = "Java";
        String title_element;

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                request,
                "Cannot find search '" + request + "'",
                5
        );

        MainPageObject.waitForElementPresent(By.id("org.wikipedia:id/page_list_item_container"), "There is no results by '" + request + "", 5);
        List<WebElement> page_list_item_container = driver.findElementsByXPath("//*[@resource-id='org.wikipedia:id/page_list_item_container']");

        for (WebElement el: page_list_item_container) {
            title_element = el.findElement(By.id("org.wikipedia:id/page_list_item_title")).getAttribute("text");

            if (title_element.toLowerCase().contains(request.toLowerCase())) {
                continue;
            } else {
                MainPageObject.assertFail("Article without '" + request + "'" + " in title was found in search results.");
            }
        }
    }

    @Test
    public void testSaveTwoArticlesAndDeleteOneOfThem()
    {
        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        String request = "Java";
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                request,
                "Cannot find search input",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        MainPageObject.waitForElementPresent(
                By.xpath(search_result_locator),
                "Cannot find anything by the request '" + request + "'",
                15
        );

        List<WebElement> elements = driver.findElements(By.xpath(search_result_locator));
        elements.get(0).click();

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        String title_first = driver.findElement(By.id("org.wikipedia:id/view_page_title_text")).getAttribute("text");

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                20
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'Got it' tip overlay",
                10
        );

        MainPageObject.waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of article folder",
                10
        );

        String name_of_folder = "Ex5";
        MainPageObject.waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot put text into article folder input",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press OK button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X button",
                5
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        request = "Junit";
        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                request,
                "Cannot find search input",
                5
        );

        MainPageObject.waitForElementPresent(
                By.xpath(search_result_locator),
                "Cannot find anything by the request '" + request + "'",
                15
        );

        elements = driver.findElements(By.xpath(search_result_locator));

        elements.get(1).click();

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        String title_second = driver.findElement(By.id("org.wikipedia:id/view_page_title_text")).getAttribute("text");

        MainPageObject.waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find button to open article options",
                20
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/list_of_lists']//*[@text='" + name_of_folder + "']"),
                "Cannot find created folder by name '" + name_of_folder + "'",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.id("org.wikipedia:id/snackbar_action"),
                "Cannot find the button on SnackBar",
                15
        );

        MainPageObject.swipeElementToLeft(
                By.xpath("//*[@text='" + title_second + "']"),
                "Cannot find saved article"
        );

        MainPageObject.waitForElementNotPresent(
                By.xpath("//*[@text='" + title_second + "']"),
                "Cannot delete saved article",
                15
        );

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[@text='" + title_first + "']"),
                "Cannot find the article by name '" + title_first + "'",
                15
        );

        MainPageObject.waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        String title_article = driver.findElement(By.id("org.wikipedia:id/view_page_title_text")).getAttribute("text");

        assertEquals(
                "",
                title_first,
                title_article
        );
    }

    @Test
    public void testArticleTitle()
    {
        String request = "Appium";

        MainPageObject.waitForElementAndClick(
                By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
                "Cannot find 'Search Wikipedia' input",
                5
        );

        MainPageObject.waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]"),
                request,
                "Cannot find search input",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        MainPageObject.waitForElementPresent(
                By.xpath(search_result_locator),
                "Cannot find anything by the request '" + request + "'",
                15
        );

        List<WebElement> elements = driver.findElements(By.xpath(search_result_locator));
        elements.get(0).click();

        MainPageObject.assertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find title of Article"
        );
    }
}
