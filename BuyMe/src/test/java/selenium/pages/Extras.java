package selenium.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import java.util.List;

public class Extras {
    private static WebDriver driver;
    private ChooseGifts chooseGifts;


    @FindBy(className = "fb_reset")
    private WebElement loadingDots;

    @FindBy(xpath = ".//li[contains(@class,'parsley-required')]")
    private List<WebElement> errorMessage;

    @FindBy(className = "menu")
    private WebElement menuDropDown;

    @FindBy(xpath = "//span[text() = 'יציאה']")
    public WebElement logout;

    @FindBy(xpath = "//span[@alt = 'סגירה']")
    private WebElement closeX;

    @FindBy(className = "btn-purchase")
    private WebElement chooseCardToBuy;

    @FindBy(xpath = "//*[@class='text']")
    private List<WebElement> textOfSendTo;

    @FindBy(xpath = "//*[text()='למי לשלוח']")
    public WebElement sendTo;

    @FindBy(xpath = "//*[@class='receiver']/child::span[2]")
    private WebElement receiverName;

    @FindBy(xpath = "//*[@class='sender']/child::span[2]")
    private WebElement senderName;

    @FindBy(className = "card-img")
    public WebElement cardImage;

    @FindBy(className = "input-cash")
    private List<WebElement> insertAmount;

    @FindBy (xpath = "//a[text()='תמצאו לי מתנה']")
    private WebElement submitSearch;


    @FindBy(xpath = "//span[text()='לבחירה']")
    private WebElement submitCard;


    public Extras(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public int getLoadingDotsHeight() {
        return loadingDots.getSize().getHeight();
    }

    public int getLoadingDotsWidth() {
        return loadingDots.getSize().getWidth();
    }

    public List<WebElement> getErrorMessage() {
        return errorMessage;
    }

    public void clickOnX() {
        closeX.click();
    }

    public List<WebElement> getSendToText() {
        return textOfSendTo;
    }

    public WebElement getSelectionList(List<WebElement> listOfElementsWithText) {
//        listOfElementsWithText = getSendToText();
        WebElement element = null;
        for (WebElement webElement : listOfElementsWithText) {
            if (webElement.equals(sendTo)) {
                element = webElement;
                return element;
            } else {
                System.out.println("didn't find the element yet...searching");
                ;
            }

        }
        return element;
    }

    public String getRGBColorReturnHexColor(String colorOfSendTo) {
        String[] hexValue = colorOfSendTo.replace("rgba(", "").replace(")", "").split(",");

        int hexValue1 = Integer.parseInt(hexValue[0]);
        hexValue[1] = hexValue[1].trim();
        int hexValue2 = Integer.parseInt(hexValue[1]);
        hexValue[2] = hexValue[2].trim();
        int hexValue3 = Integer.parseInt(hexValue[2]);
        String actualColor = String.format("#%02x%02x%02x", hexValue1, hexValue2, hexValue3);

        return actualColor;
    }

    public String getReceiverName() {
        return receiverName.getText();
    }

    public String getSenderName() {
        return senderName.getText();
    }

    private List<WebElement> insertAmounts() {
        return insertAmount;
    }


    public void selectCardAndAmount(int amount) {
        chooseGifts = new ChooseGifts(driver);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        chooseGifts.getCard().click();
        chooseGifts.getCard().click();
        List<WebElement> myElementList = insertAmounts();
        if (myElementList.isEmpty()) {
            submitCard.click();}
                 else {
                     insertAmount.get(0).sendKeys(String.valueOf(amount));
                     insertAmount.get(0).sendKeys(Keys.ENTER);
                    }
    }

}


