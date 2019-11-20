package model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Countup extends Pane {

    Label label;
    int time, x, y;
    public Countup(int x, int y) {
        this.x = x;
        this.y = y;
        label = new Label("00:00");
        label.setFont(Font.font("Verdana", FontWeight.BOLD,30));
        label.setTextFill(Color.web("#FFF"));

        getChildren().addAll(label);
        setTranslateX(x - getWidth());
        setTranslateY(y);
    }

    public void setTime(int time) {
        this.time = time;
        String times = "";
        if(time / 3600 < 10) {
            times += "0";
        }
        times += time / 3600;
        times += ":";
        if(time / 60 < 10) {
            times += "0";
        }
        times += time / 60;
        times += ".";
        if(time % 60 < 10) {
            times += "0";
        }
        times += time % 60;

        label.setText(times);
        label.setAlignment(Pos.CENTER_RIGHT);
        setTranslateX(x - getWidth());
    }

    public String getTime() {
        return Integer.toString(time);
    }

    public String getTimeString() {
        return label.getText();
    }
}
