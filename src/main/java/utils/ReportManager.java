package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import org.testng.ITestContext;
import org.testng.Reporter;


public class ReportManager {

    ExtentTest extentTest ;
    public static ExtentReports extent = null;

    /**
     * Method to create html report file in the location
     * @param context
     * @return Extent report .html file
     */
    protected ExtentReports createInstance(ITestContext context) {
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter("extent.html");
        htmlReporter.config().setReportName(context.getCurrentXmlTest().getName());
        htmlReporter.config().setTheme(Theme.DARK);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        return extent;
    }

    /**
     * Method to log the Scenario name
     * @param scenarioName
     */
    public synchronized void logScenario(String scenarioName) {
        Markup m = MarkupHelper.createLabel(scenarioName, ExtentColor.BLUE);
        extentTest = extent.createTest(scenarioName);
        extentTest.info(m);
        Reporter.log(scenarioName+"\n");

    }

    /**
     * Method to log each step definition
     * @param message
     */
    public synchronized void logStepAction(String message) {
        extentTest.info(message);
        Reporter.log(message+"\n");
    }

    /**
     * Method to deconstruct the ExtentReports object
     */
    public void reportTearDown(){
        extent.flush();
    }

}