package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DropdownPage - https://the-internet.herokuapp.com/dropdown
 */
public class DropdownPage extends BasePage {

    @FindBy(id = "dropdown")
    private WebElement dropdownElement;

    private final By dropdownLocator = By.id("dropdown");

    public void selectOption(String optionText) {
        log.info("Selecting dropdown option: {}", optionText);
        selectByVisibleText(dropdownLocator, optionText);
    }

    public void selectByOptionValue(String value) {
        selectByValue(dropdownLocator, value);
    }

    public void selectByOptionIndex(int index) {
        selectByIndex(dropdownLocator, index);
    }

    public String getSelectedOptionText() {
        return new Select(dropdownElement).getFirstSelectedOption().getText();
    }

    public List<String> getAllOptions() {
        return new Select(dropdownElement).getOptions()
            .stream()
            .map(WebElement::getText)
            .collect(Collectors.toList());
    }

    public boolean isOptionSelected(String optionText) {
        return getSelectedOptionText().equals(optionText);
    }

    public int getOptionCount() {
        return new Select(dropdownElement).getOptions().size();
    }
}
