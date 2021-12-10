package id.pbotubes;

import id.pbotubes.utils.ConnectionDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {
    @FXML
    private Label invalidLabel;

    @FXML
    private Button cancelButton;

    @FXML
    private Button loginButtton;

    @FXML
    private TextField usernameTextField;

    @FXML
    private TextField passwordTextField;

    private Stage stage;
    private Scene scene;
    private Parent root;


    public void CancelButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void LoginButtonAction(ActionEvent event) {
        //cek jika field ada yang kosong
        if (!usernameTextField.getText().isBlank() && !passwordTextField.getText().isBlank()) {
            validateLogin(event);
        } else {
            invalidLabel.setText("Username dan Password wajib diisi");
        }
    }

    public void validateLogin(ActionEvent event) {
        String username = usernameTextField.getText();
        //panggil fungsi koneksi
        ConnectionDB connectionDB = new ConnectionDB();
        Connection connection = connectionDB.getConnection();
        String verifyLogin = "SELECT username,password,role FROM user WHERE username = '" + usernameTextField.getText() + "' " +
                "AND password = '" + passwordTextField.getText() + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(verifyLogin);
            //jika data benar
            if (resultSet.next()) {
                //login sesuai hak aksesnya
                if (resultSet.getInt("role") == 1) {
                    invalidLabel.setText("Admin");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("admin/menu-admin.fxml"));
                    root = loader.load();

                    AdminController scene2Controller = loader.getController();
                    scene2Controller.displayName(username);

                } else {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("member/menu-member.fxml"));
                    root = loader.load();

                    MemberController scene2Controller = loader.getController();
                    scene2Controller.displayName(username);

                }
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Username / Password salah");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}