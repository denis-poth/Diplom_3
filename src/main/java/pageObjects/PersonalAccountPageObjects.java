package pageObjects;

public class PersonalAccountPageObjects {
    public static final String ENTER_BUTTON = "//button[contains(@class, 'button_button__33qZ0') and contains(@class, 'button_button_type_primary__1O7Bx') and contains(@class, 'button_button_size_medium__3zxIa')]\n";
    public static final String REGISTRATION_BUTTON = "//*[@id=\"root\"]/div/main/div/div/p[1]/a";
    public static final String RESET_PASSWORD = "//a[contains(@class, 'Auth_link__1fOlj') and text()='Восстановить пароль']\n";
    public static final String EMAIL_INPUT_FIELD = "//input[contains(@class, 'text') and contains(@class, 'input__textfield') and contains(@class, 'text_type_main-default') and @type='text' and @name='name']\n";
    public static final String PASSWORD_INPUT_FIELD = "//input[contains(@class, 'text') and contains(@class, 'input__textfield') and contains(@class, 'text_type_main-default') and @type='password' and @name='Пароль']\n";
    public static final String QUIT_BUTTON = "//*[@id=\"root\"]/div/main/div/nav/ul/li[3]/button";
    public static final String LOGIN_TEXT = "//*[@id=\"root\"]/div/main/div/h2";
}
