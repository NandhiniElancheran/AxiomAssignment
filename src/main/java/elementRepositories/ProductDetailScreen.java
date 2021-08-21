package elementRepositories;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.offset.PointOption;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;

import org.testng.Assert;

import java.util.HashMap;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static java.time.Duration.ofMillis;

public class ProductDetailScreen {
    AppiumDriver driver;

    public ProductDetailScreen(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
    }

    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/toolbar_title")
    private MobileElement toolbarTitle_txt;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/product_detail_name")
    private MobileElement productName_txt;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/product_detail_type")
    private MobileElement productType_txt;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/color_dimension_cabinet_text")
    private MobileElement productVariant_txt;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/product_detail_price_value")
    private MobileElement productPrice_txt;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/price_inclusive_or_exclusive_of_tax")
    private MobileElement productTax_txt;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/price_reflects_selected_option")
    private MobileElement productPriceSelection_txt;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/product_qty")
    private MobileElement productQuantity_btn;
    @AndroidFindBy(id = "android:id/numberpicker_input")
    private MobileElement numberPicker;
    @AndroidFindBy(xpath = "(//android.widget.Button)[1]")
    private MobileElement selectNextQuantity;
    @AndroidFindBy(id = "android:id/button2")
    private MobileElement cancel_btn;
    @AndroidFindBy(id = "android:id/button1")
    private MobileElement ok_btn;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/alertTitle")
    private MobileElement alertTitle_txt;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/btn_add_to_cart")
    private MobileElement addToCart_btn;

    public void verifyToolbarTitle(String productName) {
        Assert.assertEquals(toolbarTitle_txt.getText().trim(), productName, "Product Title is not matched");
    }

    public void verifyProductDetail(HashMap<String, String> productDetail) {
        Assert.assertEquals(productName_txt.getText().trim(), productDetail.get("productName"), "Product name is not matched");
        Assert.assertEquals(productType_txt.getText().trim(), productDetail.get("productDesc").split(",")[0], "Product type is not matched");
        Assert.assertEquals(productPrice_txt.getText().trim(), productDetail.get("productPrice"), "Product Price is not matched");
        Assert.assertEquals(productTax_txt.getText().trim(), "Price incl. VAT", "Tax statement is not matched");
        Assert.assertEquals(productPriceSelection_txt.getText().trim(), "The price reflects selected options.", "Product price statement is not matched");
        Assert.assertEquals(productQuantity_btn.getText().trim(), "1", "Product quantity is not matched");

    }

    public void verifySelectQuantity() {
        Assert.assertEquals(alertTitle_txt.getText().trim(), "Select quantity", "Alert title text is not matched");
        Assert.assertEquals(ok_btn.getText().trim(), "OK", "Ok button is not matched");
        Assert.assertEquals(cancel_btn.getText().trim(), "CANCEL", "Cancel button is not matched");
    }

    public void clickAndSelectProductQuantity(String quantity) {
        productQuantity_btn.click();
        verifySelectQuantity();
        setProductQuantity(quantity);
        ok_btn.click();
    }

    public void setProductQuantity(String qty) {
        try {
            driver.findElement(By.xpath("//android.widget.Button[@text='" + qty + "']")).isDisplayed();
            MobileElement mobileElement = (MobileElement) this.driver.findElement(By.xpath("//android.widget.Button[@text='" + qty + "']"));
            mobileElement.click();
        } catch (NoSuchElementException exception) {
            MobileElement el = (MobileElement) this.driver.findElement(MobileBy.AndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\"android.widget.Button\").scrollable(true).instance(0)).scrollForward().scrollIntoView(new UiSelector().text(\"" + qty + "\").instance(0))"));
            el.click();
        }
    }

    public String getSelectedQuantity() {
        System.out.println("Quantity: " + productQuantity_btn.getText().trim());
        return productQuantity_btn.getText().trim();
    }

    public void verifyAndSelectAddToCartBtn() {
        new TouchAction(driver)
                .press(PointOption.point(540, 1824))
                .waitAction(waitOptions(ofMillis(1000)))
                .moveTo(PointOption.point(540, 672))
                .release()
                .perform();

        Assert.assertEquals(addToCart_btn.getText().trim(), "ADD TO SHOPPING BAG", "Add To Shopping Bag button is not matched");
        addToCart_btn.click();
    }

    public void navigateToProductListScreen() {
        this.driver.navigate().back();
    }

}

