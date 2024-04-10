import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.*;
import methods.WebDriverSettings;

import static org.junit.Assert.assertTrue;

public class LogInTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        driver = WebDriverSettings.getDriver();
        driver.get("https://stellarburgers.nomoreparties.site/");
        wait = new WebDriverWait(driver, 5);

    }

    // Проверка входа в аккаунт, с помощью нажатия кнопки "Войти в аккаунт" на главной странице
    @Test
    public void testLogInByEnterAccButton() throws InterruptedException {
        // Клик по кнопке "Личный кабинет"
        waitAndClick(MainPageObjects.LOGIN_ACCOUNT_BUTTON);
        // Ввод данных для входа
        waitAndSendKeys(PersonalAccountPageObjects.EMAIL_INPUT_FIELD, "marssisthegod@gmail.com");
        waitAndSendKeys(PersonalAccountPageObjects.PASSWORD_INPUT_FIELD, "ourancientisbetter");
        // Нажатие на кнопку входа
        waitAndClick(PersonalAccountPageObjects.ENTER_BUTTON);
        // Проверяем наличие кнопки "Оформить заказ" на главной странице
        Thread.sleep(1000); // Подождать 1 секунду
        WebElement loginMenuElement = driver.findElement(By.xpath(MainPageObjects.ORDER_TEXT));
        assertTrue("Текст 'Оформить заказ' не найден на главной странице", loginMenuElement.getText().contains("Оформить заказ"));
        System.out.println("Тест выполнен успешно: выполнен вход в аккаунт, произошел переход на главную страницу");
    }

    // Проверка входа в аккаунт, с помощью нажатия кнопки "Личный кабинет" вверху справа экрана
    @Test
    public void testLogInByPersonalAccountButton() throws InterruptedException {
        // Клик по кнопке "Личный кабинет"
        waitAndClick(MainPageObjects.PERSONAL_ACCOUNT_BUTTON);
        // Ввод данных для входа
        waitAndSendKeys(PersonalAccountPageObjects.EMAIL_INPUT_FIELD, "marssisthegod@gmail.com");
        waitAndSendKeys(PersonalAccountPageObjects.PASSWORD_INPUT_FIELD, "ourancientisbetter");
        // Нажатие на кнопку входа
        waitAndClick(PersonalAccountPageObjects.ENTER_BUTTON);
        // Проверяем наличие кнопки "Оформить заказ" на главной странице
        Thread.sleep(1000); // Подождать 1 секунду
        WebElement loginMenuElement = driver.findElement(By.xpath(MainPageObjects.ORDER_TEXT));
        assertTrue("Текст 'Оформить заказ' не найден на главной странице", loginMenuElement.getText().contains("Оформить заказ"));
        System.out.println("Тест выполнен успешно: выполнен вход в аккаунт, произошел переход на главную страницу");
    }

    // Проверка входа в аккаунт, с помощью нажатия кнопки "Войти" на странице регистрации
    @Test
    public void testLogInByRegistrationFormButton() throws InterruptedException {
        // Клик по кнопке "Личный кабинет"
        waitAndClick(MainPageObjects.PERSONAL_ACCOUNT_BUTTON);
        // Клик по кнопке "Регистрация"
        waitAndClick(PersonalAccountPageObjects.REGISTRATION_BUTTON);
        // Клик по кнопке "Войти" из раздела "уже зарегистрированы?"
        waitAndClick(RegistrationPageObjects.ENTER_BUTTON);
        // Ввод данных для входа
        waitAndSendKeys(PersonalAccountPageObjects.EMAIL_INPUT_FIELD, "marssisthegod@gmail.com");
        waitAndSendKeys(PersonalAccountPageObjects.PASSWORD_INPUT_FIELD, "ourancientisbetter");
        // Нажатие на кнопку входа
        waitAndClick(PersonalAccountPageObjects.ENTER_BUTTON);
        // Проверяем наличие кнопки "Оформить заказ" на главной странице
        Thread.sleep(1000); // Подождать 1 секунду
        WebElement loginMenuElement = driver.findElement(By.xpath(MainPageObjects.ORDER_TEXT));
        assertTrue("Текст 'Оформить заказ' не найден на главной странице", loginMenuElement.getText().contains("Оформить заказ"));
        System.out.println("Тест выполнен успешно: выполнен вход в аккаунт, произошел переход на главную страницу");
    }

    // Проверка входа в аккаунт, с помощью нажатия кнопки "Войти" на странице восстановления пароля
    @Test
    public void testLogInByPasswordRecoveryButton() throws InterruptedException {
        // Клик по кнопке "Личный кабинет"
        waitAndClick(MainPageObjects.PERSONAL_ACCOUNT_BUTTON);
        // Клик по кнопке "Восстановить пароль" из раздела "Забыли пароль?"
        waitAndClick(PersonalAccountPageObjects.RESET_PASSWORD);
        // Клик по кнопке "Войти" из раздела "Вспомнили пароль?"
        waitAndClick(ResetPasswordPageObjects.ENTER_BUTTON);
        // Ввод данных для входа
        waitAndSendKeys(PersonalAccountPageObjects.EMAIL_INPUT_FIELD, "marssisthegod@gmail.com");
        waitAndSendKeys(PersonalAccountPageObjects.PASSWORD_INPUT_FIELD, "ourancientisbetter");
        // Нажатие на кнопку входа
        waitAndClick(PersonalAccountPageObjects.ENTER_BUTTON);
        // Проверяем наличие кнопки "Оформить заказ" на главной странице
        Thread.sleep(1000); // Подождать 1 секунду
        WebElement loginMenuElement = driver.findElement(By.xpath(MainPageObjects.ORDER_TEXT));
        assertTrue("Текст 'Оформить заказ' не найден на главной странице", loginMenuElement.getText().contains("Оформить заказ"));
        System.out.println("Тест выполнен успешно: выполнен вход в аккаунт, произошел переход на главную страницу");
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
