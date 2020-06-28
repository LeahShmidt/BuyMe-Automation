package selenium.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class InvokeBrowser {

    Files files = new Files();



    private  WebDriver invokeChrome(){
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\lshmidt\\SeleniumDrivers\\chromedriver.exe");
// set as headless:
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver();
        return driver;

    }

    private  WebDriver invokeFF(){
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\lshmidt\\SeleniumDrivers\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        return driver;

    }

    public  WebDriver getBrowser() throws ParserConfigurationException, SAXException, IOException {
        String browser = files.readFile("browser");
        WebDriver driver;
        switch (browser){
            case "chrome":{
                driver = invokeChrome();
                
                break;}
            case "FF":{
                driver = invokeFF();
                break;
                }
            default:
                throw new IllegalStateException("Unexpected value: " + browser);
        }
            return driver;
    }


}
