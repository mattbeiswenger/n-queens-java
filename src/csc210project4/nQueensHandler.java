/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csc210project4;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

/**
 *
 * @author matthewbeiswenger
 */
public class nQueensHandler implements EventHandler<ActionEvent>
{
    GridPane board;
    TextField tf;
    Map<String, Label> labelMap = new HashMap<>();

    public nQueensHandler(TextField gameSizeTf, GridPane board)
    {
        tf = gameSizeTf;
        this.board = board;
    }
    
    @Override
    public void handle(ActionEvent event)
    {
        // clear the gridpane
        board.getChildren().clear();
        // retrieve the number from the input
        int boardSize = Integer.parseInt(tf.getText());
        // populate the board with labels and add labels to map
        for (int r = 0; r < boardSize; r++)
        {
            for(int c = 0; c < boardSize; c++)
            {
                // add green checkered box on indices that are either both odds
                // or both evens
                if (r % 2 == 0 && c % 2 == 0 || r % 2 != 0 && c % 2 != 0)
                {
                    // place String and Label in map
                    labelMap.put(String.format("%d-%d", r, c), gLabel(new Label()));
                    // add the corresponding label to the grid pane
                    board.add(labelMap.get(String.format("%d-%d", r, c)), r, c);
                }
                // else add the yellow checkered box
                else
                {
                    labelMap.put(String.format("%d-%d", r, c), yLabel(new Label()));
                    board.add(labelMap.get(String.format("%d-%d", r, c)), r, c);
                }
            }
        }

        // display alert if the board size does not allow placement
        if (boardSize == 3 || boardSize == 2 || boardSize < 1)
        {
            Alert a = new Alert(AlertType.INFORMATION);
            a.setTitle("Message");
            a.setHeaderText("Message");
            a.setContentText("No placements are possible");
            a.showAndWait();
        }
        else
        {
            // call the recursive method
            extend(new LinkedList<Place>(), 0, boardSize);
        }
    }
    
    // green label
    Label gLabel(Label l)
    {
        String color = "green";
        l.setStyle("-fx-background-color: " + color);
        l.setFont(Font.font(30));
        l.setPadding(new Insets(10, 30, 10, 30));
        l.setText("   ");

        return l;
    }
    
    // yellow label
    Label yLabel(Label l)
    {
        String color = "yellow";
        l.setStyle("-fx-background-color: " + color);
        l.setFont(Font.font(30));
        l.setPadding(new Insets(10, 30, 10, 30));
        l.setText("   ");

        return l;
    }
    
    

    // Try to extend a partial queens configuration by placing
    // a queen in a given row.
    boolean extend(LinkedList<Place> partial, int row, int gameSize)
    {

        // queens were successfully placed
        if (row >= gameSize)
        {
            return true;
        }

        // iterate through row to see if a queen can be placed
        for (int col = 0; col < gameSize; col++)
        {
            
            // create a place object for the specified location
            Place p = new Place(row, col);
            
            // check if there is a conflict in placement
            if (!conflict(partial, row, col))
            {
               // add place to partial solution
               partial.add(p);
               // mark board
               labelMap.get(p.toString()).setText("Q");
            }
            else
            {
                continue;
            }

            // call the function on the next row
            if(extend(partial, row + 1, gameSize))
            {
                return true;
            }
            else
            {
                // conflict occurred so we must remove place from list
                partial.remove(p);
                labelMap.get(p.toString()).setText("   ");
            }
        }

        // backtrack to last row
        return false;
    }

    boolean conflict(Collection<Place> places, int r, int c)
    {
        
        // the row and column represent where we are placing
        // the new Place
        
        // we need to check if that row and column conflicts with any
        // existing Places
        
        
        Iterator<Place> iterator = places.iterator();
        
        // iterate through partial solution, calling Place method
        // for each Place currently in the list
        while (iterator.hasNext())
        {
            if (iterator.next().conflict(r, c))
            {
                return true;
            }
        }
        
        return false;
    }
}

class Place
{
    public final int row;
    public final int col;

    public Place(int r, int c)
    {
        row = r; col = c;
    }

    public boolean conflict(int r, int c)
    {

        // will return true if and only if (r, c) is in the same row,
        // column, or diagonal as this Place object.
        
        // column and row
        if (this.row == r || this.col == c)
        {    
            return true;
        }
        
        // falling diagonal
        int temp_row = r;
        int temp_col = c;
        while (this.row <= r && this.col <= c)
        {
            if (this.row == r && this.col == c)
            {
                return true;
            }
            
            r--;
            c--;
        }
        
        // reset row and col
        r = temp_row;
        c = temp_col;
        
        // rising diagonal
        while (this.row <= r && this.col >= col)
        {
            if (this.row == r && this.col == c)
            {
                return true;
            }
            
            r--;
            c++;
        }
        
        // no conflict
        return false;
    }

    @Override
    public String toString()
    {
        return row+"-"+col;
    }
}


