package tests;

import lib.CoreTestCase;
import lib.Platform;
import lib.ui.ArticlePageObject;
import lib.ui.SearchPageObject;
import lib.ui.factories.ArticlePageObjectFactory;
import lib.ui.factories.SearchPageObjectFactory;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {

    @Test
    public void testCompareArticleTitle()
    {
        this.skipWelcomePageForIOSApp();

        String request = "Singleton pattern";

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine(request);
        SearchPageObject.clickByArticleWithSubstring("Design pattern in object-oriented software development");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        String article_title = ArticlePageObject.getArticleTitle();

        assertEquals(
                "We see unexpected title!",
                "Singleton pattern",
                article_title
        );
    }

    // для iOS порефакторить
    @Test
    public void testArticleTitlePresent()
    {
        if (Platform.getInstance().isIOS()) return;

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        String request = "Singleton pattern";
        SearchPageObject.typeSearchLine(request);

        SearchPageObject.clickByArticleWithSubstring("Design pattern in object-oriented software development");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.assertTitlePresent();
    }

    @Test
    public void testSwipeArticle()
    {
        this.skipWelcomePageForIOSApp();

        SearchPageObject SearchPageObject = SearchPageObjectFactory.get(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.typeSearchLine("Singleton pattern");
        SearchPageObject.clickByArticleWithSubstring("Design pattern in object-oriented software development");

        ArticlePageObject ArticlePageObject = ArticlePageObjectFactory.get(driver);
        ArticlePageObject.waitForTitleElement();
        ArticlePageObject.swipeToFooter();
    }
}
