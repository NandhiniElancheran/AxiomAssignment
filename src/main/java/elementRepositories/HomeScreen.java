package elementRepositories;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class HomeScreen {
    AppiumDriver driver;

    public HomeScreen(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @AndroidFindBy(id = "layout_shop_ikea")
    private MobileElement shopIkea_btn;
    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Shop IKEA')]")
    private MobileElement shopIkea_txt;
    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Furniture')]")
    private MobileElement headerSubMenu_txt;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/toolbar")
    private MobileElement searchBar;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/textView7")
    private MobileElement searchField;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/search_products_text_view")
    private MobileElement searchProducts;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/tv_text")
    private MobileElement searchResultValue_txt;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/nav_icon")
    private MobileElement cartIcon;
    @AndroidFindBy(id = "com.ikea.alfuttaim.store:id/tab_badge")
    private MobileElement cartBadge_txt;

    public void verifyShopIkeaIsEnabled() {
        Assert.assertEquals(shopIkea_btn.getAttribute("enabled"), "true", "Shop IKEA button is not enabled");
    }

    public void verifyHeaderBanner() {
        Assert.assertEquals(shopIkea_txt.getText().trim(), "Shop IKEA", "Header button text is not matched");
        Assert.assertEquals(headerSubMenu_txt.getText().trim(), "Furniture, home accessories", "Sub header text is not matched");
        Assert.assertTrue(searchBar.isDisplayed(), "Search bar is not displayed");
    }

    public void enterProductTextInSearch(String searchText) {
        searchField.click();
        searchProducts.sendKeys(searchText);
        Assert.assertEquals(searchResultValue_txt.getText().trim(), searchText, "Entered text is not matched with result list");
        searchResultValue_txt.click();
    }

    public void verifyAndSelectCartIcon() {
        Assert.assertTrue(cartIcon.isDisplayed(), "Cart Icon is not visible");
        Assert.assertEquals(cartBadge_txt.getText().trim(), "1", "Added badge count is not matched");
        cartIcon.click();
    }

    public void navigateToHomeScreen() {
        this.driver.navigate().back();
    }

}
