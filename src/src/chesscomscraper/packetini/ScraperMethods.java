package chesscomscraper.packetini;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 *
 * @author BronsteinPawn, AKA Chess.com LORD
 */
public class ScraperMethods {
    
    /**
     * Connects to webpage
     * @param drvr Driver that will be used to establish the connection
     * @param url Webpage URL that the driver will be connected to
     * @throws java.lang.Exception
     */
    public void connectToPage(WebDriver drvr, String url) throws Exception{
            drvr.navigate().to(url);
    }
    
    /**
     * Clicks on the Chess.com download PGN button. Its necessary for the webpage
     * to fully load before executing this method
     * 
     * @param drvr Driver that has already navigated to the chess.com game
     * @throws java.lang.Exception
     */
    public void clickDownload(WebDriver drvr) throws Exception{
        WebElement c = drvr.findElement(By.xpath("//*[@id=\"chess-board-sidebar\"]/div[3]/div[8]/span[2]/a[7]/i"));
        c.click();
    }
    
    /**
     * 
     * @param drvr
     * @return 
     * @throws java.lang.Exception
     */
    public String getPGNTab(WebDriver drvr)throws Exception{
        WebElement textPGN = drvr.findElement(By.xpath("//*[@id=\"spa_chessboard_ShareMenuPgnContentTextareaId\"]"));
        return textPGN.getAttribute("value");
    }
    
}
