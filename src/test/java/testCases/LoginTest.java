package testCases;

import base.Base;
import com.selcuk.pages.HomePage;
import com.selcuk.pages.LoginPage;
import com.selcuk.utils.Utilities;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends Base {
    LoginPage loginPage;
    public LoginTest(){
        super();
    }
    public WebDriver driver;
    @BeforeMethod
    public void setup(){
        driver = initializeBrowserAndOpenApplicationURL(prop.getProperty("browserName"));
        HomePage homePage = new HomePage(driver);
        loginPage = homePage.naviageToLoginPage();
    }
    @AfterMethod
    public void tearDown(){
        driver.quit();
    }
    @Test(testName = "validCredentialsSupplier")
    public Object[][] supplyTestData(){
        Object[][] data = Utilities.getTestDataFromExcel("Login");
        return data;
    }
    @Test(priority = 2)
    public void verifyLoginWithInvalidCredentials(){
        loginPage.login(Utilities.generateEmailWithTimeStamp(),dataProp.getProperty("invalidPassword"));
        Assert.assertTrue(loginPage.retrieveEmailPasswordNotMatchingWarningMessageText()
                .contains(dataProp.getProperty("emailPasswordNoMatchWarning"))
                ,"Expected Warning message is not displayed");

    }
    @Test(priority=3)
    public void verifyLoginWithInvalidEmailAndValidPassword() {

        loginPage.login(Utilities.generateEmailWithTimeStamp(),prop.getProperty("validPassword"));
        Assert.assertTrue(loginPage.retrieveEmailPasswordNotMatchingWarningMessageText()
                .contains(dataProp.getProperty("emailPasswordNoMatchWarning")),
                "Expected Warning message is not displayed");

    }

    @Test(priority=4)
    public void verifyLoginWithValidEmailAndInvalidPassword() {

        loginPage.login(prop.getProperty("validEmail"),dataProp.getProperty("invalidPassword"));
        Assert.assertTrue(loginPage.retrieveEmailPasswordNotMatchingWarningMessageText()
                .contains(dataProp.getProperty("emailPasswordNoMatchWarning")),
                "Expected Warning message is not displayed");

    }

    @Test(priority=5)
    public void verifyLoginWithoutProvidingCredentials() {

        loginPage.clickOnLoginButton();
        Assert.assertTrue(loginPage.retrieveEmailPasswordNotMatchingWarningMessageText()
                .contains(dataProp.getProperty("emailPasswordNoMatchWarning")),
                "Expected Warning message is not displayed");

    }
    }

