package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;

import lib.Platform;

abstract public class ArticlePageObject extends MainPageObject {

    protected static String
        TITLE,
        FOOTER_ELEMENT,
        OPTIONS_BUTTON,
        OPTIONS_LIST,
        OPTIONS_ADD_TO_MY_LIST_BUTTON,
        ADD_TO_MY_LIST_OVERLAY,
        MY_LIST_NAME_INPUT,
        MY_LIST_OK_BUTTON,
        MY_LIST_IN_LISTS_TPL,
        CLOSE_ARTICLE_BUTTON,
        TIP_FOR_SAVED;

    public ArticlePageObject(AppiumDriver driver)
    {
        super(driver);
    }

    /* TEMPLATE METHODS */
    private static String getListFromListsElement(String substring)
    {
        return MY_LIST_IN_LISTS_TPL.replace("{FOLDER_NAME}", substring);
    }
    /* TEMPLATE METHODS */

    public WebElement waitForTitleElement()
    {
        return this.waitForElementPresent(
                TITLE,
                "Cannot find article title on page",
                15);
    }

    public String getArticleTitle()
    {
        WebElement title_element = waitForTitleElement();
        if (Platform.getInstance().isAndroid()) {
            return title_element.getAttribute("text");
        } else {
            return title_element.getAttribute("name");
        }
    }

    public void assertTitlePresent()
    {
        this.waitForElementPresent(
                TITLE,
                "Cannot find title of Article",
                20
        );
    }

    public void swipeToFooter()
    {
        if (Platform.getInstance().isAndroid()) {
            this.swipeUpToFindElement(FOOTER_ELEMENT,"Cannot find the end of article",40);
        } else {
            this.swipeUpTillElementAppear(FOOTER_ELEMENT,"Cannot find the end of article",60);
        }
    }

    public void waitForOpenOptions()
    {
        this.waitForElementPresent(OPTIONS_LIST, "Cannot find Options for Article");
    }

    public void addArticleToMyList(String name_of_folder)
    {
        this.waitForElementPresent(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                30
        );

        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot click button to open article options",
                30
        );

        this.waitForOpenOptions();
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                30
        );

        this.waitForElementAndClick(
                ADD_TO_MY_LIST_OVERLAY,
                "Cannot find 'Got it' tip overlay",
                30
        );

        this.waitForElementAndClear(
                MY_LIST_NAME_INPUT,
                "Cannot find input to set name of article folder",
                30
        );

        this.waitForElementAndSendKeys(
                MY_LIST_NAME_INPUT,
                name_of_folder,
                "Cannot put text into article folder input",
                30
        );

        this.waitForElementAndClick(
                MY_LIST_OK_BUTTON,
                "Cannot press OK button",
                30
        );
    }

    public void addArticleToMyExistList(String name_of_folder)
    {
        this.waitForElementPresent(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                30
        );
        this.waitForElementAndClick(
                OPTIONS_BUTTON,
                "Cannot find button to open article options",
                20
        );

        this.waitForOpenOptions();
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                20
        );

        String my_folder_xpath = getListFromListsElement(name_of_folder);
        this.waitForElementAndClick(
                my_folder_xpath,
                "Cannot find created folder by name '" + name_of_folder + "' and save article to it",
                20
        );
    }

    public void addArticleToMySaved()
    {
        this.waitForElementAndClick(
                OPTIONS_ADD_TO_MY_LIST_BUTTON,
                "Cannot find option to add article to reading list",
                20
        );
    }

    public void closeTipForSaved()
    {
        this.waitForElementAndClick(
                TIP_FOR_SAVED,
                "Cannot find tip  add article to reading list",
                20
        );
    }

    public void closeArticle()
    {
        this.waitForElementPresent(
                CLOSE_ARTICLE_BUTTON,
                "Cannot find X button",
                20
        );

        this.waitForElementAndClick(
                CLOSE_ARTICLE_BUTTON,
                "Cannot close article, cannot click X button",
                20
        );
    }
}
