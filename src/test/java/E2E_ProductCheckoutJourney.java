import common.BaseUtil;
import common.TestBaseSetup;
import elementRepositories.*;
import org.testng.annotations.Test;
import utils.ExcelFileReader;

import java.util.HashMap;

public class E2E_ProductCheckoutJourney extends TestBaseSetup {

    @Test( dataProvider = "getExcelData", dataProviderClass = ExcelFileReader.class)
    public void verifyProductCheckoutJourney(HashMap<String,String> map){

        HomeScreen homeScreen = new HomeScreen(BaseUtil.mobileDriver);
        ProductListScreen productListScreen = new ProductListScreen(BaseUtil.mobileDriver);
        ProductDetailScreen productDetailScreen = new ProductDetailScreen(BaseUtil.mobileDriver);
        ProductCartScreen productCartScreen = new ProductCartScreen(BaseUtil.mobileDriver);

        String productType = map.get("ProductType");
        String quantity = map.get("ProductQuantity");
        HashMap<String,String> productDetail = new HashMap<String ,String>();

        logScenario("Scenario: Product Order checkout journey");

        logStepAction("Step 1: Verify Home screen");
        homeScreen.verifyShopIkeaIsEnabled();

        logStepAction("Step 2: Verify Header banner icons in home screen");
        homeScreen.verifyHeaderBanner();

        logStepAction("Step 3: Enter Product Type in search field");
        homeScreen.enterProductTextInSearch(productType);

        logStepAction("Step 4: Verify product catalog details");
        productListScreen.verifyProductCatalog(productType);

        logStepAction("Step 5: Verify Product Variant Info ");
        productListScreen.verifyProductInfo(productType);

        logStepAction("Step 6: Return the Product details from Product List screen");
        productDetail = productListScreen.getProductDetail();

        logStepAction("Step 7: Select the first Product from list");
        productListScreen.selectFirstProduct();

        logStepAction("Step 8: Verify toolbar title in Product Detail screen");
        productDetailScreen.verifyToolbarTitle(productDetail.get("productName"));

        logStepAction("Step 9: Verify the Product details in Product Detail screen");
        productDetailScreen.verifyProductDetail(productDetail);

        logStepAction("Step 10: Select product quantity");
        productDetailScreen.clickAndSelectProductQuantity(quantity);

        logStepAction("Step 11: Return the selected Product Quantity");
        String quantityFromProductDetail = productDetailScreen.getSelectedQuantity();
        productDetail.put("productQuantity",quantityFromProductDetail);

        logStepAction("Step 12: Click Add To Shopping Bag button");
        productDetailScreen.verifyAndSelectAddToCartBtn();

        logStepAction("Step 13: Navigate back to Home screen");
        productDetailScreen.navigateToProductListScreen();
        productListScreen.navigateToPreviousScreen();
        homeScreen.navigateToHomeScreen();

        logStepAction("Step 14: Verify the cart item badge count");
        homeScreen.verifyAndSelectCartIcon();

        logStepAction("Step 15: Verify Shopping cart title");
        productCartScreen.verifyShoppingCartTitle();

        logStepAction("Step 16: Verify Product details in Shopping cart screen");
        productCartScreen.verifyProductDetails(productDetail);

        logStepAction("Step 17: Verify and Select Remove button");
        productCartScreen.clickRemoveButton();

        logStepAction("Step 18: Verify Remove alert popup");
        productCartScreen.verifyRemoveCartPopup();

        logStepAction("Step 19: Select Yes button to remove all products from cart");
        productCartScreen.clickYesButton();

        logStepAction("Step 20: Verify Empty cart message");
        productCartScreen.verifyEmptyCart();

    }
}
