package Controllers;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
// Test
public class LeaderboardsController implements Initializable {

    @FXML
    AnchorPane mainAnchor;
    @FXML
    TableView<Record> JigsawTable, hangmanTable, tentsTable;
    @FXML
    TableColumn<Record, String> jigsawUser, jigsawDifficulty, jigsawTime, hangmanUser, hangmanDifficulty,
            hangmanTime, tentsUser, tentsDifficulty, tentsTime;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /* Jigsaw */
        jigsawUser.setCellValueFactory(new PropertyValueFactory<Record, String>("userName"));
        jigsawDifficulty.setCellValueFactory(new PropertyValueFactory<Record, String>("difficulty"));
        jigsawTime.setCellValueFactory(new PropertyValueFactory<Record, String>("time"));
        /* Hangman */
        hangmanUser.setCellValueFactory(new PropertyValueFactory<Record, String>("userName"));
        hangmanDifficulty.setCellValueFactory(new PropertyValueFactory<Record, String>("difficulty"));
        hangmanTime.setCellValueFactory(new PropertyValueFactory<Record, String>("time"));
        /* Tents */
        tentsUser.setCellValueFactory(new PropertyValueFactory<Record, String>("userName"));
        tentsDifficulty.setCellValueFactory(new PropertyValueFactory<Record, String>("difficulty"));
        tentsTime.setCellValueFactory(new PropertyValueFactory<Record, String>("time"));
        try {
            JigsawTable.setItems(getRecords("JigsawLeaderboard"));
            hangmanTable.setItems((getRecords("HangmanLeaderboard")));
            tentsTable.setItems((getRecords("TentsLeaderboard")));
        } catch (SQLException e) {

        }
    }

    public ObservableList<Record> getRecords(String game) throws SQLException {
        ObservableList<Record> records = FXCollections.observableArrayList();
        ArrayList<String> record = Leaderboard.getAllRecords(game);
        for (int i = 0; i < Leaderboard.getAllRecords(game).size(); i += 3) {
            Record jr = new Record(record.get(i), record.get(i + 1), record.get(i + 2));
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