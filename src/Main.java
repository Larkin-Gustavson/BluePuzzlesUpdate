import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
// Test
public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Views/login.fxml")); // load the login area
        primaryStage.getIcons().add(new Image("https://image.flaticon.com/icons/png/512/417/417752.png")); // get this favicon and show it for the entire app
        primaryStage.setTitle("Blue Puzzles"); // set the title of the window of the game
        primaryStage.setScene(new Scene(root, 900, 600)); // the dimensions of the window
        primaryStage.setResizable(false); // make it so that users cannot resize the screen of the game
        primaryStage.show(); // show what is on stage
    }
}
