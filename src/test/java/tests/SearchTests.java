package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch()
    {
        this.skipWelcomePageForIOSApp();

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("bject-oriented programming language");
    }

    // для iOS и mobile web порефакторить
    @Test
    public void testPlaceholderInSearchInput()
    {
        if (Platform.getInstance().isIOS()) return;

        this.skipWelcomePageForIOSApp();

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String search_placeholder = SearchPageObject.getPlaceholderSearchInput();

        assertEquals(
                "Placeholder is not 'Search…'!",
                "Search…",
                search_placeholder
        );
    }

    @Test
    public void testCancelSearch()
    {
        this.skipWelcomePageForIOSApp();

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testCancelSearchResults()
    {
        this.skipWelcomePageForIOSApp();

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String request = "Junit";
        SearchPageObject.typeSearchLine(request);

        int count_articles = SearchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "Wikipedia found less than 2 articles by '" + request + "'",
                count_articles > 1
        );

        SearchPageObject.clickCancelSearch();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    @Test
    public void testAmountOfNotEmptySearch()
    {
        this.skipWelcomePageForIOSApp();

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String request = "Java";
        SearchPageObject.typeSearchLine(request);
        int amount_of_search_results = SearchPageObject.getAmountOfFoundArticles();

        assertTrue(
                "We found too few results!",
                amount_of_search_results > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch()
    {
        this.skipWelcomePageForIOSApp();

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String request = "qqwewgrge";
        SearchPageObject.typeSearchLine(request);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    // для iOS и mobile web порефакторить
    @Test
    public void testSearchResultMatch()
    {
        this.skipWelcomePageForIOSApp();

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String request = "Java";
        SearchPageObject.typeSearchLine(request);
        SearchPageObject.assertResultTitlesContainsRequest(request);
    }

    @Test
    public void testSearchResultMatchTitleAndDesc()
    {
        this.skipWelcomePageForIOSApp();

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String request = "Novosibirsk";
        SearchPageObject.typeSearchLine(request);
        SearchPageObject.waitForElementByTitleAndDescription("Novosibirsk", "Russia");
        SearchPageObject.waitForElementByTitleAndDescription("Oblast", "Russia");
        SearchPageObject.waitForElementByTitleAndDescription("State University", "niversity");
    }
}
