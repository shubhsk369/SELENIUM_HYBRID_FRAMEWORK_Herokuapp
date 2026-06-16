package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * CheckboxPage - https://the-internet.herokuapp.com/checkboxes
 */
public class CheckboxPage extends BasePage {

    @FindBy(css = "#checkboxes input[type='checkbox']")
    private List<WebElement> checkboxes;

    private final By checkboxLocator = By.cssSelector("#checkboxes input[type='checkbox']");

    public void checkCheckboxByIndex(int index) {
        log.info("Checking checkbox at index: {}", index);
        WebElement checkbox = checkboxes.get(index);
        checkCheckbox(checkbox);
    }

    public void uncheckCheckboxByIndex(int index) {
        log.info("Unchecking checkbox at index: {}", index);
        WebElement checkbox = checkboxes.get(index);
        uncheckCheckbox(checkbox);
    }

    public boolean isCheckboxChecked(int index) {
        return checkboxes.get(index).isSelected();
    }

    public int getCheckboxCount() {
        return checkboxes.size();
    }

    public void checkAllCheckboxes() {
        for (WebElement cb : checkboxes) {
            checkCheckbox(cb);
        }
    }

    public void uncheckAllCheckboxes() {
        for (WebElement cb : checkboxes) {
            uncheckCheckbox(cb);
        }
    }

    public boolean isPageLoaded() {
        return !findElements(checkboxLocator).isEmpty();
    }
}
