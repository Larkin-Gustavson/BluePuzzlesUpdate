import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.*;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/login.fxml"));
        primaryStage.setTitle("Blue Puzzles");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("https://image.flaticon.com/icons/png/512/417/417752.png"));
    }

    public static void main(String[] args) {launch(args); }


}
