package tests;

import lib.CoreTestCase;
import lib.ui.SearchPageObject;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class SearchTests extends CoreTestCase {

    @Test
    public void testSearch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Java");
        SearchPageObject.waitForSearchResult("Object-oriented programming language");
    }

    // для iOS порефакторить
    @Test
    public void testPlaceholderInSearchInput()
    {
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
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.waitForCancelButtonToAppear();
        SearchPageObject.clickCancelSearch();
        SearchPageObject.waitForCancelButtonToDisappear();
    }

    @Test
    public void testCancelSearchResults()
    {
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
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String request = "qqwewgrge";
        SearchPageObject.typeSearchLine(request);
        SearchPageObject.waitForEmptyResultsLabel();
        SearchPageObject.assertThereIsNoResultOfSearch();
    }

    // для iOS порефакторить
    @Test
    public void testSearchResultMatch()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String request = "Java";
        SearchPageObject.typeSearchLine(request);
        SearchPageObject.assertResultTitlesContainsRequest(request);
    }

    // Ex12*: Рефакторинг тестов
    @Test
    public void testSearchResultMatchTitleAndDesc()
    {
        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String request = "Novosibirsk";
        SearchPageObject.typeSearchLine(request);
        SearchPageObject.waitForElementByTitleAndDescription("Novosibirsk", "Russian city, the administrative center of Siberian Federal District");
        SearchPageObject.waitForElementByTitleAndDescription("Novosibirsk Oblast", "Federal subject of Russia");
        SearchPageObject.waitForElementByTitleAndDescription("Novosibirsk State University", "University");
    }
}
