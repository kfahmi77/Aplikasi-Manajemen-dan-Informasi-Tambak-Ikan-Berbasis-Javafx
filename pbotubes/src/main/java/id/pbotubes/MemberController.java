package id.pbotubes;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MemberController {
    @FXML
    private Label usernameLabel;

    public void displayName(String username) {
        HelloMessage helloMessage = new HelloMessage(usernameLabel, username);
        helloMessage.getMessageDate(usernameLabel);

    }

}
