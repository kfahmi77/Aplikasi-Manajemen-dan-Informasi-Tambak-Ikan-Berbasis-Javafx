package id.pbotubes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;

import java.util.Calendar;

public class HelloMessage {
    @FXML
    private Label usernameLabel;
    private String username;

    @FXML
    private Menu menuBarUser;

    public HelloMessage(Label usernameLabel, String username) {
        this.usernameLabel = usernameLabel;
        this.username = username;
    }

    public HelloMessage(Menu menuBarUser, String username) {
        this.menuBarUser = menuBarUser;
        this.username = username;
    }

    public void getMessageDate(Label usernameLabel) {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);

        if (hours >= 1 && hours <= 10) {
            usernameLabel.setText("Pagi ");
        } else if (hours >= 11 && hours <= 14) {
            usernameLabel.setText("Siang ");
        } else if (hours >= 15 && hours <= 18) {
            usernameLabel.setText("Sore ");
        } else if (hours >= 19) {
            usernameLabel.setText("Malam ");
        }
    }

    public void getMessageDate2(String username, Menu menuBarUser) {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);

        if (hours >= 1 && hours <= 10) {
            menuBarUser.setText(username);
        } else if (hours >= 11 && hours <= 14) {
            menuBarUser.setText(username);
        } else if (hours >= 15 && hours <= 18) {
            menuBarUser.setText(username);
        } else if (hours >= 19) {
            menuBarUser.setText(username);
        }
    }
}
