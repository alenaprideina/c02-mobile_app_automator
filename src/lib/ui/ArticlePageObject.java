package lib.ui;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ArticlePageObject extends MainPageObject {

    private static final String
        TITLE = "org.wikipedia:id/view_page_title_text",
        FOOTER_ELEMENT = "//*[@text='View page in browser']",
        OPTIONS_BUTTON = "//android.widget.ImageView[@content-desc='More options']",
        OPTIONS_LIST = "//*[@class='android.widget.ListView']//*[@text='Add to reading list']",
        OPTIONS_ADD_TO_MY_LIST_BUTTON = "//*[@text='Add to reading list']",
        ADD_TO_MY_LIST_OVERLAY = "org.wikipedia:id/onboarding_button",
        MY_LIST_NAME_INPUT = "org.wikipedia:id/text_input",
        MY_LIST_OK_BUTTON = "//*[@text='OK']",
        MY_LIST_IN_LISTS_TPL = "//*[@resource-id='org.wikipedia:id/list_of_lists']//*[@text='{FOLDER_NAME}']",
        CLOSE_ARTICLE_BUTTON = "//android.widget.ImageButton[@content-desc='Navigate up']";

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
        return this.waitForElementPresent(By.id(TITLE), "Cannot find article title on page", 15);
    }

    public String getArticleTitle()
    {
        WebElement title_element = waitForTitleElement();
        return title_element.getAttribute("text");
    }

    public void assertTitlePresent()
    {
        this.assertElementPresent(
            By.id(TITLE),
            "Cannot find title of Article"
        );
    }

    public void swipeToFooter()
    {
        this.swipeUpToFindElement(
                By.xpath(FOOTER_ELEMENT),
                "Cannot find the end of article",
                20);
    }

    public void waitForOpenOptions()
    {
        this.waitForElementPresent(By.xpath(OPTIONS_LIST), "Cannot find Options for Article");
    }

    public void addArticleToMyList(String name_of_folder)
    {
        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find button to open article options",
                30
        );

        this.waitForOpenOptions();
        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find option to add article to reading list",
                30
        );

        this.waitForElementAndClick(
                By.id(ADD_TO_MY_LIST_OVERLAY),
                "Cannot find 'Got it' tip overlay",
                30
        );

        this.waitForElementAndClear(
                By.id(MY_LIST_NAME_INPUT),
                "Cannot find input to set name of article folder",
                30
        );

        this.waitForElementAndSendKeys(
                By.id(MY_LIST_NAME_INPUT),
                name_of_folder,
                "Cannot put text into article folder input",
                30
        );

        this.waitForElementAndClick(
                By.xpath(MY_LIST_OK_BUTTON),
                "Cannot press OK button",
                30
        );
    }

    public void addArticleToMyExistList(String name_of_folder)
    {
        this.waitForElementAndClick(
                By.xpath(OPTIONS_BUTTON),
                "Cannot find button to open article options",
                20
        );

        this.waitForElementAndClick(
                By.xpath(OPTIONS_ADD_TO_MY_LIST_BUTTON),
                "Cannot find option to add article to reading list",
                20
        );

        String my_folder_xpath = getListFromListsElement(name_of_folder);
        this.waitForElementAndClick(
                By.xpath(my_folder_xpath),
                "Cannot find created folder by name '" + name_of_folder + "' and save article to it",
                20
        );
    }

    public void closeArticle()
    {
        this.waitForElementAndClick(
                By.xpath(CLOSE_ARTICLE_BUTTON),
                "Cannot close article, cannot find X button",
                20
        );
    }
}
