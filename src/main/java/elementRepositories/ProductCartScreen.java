package elementRepositories;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.HashMap;

public class ProductCartScreen {

    AppiumDriver driver;

    public ProductCartScreen(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/shopping_cart_heading")
    private MobileElement shoppingCartTitle_txt;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/tv_product_name")
    private MobileElement productName;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/tv_product_type")
    private MobileElement productType;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/tv_price")
    private MobileElement productPrice;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/product_qty")
    private MobileElement productQuantity;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/tv_sub_total")
    private MobileElement subTotalPrice_txt;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/total_cart_item")
    private MobileElement cartItem_txt;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/total_cart_price")
    private MobileElement totalPrice_txt;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/btn_checkout")
    private MobileElement checkout_btn;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/iv_remove")
    private MobileElement remove_btn;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/popup_title")
    private MobileElement removePopUp_title;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/popup_btn")
    private MobileElement yes_btn;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/popup_other_btn")
    private MobileElement no_btn;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/empty_text")
    private MobileElement emptyCartMsg_txt;


    public void verifyShoppingCartTitle() {
        Assert.assertEquals(shoppingCartTitle_txt.getText().trim(), "SHOPPING BAG", "Shopping bag title is not matched");
    }

    public void verifyProductDetails(HashMap<String, String> productDetail) {

        WebDriverWait wait = new WebDriverWait(driver, 65);
        wait.until(ExpectedConditions.visibilityOf(productName));

        Assert.assertEquals(productName.getText().trim(), productDetail.get("productName"), "Product Name is not matched");
        Assert.assertEquals(productType.getText().trim(), productDetail.get("productDesc").replaceAll("^[0-9]", "").split(",")[0].trim(), "Product type is not matched");
        Assert.assertEquals(productPrice.getText().trim(), productDetail.get("productPrice"), "Product Price is not matched");
        Assert.assertEquals(productQuantity.getText().trim(), productDetail.get("productQuantity"), "Product quantity is not matched");
        String totalPrice = calculateTotalPrice(productDetail.get("productPrice"), productDetail.get("productQuantity"));
        Assert.assertEquals(subTotalPrice_txt.getText().replaceAll("[^\\d.]", "").trim(), totalPrice, "Sub total is not matched");
        Assert.assertEquals(cartItem_txt.getText().trim(), "1 Item", "Cart Item count is not matched");
        Assert.assertEquals(totalPrice_txt.getText().replaceAll("[^\\d.]", "").trim(), totalPrice, "Total is not matched");
        Assert.assertEquals(checkout_btn.getText().trim(), "PROCEED TO CHECKOUT", "Checkout button text is not matched");

    }

    public String calculateTotalPrice(String productPrice, String quantity) {
        Double price = Double.parseDouble(productPrice.replaceAll("[^0-9]", "").trim());
        int qty = Integer.parseInt(quantity);
        Double totalPrice = price * qty;
        return String.valueOf(Math.round(totalPrice));
    }

    public void clickRemoveButton() {
        Assert.assertTrue(remove_btn.isDisplayed(), "Remove button is not displayed");
        remove_btn.click();
    }

    public void verifyRemoveCartPopup() {
        Assert.assertEquals(removePopUp_title.getText().trim(), "Do you want to remove this product from shopping bag?", "Title is not matched");
        Assert.assertEquals(yes_btn.getText().trim(), "YES", "Yes button is not displayed");
        Assert.assertEquals(no_btn.getText().trim(), "NO", "No button is not displayed");
    }

    public void clickYesButton() {
        yes_btn.click();
    }

    public void verifyEmptyCart() {
        Assert.assertEquals(emptyCartMsg_txt.getText().trim(), "Your shopping bag is waiting for it’s first product", "Empty cart message is not matched");
    }
}
