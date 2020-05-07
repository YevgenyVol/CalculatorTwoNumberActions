import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;

public class GeneralFunc {
    static String browserXml = "E:\\intellij\\CalculatorTwoNumberActions\\calculator.xml";

    //read from xml
    public static String readFromFile(String keyData, String path) throws Exception{
        File xmlFile = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dbuilder = dbFactory.newDocumentBuilder();
        Document doc = dbuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName(keyData).item(0).getTextContent();
    }//end of readFromFile

    //get first number from XML
    public static String firstNumber() throws Exception {
        return readFromFile("FirstNumber",browserXml);
    }

    //get second number from XML
    public static String secondNumber() throws Exception {
        return readFromFile("SecondNumber",browserXml);
    }

    //Take screenshot function
    public static String takeScreenShot(String ImagesPath, WebDriver driver) {
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File screenShotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        File destinationFile = new File(ImagesPath+".png");
        try {
            FileHandler.copy(screenShotFile, destinationFile);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return ImagesPath+".png";
    }//end of takeScreenShot

    //pressing chose char from xml on calculator
    public static void charToPress(char num){
        switch (num){
            case '1' : {
                CalculatorActions.driver.findElement(By.id("com.android.calculator2:id/digit_1")).click();
                break;
            }
            case '2' : {
                CalculatorActions.driver.findElement(By.id("com.android.calculator2:id/digit_2")).click();
                break;
            }
            case '3' : {
                CalculatorActions.driver.findElement(By.id("com.android.calculator2:id/digit_3")).click();
                break;
            }
            case '4' : {
                CalculatorActions.driver.findElement(By.id("com.android.calculator2:id/digit_4")).click();
                break;
            }
            case '5' : {
                CalculatorActions.driver.findElement(By.id("com.android.calculator2:id/digit_5")).click();
                break;
            }
            case '6' : {
                CalculatorActions.driver.findElement(By.id("com.android.calculator2:id/digit_6")).click();
                break;
            }
            case '7' : {
                CalculatorActions.driver.findElement(By.id("com.android.calculator2:id/digit_7")).click();
                break;
            }
            case '8' : {
                CalculatorActions.driver.findElement(By.id("com.android.calculator2:id/digit_8")).click();
                break;
            }
            case '9' : {
                CalculatorActions.driver.findElement(By.id("com.android.calculator2:id/digit_9")).click();
                break;
            }
            case '0' : {
                CalculatorActions.driver.findElement(By.id("com.android.calculator2:id/digit_0")).click();
                break;
            }
            case '.' : {
                CalculatorActions.driver.findElement(By.id("com.android.calculator2:id/dec_point")).click();
                break;
            }
            default: break;
        }//end switch
    }//end of char to press

    //dismantle string
    public static void dismantleString(String numString){
        //first number press on calculator
        while (!(numString.isEmpty())) {
            //press button on calculator position 0
            charToPress(numString.charAt(0));
            //remove char from position 0 (first char)
            numString = numString.substring(1);
        }//end while
    }//end dismantleString
}//end GeneralFunc
