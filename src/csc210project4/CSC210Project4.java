/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc210project4;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author matthewbeiswenger
 */
public class CSC210Project4 extends Application
{
    
    @Override
    public void start(Stage stage)
    {
        // label and textfield for input
        Label l = new Label("Size of New Game");
        TextField tf = new TextField();
        
        // add label and textfield to hbox
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(l, tf);
        hbox.setAlignment(Pos.BOTTOM_CENTER);
        hbox.setPadding(new Insets(12, 50, 50, 50));
        
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        
        // place gridpane in vbox
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(gp);
        
        BorderPane bp = new BorderPane();
        bp.setCenter(vbox);
        bp.setBottom(hbox);
        
        Scene scene = new Scene(bp, 800, 500);
        
        stage.setTitle("N-Queens Problem");
        stage.setScene(scene);
        stage.show();
        
        EventHandler nQueensHandler = new nQueensHandler (tf, gp);
        tf.setOnAction(nQueensHandler);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }
    
}
