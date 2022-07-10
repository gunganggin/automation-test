package Common;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class CommonFunction {

    public int sleep(int time){
        try {
            Thread.sleep(time);
        } catch (Exception ex){
            throw new RuntimeException("Can not Sleep");
        }
        return time;
    }

    public void windowScroll0500(JavascriptExecutor js){
        js.executeScript("window.scrollBy(0,500)");
    }

    // Function set attribute: Cú pháp: arguments[0].setAttribute("disabled", )
    public void setAttribute(JavascriptExecutor js, WebElement element, String attr){
        js.executeScript("arguments[0].setAttribute('" + attr + "', '')", element);
    }

    // Function remove attribute: Cú pháp: arguments[0].removeAttribute("disabled")
    public void removeAttribute(JavascriptExecutor js, WebElement element, String attr){
        js.executeScript("arguments[0].removeAttribute('" + attr + "')", element);
    }

    public String getValue(WebElement element){
        return element.getAttribute("value");
    }

    public String getExpectedTextSelected(Select select, int index){
        index = select.getOptions().indexOf(select.getFirstSelectedOption());
        String expectedTextSelection = "Selected: value" + (index + 1);
        return expectedTextSelection;
    }

}
