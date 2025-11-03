package org.example.GUI;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BadScan {

    public static Scene createScene(){

        Text title = new Text(875, 623, "Bad scan");
        title.setFont(new Font(50));


        Group root = new Group(title);
        return new Scene(root);
    }
}
