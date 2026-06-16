package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * WebTablePage - https://the-internet.herokuapp.com/tables
 */
public class WebTablePage extends BasePage {

    @FindBy(css = "#table1")
    private WebElement table1;

    @FindBy(css = "#table1 tbody tr")
    private List<WebElement> tableRows;

    @FindBy(css = "#table1 thead th")
    private List<WebElement> tableHeaders;

    private final By tableLocator  = By.cssSelector("#table1");
    private final By rowsLocator   = By.cssSelector("#table1 tbody tr");
    private final By headersLocator = By.cssSelector("#table1 thead th");

    public int getRowCount() {
        return findElements(rowsLocator).size();
    }

    public int getColumnCount() {
        return findElements(headersLocator).size();
    }

    public String getCellData(int rowIndex, int colIndex) {
        List<WebElement> rows = findElements(rowsLocator);
        WebElement row = rows.get(rowIndex);
        List<WebElement> cells = row.findElements(By.tagName("td"));
        return cells.get(colIndex).getText();
    }

    public List<String> getColumnData(String columnHeader) {
        List<String> columnData = new ArrayList<>();
        List<WebElement> headers = findElements(headersLocator);

        int colIndex = -1;
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).getText().equalsIgnoreCase(columnHeader)) {
                colIndex = i;
                break;
            }
        }

        if (colIndex == -1) {
            throw new RuntimeException("Column not found: " + columnHeader);
        }

        List<WebElement> rows = findElements(rowsLocator);
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (colIndex < cells.size()) {
                columnData.add(cells.get(colIndex).getText());
            }
        }
        return columnData;
    }

    public List<String> getHeaderNames() {
        List<String> headers = new ArrayList<>();
        for (WebElement header : tableHeaders) {
            headers.add(header.getText());
        }
        return headers;
    }

    public boolean isTableDisplayed() {
        return isDisplayed(tableLocator);
    }

    public String getRowDataByLastName(String lastName) {
        List<WebElement> rows = findElements(rowsLocator);
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            if (!cells.isEmpty() && cells.get(0).getText().equalsIgnoreCase(lastName)) {
                StringBuilder sb = new StringBuilder();
                cells.forEach(c -> sb.append(c.getText()).append(" | "));
                return sb.toString().trim();
            }
        }
        return "Not Found";
    }
}
