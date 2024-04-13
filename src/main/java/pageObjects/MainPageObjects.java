package pageObjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPageObjects {
    public static final String PERSONAL_ACCOUNT_BUTTON = "//*[@id=\"root\"]/div/header/nav/a/p";
    public static final String LOGIN_ACCOUNT_BUTTON = "//button[contains(@class, 'button_button_type_primary__1O7Bx') and contains(@class, 'button_button_size_large__G21Vg')]\n";
    public static final String CONSTRUCTOR_BUTTON = "//p[@class=\"AppHeader_header__linkText__3q_va ml-2\"]\n";
    public static final String BUN_BUTTON = "//div[contains(@class, 'tab_tab') and span[text()='Булки']]";
    public static final String SAUCE_BUTTON = "//span[@class='text text_type_main-default'][text()='Соусы']";
    public static final String FILLING_BUTTON = "//div[@class='tab_tab__1SPyG  " +
            "pt-4 pr-10 pb-4 pl-10 noselect']/span[text()='Начинки']";
    public static final String MAIN_HEADER_BUTTON = "//div[contains(@class, 'AppHeader_header__logo__2D0X2')]";
    public static final String CREATE_BURGER_TEXT = "//*[@id='root']/div/main/section[1]/h1";
    public static final String ORDER_TEXT = "//*[@id=\"root\"]/div/main/section[2]/div/button";

    private static final By activeBunsButton = By.xpath("//div[contains(@class, 'tab_tab_type_current') and span[text()='Булки']]");
    private static final By activeSaucesButton = By.xpath("//div[contains(@class, 'tab_tab_type_current') and span[text()='Соусы']]");
    private static final By activeFillingsButton = By.xpath("//div[contains(@class, 'tab_tab_type_current') and span[text()='Начинки']]");

    public static boolean isBunButtonActive(WebDriver driver) {
        return driver.findElement(activeBunsButton).isDisplayed();
    }

    public static boolean isSauceButtonActive(WebDriver driver) {
        return driver.findElement(activeSaucesButton).isDisplayed();
    }

    public static boolean isFillingButtonActive(WebDriver driver) {
        return driver.findElement(activeFillingsButton).isDisplayed();
    }
}
