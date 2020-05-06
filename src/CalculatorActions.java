
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

@FixMethodOrder
        (MethodSorters.NAME_ASCENDING)
public class CalculatorActions {

    @Rule
    //get method name
    public TestName name = new TestName();
    //driver
    static AndroidDriver<MobileElement> driver;
    //extent report
    private static ExtentReports extent;
    private static ExtentTest myTests;
    //screenshot path
    static String imagePath = "E:\\intellij\\CalculatorTwoNumberActions\\screenshot";
    static String browserXml = "";


    @BeforeClass
    public static void setUp() throws Exception {

        //set up path of xml , config xml and report
//        ExtentHtmlReporter html = new ExtentHtmlReporter("E:\\intellij\\CalculatorTwoNumberActions\\calculator.html");
//        ExtentXReporter extentx = new ExtentXReporter("localhost");
//        ExtentReports extent = new ExtentReports();
//        extent.attachReporter(html, extentx);
//        html.config("")
//        html.loadConfig(new File("E:\\intellij\\CalculatorTwoNumberActions\\reportConfig.xml"));
        extent = new ExtentReports("E:\\intellij\\CalculatorTwoNumberActions\\calculator2.html");                                //report html path
        extent.loadConfig(new File("E:\\intellij\\CalculatorTwoNumberActions\\reportConfig.xml"));                            //xml config path

        //capabilities
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Android Device");
        capabilities.setCapability("newCommandTimeout", 120);
        capabilities.setCapability("platformVersion", "9");

        capabilities.setCapability("appPackage", "com.android.calculator2");
        capabilities.setCapability("appActivity", "com.android.calculator2.Calculator");

        URL url = new URL("http://0.0.0.0:4723/wd/hub/");
        driver = new AndroidDriver(url, capabilities);
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
        Thread.sleep(3000);


    }//end setup


    @After
    //finish test
    public void closeTest(){
        myTests.log(LogStatus.INFO, "", "Test Finished");
        extent.endTest(myTests);
    }//end of closeTest

    @AfterClass
    public static void closeDriver(){
        extent.flush();
        driver.closeApp();
        driver.quit();
    }

    @Test
    //adds 2 numbers
    public void test01_Add() throws Exception {
        myTests = extent.startTest("Add 2 numbers");
        myTests.log(LogStatus.INFO, "Test '" + name.getMethodName() + "' started");

        //first and second number as string and int
        String first = GeneralFunc.firstNumber();
        double firstNumber = Double.parseDouble(first);
        String second = GeneralFunc.secondNumber();
        double secondNumber = Double.parseDouble(second);

        //first number dismantle
        GeneralFunc.dismantleString(first);
        //+
        driver.findElement(By.id("com.android.calculator2:id/op_add")).click();
        //second number dismantle
        GeneralFunc.dismantleString(second);
        //=
        driver.findElement(By.id("com.android.calculator2:id/eq")).click();

        String actualResultString = driver.findElement(By.id("com.android.calculator2:id/formula")).getText();
        actualResultString = actualResultString.replace(",","");

        //Assert results
        double actualResult = Double.parseDouble(actualResultString);
        double expectedResult = firstNumber + secondNumber;

        try {
            Assert.assertEquals(expectedResult, actualResult, 0.001);
            myTests.log(LogStatus.PASS, name.getMethodName() + ". calculation completed, correct result");
            myTests.log(LogStatus.PASS, "", myTests.addScreenCapture(GeneralFunc.takeScreenShot(imagePath + "\\" + System.currentTimeMillis(), driver)));
        }
        catch (AssertionError a){
                myTests.log(LogStatus.FAIL, name.getMethodName() + ". calculation failed, wrong result. Expected was: " + expectedResult + " actual is: " + actualResult);
                myTests.log(LogStatus.FAIL, "", myTests.addScreenCapture(GeneralFunc.takeScreenShot(imagePath + "\\" + System.currentTimeMillis(), driver)));
        }//end try-catch
    }//end test

    @Test
    //subs 2 numbers
    public void test02_Sub() throws Exception {
        Thread.sleep(3000);
        //first and second number as string and int
        String first = GeneralFunc.firstNumber();
        double firstNumber = Double.parseDouble(first);
        String second = GeneralFunc.secondNumber();
        double secondNumber = Double.parseDouble(second);
        double negativeFlag = 1;

        //first number dismantle
        GeneralFunc.dismantleString(first);
        //-
        driver.findElement(By.id("com.android.calculator2:id/op_sub")).click();
        //second number dismantle
        GeneralFunc.dismantleString(second);
        //=
        driver.findElement(By.id("com.android.calculator2:id/eq")).click();

        //replacing non numerical chars
        String actualResultString = driver.findElement(By.id("com.android.calculator2:id/formula")).getText();
        actualResultString = actualResultString.replace(",","");
        if (actualResultString.charAt(0) == '−'){
            actualResultString = actualResultString.replace("−","");
            negativeFlag = -1;
        }

        //Assert results
        double actualResultDouble = Double.parseDouble(actualResultString)*negativeFlag;
        double expectedResult = firstNumber - secondNumber;
        try{
            Assert.assertEquals(expectedResult,actualResultDouble,0.001);
            System.out.println("correct " + expectedResult + " " + actualResultDouble);
        }
        catch (AssertionError A){
            System.out.println("wrong " + expectedResult + " " + actualResultDouble);
        }//end try-catch
    }//end test

    @Test
    //multiply 2 numbers
    public void test03_Mult() throws Exception {
        Thread.sleep(3000);
        //first and second number as string and int
        String first = GeneralFunc.firstNumber();
        double firstNumber = Double.parseDouble(first);
        String second = GeneralFunc.secondNumber();
        double secondNumber = Double.parseDouble(second);

        //first number dismantle
        GeneralFunc.dismantleString(first);
        //*
        driver.findElement(By.id("com.android.calculator2:id/op_mul")).click();
        //second number dismantle
        GeneralFunc.dismantleString(second);
        //=
        driver.findElement(By.id("com.android.calculator2:id/eq")).click();
        //replacing non numerical chars
        String actualResultString = driver.findElement(By.id("com.android.calculator2:id/formula")).getText();
        actualResultString = actualResultString.replace(",","");

        //Assert results
        double actualResult = Double.parseDouble(actualResultString);
        double expectedResult = firstNumber * secondNumber;

        try{
            Assert.assertEquals(expectedResult,actualResult,0.001);
            System.out.println("correct " + expectedResult + " " + actualResult);
        }
        catch (AssertionError A){
            System.out.println("wrong " + expectedResult + " " + actualResult);
        }//end try-catch
    }//end test

    @Test
    //dividing 2 numbers
    public void test04_Div() throws Exception {
        Thread.sleep(3000);
        //first and second number as string and int
        String first = GeneralFunc.firstNumber();
        double firstNumber = Double.parseDouble(first);
        String second = GeneralFunc.secondNumber();
        double secondNumber = Double.parseDouble(second);
        //checks possibility of dividing by 0
        if ((second.length()==1) && (second.charAt(0) == '0')){
            System.out.println("div by 0 unavailable");
        }
        else {
            //first number dismantle
            GeneralFunc.dismantleString(first);
            //div operator
            driver.findElement(By.id("com.android.calculator2:id/op_div")).click();
            //second number dismantle
            GeneralFunc.dismantleString(second);
            //=
            driver.findElement(By.id("com.android.calculator2:id/eq")).click();
            //replacing non numerical chars
            String actualResultString = driver.findElement(By.id("com.android.calculator2:id/formula")).getText();
            actualResultString = actualResultString.replace(",", "");

            //Assert results
            double actualResult = Double.parseDouble(actualResultString);
            double expectedResult = firstNumber / secondNumber;

            try {
                Assert.assertEquals(expectedResult, actualResult, 0.001);
                System.out.println("correct " + expectedResult + " " + actualResult);
            } catch (AssertionError A) {
                System.out.println("wrong " + expectedResult + " " + actualResult);
            }//end try-catch
        }//end else
    }//end test


}//end CalculatorActions
