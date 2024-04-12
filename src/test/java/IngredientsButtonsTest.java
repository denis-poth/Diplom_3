import org.junit.After;
import org.junit.Assert;
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
        // Подождать 1 секунду
        Thread.sleep(1000);
        // Нажать на кнопку "Булки", для непосредственного теста перехода к разделу "Булки"
        waitAndClick(MainPageObjects.BUN_BUTTON);
        // Подождать 1 секунду
        Thread.sleep(1000);

        Assert.assertTrue("Булки не активны", MainPageObjects.isBunButtonActive(driver));
        System.out.println("Тест пройден успешно: выполнен скролл до раздела 'Булки'");
    }

    // Проверка нажатия на кнопку "Соусы" на экране конструктора
    @Test
    public void testIngredientSauce() throws InterruptedException {
        // сначала Нажать на кнопку "Начинки", чтобы опустить список вниз
        waitAndClick(MainPageObjects.FILLING_BUTTON);
        // Подождать 1 секунду
        Thread.sleep(1000);
        // Нажать на кнопку "Соусы", чтобы вернуться к разделу и проверить работоспособность перехода
        waitAndClick(MainPageObjects.SAUCE_BUTTON);
        // Подождать 1 секунду
        Thread.sleep(1000);

        Assert.assertTrue("Соусы не активны", MainPageObjects.isSauceButtonActive(driver));
        System.out.println("Тест пройден успешно: выполнен скролл до раздела 'Соусы'");
    }

    // Проверка нажатия на кнопку "Начинки" на экране конструктора
    @Test
    public void testIngredientFilling() throws InterruptedException {
        // Нажать на кнопку "Начинки"
        waitAndClick(MainPageObjects.FILLING_BUTTON);
        // Подождать 1 секунду
        Thread.sleep(1000);
        Assert.assertTrue("Начинки не активны", MainPageObjects.isFillingButtonActive(driver));
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
