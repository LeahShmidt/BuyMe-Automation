package selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class ChooseGifts {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @FindBy (className = "thumbnail")
    public WebElement card;

    @FindBy (xpath = "//a[text()='תמצאו לי מתנה']")
    private WebElement submitSearch;

    public ChooseGifts(WebDriver driver)  {
        this.driver=driver;

        PageFactory.initElements(driver, this);
    }


    public WebElement getCard(){
        return card;
    }

    public void clickOnCard(){
        WebDriverWait driverWait = new WebDriverWait(driver,20);
        driverWait.until(ExpectedConditions.elementToBeClickable(card));
        driverWait.until(ExpectedConditions.visibilityOf(card));
        getCard().click();
    }
}
