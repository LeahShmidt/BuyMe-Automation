package selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Registration {

    private static WebDriver driver;
    private static WebDriverWait driverWait;


    @FindBy (xpath = "//span[@class='seperator-link' and text()='כניסה']")
    private WebElement signIn;

    @FindBys({@FindBy(xpath = "//span[@class='seperator-link']"), @FindBy(xpath = "//span[text()='כניסה']")})
    private WebElement signIn2;

    @FindBy (xpath = "//span[@class='seperator-link']")
    private WebElement signIn3;


    @FindBy (xpath = "//span[text()='להרשמה']")
    private WebElement signupButton;

    @FindBy(xpath = "//input[@placeholder='שם פרטי']")
    private WebElement name;

    @FindBy (xpath = "//input[@placeholder='מייל']")
    private WebElement email;

    @FindBy (id = "valPass")
    private WebElement password;

    @FindBy(xpath = "//input[@placeholder='אימות סיסמה']")
    private WebElement verifyPassword;

    @FindBy (className = "ui-btn")
    private WebElement submitButton;

    public Registration(WebDriver driver)  {
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    public void signIn(){
        WebDriverWait driverWait = new WebDriverWait(driver,20);
        driverWait.until(ExpectedConditions.visibilityOf(signIn3));
        signIn3.click();
    }

    public void signUp(){
        WebDriverWait driverWait = new WebDriverWait(driver,20);
        driverWait.until(ExpectedConditions.elementToBeClickable(signupButton));
        signupButton.click();
    }

    public void setName (String username){
        name.sendKeys(username);
    }
    public void setEmail (String myEmail){
        email.sendKeys(myEmail);
    }
    public void setPassword (String myPassword){
        password.sendKeys(myPassword);
    }

    public void setVerifyPassword (String verify){
        verifyPassword.sendKeys(verify);
    }

    public void submitLogin(){
        submitButton.click();
    }
}
