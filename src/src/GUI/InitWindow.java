package GUI;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author BronsteinPawn, AKA Chess.com LORD
 */
public class InitWindow extends Application{
    
    //Main window used troughout the program
    Stage mainStage;
    GridPane theLayout;
    
    //Header/baner
    Label header;
    Text instructions;
    
    /*Check boxes used to let user choose whether game that will be scraped
    is a game that has already finished or if it is a live game*/
    CheckBox liveGame;
    CheckBox finishedGame;
    boolean isLive; //False if it is not a live game. True if it is. False by default 
    
    //Button to go to next stage
    Button continueBtn;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        /******/
        //Initializing objects
        /******/
        mainStage = new Stage();
        theLayout = new GridPane();
        
        header = new Label();
        header.setFont(new Font("Arial", 20));
        header.setText("            I need a banner/logo please"); //3  tabs
        
        instructions = new Text();
        instructions.setFont(FontsClass.defFont());
        instructions.setFill(Color.BLACK);
        instructions.setText("          Select the type of game you want to scrape");//10 spaces
        
        liveGame = new CheckBox();
        liveGame.setFont(FontsClass.defFont());
        liveGame.setText("Live game");
        liveGame.setOnAction(e ->{
            if(liveGame.isSelected()){
                isLive = true;
                finishedGame.setSelected(false);
                
            }
            if(!liveGame.isSelected()){
                isLive = false;
                finishedGame.setSelected(true);
            }
        });
        
        finishedGame = new CheckBox();
        finishedGame.setFont(FontsClass.defFont());
        finishedGame.setText("Game already finished");
        finishedGame.setSelected(true);
        finishedGame.setOnAction(e ->{
            if(finishedGame.isSelected()){
                isLive = false;
                liveGame.setSelected(false);
            }
            if(!finishedGame.isSelected()){
                isLive = true;
                liveGame.setSelected(true);
            }
        });
        
        isLive = false; //Default value
        
        continueBtn = new Button();
        continueBtn.setFont(FontsClass.defFont());
        continueBtn.setText("Continue");
        continueBtn.setOnAction(e ->{
            
            if(isLive){
                LiveGameWindow liveWindow = new LiveGameWindow();
                try {
                    liveWindow.start(mainStage);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
            if(!isLive){
               FinishedGameWindow finishWindow = new FinishedGameWindow();
                try {
                    finishWindow.start(mainStage);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        });
        
        /******/
        //Setting up column constraints to center objects
        /******/
        
        //Far left column. Acts as padding
        ColumnConstraints colOne = new ColumnConstraints();
        colOne.setPrefWidth(50);
        theLayout.getColumnConstraints().add(0, colOne);
        
        //Main Columns. This is where liveGame checkbox is stored
        ColumnConstraints colTwo = new ColumnConstraints();
        colTwo.setPrefWidth(90);
        theLayout.getColumnConstraints().add(1, colTwo);
        
        //Center column, this is where  continueBtn is stored
        ColumnConstraints colThree = new ColumnConstraints();
        colThree.setPrefWidth(75);
        theLayout.getColumnConstraints().add(2, colThree);
        
        //Main columns. This is where finishedGame checkbox is stored
        ColumnConstraints colFour = new ColumnConstraints();
        colFour.setPrefWidth(85);
        theLayout.getColumnConstraints().add(3, colFour);
        
        //Far right column. Acts as padding
        ColumnConstraints colFive = new ColumnConstraints();
        colFive.setPrefWidth(100);
        theLayout.getColumnConstraints().add(4, colFive);
        
        /*****/
        //Adding objects to layout
        /*****/
        GridPane.setConstraints(header, 0, 0, 5, 1);
        theLayout.getChildren().add(header);
        
        GridPane.setConstraints(instructions, 1, 1, 3, 1);
        theLayout.getChildren().add(instructions);
        
        GridPane.setConstraints(finishedGame, 1, 2, 2, 1);
        theLayout.getChildren().add(finishedGame);
        
        GridPane.setConstraints(liveGame, 3, 2, 1, 1);
        theLayout.getChildren().add(liveGame);
                
        GridPane.setConstraints(continueBtn, 2, 3, 1, 1);
        theLayout.getChildren().add(continueBtn);
        
        theLayout.setPadding(new Insets(10, 10, 10, 10));
        theLayout.setVgap(10);
        theLayout.setHgap(12);
        
        /*****/
        //Setting up scene and stage
        /*****/
        Scene initScene = new Scene(theLayout, 370, 160);
        mainStage.setScene(initScene);
        mainStage.setTitle("Chesscom Scraper");
        mainStage.setResizable(false);
        mainStage.show();
    }
    
    public static void main(String[] args){
        launch(args);
    }
    
}
