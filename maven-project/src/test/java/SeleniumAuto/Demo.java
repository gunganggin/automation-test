package SeleniumAuto;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import io.qameta.allure.testng.AllureTestNg;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import Common.*;

public class Demo {

    WebDriver chromeDriver = null;
    boolean isCheckbox;

    CommonFunction common = new CommonFunction();

    JavascriptExecutor js;

    @BeforeTest
    public void SetUp(){
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        // turn on developer mode
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        // turn off feature save password of Google
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);

        options.setExperimentalOption("prefs", prefs);

        chromeDriver = new ChromeDriver(options);

        chromeDriver.manage().window().maximize();

        chromeDriver.manage().window().fullscreen();

        // wait ngầm định
        chromeDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        js = (JavascriptExecutor) chromeDriver;
    }

    @AfterTest
    public void TearDown(){
        common.sleep(1000);

        chromeDriver.close();

        chromeDriver.quit();
    }

    @BeforeMethod
    public void beforeMethod(){
        System.out.println("Day la Before Method");
    }

    @AfterMethod
    public void afterMethod(ITestResult result){
        chromeDriver.manage().deleteAllCookies();

        System.out.println("Day la After Method");

        if(result.getStatus() == ITestResult.FAILURE) {

            String testMethod = result.getName();

            String fileLocaltion = "/Users/gun/Documents/JavaCodeForAuto/AnhTester/maven-project/ScreenShots" + testMethod + ".png";

            File screenShot = ((TakesScreenshot) chromeDriver).getScreenshotAs(OutputType.FILE);

            try {
                FileUtils.copyFile(screenShot, new File(fileLocaltion));
                Path content = Paths.get(fileLocaltion);
                try (InputStream is = Files.newInputStream(content)) {
                    Allure.addAttachment(testMethod, is);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

//    @Test
//    public void button() {
//        chromeDriver.get("http://anhtester.com");
//
//        WebElement btnLogin = chromeDriver.findElement(By.id("btn-login"));
//        btnLogin.click();
//
//        WebElement emailLogin = chromeDriver.findElement(By.name("email"));
//        WebElement passLogin = chromeDriver.findElement(By.name("password"));
//        emailLogin.sendKeys("tranphan167@gmail.com");
//        passLogin.sendKeys("CA9c0c[0A_");
//
//        common.windowScroll0500(js);
//
//        common.sleep(8000);
//
//        WebElement btnSubmit = chromeDriver.findElement(By.id("login"));
//        btnSubmit.click();
//
//        WebElement lkPstMaga = chromeDriver.findElement(By.linkText("Posts Management"));
//        lkPstMaga.click();
//
//        // Css theo ID
//        WebElement btnCss = chromeDriver.findElement(By.cssSelector("a#btn-dangbai"));
//        btnCss.click();
//    }

    // test diff web
    //get all text of elements
    @Test
    public void compareAllTextInPage(){

        chromeDriver.get("https://auto.fresher.dev/lessons/lession7/index.html");

        WebElement titleButtonTxt = chromeDriver.findElement(By.xpath("//*[contains(text(),'Button')]"));
        Assert.assertEquals(titleButtonTxt.getText(), "Button");

        WebElement lblTxt = chromeDriver.findElement(By.xpath("//div[@id='lbStatusButton']"));
        Assert.assertEquals(lblTxt.getText(), "A simple primary alert—check it out!");

        WebElement btn1 = chromeDriver.findElement(By.xpath("//*[@id='btnExample1']"));
        btn1.click();
        Assert.assertEquals(lblTxt.getText(), "Click on Button 1");

        WebElement btn2 = chromeDriver.findElement(By.xpath("//*[contains(text(), 'Button 2')]"));
        btn2.click();
        Assert.assertEquals(lblTxt.getText(), "Click on Button 2");

        WebElement btn3 = chromeDriver.findElement(By.xpath("//*[contains(text(), 'Button 3')]"));
        btn3.click();
        Assert.assertEquals(lblTxt.getText(), "Click on Button 3");

        WebElement titleInputTxt = chromeDriver.findElement(By.xpath("//*[contains(text(),'Input')]"));
        Assert.assertEquals(titleInputTxt.getText(), "Input");

        WebElement titleTextInputTxt = chromeDriver.findElement(By.xpath("//*[contains(text(),'Text input')]"));
        Assert.assertEquals(titleTextInputTxt.getText(), "Text input");

        WebElement inputText = chromeDriver.findElement(By.xpath("//input[@id='txtInput1']"));
        Assert.assertEquals(inputText.isEnabled(), true);
        Assert.assertEquals(inputText.getAttribute("value"), "Default value of input");
        inputText.clear();
        inputText.sendKeys("asdhjasvdjashdva");
        Assert.assertEquals(inputText.getAttribute("value"), "asdhjasvdjashdva");

        WebElement txtDis = chromeDriver.findElement(By.xpath("//*[contains(text(), 'Text input disabled')]"));
        Assert.assertEquals(txtDis.getText(), "Text input disabled");

        WebElement inputTextDis = chromeDriver.findElement(By.xpath("//*[@type='text' and @id='txtInput2']"));
        Assert.assertEquals(inputTextDis.getAttribute("value"), "Default value of input disabled");
        Assert.assertEquals(inputTextDis.isEnabled(), false);

        common.removeAttribute(js, inputTextDis, "disabled");
        inputTextDis.clear();
        inputTextDis.sendKeys("asdhjasvdjashdva");
        Assert.assertEquals(inputTextDis.getAttribute("value"), "asdhjasvdjashdva");
        common.setAttribute(js, inputTextDis, "disabled");

        WebElement numInput = chromeDriver.findElement(By.xpath("//*[contains(text(), 'Number input')]"));
        Assert.assertEquals(numInput.getText(), "Number input");

        WebElement inputNum = chromeDriver.findElement(By.xpath("//input[@type='number' and @id='txtInput3']"));
        Assert.assertEquals(inputNum.getAttribute("value"), "1500");
        inputNum.clear();

        String actualInputTextNum = "sfwd2131.324213#$%%^$%";
        String expectedInputTextNum = "2131.324213";
        inputNum.sendKeys(actualInputTextNum);

        Assert.assertEquals(common.getValue(inputNum), expectedInputTextNum);

        inputNum.clear();
        inputNum.sendKeys("asdhajdjasd");

        Assert.assertEquals(common.getValue(inputNum), "");
//        Assert.assertEquals(common.getValue(inputNum), "edfed"); // Test Fail to generate screenshot
    }

    // Function Select
    @Test
    public void selectOption(){

        chromeDriver.get("https://auto.fresher.dev/lessons/lession7/index.html");

        WebElement selectOpt = chromeDriver.findElement(By.xpath("//select[@id='exampleSelect1']"));
        Select select = new Select(selectOpt);
        select.selectByValue("value2");

        select.selectByIndex(3);

        select.selectByVisibleText("Option 5");

        // get option đã selected
        WebElement selectedOption = select.getFirstSelectedOption();
        System.out.println(selectedOption.getText());

        // lấy giá trị của attribute value
        System.out.println(selectedOption.getAttribute("value"));
        System.out.println(common.getValue(selectedOption));

        // Check text in lable after selected option
        int index = select.getOptions().indexOf(select.getFirstSelectedOption());

        System.out.println(index);

        WebElement lblSelected = chromeDriver.findElement(By.xpath("//div[@id='lbStatusSelect']"));

        Assert.assertEquals(lblSelected.getText(), common.getExpectedTextSelected(select, index));

        //  Option 3
        select.selectByIndex(3);

        Assert.assertEquals(lblSelected.getText(), common.getExpectedTextSelected(select, index));

        // Option 1
        select.selectByVisibleText("Option 1");

        Assert.assertEquals(lblSelected.getText(), common.getExpectedTextSelected(select, index));

        // Kiểm tra có phải dạng Multiple hay ko?
        if(select.isMultiple() == true){
            System.out.println("Đây thuộc kiểu lựa chọn multiple");
        }
        else {
            System.out.println("Chỉ được chọn một option");
        }

        // Số lượng option trong select
        System.out.println(select.getOptions().size());
    }

    @Test
    public void radioCheck(){
        chromeDriver.get("https://auto.fresher.dev/lessons/lession7/index.html");

        common.windowScroll0500(js);

        WebElement titleRadioCheck = chromeDriver.findElement(By.xpath("//*[contains(text(), 'Radio Button')]"));
        Assert.assertEquals(titleRadioCheck.getText(), "Radio Button");

        List<WebElement> listRadio = chromeDriver.findElements(By.xpath("//input[@name='exampleRadios' and @type='radio']"));
        for( int i = 0; i < listRadio.size(); i++){
            WebElement radioEle = listRadio.get(i);
            boolean radioCheck = radioEle.isSelected();
            int check = 0;
            if(radioCheck == true){
                check++;
                if(check == 2){
                    Assert.fail("Radio can not multi check");
                    break;
                }
            }
        }
    }

    @Test
    public void dropDown(){
        chromeDriver.get("https://auto.fresher.dev/lessons/lession7/index.html");

        common.windowScroll0500(js);

        WebElement titleDropdown = chromeDriver.findElement(By.xpath("//h3[contains(text(),'Dropdown')]"));
        Assert.assertEquals(titleDropdown.getText(), "Dropdown");

        WebElement btnDropdown = chromeDriver.findElement(By.xpath("//a[@id='dropdownMenuLink']"));
        Assert.assertEquals(btnDropdown.getText(), "Dropdown link");
        btnDropdown.click();

        WebElement btnDropdowned = chromeDriver.findElement(By.xpath("//div[@class='dropdown-menu show']"));

        List<WebElement> listDropdown = chromeDriver.findElements(By.xpath("//span[@class='dropdown-item']"));
        System.out.println(listDropdown.size());

        for(int i = 0; i < listDropdown.size(); i++){
            if(!btnDropdowned.isDisplayed()) {
                btnDropdown.click();
            }else {
                WebElement action = listDropdown.get(i);
                String textAction = action.getText();
                action.click();
                Assert.assertEquals(textAction, btnDropdown.getText());
            }
        }
    }

    @Test
    public void dynamicDropdown(){
        chromeDriver.get("https://techydevs.com/demos/themes/html/listhub-demo/listhub/index.html");
        common.sleep(2000);

        // element to click
        WebElement clickSelect = chromeDriver.findElement(By.xpath("//body/section[1]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]"));
        clickSelect.click();
        common.sleep(1000);

        WebElement textLabletoCompare = chromeDriver.findElement(By.xpath("/html[1]/body[1]/section[1]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/a[1]/span[1]"));

        // list to select
        List<WebElement> listToSelect = chromeDriver.findElements(By.xpath("//body/section[1]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/ul[1]/li"));
        // Set i không thể cho chạy bắt đầu bằng 0
        for(int i = 1; i <  listToSelect.size(); i++){
            WebElement selectCountry = chromeDriver.findElement(By.xpath("/html[1]/body[1]/section[1]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/ul[1]/li[" + i + "]"));
            String textSelectedCountry = selectCountry.getText();
            System.out.println(textSelectedCountry);
            selectCountry.click();
            common.sleep(100);
            System.out.println(textLabletoCompare.getText());
            Assert.assertEquals(textSelectedCountry, textLabletoCompare.getText());
            common.sleep(100);
            clickSelect.click();
        }
    }

    // Alert
    @Test
    public void praAlert(){
        chromeDriver.get("https://demoqa.com/alerts");
        common.sleep(2000);

        chromeDriver.findElement(By.xpath("//button[@id='promtButton']")).click();
        common.sleep(1000);

        Alert alert = chromeDriver.switchTo().alert();

        System.out.println(alert.getText());


        // Sendkey trên Alert
        alert.sendKeys("Hằng Mỹ");

        // click OK trên alert
        alert.accept();

        WebElement textAlertPrint = chromeDriver.findElement(By.xpath("//span[@id='promptResult']"));
        Assert.assertEquals(textAlertPrint.getText(), "You entered Hằng Mỹ");

        // click Cancel trên alert
//        alert.dismiss();
    }

    @Test
    public void praPopup(){
        chromeDriver.get("http://demo.guru99.com/popup.php");
        chromeDriver.findElement(By.xpath("//a[contains(text(),'Click Here')]")).click();

        // Lấy ID của window gốc
        String mainWindow = chromeDriver.getWindowHandle();

        // lấy tất cả các windows
        // Set list lấy các phần tử không trùng lặp
        Set<String> windows = chromeDriver.getWindowHandles();

        for(String window : windows){
            // kiểm tra window hiện tại
            if(!mainWindow.equals(window)) {
                // switch sang window khác nhau
                WebDriver window1 = chromeDriver.switchTo().window(window);
                common.sleep(1000);

                System.out.println(window1.getCurrentUrl());
                System.out.println(window1.getTitle());

                common.sleep(1000);

                chromeDriver.findElement(By.xpath("//input[@name='emailid']")).sendKeys("HangMy@gmail.com");
                chromeDriver.findElement(By.xpath("//input[@name='btnLogin']")).submit();

                String textTablePageWindow1 = chromeDriver.findElement(By.xpath("//table")).getText();
                System.out.println(textTablePageWindow1);
            }

            // chuyển về mainWindow
            chromeDriver.switchTo().window(mainWindow);
            System.out.println(chromeDriver.getCurrentUrl());
            common.sleep(1000);
        }
    }
}
