package common;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.AppiumServerSetUp;
import utils.PropertyFileReader;

import java.util.concurrent.TimeUnit;

public class BaseUtil {
    public static AppiumDriver mobileDriver = null;
    public static PropertyFileReader propertyFileReader = new PropertyFileReader();

    /**
     * Method to instantiate Appium server
     */
    public static void init() {
        AppiumServerSetUp.startAppium();
    }

    /**
     * Method to setup the Appium driver capabilities and initialize the Android driver
     */
    public static void initializeTestBase() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", propertyFileReader.getProperty("platformName"));
        capabilities.setCapability("platformVersion", propertyFileReader.getProperty("platformVersion"));
        capabilities.setCapability("deviceName", propertyFileReader.getProperty("deviceName"));
        capabilities.setCapability("app", propertyFileReader.getProperty("app"));
        capabilities.setCapability("appPackage", propertyFileReader.getProperty("appPackage"));
        capabilities.setCapability("appActivity", propertyFileReader.getProperty("appActivity"));
        capabilities.setCapability("noReset", false);
        capabilities.setCapability("newCommandTimeout", 5000);

        mobileDriver = new AndroidDriver(AppiumServerSetUp.getAppiumUrl(), capabilities);
        mobileDriver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
    }

    /**
     * Method to stop Appium server
     */
    public static void tearDownSuite() {
        AppiumServerSetUp.stopAppium();
    }
}
