package lib.ui;

import lib.Platform;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.List;

abstract public class MyListsPageObject extends MainPageObject {
    protected static String
        FOLDER_ELEMENT,
        FOLDER_BY_NAME_TPL,
        ARTICLE_BY_TITLE_TPL,
        ARTICLE_ELEMENT,
        ARTICLE_ITEM_TITLE,
        REMOVE_FROM_SAVED_BUTTON;

    /* TEMPLATE METHODS */
    private static String getFolderXPathByName(String name_of_folder) {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }
    private static String getSavedArticleXpathByTitle(String article_title) {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }
    private static String getRemoveButtonByTitle(String article_title) {
        return REMOVE_FROM_SAVED_BUTTON.replace("{TITLE}", article_title);
    }
    /* TEMPLATE METHODS */

    public MyListsPageObject(RemoteWebDriver driver)
    {
        super(driver);
    }

    public void openFolderByName(String name_of_folder)
    {
        this.waitForElementPresent(FOLDER_ELEMENT, "Cannot find List of folders", 30);
        String folder_name_xpath = getFolderXPathByName(name_of_folder);

        this.waitForElementAndClick(
                folder_name_xpath,
                "Cannot find folder by name '" + name_of_folder + "' in MyLists",
                30
        );
    }

    public void waitForArticleToAppearByTitle(String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);

        this.waitForElementPresent(
                article_xpath,
                "Cannot find saved article by title " + article_title,
                20
        );
    }

    public void waitForArticleToDisappearByTitle(String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);

        this.waitForElementNotPresent(
                article_xpath,
                "Saved article still present with title " + article_title,
                20
        );
    }

    public void swipeByArticleToDelete(String article_title)
    {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getSavedArticleXpathByTitle(article_title);

        if (Platform.getInstance().isIOS() || Platform.getInstance().isAndroid()) {
            this.swipeElementToLeft(
                    article_xpath,
                    "Cannot find saved article in MyLists"
            );
        } else {
            String remove_locator = getRemoveButtonByTitle(article_title);

            this.waitForElementPresent(remove_locator,"Cannot find button to remove an article from saved",20);
            this.waitForElementAndClick(
                    remove_locator,
                    "Cannot click button to remove an article from saved",
                    20
            );
        }

        if (Platform.getInstance().isIOS()) {
            this.clickElementToTheRightUpperCorner(article_xpath, "Cannot find saved article");
        }

        if (Platform.getInstance().isMW()) {
            driver.navigate().refresh();
            driver.navigate().refresh();
            driver.navigate().refresh();
        }

        this.waitForArticleToDisappearByTitle(article_title);
    }

    public void openArticleByTitle(String article_title)
    {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementAndClick(
                article_xpath,
                "Cannot find and click article by title '" + article_title + "' in MyLists",
                20);
    }

    public void assertExistArticleTitleContainsSubstring(String substring)
    {
        this.waitForElementPresent(
                ARTICLE_ELEMENT,
                "There is no saved elements",
                5);

        By by = this.getLocatorByString(ARTICLE_ELEMENT);
        List<WebElement> page_list_item_container = driver.findElements(by);

        String title_element;
        for (WebElement el: page_list_item_container) {

            if (Platform.getInstance().isAndroid()) {
                By by_el = this.getLocatorByString(ARTICLE_ITEM_TITLE);
                title_element = el.findElement(by_el).getAttribute("text");
            } else {
                By by_el = this.getLocatorByString(ARTICLE_ELEMENT);
                title_element = el.findElement(by_el).getAttribute("name");
            }

            if (title_element.toLowerCase().contains(substring.toLowerCase())) {
                continue;
            } else {
                this.assertFail("Article without '" + substring + "'" + " in title was found in search results.");
            }
        }
    }
}
