package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * MouseHoverPage - https://the-internet.herokuapp.com/hovers
 */
public class MouseHoverPage extends BasePage {

    @FindBy(css = ".figure")
    private List<WebElement> figures;

    @FindBy(css = ".figure .figcaption")
    private List<WebElement> figCaptions;

    private final By figuresLocator  = By.cssSelector(".figure");
    private final By captionLocator  = By.cssSelector(".figure .figcaption");

    public void hoverOverFigure(int index) {
        log.info("Hovering over figure at index: {}", index);
        List<WebElement> figs = findElements(figuresLocator);
        hoverOver(figs.get(index));
    }

    public boolean isCaptionVisibleAfterHover(int index) {
        hoverOverFigure(index);
        sleep(300); // slight delay for CSS transition
        List<WebElement> captions = findElements(captionLocator);
        try {
            return captions.get(index).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getCaptionText(int index) {
        hoverOverFigure(index);
        sleep(300);
        List<WebElement> captions = findElements(captionLocator);
        return captions.get(index).getText();
    }

    public int getFigureCount() {
        return findElements(figuresLocator).size();
    }
}
