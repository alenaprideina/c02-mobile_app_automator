package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject {
    public static final String
        FOLDER_ELEMENT = "//*[@resource-id='org.wikipedia:id/item_container']",
        FOLDER_BY_NAME_TPL = "//*[@text='{FOLDER_NAME}']",
        ARTICLE_BY_TITLE_TPL = "//*[@text='{TITLE}']";

    /* TEMPLATE METHODS */
    private static String getFolderXPathByName(String name_of_folder)
    {
        return FOLDER_BY_NAME_TPL.replace("{FOLDER_NAME}", name_of_folder);
    }
    private static String getSavedArticleXpathByTitle(String article_title)
    {
        return ARTICLE_BY_TITLE_TPL.replace("{TITLE}", article_title);
    }
    /* TEMPLATE METHODS */

    public MyListsPageObject(AppiumDriver driver)
    {
        super(driver);
    }

    public void waitForOpenMyLists()
    {
        this.waitForElementPresent(By.xpath(FOLDER_ELEMENT), "Cannot find List of folders");
    }

    public void openFolderByName(String name_of_folder)
    {
        this.waitForOpenMyLists();
        String folder_name_xpath = getFolderXPathByName(name_of_folder);

        this.waitForElementAndClick(
                By.xpath(folder_name_xpath),
                "Cannot find folder by name '" + name_of_folder + "' in MyLists",
                30
        );
    }

    public void waitForArticleToAppearByTitle(String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);

        this.waitForElementPresent(
                By.xpath(article_xpath),
                "Cannot find saved article by title " + article_title,
                20
        );
    }

    public void waitForArticleToDisappearByTitle(String article_title)
    {
        String article_xpath = getSavedArticleXpathByTitle(article_title);

        this.waitForElementNotPresent(
                By.xpath(article_xpath),
                "Saved article still present with title " + article_title,
                20
        );
    }

    public void swipeByArticleToDelete(String article_title)
    {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.swipeElementToLeft(
                By.xpath(article_xpath),
                "Cannot find saved article in MyLists"
        );

        this.waitForArticleToDisappearByTitle(article_title);
    }

    public void openArticleByTitle(String article_title)
    {
        this.waitForArticleToAppearByTitle(article_title);
        String article_xpath = getSavedArticleXpathByTitle(article_title);
        this.waitForElementAndClick(
                By.xpath(article_xpath),
                "Cannot find and click article by title '" + article_title + "' in MyLists",
                20);
    }
}
