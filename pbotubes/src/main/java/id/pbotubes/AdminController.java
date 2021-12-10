package id.pbotubes;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import id.pbotubes.models.Member;
import id.pbotubes.utils.ConnectionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AdminController implements Initializable {
    @FXML
    private Button btnRefresh;
    @FXML
    private Button btnAddMember;
    @FXML
    private TableView<Member> memberTableView;
    @FXML
    private TableColumn<Member, String> idCol;
    @FXML
    private TableColumn<Member, String> userCol;
    @FXML
    private TableColumn<Member, String> fullNameCol;
    @FXML
    private TableColumn<Member, String> addressCol;
    @FXML
    private TableColumn<Member, String> editCol;
    @FXML
    private Label usernameLabel;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Menu menuBarUser;

    String query = null;
    Connection connection = null ;
    PreparedStatement preparedStatement = null ;
    ResultSet resultSet = null ;
    Member member = null;



    //fungsi untuk mendapatkan list dari Class Member
    ObservableList<Member>  memberList = FXCollections.observableArrayList();

    //jalankan method pertama kali
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }

    //method meload data
    private void loadData() {
        ConnectionDB connectionDB = new ConnectionDB();
        AtomicReference<Connection> connection = new AtomicReference<>(connectionDB.getConnection());

        refreshTable();
        //buat nama header tabel dari namafx dari menu-admin.fxml
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        userCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        fullNameCol.setCellValueFactory(new PropertyValueFactory<>("alamat"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("namaLengkap"));

        //tambah cell untuk button edit dan hapus
        Callback<TableColumn<Member, String>, TableCell<Member, String>> cellFoctory = (TableColumn<Member, String> param) -> {
            // buat cell berisi button
            final TableCell<Member, String> cell = new TableCell<Member, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        Button deleteIcon = new Button();
                        Button editIcon = new Button();

                        deleteIcon.setStyle(
                                "-fx-background-color: #ff0000;"

                        );
                        editIcon.setStyle(
                                "-fx-background-color: #ffea00;"
                        );
                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                            try {
                                //hapus data
                                member = memberTableView.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM `user` WHERE id_user  = "+member.getId();
                                ConnectionDB connectionDB = new ConnectionDB();
                                Connection connection = connectionDB.getConnection();
                                preparedStatement = connection.prepareStatement(query);
                                preparedStatement.execute();
                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {
                            //edit data -> masih ada bug
                            member = memberTableView.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader ();
                            loader.setLocation(getClass().getResource("admin/add-member.fxml"));
                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            AddMemberController addMemberController = loader.getController();
                            addMemberController.setUpdate(true);
                            addMemberController.setTextField(member.getId(), member.getUsername(),
                                    member.getPassword(),member.getNamaLengkap(),member.getAlamat());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();




                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        editCol.setCellFactory(cellFoctory);
        memberTableView.setItems(memberList);
    }


    //fungsi untuk menampilkan waktu dan nama user yang login
    public void displayName(String username) {
        HelloMessage helloMessage = new HelloMessage(usernameLabel, username);
        helloMessage.getMessageDate(usernameLabel);

        HelloMessage helloMessage1 = new HelloMessage(menuBarUser, username);
        helloMessage1.getMessageDate2(username, menuBarUser);

    }

    public void logOut(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("hello-view.fxml"));
        Parent ReportManager = loader.load();
        Scene ReportManagerScene = new Scene(ReportManager);

        Stage window = (Stage) anchorPane.getScene().getWindow();

        window.setScene(ReportManagerScene);

        window.show();
    }


    //fungsi untuk me refresh data table
    public void refreshTable() {
        memberList.clear();
        query = "SELECT * FROM user";
        try {
            ConnectionDB connectionDB = new ConnectionDB();
            Connection connection = connectionDB.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                memberList.add(new Member(
                        resultSet.getInt("id_user"),
                        resultSet.getString("username"),
                        resultSet.getString("nama_lengkap"),
                        resultSet.getString("alamat")));
                memberTableView.setItems(memberList);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //fungsi untuk menambah data anggota
    public void addMemberView(MouseEvent mouseEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("admin/add-member.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
