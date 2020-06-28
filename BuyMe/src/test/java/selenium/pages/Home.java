package selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.ArrayList;
import java.util.List;

public class Home {

    private static WebDriver driver;
    private static WebDriverWait wait;
    private String gmail = "@gmail.com";

    @FindBy (className = "seperator-link")
    private WebElement login2;

    @FindBy (xpath = "//input[@placeholder='מייל']")
    private WebElement email;

    @FindBy (xpath = "//input[@placeholder='סיסמה']")
    private WebElement password;

    @FindBy (className = "ui-btn")
    private WebElement submit;

    @FindBy (xpath = "//span[text()='החשבון שלי']")
    public WebElement myAccount;

    @FindBy(className = "ember-chosenselect")
    private List<WebElement> allElements;

    @FindBy(xpath = ".//li[contains(@class,'active-result')]")
    private List<WebElement> selectOne;

    @FindBy (className = "search")
    public WebElement submitSearch;

    @FindBy(className = "page-title")
    private WebElement title;

    public Home(WebDriver driver)  {
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }


    public void verifyMyAccountIsVisible(){
        WebDriverWait driverWait = new WebDriverWait(driver,20);

        driverWait.until(ExpectedConditions.visibilityOf(myAccount));

    }
    public List<WebElement> getAllElements(){
        return allElements;
    }

    public List<WebElement> getSelectOne(){
        return selectOne;
    }

    public List<String> getSelectionList(List<String> searchParams, String amount, String area , String category, List<WebElement> allElements)  throws InterruptedException {
        //create an empty list in which the selected items will be stored
        searchParams = new ArrayList<>();
        amount = null;
        area = null;
        category = null;
        //iterate through all the webElements
        label:
        for (int i = 0; i < allElements.size(); i++) {
            String searchValue = allElements.get(i).getText();
            System.out.println(searchValue);
            switch (searchValue) {
                case "סכום": {
                    allElements.get(i).click();
                    List<WebElement> selectA = getSelectOne();
                    amount = selectA.get(2).getText();
                    searchParams.add(amount);
                    selectA.get(2).click();

                    Thread.sleep(300);
                    i++;
                    break;
                }
                case "אזור": {
                    allElements.get(i).click();
                    List<WebElement> selectA = getSelectOne();
                    area = selectA.get(3).getText();
                    searchParams.add(area);

                    selectA.get(3).click();

                    Thread.sleep(300);
                    i++;
                    break;
                }
                case "קטגוריה": {
                    allElements.get(i).click();
                    List<WebElement> selectA = getSelectOne();
                    category = selectA.get(1).getText();
                    searchParams.add(category);

                    selectA.get(1).click();

                    Thread.sleep(300);
                    break label;
                }
                default:
                    System.out.println("problem");
                    break;
            }
        }
        //return all selected categories
            return searchParams;

    }


    public void setSubmitSearch(){
        try { Thread.sleep(300); }
        catch (InterruptedException e) { e.printStackTrace(); }
        submitSearch.click();
    }

    public String getTitle(){
        return title.getText();
    }

}
