import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Views/login.fxml"));
        primaryStage.setTitle("Blue Puzzles");
        primaryStage.setScene(new Scene(root, 700, 500));
        primaryStage.show();
        //test comment
    }

    public static void main(String[] args) {launch(args); }

}
