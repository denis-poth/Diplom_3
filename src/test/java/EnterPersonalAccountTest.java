import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.Assert.assertTrue;
import pageObjects.*;
import methods.WebDriverSettings;
public class EnterPersonalAccountTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        driver = WebDriverSettings.getDriver();
        driver.get("https://stellarburgers.nomoreparties.site/");
        wait = new WebDriverWait(driver, 5);
    }

    // Проверка перехода в Личный Кабинет по клику на "Личный кабинет"
    @Test
    public void testEnterPersonalAccByClick(){
        waitAndClick(MainPageObjects.PERSONAL_ACCOUNT_BUTTON);
        // Проверяем наличие текста "Вход" в меню на странице
        WebElement loginMenuElement = driver.findElement(By.xpath(PersonalAccountPageObjects.LOGIN_TEXT));
        assertTrue("Текст 'Вход' не найден в меню на странице", loginMenuElement.getText().contains("Вход"));
        System.out.println("Тест выполнен успешно: произошел переход на страницу личного кабинета");
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
}
