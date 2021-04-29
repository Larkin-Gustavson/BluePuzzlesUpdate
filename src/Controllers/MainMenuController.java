package Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    @FXML
    AnchorPane anchorPaneMM;
    @FXML
    Button leaderboardButton;
    @FXML
    ImageView leftImage, rightImage;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void play(ActionEvent event) throws Exception{
        Parent page = FXMLLoader.load(getClass().getResource("/Views/game_select.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void test(MouseEvent mouseEvent) {

    }

    public void showImage(MouseEvent mouseEvent) {
        leftImage.setVisible(true);
        rightImage.setVisible(true);
    }

    public void hideImage(MouseEvent mouseEvent) {
        leftImage.setVisible(false);
        rightImage.setVisible(false);
    }

    @FXML
    void closeApp(ActionEvent event) {
        Platform.exit();
    }

    public void logOut(ActionEvent actionEvent) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/Views/login.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void showLeaderboard(ActionEvent actionEvent) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/Views/leaderboards.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
