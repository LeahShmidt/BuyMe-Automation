package selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class SenderAndReceiverInfo {
    private static WebDriver driver;
    private static WebDriverWait wait;

    @FindBy(xpath = "//label[@data='forSomeone']")
    private WebElement radioButtonForSomeone;


    @FindBy( xpath = "//span[contains(text(),'למי המתנה')]/following-sibling::input")
    private WebElement receiverName;

    @FindBy( xpath = "//span[contains(text(),'ממי המתנה')]/following-sibling::input")
    private WebElement senderName;

    @FindBy(xpath = "//textarea[@rows='4']")
    private WebElement message;

    @FindBy(xpath = "//input[@name='fileUpload']")
    private WebElement uploadFile;

    @FindBy(className = "ember-chosenselect")
    private WebElement allElements;

    @FindBy(xpath = ".//li[contains(@class,'active-result')]")
    private List<WebElement> selectOne;

    @FindBy(className = "send-now")
    private WebElement radioButtonSendNow;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement pay;

    @FindBy(xpath = "//span[text()='במייל']")
    private WebElement emailOption;

    @FindBy(xpath = "//input[@placeholder='כתובת המייל של מקבל/ת המתנה']")
    private WebElement emailText;

    @FindBy(xpath = "//button[text()='שמירה']")
    private WebElement saveEmail;

    public SenderAndReceiverInfo(WebDriver driver)  {
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }

    public void clickOnRadioButtonSomebodyElse(){
        radioButtonForSomeone.click();
    }

    public void insertReceiverName(String name){
        receiverName.sendKeys(name);
    }
    public WebElement getReceiverName(){
        return receiverName;
    }

    public WebElement getSenderName(){
        return senderName;
    }

    public void insertSenderName(String name){
        senderName.sendKeys(name);
    }

    public void insertMessage(String text){
        message.sendKeys(text);
    }

    public void UploadFileAction(){
        uploadFile.sendKeys("C:\\Users\\lshmidt\\Desktop\\tear-drop crystal.jpg");
    }

    private List<WebElement> getSelectOne(){
        return selectOne;
    }

    public void getSelectionList(int selection)  throws InterruptedException {
       allElements.click();
       List<WebElement> selectA = getSelectOne();
       String selectedReason = selectA.get(2).getText();
       System.out.println(selectedReason);
       selectA.get(selection).click();
       Thread.sleep(300);
    }

    public void clickOnSendRadioButton(){
        radioButtonSendNow.click();
    }

    public void sendEmailNotification(String email){
        emailOption.click();
        emailText.sendKeys(email);
        saveEmail.click();

    }

    public void performPayment(){
        pay.click();
    }

}
