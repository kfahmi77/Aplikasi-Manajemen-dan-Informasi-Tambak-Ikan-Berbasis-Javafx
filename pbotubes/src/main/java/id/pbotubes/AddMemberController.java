package id.pbotubes;

import id.pbotubes.models.Member;
import id.pbotubes.utils.ConnectionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddMemberController implements Initializable {
    @FXML
    private ComboBox<Integer> roleCombobox;
    @FXML
    private TextField addressField;
    @FXML
    private TextField fullNameField;
    @FXML
    private TextField pwdField;
    @FXML
    private TextField usernameField;

    ObservableList<Integer> listRole = FXCollections.observableArrayList(1, 2);

    String query = null;
    Connection connection = null;
    Member member = null;
    private boolean update;
    int memberId;

    //fungsi untuk menyimpan
    public void btnSave() {
        ConnectionDB connectionDB = new ConnectionDB();
        connection = connectionDB.getConnection();
        String address = addressField.getText();
        String fullname = fullNameField.getText();
        String pswd = pwdField.getText();
        String user = usernameField.getText();
        //jika ada field kosong maka akan tampil pesan error
        if (address.isEmpty() && fullname.isEmpty() && pswd.isEmpty() && user.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Isi semua data");
            alert.showAndWait();
            //jika field diisi semua maka akan panggil fungsi lain
        } else {
            getQuery();
            insert();
            btnClear();

        }

    }

    private void getQuery() {
        //berfungsi untuk menjalankan sebuah query update
        //jika bukan update maka akan menjalankan query tambah
        if (update == false) {
            query = "INSERT INTO `user`(username, password, role, nama_lengkap, alamat) VALUES (?,?,?,?,?)";
        //jika update maka akan menjalankan query update
        } else {
            query = "UPDATE `user` SET "
                    + "`username`=?,"
                    + "`password`=?,"
                    + "role =?,"
                    + "`nama_lengkap`=?,"
                    + "`alamat`= ? WHERE id_user = '" + memberId + "'";
        }
    }

    private void insert() {

        try {
            //query untuk tambah anggota
            String query = "INSERT INTO `user`(username, password, role, nama_lengkap, alamat) VALUES (?,?,?,?,?)";
            //samakan field query dengan parameter indexnya
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usernameField.getText());
            preparedStatement.setString(2, pwdField.getText());
            preparedStatement.setInt(3, roleCombobox.getValue());
            preparedStatement.setString(4, fullNameField.getText());
            preparedStatement.setString(5, addressField.getText());
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AddMemberController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //fungsi untuk mereset semua field menjadi kosong
    public void btnClear() {
        addressField.setText("");
        fullNameField.setText("");
        roleCombobox.setItems(null);
        pwdField.setText("");
        usernameField.setText("");
    }

    //method pertama kali djalankan
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roleCombobox.setItems(listRole);

    }
    //fungsi untuk menset field ketika update
    void setTextField(int id, String username, String pass, String fullname, String address) {

        memberId = id;
        usernameField.setText(username);
        pwdField.setText(pass);
        fullNameField.setText(fullname);
        addressField.setText(address);

    }

    void setUpdate(boolean b) {
        this.update = b;

    }
}
