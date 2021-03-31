package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class JigsawDifficultyController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void playEasy(ActionEvent actionEvent) throws Exception {
        String[] easyGames = {"/Views/jigsaw/easy/jigsaw.fxml"};
        int rand = randomGenerator(1) - 1;
        Parent page = FXMLLoader.load(getClass().getResource(easyGames[rand]));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void playMedium(ActionEvent actionEvent) throws Exception {
        String[] easyGames = {"/Views/jigsaw/medium/jigsawShyGuy.fxml"};
        int rand = randomGenerator(1) - 1;
        Parent page = FXMLLoader.load(getClass().getResource(easyGames[rand]));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void playHard(ActionEvent actionEvent) {
    }

    int randomGenerator(int num) {
        return (int) Math.floor((Math.random() * num + 1));
    }
}
