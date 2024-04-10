import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.*;
import methods.WebDriverSettings;

import static org.junit.Assert.assertTrue;

public class IngredientsButtonsTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        driver = WebDriverSettings.getDriver();
        driver.get("https://stellarburgers.nomoreparties.site/");
        wait = new WebDriverWait(driver, 5);
    }

    // Проверка нажатия на кнопку "Булки" на экране конструктора
    @Test
    public void testIngredientBun() throws InterruptedException {
        // Нажать на кнопку "Соусы", чтобы перейти в другой раздел и проверить возвращение к разделу "Булки"
        waitAndClick(MainPageObjects.SAUCE_BUTTON);
        Thread.sleep(1000); // Подождать 1 секунду
        // Нажать на кнопку "Булки", для непосредственного теста перехода к разделу "Булки"
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
        // сначала Нажать на кнопку "Начинки", чтобы опустить список вниз
        waitAndClick(MainPageObjects.FILLING_BUTTON);
        Thread.sleep(1000); // Подождать 1 секунду
        // Нажать на кнопку "Соусы", чтобы вернуться к разделу и проверить работоспособность перехода
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
        // Нажать на кнопку "Начинки"
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
}
