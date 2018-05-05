package GUI;

import com.sun.glass.events.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author BronsteinPawn, AKA Chess.com LORD
 */
public class LiveGameWindow extends Application{
    
    WebDriver theDriver; //Driver used by SeleniumAPI to connect to webpage
    //Timer used to try to retrieve move every certain time.
    int counter = 1; //By default
    Timer checkMove = new Timer();
    TimerTask getMove = new TimerTask(){
        @Override
        public void run() {
            
            //Xpath where <a> tag containing the move is located. 
            String elementPath = "//*[@id=\"vertical_moveListControl_gotomoveid_0_" + counter + "\"]";
            try{
                /*This is the <a> tag containing the move. If the tag doesnt exist yet
                an exception is thrown and we wait to rerun the method again*/
                WebElement moveContainer = theDriver.findElement(By.xpath(elementPath));
                String move = formatMove(moveContainer.getText(), counter);
                pgnDisplay.appendText(move);
                counter++;
            
            }catch(Exception ex){
                ex.printStackTrace();
            }
            
        }
    };
    
    GridPane theLayout;
    
    Label header;
    Text instructions;
    Text status;
    
    TextArea pgnDisplay;
    
    Button connectBtn;
    Button startBtn;
    Button stopBtn;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        /*******/
        //Initializing objects
        /*******/
        theLayout = new GridPane();
        
        //Logo
        header = new Label();
        header.setFont(new Font("Arial", 20));
        header.setText("                I need a banner/logo please"); //4Tabs
        
        //Instructions to customer
        instructions = new Text();
        instructions.setFont(FontsClass.defFont());
        instructions.setFill(Color.BLACK);
        instructions.setText("                      Press connect and navigate to the chess game.\n" //6 tabs
                + "Once there press start to begin the real time scraping and stop to stop it.");
        
        //Says whether method is running or not
        status = new Text();
        status.setFont(FontsClass.defFont());
        status.setText("                                        Not scraping"); //10 tabs
        status.setFill(Color.RED);
        
        //TextArea where PGN is added
        pgnDisplay = new TextArea();
        pgnDisplay.setFont(FontsClass.defFont());
        pgnDisplay.setPrefWidth(360);
        pgnDisplay.setPrefHeight(260);
        
        //ConnectButton to open the chrome window
        connectBtn = new Button();
        connectBtn.setFont(FontsClass.defFont());
        connectBtn.setText("Connect");
        connectBtn.setPrefWidth(100);
        connectBtn.setOnAction(e ->{
        /*Driver is initialized. This driver is what connects the application
        to the internet. ChromeOptions are used to make the browser headless
        which means there wont be any visible chrome window.*/
        System.setProperty("webdriver.chrome.driver", "src/drivers/chromedriver.exe");
        theDriver = new ChromeDriver();
        theDriver.navigate().to("https://www.chess.com/play/computer");
        });
        
        //Start button to begin scraping
        startBtn = new Button();
        startBtn.setFont(FontsClass.defFont());
        startBtn.setText("Start");
        startBtn.setPrefWidth(100);
        startBtn.setOnAction(e ->{
            checkMove.scheduleAtFixedRate(getMove, 1000, 3000);
            status.setText("                                        Scraping!!!"); //10  tabs
            status.setFill(Color.GREEN);
        });
        
        stopBtn = new Button();
        stopBtn.setFont(FontsClass.defFont());
        stopBtn.setText("Stop");
        stopBtn.setPrefWidth(100);
        stopBtn.setOnAction(e ->{
            checkMove.cancel();
            checkMove.purge();
            status.setText("                                        Not scraping"); //10 tabs
            status.setFill(Color.RED);
        });
        
        /*******/
        //Set up column constraints 
        /******/
        ColumnConstraints colOne = new ColumnConstraints();
        colOne.setPrefWidth(50);
        theLayout.getColumnConstraints().add(0, colOne);
        
        ColumnConstraints colTwo = new ColumnConstraints();
        colTwo.setPrefWidth(120);
        theLayout.getColumnConstraints().add(1, colTwo);
        
        ColumnConstraints colThree = new ColumnConstraints();
        colThree.setPrefWidth(120);
        theLayout.getColumnConstraints().add(2, colThree);
        
        ColumnConstraints colFour = new ColumnConstraints();
        colFour.setPrefWidth(120);
        theLayout.getColumnConstraints().add(3, colFour);
        
        ColumnConstraints colFive = new ColumnConstraints();
        colFive.setPrefWidth(50);
        theLayout.getColumnConstraints().add(4, colFive);
        
        /*****/
        //Adding objects to layout
        /*****/
        GridPane.setConstraints(header, 0, 0, 5, 1);
        theLayout.getChildren().add(header);
        
        GridPane.setConstraints(instructions, 1, 1, 3, 1);
        theLayout.getChildren().add(instructions);
        
        GridPane.setConstraints(status, 1, 2, 3, 1);
        theLayout.getChildren().add(status);
        
        GridPane.setConstraints(pgnDisplay, 1, 3, 3, 1);
        theLayout.getChildren().add(pgnDisplay);
        
        GridPane.setConstraints(connectBtn, 1, 4, 1, 1);
        theLayout.getChildren().add(connectBtn);
        
        GridPane.setConstraints(startBtn, 2, 4, 1, 1);
        theLayout.getChildren().add(startBtn);
        
        GridPane.setConstraints(stopBtn, 3, 4, 1, 1);
        theLayout.getChildren().add(stopBtn);
        
        theLayout.setPadding(new Insets(10, 10, 10, 10));
        theLayout.setVgap(10);
        theLayout.setHgap(20);
        
        /******/
        //Setting up scene and stage
        /******/
        Scene theScene = new Scene(theLayout, 520, 370);
        primaryStage.setScene(theScene);
        primaryStage.setTitle("Chesscom Scraper");
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(e ->{
            primaryStage.close();
        });
        primaryStage.show();
    }
    
    /**
     * Formats a move to add the move number if it is white´s turn or just a space
     * if it is black´s turn.
     * @param move
     * @param count
     * @return 
     */
    public String formatMove(String move, int count){
        
        //If the first bit is 0 it means it is even
        if((count & 1 ) == 0){
            //Number is even which means it is black´s turn. Just add a space before the move
            move = " " + move;
        }
        else{
            //Move is odd
            int ifEven = count + 1;
            int moveNumber = ifEven/2;
            move = moveNumber + "." + move + " ";
        }
        return move;
    }
}
