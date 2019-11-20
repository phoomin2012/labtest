package model;

import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Score extends Pane {

    Label point;
    public Score(int x, int y) {
        point = new Label("0");
        point.setFont(Font.font("Verdana", FontWeight.BOLD,30));
        point.setTextFill(Color.web("#FFF"));

        setTranslateX(x);
        setTranslateY(y);

        getChildren().addAll(point);
    }

    public void setPoint(int score) {
        point.setText(Integer.toString(score));
    }

    public String getPoint() {
        return point.getText();
    }
}
