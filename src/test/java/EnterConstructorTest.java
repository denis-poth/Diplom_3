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
import static org.junit.Assert.assertTrue;
import pageObjects.*;

public class EnterConstructorTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        driver = getDriver();
        driver.get("https://stellarburgers.nomoreparties.site/");
        wait = new WebDriverWait(driver, 5);
    }

    // Переход в Конструктор с помощью нажатия на кнопку "Конструктор" в верхнем левом углу экрана
    @Test
    public void testEnterConstructorByClickConstructorButton() throws InterruptedException {
        waitAndClick(MainPageObjects.PERSONAL_ACCOUNT_BUTTON);
        waitAndClick(MainPageObjects.CONSTRUCTOR_BUTTON);
        // Проверяем наличие текста "Соберите бургер" на странице
        Thread.sleep(1000); // Подождать 1 секунду
        WebElement headerElement = driver.findElement(By.xpath(MainPageObjects.CREATE_BURGER_TEXT));
        assertTrue("Текст 'Соберите бургер' не найден на странице", headerElement.getText().contains("Соберите бургер"));
        System.out.println("Тест пройден успешно: выполнен переход в конструктор");
    }

    // Переход в Конструктор с помощью нажатия на главный заголовок "stellar burgers"
    @Test
    public void testEnterConstructorByClickMainHeader() throws InterruptedException {
        waitAndClick(MainPageObjects.PERSONAL_ACCOUNT_BUTTON);
        waitAndClick(MainPageObjects.MAIN_HEADER_BUTTON);
        // Проверяем наличие текста "Соберите бургер" на странице
        Thread.sleep(1000); // Подождать 1 секунду
        WebElement headerElement = driver.findElement(By.xpath(MainPageObjects.CREATE_BURGER_TEXT));
        assertTrue("Текст 'Соберите бургер' не найден на странице", headerElement.getText().contains("Соберите бургер"));
        System.out.println("Тест пройден успешно: выполнен переход в конструктор");
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
