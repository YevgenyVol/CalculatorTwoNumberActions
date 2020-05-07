
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


    @BeforeClass
    //setup
    public static void setUp() throws Exception {
        extent = new ExtentReports("E:\\intellij\\CalculatorTwoNumberActions\\calculator2.html");                                //report html path
        extent.loadConfig(new File("E:\\intellij\\CalculatorTwoNumberActions\\reportConfig.xml"));                            //xml config path

        //android capabilities
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Android Device");
        capabilities.setCapability("newCommandTimeout", 120);
        capabilities.setCapability("platformVersion", "9");

        capabilities.setCapability("appPackage", "com.android.calculator2");
        capabilities.setCapability("appActivity", "com.android.calculator2.Calculator");

        URL url = new URL("http://0.0.0.0:4723/wd/hub/");       //appium
        driver = new AndroidDriver(url, capabilities);
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
    }//end setup


    @After
    //finish test
    public void closeTest(){
        myTests.log(LogStatus.INFO, "", "Test Finished");
        extent.endTest(myTests);
    }//end of closeTest


    @AfterClass
    //close app, save report
    public static void closeDriver(){
        extent.flush();
        driver.closeApp();
        driver.quit();
    }



    @Test
    //adds 2 numbers
    public void test01_Add() throws Exception {
        Thread.sleep(1000);
        //start test
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

        //replacing non numerical chars
        String actualResultString = driver.findElement(By.id("com.android.calculator2:id/formula")).getText();
        actualResultString = actualResultString.replace(",","");

        //Assert results
        double actualResult = Double.parseDouble(actualResultString);
        double expectedResult = firstNumber + secondNumber;
        myTests.log(LogStatus.INFO, "numbers are " + firstNumber + " and " + secondNumber);
        try {
            Assert.assertEquals(expectedResult, actualResult, 0.001);
            myTests.log(LogStatus.PASS, name.getMethodName() + ". calculation completed, result is correct");
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
        Thread.sleep(1000);
        //start test
        myTests = extent.startTest("Subtracts 2 numbers");
        myTests.log(LogStatus.INFO, "Test '" + name.getMethodName() + "' started");

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
        myTests.log(LogStatus.INFO, "numbers are " + firstNumber + " and " + secondNumber);
        //Assert results
        double actualResultDouble = Double.parseDouble(actualResultString)*negativeFlag;
        double expectedResult = firstNumber - secondNumber;
        try{
            Assert.assertEquals(expectedResult,actualResultDouble,0.001);
            myTests.log(LogStatus.PASS, name.getMethodName() + ". calculation completed, result is correct");
            myTests.log(LogStatus.PASS, "", myTests.addScreenCapture(GeneralFunc.takeScreenShot(imagePath + "\\" + System.currentTimeMillis(), driver)));
        }
        catch (AssertionError A){
            myTests.log(LogStatus.FAIL, name.getMethodName() + ". calculation failed, wrong result. Expected was: " + expectedResult + " actual is: " + actualResultDouble);
            myTests.log(LogStatus.FAIL, "", myTests.addScreenCapture(GeneralFunc.takeScreenShot(imagePath + "\\" + System.currentTimeMillis(), driver)));
        }//end try-catch
    }//end test



    @Test
    //multiply 2 numbers
    public void test03_Mult() throws Exception {
        Thread.sleep(1000);
        //start test
        myTests = extent.startTest("Multiplying 2 numbers");
        myTests.log(LogStatus.INFO, "Test '" + name.getMethodName() + "' started");

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
        myTests.log(LogStatus.INFO, "numbers are " + firstNumber + " and " + secondNumber);
        try{
            Assert.assertEquals(expectedResult,actualResult,0.001);
            myTests.log(LogStatus.PASS, name.getMethodName() + ". calculation completed, result is correct");
            myTests.log(LogStatus.PASS, "", myTests.addScreenCapture(GeneralFunc.takeScreenShot(imagePath + "\\" + System.currentTimeMillis(), driver)));
        }
        catch (AssertionError A){
            myTests.log(LogStatus.FAIL, name.getMethodName() + ". calculation failed, wrong result. Expected was: " + expectedResult + " actual is: " + actualResult);
            myTests.log(LogStatus.FAIL, "", myTests.addScreenCapture(GeneralFunc.takeScreenShot(imagePath + "\\" + System.currentTimeMillis(), driver)));
        }//end try-catch
    }//end test



    @Test
    //dividing 2 numbers
    public void test04_Div() throws Exception {
        Thread.sleep(1000);
        //dividing 2 numbers
        myTests = extent.startTest("Dividing 2 numbers");
        myTests.log(LogStatus.INFO, "Test '" + name.getMethodName() + "' started");

        //first and second number as string and int
        String first = GeneralFunc.firstNumber();
        double firstNumber = Double.parseDouble(first);
        String second = GeneralFunc.secondNumber();
        double secondNumber = Double.parseDouble(second);

        myTests.log(LogStatus.INFO, "numbers are " + firstNumber + " and " + secondNumber);
        //checks possibility of dividing by 0
        if ((second.length()==1) && (second.charAt(0) == '0')){
            myTests.log(LogStatus.ERROR, name.getMethodName() + ". YOU CAN NOT DIVIDE BY 0!!!");
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
                myTests.log(LogStatus.PASS, name.getMethodName() + ". calculation completed, result is correct");
                myTests.log(LogStatus.PASS, "", myTests.addScreenCapture(GeneralFunc.takeScreenShot(imagePath + "\\" + System.currentTimeMillis(), driver)));
            } catch (AssertionError A) {
                myTests.log(LogStatus.FAIL, name.getMethodName() + ". calculation failed, wrong result. Expected was: " + expectedResult + " actual is: " + actualResult);
                myTests.log(LogStatus.FAIL, "", myTests.addScreenCapture(GeneralFunc.takeScreenShot(imagePath + "\\" + System.currentTimeMillis(), driver)));
            }//end try-catch
        }//end else
    }//end test



}//end CalculatorActions
