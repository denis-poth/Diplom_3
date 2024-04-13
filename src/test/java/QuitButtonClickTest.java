import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.*;
import methods.WebDriverSettings;

import static org.junit.Assert.assertTrue;

public class QuitButtonClickTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        driver = WebDriverSettings.getDriver();
        driver.get("https://stellarburgers.nomoreparties.site/");
        wait = new WebDriverWait(driver, 5);
    }

    //Проверка выхода из аккаунта по кнопке "Выйти" в личном кабинете
    @Test
    public void testLogInByPersonalAccountButton(){
        // Клик по кнопке "Личный кабинет"
        waitAndClick(MainPageObjects.PERSONAL_ACCOUNT_BUTTON);

        // Ввод данных для входа
        waitAndSendKeys(PersonalAccountPageObjects.EMAIL_INPUT_FIELD, "marssisthegod@gmail.com");
        waitAndSendKeys(PersonalAccountPageObjects.PASSWORD_INPUT_FIELD, "ourancientisbetter");

        // Нажатие на кнопку входа
        waitAndClick(PersonalAccountPageObjects.ENTER_BUTTON);
        // Клик по кнопке "Личный кабинет"
        waitAndClick(MainPageObjects.PERSONAL_ACCOUNT_BUTTON);
        // Клик по кнопке "Выход"
        waitAndClick(PersonalAccountPageObjects.QUIT_BUTTON);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PersonalAccountPageObjects.LOGIN_TEXT)));
        // Проверяем, что элемент "Вход" есть на странице
        assertTrue(driver.findElement(By.xpath(PersonalAccountPageObjects.LOGIN_TEXT)).isDisplayed());
        System.out.println("Тест выполнен успешно: произошел выход из аккаунта");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void waitAndClick(String xpath) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            element.click();
        } catch (ElementClickInterceptedException e) {
            // Если обычный клик вызывает ошибку, используем JavaScript для клика
            clickWithJavaScript(xpath);
        }
    }

    private void clickWithJavaScript(String xpath) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    private void waitAndSendKeys(String xpath, String keys) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        element.sendKeys(keys);
    }
}
