package GUI;

import chesscomscraper.packetini.ScraperMethods;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

/**
 *
 * @author BronsteinPawn, AKA Chess.com LORD
 */
public class FinishedGameWindow extends Application{
    
    WebDriver theDriver; //Driver used by Selenium
    
    //GUI objects
    GridPane theLayout;
    
    Label header; 
    Text instructions;
    Text status;
    
    TextField urlFld;
    TextArea pgnDisplay;
    
    Button connectBtn;
    Button getPGN;

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        /*******/
        //Initializing GUI objects
        /*******/
        theLayout = new GridPane();
        
        header = new Label();
        header.setFont(new Font("Arial", 24));
        header.setText("              I need a banner/logo please");
        
        //Provides instructions to the user
        instructions = new Text(); 
        instructions.setFont(new Font("Arial", 16));
        instructions.setFill(Color.BLACK);
        instructions.setText("            Enter the chess game URL below"); //12 spaces
        
        //Displays the status of the connection to the page. Disconnected by default
        status = new Text();
        status.setFont(FontsClass.defFont());
        status.setFill(Color.RED);
        status.setText("          Disconnected"); //10 spaces to center it
        
        //Field where URL is typed into
        urlFld = new TextField();
        urlFld.setFont(FontsClass.defFont());
        urlFld.setPromptText("https://www.chess.com/daily/game/1");
        
        //Area where PGN is displayed
        pgnDisplay = new TextArea();
        pgnDisplay.setWrapText(true); //If text exceeds container line will break/wrap
        pgnDisplay.setFont(FontsClass.defFont());
        pgnDisplay.setPrefWidth(360);
        pgnDisplay.setPrefHeight(260);
        
        connectBtn = new Button();
        connectBtn.setFont(FontsClass.defFont());
        connectBtn.setText("Connect");
        connectBtn.setTooltip(new Tooltip("Connect to the webpage in order to scrape it"));
        connectBtn.setOnAction(e ->{
            
            try{
                
                ScraperMethods scpM = new ScraperMethods();
                String gameUrl = urlFld.getText();
                scpM.connectToPage(theDriver, gameUrl);
                
                //If it works, change status to connected
                status.setText("            Connected"); //12 spaces to center it
                status.setFill(Color.GREEN);
            }
            catch(Exception ex){
                status.setText("          Disconnected"); //10 spaces to center it
                status.setFill(Color.RED);
            }
            
        });
        
        getPGN = new Button();
        getPGN.setFont(FontsClass.defFont());
        getPGN.setText("Get PGN");
        getPGN.setTooltip(new Tooltip("Retrieve the game PGN"));
        getPGN.setOnAction(e ->{
        
            try{
                ScraperMethods scpM = new ScraperMethods();
                scpM.clickDownload(theDriver);
                Thread.sleep(1000);
                pgnDisplay.setText(scpM.getPGNTab(theDriver));
            }
            catch(Exception ex){}
        });
        
        /******/
        /*Setting up the layout. Column constraints are added to layout to 
        setup objects correctly. colOne and colFour act as paddings. They are
        the outer left and right columns.colTwo and colThree will contain
        most objects. colOne and colFour and mostly used as padding*/
        /******/
        ColumnConstraints colOne = new ColumnConstraints();
        colOne.setPrefWidth(80);
        theLayout.getColumnConstraints().add(0, colOne);
        
        ColumnConstraints colTwo = new ColumnConstraints();
        colTwo.setPrefWidth(100);
        theLayout.getColumnConstraints().add(1, colTwo);
        
        ColumnConstraints colThree = new ColumnConstraints();
        colThree.setPrefWidth(80);
        theLayout.getColumnConstraints().add(2, colThree);
        
        ColumnConstraints colFour = new ColumnConstraints();
        colFour.setPrefWidth(80);
        theLayout.getColumnConstraints().add(3, colFour);
        
        ColumnConstraints colFive = new ColumnConstraints();
        colFive.setPrefWidth(100);
        theLayout.getColumnConstraints().add(4, colFive);
        
        ColumnConstraints colSix = new ColumnConstraints();
        colSix.setPrefWidth(80);
        theLayout.getColumnConstraints().add(5, colSix);
        
        /******/
        //Adding objects to layout
        /******/
        GridPane.setConstraints(header, 0, 0, 6, 1);
        theLayout.getChildren().add(header);
        
        GridPane.setConstraints(instructions, 1, 1, 4, 1);
        theLayout.getChildren().add(instructions);
        
        GridPane.setConstraints(urlFld, 1, 2, 4, 1);
        theLayout.getChildren().add(urlFld);
        
        GridPane.setConstraints(status, 2, 3, 2, 1);
        theLayout.getChildren().add(status);
        
        GridPane.setConstraints(pgnDisplay, 1, 4, 4, 1);
        theLayout.getChildren().add(pgnDisplay);
        
        GridPane.setConstraints(connectBtn, 2, 5, 1, 1);
        theLayout.getChildren().add(connectBtn);
        
        GridPane.setConstraints(getPGN, 3, 5, 1, 1);
        theLayout.getChildren().add(getPGN);
        
        theLayout.setPadding(new Insets(10, 10, 10, 10));
        theLayout.setVgap(10);
        theLayout.setHgap(5);
        
        /*******/
        //Setting up scene and displaying stage
        /*******/
        Scene theScene = new Scene(theLayout, 520, 350);
        primaryStage.setScene(theScene);
        primaryStage.setTitle("Chesscom Scraper");
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e ->{
            primaryStage.close();
        });
        primaryStage.show();
        
        /*Driver is initialized. This driver is what connects the application
        to the internet. ChromeOptions are used to make the browser headless
        which means there wont be any visible chrome window.*/
        System.setProperty("webdriver.chrome.driver", "src/drivers/chromedriver.exe");
        ChromeOptions browserSettings = new ChromeOptions();
        browserSettings.addArguments("--headless");
        browserSettings.addArguments("--disable-gpu");
        theDriver = new ChromeDriver(browserSettings);
        
    }
}
