import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.*;

import static org.junit.Assert.assertTrue;

public class QuitButtonClickTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        driver = getDriver();
        driver.get("https://stellarburgers.nomoreparties.site/");
        wait = new WebDriverWait(driver, 5);
    }

    //Проверка выхода из аккаунта по кнопке "Выйти" в личном кабинете
    @Test
    public void testLogInByPersonalAccountButton() throws InterruptedException {
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
        // Проверяем наличие текста "Вход" в меню на странице
        Thread.sleep(1000); // Подождать 1 секунду
        WebElement loginMenuElement = driver.findElement(By.xpath(PersonalAccountPageObjects.LOGIN_TEXT));
        assertTrue("Текст 'Вход' не найден в меню на странице", loginMenuElement.getText().contains("Вход"));
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
    private void waitUntil(String xpath){
        // Ожидание, пока кнопка станет видимой на странице
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    // Получение драйвера в зависимости от переменной окружения BROWSER
    private WebDriver getDriver() {
        String driverType = System.getenv("BROWSER");
        if (driverType == null) {
            // Если переменная окружения не установлена, используем chrome по умолчанию
            driverType = "chrome";
        }

        switch (driverType.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                driver = new ChromeDriver(chromeOptions);
                driver.manage().window().maximize();
                return driver;
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                driver = new FirefoxDriver(firefoxOptions);
                driver.manage().window().maximize();
                return driver;
            case "yandex":
                ChromeOptions yandexOptions = new ChromeOptions();
                driver = new ChromeDriver(yandexOptions);
                System.setProperty("webdriver.chrome.driver", "src/test/resources/yandexdriver.exe");
                return driver;
            default:
                throw new IllegalArgumentException("Этот браузер не поддерживается, выберите 'chrome','firefox' или 'yandex'");
        }
    }
}
