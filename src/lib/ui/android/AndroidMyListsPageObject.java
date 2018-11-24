package lib.ui.android;

import io.appium.java_client.AppiumDriver;
import lib.ui.MyListsPageObject;

public class AndroidMyListsPageObject extends MyListsPageObject
{
    static {
        FOLDER_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/item_container']";
        FOLDER_BY_NAME_TPL = "xpath://*[@text='{FOLDER_NAME}']";
        ARTICLE_BY_TITLE_TPL = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_title'][contains(@text, '{TITLE}')]";
        ARTICLE_ELEMENT = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_container']";
        ARTICLE_ITEM_TITLE = "xpath://*[@resource-id='org.wikipedia:id/page_list_item_title']";
    }

    public AndroidMyListsPageObject(AppiumDriver driver)
    {
        super(driver);
    }
}
