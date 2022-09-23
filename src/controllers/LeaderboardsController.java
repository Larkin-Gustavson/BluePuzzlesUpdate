package controllers;

import db.Record;
import db.TimeAttackLeaderboard;
import db.TimeAttackRecord;
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
import java.util.List;
import java.util.ResourceBundle;

public class LeaderboardsController implements Initializable {
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private TableView<Record> jigsawTable;
    @FXML
    private TableView<Record> hangmanTable;
    @FXML
    private TableView<Record> tentsTable;
    @FXML
    private TableView<Record> memoryTable;
    @FXML
    private TableView<TimeAttackRecord> timeAttackTable;
    @FXML
    private TableColumn<Record, String> jigsawUser;
    @FXML
    private TableColumn<Record, String> jigsawDifficulty;
    @FXML
    private TableColumn<Record, String> jigsawTime;
    @FXML
    private TableColumn<Record, String> hangmanUser;
    @FXML
    private TableColumn<Record, String> hangmanDifficulty;
    @FXML
    private TableColumn<Record, String> hangmanTime;
    @FXML
    private TableColumn<Record, String> tentsUser;
    @FXML
    private TableColumn<Record, String> tentsDifficulty;
    @FXML
    private TableColumn<Record, String> tentsTime;
    @FXML
    private TableColumn<Record, String> memoryUser;
    @FXML
    private TableColumn<Record, String> memoryDifficulty;
    @FXML
    private TableColumn<Record, String> memoryTime;
    @FXML
    private TableColumn<TimeAttackRecord, String> timeAttackUsername;
    @FXML
    private TableColumn<TimeAttackRecord, String> timeAttackPoints;
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

        /* Memory */
        memoryUser.setCellValueFactory(new PropertyValueFactory<Record, String>("userName"));
        memoryDifficulty.setCellValueFactory(new PropertyValueFactory<Record, String>("difficulty"));
        memoryTime.setCellValueFactory(new PropertyValueFactory<Record, String>("time"));

        /* Time Attack */
        timeAttackUsername.setCellValueFactory(new PropertyValueFactory<TimeAttackRecord, String>("userName"));
        timeAttackPoints.setCellValueFactory(new PropertyValueFactory<TimeAttackRecord, String>("points"));
        try {
            jigsawTable.setItems(getRecords("JigsawLeaderboard"));
            hangmanTable.setItems((getRecords("HangmanLeaderboard")));
            tentsTable.setItems((getRecords("TentsLeaderboard")));
            memoryTable.setItems(getRecords("MemoryLeaderboard"));
            timeAttackTable.setItems(getTimeAttackRecords());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Record> getRecords(String game) throws SQLException {
        ObservableList<Record> records = FXCollections.observableArrayList();
        List<String> record = Leaderboard.getAllRecords(game);
        for (int i = 0; i < Leaderboard.getAllRecords(game).size(); i += 3) {
            Record jr = new Record(record.get(i), record.get(i + 1), record.get(i + 2));
            records.add(jr);
        }

        return records;
    }

    public ObservableList<TimeAttackRecord> getTimeAttackRecords() throws SQLException {
        ObservableList<TimeAttackRecord> records = FXCollections.observableArrayList();
        List<String> allRecords = TimeAttackLeaderboard.getAllRecords();
        for (int i = 0; i < TimeAttackLeaderboard.getAllRecords().size(); i += 2) {
            TimeAttackRecord jr = new TimeAttackRecord(allRecords.get(i), Integer.parseInt(allRecords.get(i + 1)));
            records.add(jr);
        }

        return records;
    }

    public void goBack(ActionEvent actionEvent) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/views/mainmenu.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}