package lib.ui;

import lib.Platform;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.concurrent.TimeUnit;

abstract public class NavigationUI extends MainPageObject {

    protected static String
        MY_LISTS_LINK,
        OPEN_NAVIGATION;

    public NavigationUI(RemoteWebDriver driver)
    {
        super(driver);
    }

    public void OpenNavigation()
    {
        if (Platform.getInstance().isMW()) {
            this.waitForElementPresent(OPEN_NAVIGATION, "Cannot find open navigation button", 20);
            this.waitForElementAndClick(OPEN_NAVIGATION, "Cannot find and click open navigation button", 20);
        } else {
            System.out.println("Method OpenNavigation() does nothing for platform " + Platform.getInstance().getPlatformVar());
        }
    }

    public void clickMyLists()
    {
        if (Platform.getInstance().isMW()) {
            this.tryClickElementWithFewAttempts(
                    MY_LISTS_LINK,
                    "Cannot find navigation button to 'My lists'",
                    15);
        } else {
            this.waitForElementAndClick(
                    MY_LISTS_LINK,
                    "Cannot find navigation button to 'My lists'",
                    5
            );
        }
    }
}
