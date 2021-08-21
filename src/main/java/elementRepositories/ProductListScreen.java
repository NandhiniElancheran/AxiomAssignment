package elementRepositories;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.HashMap;

public class ProductListScreen {
    AppiumDriver driver;

    public ProductListScreen(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/tv_catalog")
    private MobileElement productCatalog_txt;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/tv_product_name")
    private MobileElement productName_txt;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/tv_product_description")
    private MobileElement productDesc_txt;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/tv_regular_price")
    private MobileElement productPrice_txt;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/iv_add_to_cart")
    private MobileElement addToCart_btn;
    @AndroidFindBy(xpath = "//android.widget.FrameLayout[@content-desc=\"IKEA UAE\"]/android.view.ViewGroup[1]")
    private MobileElement backArrow_btn;

    public void verifyProductCatalog(String catalogText) {
        Assert.assertEquals(productCatalog_txt.getText().trim(), "\"" + catalogText.toLowerCase() + "\"", "Catalog Tilte text is not matched");
    }

    public HashMap<String, String> getProductDetail() {
        HashMap<String, String> productDetail = new HashMap<String, String>();
        productDetail.put("productName", productName_txt.getText().trim());
        productDetail.put("productDesc", productDesc_txt.getText().trim());
        productDetail.put("productPrice", productPrice_txt.getText().trim());
        return productDetail;
    }

    public void verifyProductInfo(String catalogText) {
        String productDesc = productDesc_txt.getText().replaceAll("[0-9]", "").split(",")[0].trim();
        Assert.assertEquals(productDesc, catalogText, "Product info is not matching with catalog text");
    }

    public void selectFirstProduct() {
        productName_txt.click();
    }

    public void navigateToPreviousScreen() {
        Assert.assertTrue(backArrow_btn.isDisplayed(), "Back Arrow is not displayed");
        backArrow_btn.click();
        this.driver.navigate().back();
    }
}
