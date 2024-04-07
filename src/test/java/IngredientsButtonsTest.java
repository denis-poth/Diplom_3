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

public class IngredientsButtonsTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        driver = getDriver();
        driver.get("https://stellarburgers.nomoreparties.site/");
        wait = new WebDriverWait(driver, 5);
    }

    // Проверка нажатия на кнопку "Булки" на экране конструктора
    @Test
    public void testIngredientBun() throws InterruptedException {
        // Нажать на кнопку "Булки"
        waitAndClick(MainPageObjects.BUN_BUTTON);
        // Проверяем наличие текста "Булки" на странице
        Thread.sleep(1000); // Подождать 1 секунду
        WebElement headerElement = driver.findElement(By.xpath(MainPageObjects.BUN_TEXT));
        assertTrue("Текст 'Булки' не найден на странице", headerElement.getText().contains("Булки"));
        System.out.println("Тест пройден успешно: выполнен скролл до раздела 'Булки'");
    }

    // Проверка нажатия на кнопку "Соусы" на экране конструктора
    @Test
    public void testIngredientSauce() throws InterruptedException {
        // Нажать на кнопку "Соусы"
        waitAndClick(MainPageObjects.SAUCE_BUTTON);
        // Проверяем наличие текста "Соусы" на странице
        Thread.sleep(1000); // Подождать 1 секунду
        WebElement headerElement = driver.findElement(By.xpath(MainPageObjects.SAUCE_TEXT));
        assertTrue("Текст 'Соусы' не найден на странице", headerElement.getText().contains("Соусы"));
        System.out.println("Тест пройден успешно: выполнен скролл до раздела 'Соусы'");
    }

    // Проверка нажатия на кнопку "Начинки" на экране конструктора
    @Test
    public void testIngredientFilling() throws InterruptedException {
        // Нажать на кнопку "Начинка"
        waitAndClick(MainPageObjects.FILLING_BUTTON);
        // Проверяем наличие текста "Начинки" на странице
        Thread.sleep(1000); // Подождать 1 секунду
        WebElement headerElement = driver.findElement(By.xpath(MainPageObjects.FILLING_TEXT));
        assertTrue("Текст 'Начинки' не найден на странице", headerElement.getText().contains("Начинки"));
        System.out.println("Тест пройден успешно: выполнен скролл до раздела 'Начинки'");
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
