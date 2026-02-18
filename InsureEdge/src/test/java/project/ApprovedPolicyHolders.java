package project;

import java.time.Duration;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class ApprovedPolicyHolders extends BaseTest {
    private JavascriptExecutor js;
    @BeforeMethod
    public void navigateToAppliedPolicyHolders() {
        js = (JavascriptExecutor) driver;
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#sidebar-nav > li:nth-child(5) > a")
        ));
        button.click();
        WebElement viewApplied = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#policyHolder-nav > li:nth-child(2) > a")
        ));
        viewApplied.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.pagetitle h1")));
    }
    @Test(priority = 0)
    public void validatePageHeaderUi() {
        String expectedTitle = "Approved Policy Holders";
        String actualTitle = driver.findElement(By.cssSelector("div.pagetitle h1")).getText().trim();
        Assert.assertEquals(actualTitle, expectedTitle, "Page title mismatch");
    }
    @Test(priority = 1)
    public void validateBreadcrumbUI() {
        String expectedRoot = "Dashboard";              // or "Policy Holders"
        String expectedLeaf = "Approved Policy Holders"; // or "View"

        String b1 = driver.findElement(By.cssSelector("ol.breadcrumb li:nth-child(1) a")).getText().trim();
        String b2 = driver.findElement(By.cssSelector("ol.breadcrumb li:nth-child(2)")).getText().trim();

        SoftAssert softly = new SoftAssert();
        softly.assertEquals(b1, expectedRoot, "Breadcrumb root mismatch");
        softly.assertEquals(b2, expectedLeaf, "Breadcrumb leaf mismatch");

        WebElement crumb = driver.findElement(By.cssSelector("ol.breadcrumb li:nth-child(1) a"));
        softly.assertTrue(crumb.isDisplayed(), "Breadcrumb root should be displayed");
        softly.assertTrue(crumb.isEnabled(), "Breadcrumb root should be clickable");
        softly.assertAll();
    }
    @Test(priority = 2)
    public void validateCustomerNameDropdownUI() {
        WebElement label = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[normalize-space(text())='Customer Name']")));
        WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.name("ctl00$ContentPlaceHolder_Admin$ddlCustomerName")));
        SoftAssert softly = new SoftAssert();
        softly.assertTrue(dropdown.isDisplayed(), "Customer Name dropdown should be displayed");
        String actualColor = label.getCssValue("color");
        Assert.assertNotNull(actualColor, "Label color should be retrievable");
        Select select = new Select(dropdown);
        String defaultValue = select.getFirstSelectedOption().getText().trim();
        Assert.assertEquals(defaultValue, "-- All --", "Default dropdown value should be --All--");
        boolean clickable;
        try {
            wait.until(ExpectedConditions.elementToBeClickable(dropdown));
            clickable = dropdown.isEnabled();
        } catch (Exception e) {
            clickable = false;
        }
        if (!clickable || !dropdown.isDisplayed()) {
            try {
                WebElement select2Container = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("span.select2, .select2-container")));
                wait.until(ExpectedConditions.elementToBeClickable(select2Container));
                clickable = select2Container.isDisplayed() && select2Container.isEnabled();
            } catch (Exception ignored) { }
        }
        softly.assertTrue(clickable, "Customer Name dropdown should be clickable");
        List<WebElement> options = select.getOptions();
        softly.assertTrue(options.size() > 0, "Dropdown should contain at least one option");
        // If the first is a placeholder like '--All--', ensure there is at least one real option
        if (!options.isEmpty() && options.get(0).getText().trim().equalsIgnoreCase("--All--")) {
            softly.assertTrue(options.size() > 1, "Dropdown should contain customer names beyond the placeholder");
        }
        softly.assertAll();
    }
    @Test(priority = 3)
    public void Policy_Status_Filter_UI_Validation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#sidebar-nav > li:nth-child(5) > a")));
        button.click();
        WebElement button1 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#policyHolder-nav > li:nth-child(1) > a > span")));
        button1.click();
        WebElement policyStatusBotton = driver.findElement(By.id("select2-ContentPlaceHolder_Admin_ddlPolicyStatus-container"));
        boolean displayed=policyStatusBotton.isDisplayed();
        boolean enabled=policyStatusBotton.isEnabled();
        String text = policyStatusBotton.getText();
        String expected = "-- All --";
        Assert.assertEquals(displayed,true);
        Assert.assertEquals(enabled,true);
        Assert.assertEquals(text,expected);
        System.out.println("=================================================");
        System.out.println("Policy Status dropdown is enabled and displayed ");
        System.out.println("\"Policy Status\" dropdown should be clickable.");
        System.out.println("The text in the \"Policy Status \" dropdown should be displayed as(--All--)by default");
        System.out.println("=================================================");
    }
    @Test(priority = 2)
    public void validateSubCategoryDropdownUI() {
        WebElement label = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[normalize-space()='Sub Category']")));
        WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.name("ctl00$ContentPlaceHolder_Admin$ddlSubCategory")));
        SoftAssert softly = new SoftAssert();
        softly.assertTrue(dropdown.isDisplayed(), "Customer Name dropdown should be displayed");
        String actualColor = label.getCssValue("color");
        Assert.assertNotNull(actualColor, "Label color should be retrievable");
        Select select = new Select(dropdown);
        String defaultValue = select.getFirstSelectedOption().getText().trim();
        Assert.assertEquals(defaultValue, "-- All --", "Default dropdown value should be --All--");
        boolean clickable;
        try {
            wait.until(ExpectedConditions.elementToBeClickable(dropdown));
            clickable = dropdown.isEnabled();
        } catch (Exception e) {
            clickable = false;
        }
        if (!clickable || !dropdown.isDisplayed()) {
            try {
                WebElement select2Container = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("span.select2, .select2-container")));
                wait.until(ExpectedConditions.elementToBeClickable(select2Container));
                clickable = select2Container.isDisplayed() && select2Container.isEnabled();
            } catch (Exception ignored) { }
        }
        softly.assertTrue(clickable, "Customer Name dropdown should be clickable");
        List<WebElement> options = select.getOptions();
        softly.assertTrue(options.size() > 0, "Dropdown should contain at least one option");
        // If the first is a placeholder like '--All--', ensure there is at least one real option
        if (!options.isEmpty() && options.get(0).getText().trim().equalsIgnoreCase("--All--")) {
            softly.assertTrue(options.size() > 1, "Dropdown should contain customer names beyond the placeholder");
        }
        softly.assertAll();
    }
    @Test(priority = 4)
    public void searchButtonUi() {
        SoftAssert softAssert = new SoftAssert();

        WebElement searchButton = driver.findElement(By.id("ContentPlaceHolder_Admin_btnSearch"));
        String searchButtonTxt = searchButton.getAttribute("value");
        softAssert.assertEquals(searchButtonTxt, "Search","Search button text is not correct");

        softAssert.assertTrue(searchButton.isDisplayed(),"Search button is not enabled");
        softAssert.assertTrue(searchButton.isEnabled(),"Search button is not clickable");
    }
    @Test(priority = 5)
    public void resetButtonUi() {
        SoftAssert softAssert = new SoftAssert();

        WebElement resetButton = driver.findElement(By.id("ContentPlaceHolder_Admin_btnReset"));
        String resetButtonTxt = resetButton.getAttribute("value");
        softAssert.assertEquals(resetButtonTxt, "Reset","Reset button text is not correct");

        softAssert.assertTrue(resetButton.isDisplayed(),"Reset button is not enabled");
        softAssert.assertTrue(resetButton.isEnabled(),"Reset button is not clickable");
    }
    @Test(priority = 6)
    public void validateTableHeadersUI() {
        String[] expected = {
                "Customer Name",
                "Mobile Number",
                "Email",
                "Policy Name",
                "Main Category",
                "Sub Category",
                "Sum Assured",
                "Premium",
                "Tenure",
                "Applied On"
        };

        WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("ContentPlaceHolder_Admin_gvApprovedHolders")
        ));

        List<WebElement> headerCells = table.findElements(By.xpath(".//thead//tr[1]//th"));
        if (headerCells.isEmpty()) {
            headerCells = table.findElements(By.xpath(".//tr[1]//th"));
            if (headerCells.isEmpty()) {
                headerCells = table.findElements(By.xpath(".//tr[1]//td"));
            }
        }
        List<String> actual = new ArrayList<>();
        for (WebElement cell : headerCells) {
            String txt = cell.getText();
            if (txt == null) {
                txt = "";
            }
            txt = txt.trim();   // only trim now
            actual.add(txt);}
        Assert.assertEquals(actual, Arrays.asList(expected), "Header row mismatch");
    }
    @Test(priority = 9)
    public void paginationNumberValidationUI() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String expTxt = "5";

        By paginationLinkLoc = By.xpath("//table[@id='ContentPlaceHolder_Admin_gvApprovedHolders']//a[text()='" + expTxt + "']");
        WebElement paginationLink = wait.until(ExpectedConditions.presenceOfElementLocated(paginationLinkLoc));

        js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", paginationLink);

        wait.until(ExpectedConditions.elementToBeClickable(paginationLink));
        js.executeScript("arguments[0].click();", paginationLink);

        WebElement activePageSpan = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//table[@id='ContentPlaceHolder_Admin_gvApprovedHolders']//tr[12]//span")));

        String actTxt = activePageSpan.getText();

        Assert.assertEquals(actTxt, expTxt,"Pagination failed! The active page number is incorrect.");
    }

    @Test(priority = 10)
    public void Previous_Button_UI_Validation() {
        By prevBtn = By.xpath("//a[normalize-space()='Previous']");
        List<WebElement> list = driver.findElements(prevBtn);
        Assert.assertTrue(list.size() > 0,
                "Previous button is NOT present on the page.");
        WebElement previous = list.get(0);
        Assert.assertTrue(previous.isDisplayed(),
                "Previous button is present but NOT visible.");
        try {
            previous.click();
            System.out.println("Previous button is present and clickable.");
        } catch (Exception e) {
            Assert.fail("Previous button is present but NOT clickable: " + e.getMessage());
        }
    }
    @Test(priority = 11)
    public void Next_Button_UI_Validation(){
        By prevBtn = By.xpath("//a[normalize-space()='Next']");
        List<WebElement> list = driver.findElements(prevBtn);
        Assert.assertTrue(list.size() > 0,
                "Next button is NOT present on the page.");
        WebElement previous = list.get(0);
        Assert.assertTrue(previous.isDisplayed(),
                "Next button is present but NOT visible.");
        try {
            previous.click();
            System.out.println("Next button is present and clickable.");
        } catch (Exception e) {
            Assert.fail("Next button is present but NOT clickable: " + e.getMessage());
        }
    }
    @Test(priority = 12)
    public void scrollUpButtonUI() {
        SoftAssert softAssert= new SoftAssert();

        js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement scrollBtn = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("back-to-top")));

        softAssert.assertTrue(scrollBtn.isDisplayed(), "Scroll up button is not visible!");
    }

    @Test(priority = 13)
    public void validateHeaderandBreadcrumbFunctionality() {
        String expectedRoot = "Dashboard";
        String b1 = driver.findElement(By.cssSelector("ol.breadcrumb li:nth-child(1) a")).getText().trim();
        SoftAssert softly = new SoftAssert();
        softly.assertEquals(b1, expectedRoot, "Breadcrumb root mismatch");
        WebElement crumb = driver.findElement(By.cssSelector("ol.breadcrumb li:nth-child(1) a"));
        softly.assertTrue(crumb.isDisplayed(), "Breadcrumb root should be displayed");
        softly.assertTrue(crumb.isEnabled(), "Breadcrumb root should be clickable");
        wait.until(ExpectedConditions.elementToBeClickable(crumb));
        crumb.click();
        String ExpectedTitle="Dashboard";
        String b2 = driver.findElement(By.cssSelector("div[class='pagetitle'] h1")).getText().trim();
        softly.assertEquals(b1, ExpectedTitle, "Dashboard page is not loaded succesfully");
        softly.assertAll();

    }
    @Test(priority =14)
    public void validateCustomerNameDropdownFunctionality() {
        WebElement customerDropdown = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("ContentPlaceHolder_Admin_ddlCustomerName"))
        );

        Select select = new Select(customerDropdown);
        List<WebElement> options = select.getOptions();
        Assert.assertTrue(options.size() > 1, "Customer dropdown should contain at least 1 customer option beyond the placeholder. Actual size: " + options.size());

        String expectedName = options.get(1).getText().trim();
        select.selectByIndex(1);

        String selectedName = select.getFirstSelectedOption().getText().trim();
        Assert.assertEquals(selectedName, expectedName, "Selected customer name mismatch after selection.");

        String valueAttr = customerDropdown.getAttribute("value");
        Assert.assertNotNull(valueAttr, "Dropdown 'value' attribute should not be null after selecting a customer.");
        System.out.println("Customer dropdown validated. Selected: " + selectedName + " (value=" + valueAttr + ")");
    }
    @Test(priority = 15)
    public void validatePolicyNameDropdownFunctionality() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        By select2Container = By.id("select2-ContentPlaceHolder_Admin_ddlPolicyName-container");
        WebElement container = wait.until(ExpectedConditions.elementToBeClickable(select2Container));
        container.click();
        By optionsLocator = By.cssSelector("ul.select2-results__options li.select2-results__option:not([aria-disabled='true'])");
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(optionsLocator));
        Assert.assertTrue(
                options.size() > 1,
                "Policy Name dropdown should contain at least 1 option beyond the placeholder. Actual size: " + options.size()
        );
        String expectedName = options.get(1).getText().trim();
        options.get(1).click();
        String selectedName = wait.until(ExpectedConditions.visibilityOfElementLocated(select2Container)).getText().trim();
        Assert.assertEquals(
                selectedName,
                expectedName,
                "Policy Name mismatch after selection."
        );
        WebElement hiddenNativeSelect = null;
        try {
            hiddenNativeSelect = driver.findElement(By.id("ContentPlaceHolder_Admin_ddlPolicyName"));
        } catch (NoSuchElementException ignored) {}
        String valueAttr = null;
        if (hiddenNativeSelect != null) {
            valueAttr = hiddenNativeSelect.getAttribute("value");
        }
        Assert.assertNotNull(
                valueAttr,
                "Dropdown 'value' attribute should not be null after selecting a policy."
        );
        System.out.println("Policy Name dropdown validated. Selected: " + selectedName + " (value=" + valueAttr + ")");
    }
    @Test(priority = 15)
    public void vaidateSubCategoryDropdownFunctionality() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        By select2Container = By.id("select2-ContentPlaceHolder_Admin_ddlSubCategory-container");
        WebElement container = wait.until(ExpectedConditions.elementToBeClickable(select2Container));
        container.click();
        By optionsLocator = By.cssSelector("ul.select2-results__options li.select2-results__option:not([aria-disabled='true'])");
        List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(optionsLocator));
        Assert.assertTrue(
                options.size() > 1,
                "Sub Category dropdown should contain at least 1 option beyond the placeholder. Actual size: " + options.size()
        );
        String expectedName = options.get(1).getText().trim();
        options.get(1).click();
        String selectedName = wait.until(ExpectedConditions.visibilityOfElementLocated(select2Container)).getText().trim();
        Assert.assertEquals(
                selectedName,
                expectedName,
                "Sub Category mismatch after selection."
        );
        WebElement hiddenNativeSelect = null;
        try {
            hiddenNativeSelect = driver.findElement(By.id("ContentPlaceHolder_Admin_ddlSubCategory"));
        } catch (NoSuchElementException ignored) {}
        String valueAttr = null;
        if (hiddenNativeSelect != null) {
            valueAttr = hiddenNativeSelect.getAttribute("value");
        }
        Assert.assertNotNull(
                valueAttr,
                "Dropdown 'value' attribute should not be null after selecting a policy."
        );
        System.out.println("Sub Category dropdown validated. Selected: " + selectedName + " (value=" + valueAttr + ")");
    }
    @Test()
    public void Search_Button_Functionality_Validation(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement customer_name1=driver.findElement(By.xpath("//div[@class='col-md-3']/descendant::span"));
        customer_name1.click();
        WebElement customer_name=driver.findElement(By.xpath("//ul[@class='select2-results__options']//li[10]"));
        customer_name.click();

        String actual_text=customer_name1.getText();

        driver.findElement(By.id("ContentPlaceHolder_Admin_btnSearch")).click();
        WebElement realcustomer_name=driver.findElement(By.xpath("//table[@class='table table-bordered']/descendant::td[1]"));
        String expected =realcustomer_name.getText();
        SoftAssert soft=new SoftAssert();
        soft.assertEquals(actual_text,expected);
        System.out.println("search button functionality is working perfectly");

    }

    @Test(priority = 17)
    public void ResetButtonFunctionalityValidation() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        final String DEFAULT_FILTER = "-- All --";
        final By resetButton = By.xpath(
                "//*[self::button or self::input][normalize-space()='Reset' or @value='Reset' or contains(@class,'reset')]"
        );
        final By tableRows = By.xpath("//table//tbody/tr[td]");
        final By select2Options = By.xpath("//li[contains(@class,'select2-results__option') and normalize-space(.)!='']");
        Function<String, String> normalize = s -> {
            if (s == null) return "";
            return s.replace('\u00A0', ' ').replaceAll("\\s+", " ").trim();
        };
        Function<String, String> getFilterValue = (label) -> {
            WebElement rendered = driver.findElement(By.xpath(
                    "//label[normalize-space()='" + label + "']" +
                            "/ancestor::*[self::div or self::td][1]" +
                            "//span[contains(@class,'select2-selection__rendered')]"
            ));
            String raw = rendered.getAttribute("title");
            if (raw == null) raw = rendered.getText();
            return normalize.apply(raw);
        };
        BiFunction<Integer, By, List<String>> captureTableSnapshot = (limit, by) -> {
            List<WebElement> rows = wait.until(
                    org.openqa.selenium.support.ui.ExpectedConditions.presenceOfAllElementsLocatedBy(by)
            );
            java.util.List<String> snapshot = new java.util.ArrayList<>();
            int n = Math.min(rows.size(), limit);
            for (int i = 0; i < n; i++) {
                snapshot.add(normalize.apply(rows.get(i).getText()));
            }
            return snapshot;
        };

        BiFunction<String, String, String> selectAnyNonDefaultOption = (label, defaultFilterValue) -> {
            WebElement dropdownBox = driver.findElement(By.xpath(
                    "//label[normalize-space()='" + label + "']" +
                            "/ancestor::*[self::div or self::td][1]" +
                            "//span[contains(@class,'select2-selection')]"
            ));
            try {
                dropdownBox.click();
            } catch (ElementClickInterceptedException ex) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", dropdownBox);
            }

            List<WebElement> options = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(select2Options)
            );

            WebElement pick = null;
            for (WebElement opt : options) {
                String text = normalize.apply(opt.getText());
                if (text.isEmpty()) continue;
                if (text.equalsIgnoreCase(defaultFilterValue)) continue;
                if (text.equalsIgnoreCase("No results found")) continue;
                if (text.equals("...")) continue;
                pick = opt;
                break;
            }
            Assert.assertNotNull(pick, "No selectable non-default option found for: " + label);

            String chosen = normalize.apply(pick.getText());
            try {
                pick.click();
            } catch (ElementClickInterceptedException ex) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", pick);
            }

            wait.until(d -> chosen.equals(getFilterValue.apply(label)));
            return chosen;
        };
        List<String> initialSnapshot = captureTableSnapshot.apply(25, tableRows);
        org.testng.Assert.assertTrue(initialSnapshot.size() > 0, "Initial table snapshot should not be empty.");
        String chosenCustomer = selectAnyNonDefaultOption.apply("Customer Name", DEFAULT_FILTER);
        String chosenStatus   = selectAnyNonDefaultOption.apply("Policy Status", DEFAULT_FILTER);

        org.testng.Assert.assertNotEquals(getFilterValue.apply("Customer Name"), DEFAULT_FILTER,
                "Customer Name did not change from default before Reset.");
        org.testng.Assert.assertNotEquals(getFilterValue.apply("Policy Status"), DEFAULT_FILTER,
                "Policy Status did not change from default before Reset.");
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(tableRows));
        WebElement beforeFirstRow = null;
        java.util.List<WebElement> beforeRows = driver.findElements(tableRows);
        if (!beforeRows.isEmpty()) beforeFirstRow = beforeRows.get(0);

        WebElement reset = wait.until(ExpectedConditions.elementToBeClickable(resetButton));
        try {
            reset.click();
        } catch (ElementClickInterceptedException ex) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", reset);
        }

        wait.until(d -> DEFAULT_FILTER.equals(getFilterValue.apply("Customer Name")));
        wait.until(d -> DEFAULT_FILTER.equals(getFilterValue.apply("Policy Status")));

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(tableRows));
        if (beforeFirstRow != null) {
            try {
                new WebDriverWait(driver, Duration.ofSeconds(5))
                        .until(ExpectedConditions.stalenessOf(beforeFirstRow));
            } catch (TimeoutException ignored) {}
        }
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(tableRows));

        Assert.assertEquals(getFilterValue.apply("Customer Name"), DEFAULT_FILTER,
                "Customer Name did not reset to default '-- All --'.");
        Assert.assertEquals(getFilterValue.apply("Policy Status"), DEFAULT_FILTER,
                "Policy Status did not reset to default '-- All --'.");

        List<String> afterResetSnapshot = captureTableSnapshot.apply(25, tableRows);
        org.testng.Assert.assertEquals(afterResetSnapshot, initialSnapshot,
                "After Reset, table did NOT reload to its initial state.");

        System.out.println("Passed: Reset restored both filters to '-- All --' and reloaded the initial table snapshot.");
    }

    @Test(priority = 21)
    public void paginationNumberValidationFunctionality() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String expTxt = "3";
        By paginationLinkLoc = By.xpath("//table[@id='ContentPlaceHolder_Admin_gvApprovedHolders']//a[text()='" + expTxt + "']");
        WebElement paginationLink = wait.until(ExpectedConditions.presenceOfElementLocated(paginationLinkLoc));
        js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", paginationLink);
        wait.until(ExpectedConditions.elementToBeClickable(paginationLink));
        js.executeScript("arguments[0].click();", paginationLink);
        WebElement activePageSpan = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//table[@id='ContentPlaceHolder_Admin_gvApprovedHolders']//tr[12]//span")));
        String actTxt = activePageSpan.getText();
        Assert.assertEquals(actTxt, expTxt,"Pagination failed! The active page number is incorrect.");
    }
    @Test(priority = 22)
    public void Previous_Button_Functionality_Validation() {
        By prevBtn = By.xpath("//a[normalize-space()='Previous']");
        List<WebElement> list = driver.findElements(prevBtn);
        Assert.assertTrue(list.size() > 0,
                "Previous button is NOT present on the page.");
        WebElement previous = list.get(0);
        Assert.assertTrue(previous.isDisplayed(),
                "Previous button is present but NOT visible.");
        try {
            previous.click();
            System.out.println("Previous button is present and clickable.");
        } catch (Exception e) {
            Assert.fail("Previous button is present but NOT clickable: " + e.getMessage());
        }
    }
    @Test(priority = 23)
    public void Next_Button_Functionality_Validation(){
        By nextBtn = By.xpath("//a[normalize-space()='Next']");
        List<WebElement> list = driver.findElements(nextBtn);
        Assert.assertTrue(list.size() > 0,
                "Previous button is NOT present on the page.");
        WebElement next = list.get(0);
        Assert.assertTrue(next.isDisplayed(),
                "Previous button is present but NOT visible.");
        try {
            next.click();
            System.out.println("Previous button is present and clickable.");
        } catch (Exception e) {
            Assert.fail("Previous button is present but NOT clickable: " + e.getMessage());
        }
    }


    private final By scrollUpBtn = By.cssSelector("a#scrollUp, a.back-to-top, a[href='#top']");

    @Test(priority = 24)
    public void ScrollUpbuttonFunctionalityValidation() {
        js.executeScript("window.scrollTo(0, 0);");
        wait.until(ExpectedConditions.invisibilityOfElementLocated(scrollUpBtn));
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(scrollUpBtn));
        Assert.assertTrue(btn.isDisplayed(), "Scroll Up button should be visible at the bottom.");
        btn.click();
        boolean hiddenAgain = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.invisibilityOfElementLocated(scrollUpBtn));
        Assert.assertTrue(hiddenAgain, "Scroll Up button should be hidden at top.");

    }




}


 