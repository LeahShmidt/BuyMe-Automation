package selenium.tests;

import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.pages.*;
import selenium.utils.InvokeBrowser;
import selenium.utils.Screenshot;
import selenium.utils.Files;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class BuyMeTester {
    private  static WebDriver driver;
    private Screenshot screenshot = new Screenshot();
    private static String shortString = "Aa1";
    private static String uuid = RandomStringUtils.randomAlphanumeric(6);
    private String uniqueId = shortString + uuid;
    private String myName = uuid;
    private String currentTime = String.valueOf(System.currentTimeMillis());
    private static ExtentReports extent ;
    private static ExtentTest test ;
    private static final String PATH = "C:\\Users\\lshmidt\\Desktop\\";
    private static final String WEBSITE = "https://buyme.co.il/";
    private static DateFormat df = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
    private static Date date = new Date();
    private static ATUTestRecorder recorder;
    private static String videoFolder = "C:\\Users\\lshmidt\\AppData\\Local\\Temp\\ScriptVideos\\";
    private static String videoFile = "TestVideo-" + df.format(date);


    //setup drivers and extent reports and video
    @BeforeClass
    public static void before() throws ParserConfigurationException, org.xml.sax.SAXException, IOException,  ATUTestRecorderException {

        Files files = new Files();
        InvokeBrowser invokeBrowser = new InvokeBrowser();
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("C:\\Users\\lshmidt\\Desktop\\extent.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        test = extent.createTest("BuyMe test", "Sign up, choose present, and send");
        extent.setSystemInfo("Environment", "Production");
        extent.setSystemInfo("Tester", "Leah Shmidt");
        test.log(Status.INFO, "@Before class");
        driver = invokeBrowser.getBrowser();
        String site = files.readFile("URL");

        //Created object of ATUTestRecorder
        //Provide path to store videos and file name format.
        recorder = new ATUTestRecorder(videoFolder,videoFile,false);
        recorder.start();
        driver.get(site);
    }

// sign up to be a user in the system
    @Test
    public  void A_signUp() throws  IOException {

        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        Registration registration = new Registration(driver);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        registration.signIn();
        test.pass("sign up: ", MediaEntityBuilder.createScreenCaptureFromPath(screenshot.takeScreenShot(driver,
                PATH + currentTime)).build());
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        registration.signUp();
        test.log(Status.PASS, "user signed up to the system");

    }
//now sign in to the system using your credentials
    @Test
    public void B_signIn () throws IOException, InterruptedException{
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        Registration registration = new Registration(driver);
        registration.setName(myName);
        Thread.sleep(300);
        String gmail = "@gmail.com";
        registration.setEmail(uniqueId + gmail);
        Thread.sleep(300);
        registration.setPassword(uniqueId);
        registration.setVerifyPassword(uniqueId);
        Thread.sleep(300);
        System.out.println("Shhhh... My password is: " + uniqueId);
        test.log(Status.INFO, uniqueId);
        registration.submitLogin();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        Home home = new Home(driver);
        home.verifyMyAccountIsVisible();
        String expected = home.myAccount.getText();

        Assert.assertEquals("החשבון שלי",expected);
        test.log(Status.PASS, "user signed in");
        test.pass("sign In: ", MediaEntityBuilder.createScreenCaptureFromPath(screenshot.takeScreenShot(driver,
                PATH + currentTime)).build());

    }

//choose categories from 3 lists and then search for a business that you would like to buy from, verify that the search
// was performed correctly
    @Test
    public void C_selectGiftCardParameters() throws  IOException, InterruptedException {
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        Home home = new Home(driver);
        List<WebElement> selectAmount = home.getAllElements();
        String amount  = null;
        String area = null;
        String category = null;
        List<String> searchParams = new ArrayList<>();
        searchParams = home.getSelectionList(searchParams,amount, area, category, selectAmount);
        test.pass("search: ", MediaEntityBuilder.createScreenCaptureFromPath(screenshot.takeScreenShot(driver,
                PATH + currentTime)).build());
        home.setSubmitSearch();
        String title = home.getTitle();
        System.out.println(searchParams);
        System.out.println(title);

        Assert.assertTrue(title.contains("תוצאות חיפוש"));
        Assert.assertTrue(title.contains(searchParams.get(0)));
        Assert.assertTrue(title.contains(searchParams.get(1)));
        Assert.assertTrue(title.contains(searchParams.get(2)));

        test.log(Status.PASS, "search successful");

    }
// compare the website url with the current url to see that user switched to a new url:
    @Test
    public void D_compareURLs(){
     String URL = driver.getCurrentUrl();

        System.out.println(URL);

        Assert.assertNotEquals(WEBSITE, URL );
        Assert.assertTrue(URL.contains("budget"));
        Assert.assertTrue(URL.contains("category"));
        Assert.assertTrue(URL.contains("region"));

    }

// select a  gift card :
    @Test
    public void E_selectGiftCard() throws IOException {
    ChooseGifts chooseGifts = new ChooseGifts(driver);
    String selectedCard = chooseGifts.getCard().getText();
    System.out.println(selectedCard);
    test.log(Status.PASS, "select a card");
    test.pass("select card: ", MediaEntityBuilder.createScreenCaptureFromPath(screenshot.takeScreenShot(driver,
            PATH + currentTime)).build());
    chooseGifts.clickOnCard();

    }
// insert all info regarding the sender of the card, the receiver and a greeting:
    @Test
    public void F_prePaymentProcess() throws IOException, InterruptedException {
        SenderAndReceiverInfo senderAndReceiverInfo = new SenderAndReceiverInfo(driver);
        senderAndReceiverInfo.clickOnRadioButtonSomebodyElse();
        senderAndReceiverInfo.insertReceiverName("Suri");
        senderAndReceiverInfo.insertSenderName("Me");
        senderAndReceiverInfo.insertMessage("this is for your b-day in a few months....");
        senderAndReceiverInfo.UploadFileAction();
        senderAndReceiverInfo.getSelectionList(3);
        senderAndReceiverInfo.clickOnSendRadioButton();
        senderAndReceiverInfo.sendEmailNotification("myEmail@gmail.com");
        test.log(Status.PASS, "inserted all the sender and receiver info");
        test.pass("information specified: ", MediaEntityBuilder.createScreenCaptureFromPath(screenshot.takeScreenShot(driver, PATH + currentTime)).build());

        senderAndReceiverInfo.performPayment();
    }
// when the site loads, there are dots that are displayed. these are their dimensions:
    @Test
    public void G_findLoadingDotsSize (){
        Extras extras = new Extras(driver);
        Home home = new Home(driver);
        driver.get(WEBSITE);
        home.verifyMyAccountIsVisible();
        Actions action = new Actions(driver);
        WebElement we = home.myAccount;
        action.moveToElement(we).perform();
        By locator = By.xpath("//span[text() = 'יציאה']");
        driver.findElement(locator).click();
        driver.get(WEBSITE);
        int dotsHeight = extras.getLoadingDotsHeight();
        int dotsWidth = extras.getLoadingDotsWidth();
        System.out.println(dotsHeight);
        System.out.println(dotsWidth);
        test.log(Status.PASS, "loading dots...");
        try { test.pass("loading dots size ", MediaEntityBuilder.
                createScreenCaptureFromPath(screenshot.takeScreenShot(driver, PATH + currentTime)).build()); }
        catch (IOException e) { e.printStackTrace(); }

    }
// log out of the system and then attempt logging in without credentials - verify error messages:
    @Test
    public void H_assertErrorMessageForMissingCredentials () throws InterruptedException {
        Registration registration = new Registration(driver);
        Extras extras = new Extras(driver);
        Thread.sleep(1000);
        registration.signIn();
        registration.submitLogin();
        List<WebElement> errors = extras.getErrorMessage();
        String errorOnEmail = errors.get(0).getText();
        String errorOnPassword = errors.get(1).getText();
        String expectedError = "כל המתנות מחכות לך! אבל קודם צריך מייל וסיסמה";

        Assert.assertEquals(expectedError,errorOnEmail);
        Assert.assertEquals(expectedError,errorOnPassword);
        extras.clickOnX();

    }
// scroll down to the bottom og the page and take a screenshot:
    @Test
    public void I_scrollDownAndCapture(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            test.log(Status.PASS, "scroll to the bottom of the page: ");
            test.pass("scroll result: ", MediaEntityBuilder.createScreenCaptureFromPath(screenshot.takeScreenShot(driver, PATH + currentTime)).build());
        } catch (IOException e) {
            e.printStackTrace();
            test.log(Status.FAIL,"did not capture a picture of scrolldown");
        }
    }
// assert color of text in step 2 of sending the gift card:
    @Test
    public void J_assertTextColor(){
        Home home = new Home(driver);
        Extras extras = new Extras(driver);
        WebDriverWait driverWait = new WebDriverWait(driver, 20);
        driverWait.until(ExpectedConditions.visibilityOf(home.submitSearch));
        home.setSubmitSearch();
        extras.selectCardAndAmount(200);
        List <WebElement> listOfElementsWithText = extras.getSendToText();
        extras.getSelectionList(listOfElementsWithText);
        String colorOfSendTo = extras.sendTo.getCssValue("color");
        String actualColor = extras.getRGBColorReturnHexColor(colorOfSendTo);

        Assert.assertEquals("#fab442", actualColor);
        System.out.println("text is orange");
    }
//verify data in preview is the same as inputted in input fields:
    @Test
    public void K_verifyInputDataIsAsExpected(){
        Extras extras = new Extras(driver);
        SenderAndReceiverInfo senderAndReceiverInfo = new SenderAndReceiverInfo(driver);
        senderAndReceiverInfo.insertReceiverName("Libby");
        senderAndReceiverInfo.insertSenderName("Mommy");
        senderAndReceiverInfo.insertMessage("You are the sweetest girl in 1st grade!");
        String insertedReceiverName = senderAndReceiverInfo.getReceiverName().getAttribute("value");
        String insertedSenderName = senderAndReceiverInfo.getSenderName().getAttribute("value");

        Assert.assertEquals(insertedReceiverName, extras.getReceiverName());
        Assert.assertEquals(insertedSenderName, extras.getSenderName());

        System.out.println(insertedReceiverName + " equals: " + extras.getReceiverName());
        System.out.println(insertedSenderName + " equals: " + extras.getSenderName());



    }
//attach video to extent report:
    @Test
    public void createAndAttachScreencast()  {
        test.log(Status.INFO,"<a href='" + videoFolder + videoFile + ".mov" +
                "'><span class='label info'>Link to video</span></a>");
    }

// close report and driver
    @AfterClass
    public static void tearDown() throws ATUTestRecorderException {
        recorder.stop();
        driver.quit();
        test.log(Status.INFO, "completed tests");
        extent.flush();

    }


}
