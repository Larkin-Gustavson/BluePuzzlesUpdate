package Controllers;

import DB.JigsawLeaderboard;
import DB.Record;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LeaderboardsController implements Initializable {

    @FXML
    TableView<Record> jigsawTable, hangmanTable;
    @FXML
    TableColumn<Record, String> jigsawUser, jigsawDifficulty, jigsawTime, hangmanUser, hangmanDifficulty,
            hangmanTime;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* Jigsaw */
        jigsawUser.setCellValueFactory(new PropertyValueFactory<Record, String>("userName"));
        jigsawDifficulty.setCellValueFactory(new PropertyValueFactory<Record, String>("difficulty"));
        jigsawTime.setCellValueFactory(new PropertyValueFactory<Record, String>("time"));
        /* Hangman */
        jigsawUser.setCellValueFactory(new PropertyValueFactory<Record, String>("userName"));
        jigsawDifficulty.setCellValueFactory(new PropertyValueFactory<Record, String>("difficulty"));
        jigsawTime.setCellValueFactory(new PropertyValueFactory<Record, String>("time"));
        try {
            jigsawTable.setItems(getRecords());

        } catch (SQLException e) {

        }
    }

    public ObservableList<Record> getRecords() throws SQLException {
        ObservableList<Record> records = FXCollections.observableArrayList();
        ArrayList<String> record = JigsawLeaderboard.getAllRecords();
        Record jr = null;
        for (int i = 0; i < JigsawLeaderboard.getAllRecords().size(); i += 3) {
            jr  = new Record(record.get(i), record.get(i + 1), record.get(i + 2));
            records.add(jr);
        }


        return records;
    }

    public void goBack(ActionEvent actionEvent) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/Views/mainmenu.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
