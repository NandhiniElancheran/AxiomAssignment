package common;

import org.testng.ITestContext;
import org.testng.annotations.*;
import utils.ReportManager;


public class TestBaseSetup extends ReportManager {

    @BeforeSuite(alwaysRun = true)
    public void setUp(){
        BaseUtil.init();
    }

    @BeforeClass(alwaysRun = true)
    public void initializeTestBaseSetUp(){
        BaseUtil.initializeTestBase();
    }

    @BeforeMethod(alwaysRun = true)
    public void initializeTest(ITestContext context){
        createInstance(context);
    }

    @AfterMethod(alwaysRun = true)
    public void endTestMethod(){
        reportTearDown();
    }

    @AfterClass(alwaysRun = true)
    public void afterTest(){
    }

    @AfterSuite(alwaysRun = true)
    public void endTestSuite(){
        BaseUtil.tearDownSuite();
    }

}
