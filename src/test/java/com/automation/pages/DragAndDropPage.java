package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

/**
 * DragAndDropPage - https://the-internet.herokuapp.com/drag_and_drop
 */
public class DragAndDropPage extends BasePage {

    @FindBy(id = "column-a")
    private WebElement columnA;

    @FindBy(id = "column-b")
    private WebElement columnB;

    private final By columnALocator = By.id("column-a");
    private final By columnBLocator = By.id("column-b");
    private final By columnAHeader  = By.cssSelector("#column-a header");
    private final By columnBHeader  = By.cssSelector("#column-b header");

    public void dragAtoB() {
        log.info("Dragging column A to column B");
        // Standard drag and drop
        dragAndDrop(columnALocator, columnBLocator);
    }

    public void dragBtoA() {
        log.info("Dragging column B to column A");
        dragAndDrop(columnBLocator, columnALocator);
    }

    /**
     * JS-based drag and drop for sites where Actions doesn't work
     */
    public void dragAtoBViaJS() {
        log.info("Drag and Drop via JavaScript");
        String jsScript = """
            function simulateDragDrop(sourceNode, destinationNode) {
                var EVENT_TYPES = {
                    DRAG_END: 'dragend', DRAG_START: 'dragstart',
                    DROP: 'drop', DRAG_ENTER: 'dragenter', DRAG_LEAVE: 'dragleave', DRAG_OVER: 'dragover'
                };
                function createCustomEvent(type) {
                    var event = new CustomEvent(type, {bubbles: true, cancelable: true});
                    event.dataTransfer = {
                        data: {}, setData: function(type, val) { this.data[type] = val; },
                        getData: function(type) { return this.data[type]; }
                    };
                    return event;
                }
                function dispatchEvent(node, type, event) { node.dispatchEvent(event); }
                var event = createCustomEvent(EVENT_TYPES.DRAG_START);
                dispatchEvent(sourceNode, EVENT_TYPES.DRAG_START, event);
                var event2 = createCustomEvent(EVENT_TYPES.DRAG_ENTER);
                dispatchEvent(destinationNode, EVENT_TYPES.DRAG_ENTER, event2);
                var event3 = createCustomEvent(EVENT_TYPES.DROP);
                event3.dataTransfer = event.dataTransfer;
                dispatchEvent(destinationNode, EVENT_TYPES.DROP, event3);
                dispatchEvent(sourceNode, EVENT_TYPES.DRAG_END, createCustomEvent(EVENT_TYPES.DRAG_END));
            }
            simulateDragDrop(arguments[0], arguments[1]);
            """;
        js.executeScript(jsScript, columnA, columnB);
    }

    public String getColumnAText() {
        return getText(columnAHeader);
    }

    public String getColumnBText() {
        return getText(columnBHeader);
    }

    public boolean isColumnADisplayed() {
        return isDisplayed(columnALocator);
    }
}
