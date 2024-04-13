import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;


import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertTrue;
import pageObjects.*;
import methods.WebDriverSettings;



public class RegistrationTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private String email = "NakonectoPolychilos@gmail.com";
    private String password = "qweqwrqw";
    private String name = "Yraaaa";
    String accessToken;


    @Before
    public void setUp() {
        driver = WebDriverSettings.getDriver();
        driver.get("https://stellarburgers.nomoreparties.site/");
        wait = new WebDriverWait(driver, 5);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    // Проверка успешной регистрации, с помощью корректных данных
    @Test
    public void testSuccessfulRegistration(){
        // Нажатие кнопки "Личный кабинет"
        waitAndClick(MainPageObjects.PERSONAL_ACCOUNT_BUTTON);
        // Нажатие кнопки "Регистрация"
        waitAndClick(PersonalAccountPageObjects.REGISTRATION_BUTTON);
        // Ввод данных для регистрации
        waitAndSendKeys(RegistrationPageObjects.NAME_INPUT_FIELD, name);
        waitAndSendKeys(RegistrationPageObjects.EMAIL_INPUT_FIELD, email);
        waitAndSendKeys(RegistrationPageObjects.PASSWORD_INPUT_FIELD, password);
        waitAndClick(RegistrationPageObjects.REGISTRATION_BUTTON);
        // Ждем, пока элемент "Вход" не станет видимым на странице
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PersonalAccountPageObjects.LOGIN_TEXT)));
        // Проверяем, что элемент "Вход" есть на странице
        assertTrue(driver.findElement(By.xpath(PersonalAccountPageObjects.LOGIN_TEXT)).isDisplayed());
        System.out.println("Тест выполнен успешно: регистрация завершена, открылась страница входа");

        // Удаляем аккаунт через API
        User user = new User(email, password, name);
        Api api = new Api();
        Response responseAccessToken = Api.loginUser(user);
        responseAccessToken.then().assertThat().body("accessToken", notNullValue())
                .and()
                .statusCode(200);
        this.accessToken = responseAccessToken.body().jsonPath().getString("accessToken");
        Api.deleteUser(accessToken);
    }

    // Проверка регистрации, при вводе некорректного пароля (длина символов менее 6)
    @Test
    public void testInvalidPassword() {
        // Нажатие кнопки "личный кабинет"
        waitAndClick(MainPageObjects.PERSONAL_ACCOUNT_BUTTON);
        // Нажатие кнопки "Регистрация"
        waitAndClick(PersonalAccountPageObjects.REGISTRATION_BUTTON);
        // Ввод данных для регистрации
        waitAndSendKeys(RegistrationPageObjects.NAME_INPUT_FIELD, "TestUser");
        waitAndSendKeys(RegistrationPageObjects.EMAIL_INPUT_FIELD, "test@example.com");
        waitAndSendKeys(RegistrationPageObjects.PASSWORD_INPUT_FIELD, "pass");
        waitAndClick(RegistrationPageObjects.REGISTRATION_BUTTON);
        // Проверяем наличие надписи "Некорректный пароль" на странице
        WebElement invalidPasswordMessageElement = driver.findElement(By.xpath(RegistrationPageObjects.INCORRECT_PASSWORD_TEXT));
        assertTrue("Надпись 'Некорректный пароль' не найдена на странице", invalidPasswordMessageElement.getText().contains("Некорректный пароль"));
        System.out.println("Тест выполнен успешно: регистрация не завершилась, введен некорректный пароль");
    }

    @After
    public void tearDown() {
        driver.quit();
    }



    private void waitAndClick(String xpath) {
        try {
            // Устанавливаем неявное ожидание на 10 секунд перед каждым действием
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            element.click();
        } catch (ElementClickInterceptedException e) {
            // Если обычный клик вызывает ошибку, используем JavaScript для клика
            clickWithJavaScript(xpath);
        } finally {
            // Восстанавливаем исходное неявное ожидание
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
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
