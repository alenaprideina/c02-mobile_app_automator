package lib.ui;

import lib.Platform;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;

abstract public class SearchPageObject extends MainPageObject
{
    protected static String
        SEARCH_INIT_ELEMENT,
        SEARCH_INPUT,
        SEARCH_CANCEL_BUTTON,
        SEARCH_CLEAR_BUTTON,
        SEARCH_RESULT_BY_SUBSTRING_TPL,
        SEARCH_RESULT_BY_TWO_SUBSTRING_TPL,
        SEARCH_RESULT_ELEMENT,
        SEARCH_ITEM_TITLE,
        SEARCH_EMPTY_RESULT_ELEMENT;

    public SearchPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    /* TEMPLATE METHODS */
    private static String getResultSearchElement(String substring)
    {
        return SEARCH_RESULT_BY_SUBSTRING_TPL.replace("{SUBSTRING}", substring);
    }

    private static String getResultSearchElement(String title, String desc)
    {
        return SEARCH_RESULT_BY_TWO_SUBSTRING_TPL.replace("{TITLE}", title).replace("{DESC}", desc);
    }
    /* TEMPLATE METHODS */

    public void initSearchInput()
    {
        this.waitForElementAndClick(SEARCH_INIT_ELEMENT, "Cannot find and click search init element", 5);
        this.waitForElementPresent(SEARCH_INPUT, "Cannot find search input after clicking search init element");
    }

    public WebElement waitForSearchInput()
    {
        return this.waitForElementPresent(SEARCH_INPUT, "Cannot find search input", 15);
    }

    public String getPlaceholderSearchInput()
    {
        WebElement search_input_element = this.waitForSearchInput();
        return search_input_element.getAttribute("text");
    }

    public void waitForCancelButtonToAppear()
    {
        this.waitForElementPresent(SEARCH_CANCEL_BUTTON, "Cannot find search cancel button", 10);
    }

    public void waitForCancelButtonToDisappear()
    {
        this.waitForElementNotPresent(SEARCH_CANCEL_BUTTON, "Search cancel button is still present", 10);
    }

    public void clearSearchInput()
    {
        this.waitForElementPresent(
                SEARCH_CLEAR_BUTTON,
                "Cannot find button to clear search input",
                20
        );
        this.waitForElementAndClick(
                SEARCH_CLEAR_BUTTON,
                "Cannot click button to clear search input",
                20
        );
    }

    public void clickCancelSearch()
    {
        this.waitForElementAndClick(SEARCH_CANCEL_BUTTON, "Cannot find and click search cancel button", 10);
    }

    public void typeSearchLine(String search_line)
    {
        this.waitForElementPresent(SEARCH_INPUT, "Cannot find search input", 30);
        this.waitForElementAndSendKeys(SEARCH_INPUT, search_line, "Cannot type search input", 30);
    }

    public void waitForSearchResult(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementPresent(search_result_xpath, "Cannot find search result with substring " + substring);
    }

    public void clickByArticleWithSubstring(String substring)
    {
        String search_result_xpath = getResultSearchElement(substring);
        this.waitForElementAndClick(search_result_xpath, "Cannot find and click search result with substring " + substring, 10);
    }

    public int getAmountOfFoundArticles()
    {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "Cannot find anything by the request",
                15
        );

        return this.getAmountOfElements(SEARCH_RESULT_ELEMENT);
    }

    public void waitForEmptyResultsLabel()
    {
        this.waitForElementPresent(SEARCH_EMPTY_RESULT_ELEMENT,"Cannot find empty result label",15);
    }

    public void assertThereIsNoResultOfSearch()
    {
        this.assertElementNotPresent(SEARCH_RESULT_ELEMENT, "We supposed not to find any results");
    }

    public void assertResultTitlesContainsRequest(String request)
    {
        this.waitForElementPresent(
                SEARCH_RESULT_ELEMENT,
                "There is no results by '" + request + "'",
                5);

        By by = this.getLocatorByString(SEARCH_RESULT_ELEMENT);
        List<WebElement> page_list_item_container = driver.findElements(by);

        String title_element;
        for (WebElement el: page_list_item_container) {
            if (Platform.getInstance().isAndroid()) {
                By by_el = this.getLocatorByString(SEARCH_ITEM_TITLE);
                title_element = el.findElement(by_el).getAttribute("text");
            } else {
                title_element = el.getAttribute("name");
            }

            if (title_element.toLowerCase().contains(request.toLowerCase())) {
                continue;
            } else {
                this.assertFail("Article without '" + request + "'" + " in title was found in search results.");
            }
        }
    }

    public void waitForElementByTitleAndDescription(String title, String description)
    {
        String search_result_xpath = getResultSearchElement(title, description);

        this.waitForElementPresent(
                search_result_xpath,
                "Cannot find element with title = '" + title + "' and description = '" + description + "'",
                20
        );
    }
}
