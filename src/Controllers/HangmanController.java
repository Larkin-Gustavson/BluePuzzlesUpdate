package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import misc.GameTimer;
import misc.MyLabel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class HangmanController implements Initializable {
    // Test
    @FXML
    AnchorPane hangmanAnchorPane;
    @FXML
    Pane hangmanBody;
    @FXML
    Pane guessWordPane;
    @FXML
    Pane wordPane;
    @FXML
    Pane winScreen;
    @FXML
    TextField guessWordField;
    @FXML
    Text livesText;
    @FXML
    Text winorlose;
    @FXML
    Text finishTime;
    @FXML
    Label gameTime;
    int lives = 6;
    String[] words = {"School", "Laundry", "House", "Gameboy Advanced", "Amazing", "Educational", "Puzzle",
            "Blue Puzzles", "Smoke", "Maple Syrup"};
    String word;
    GameTimer gt; // Timer
    @FXML
    private TextArea hintsTextArea;
    @FXML
    private Button hintButton;
    private final ArrayList<String> schoolHints = new ArrayList<>();
    private final ArrayList<String> laundryHints = new ArrayList<>();
    private final ArrayList<String> houseHints = new ArrayList<>();
    private final ArrayList<String> gameBoyAdvancedHints = new ArrayList<>();
    private final ArrayList<String> amazingHints = new ArrayList<>();
    private final ArrayList<String> educationalHints = new ArrayList<>();
    private final ArrayList<String> puzzleHints = new ArrayList<>();
    private final ArrayList<String> bluePuzzleHints = new ArrayList<>();
    private final ArrayList<String> smokeHints = new ArrayList<>();
    private final ArrayList<String> mapleSyrupHints = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* Select a rand word from the array */
        int pick = randomGenerator(words.length - 1);
        word = words[pick].toUpperCase(); // the word
        System.out.println(word);

        /* Positions the word */
        int position = 0;
        for (int i = 0; i < word.length(); i++) {
            MyLabel label = new MyLabel(word.charAt(i));
            label.setFont(new Font("System", 30));
            wordPane.getChildren().add(label);
            label.relocate(10.0 + position, 5);
            position += 40;
        }
        gt = new GameTimer(gameTime);
        gt.start();

        System.out.println(LoginController.user);

        hintsTextArea.setEditable(false); // ensuring that the user cannot type into the area where there is a textbox
        hintsTextArea.setWrapText(true); // wraps the text onto the next line
        hintsTextArea.deselect(); // making sure that the textbox is not selected when the game loads

    }


    int randomGenerator(int number) {
        return (int) Math.floor((Math.random() * number + 1));
    }


    public void doSomething(MouseEvent mouseEvent) {
        /* Changes to letter clicked */
        Text source = (Text) mouseEvent.getSource();
        System.out.println(source.getText());
        source.setDisable(true);
        Paint paint = Color.GREY;
        source.setFill(paint);
        char guess = source.getText().charAt(0); // guessed letter
        /* Changes to word */
        boolean guessRight = false;
        for (Node node : wordPane.getChildren()) {
            if (node instanceof MyLabel) {
                MyLabel label = (MyLabel) node;
                if (guess == label.getLetter()) {
                    label.showLetter();
                    guessRight = true;
                    gameWon();
                }
            }
        }
        if (!guessRight)
            wrongGuess();
    } // End of Method

    /* Determines if the game is won */
    public boolean gameWon() {
        for (Node node : wordPane.getChildren()) {
            if (node instanceof MyLabel) {
                if (((MyLabel) node).getText().equals("_"))
                    return false;
            }
        }
        showWinScreen(true);
        System.out.println("You won!");
        return true;
    }

    public void guessWord(ActionEvent actionEvent) {
        guessWordPane.setVisible(true);
    }

    public void enterGuess(ActionEvent actionEvent) {
        guessWordPane.setVisible(false);
        if (guessWordField.getText().toUpperCase().equals(word)) {
            System.out.println("You win!");
            showWinScreen(true);
            for (Node node : wordPane.getChildren()) {
                if (node instanceof MyLabel) {
                    ((MyLabel) node).showLetter();
                }
            }
        } else {
            wrongGuess();
            guessWordField.clear();
        }

    }

    public void showWinScreen(boolean win) {
        if (win)
            winorlose.setText("You Won!");
        else
            winorlose.setText("You lost!");
        winScreen.setVisible(true);
        finishTime.setText(gt.toString());
        gt.stop();
        try {
            Leaderboard.insertNewUser("HangmanLeaderboard", LoginController.user, gameTime.getText(),
                    HangmanDifficultyController.getDifficulty());
        } catch (SQLException e) {

        }
        for (Node node : hangmanAnchorPane.getChildren())
            if (node instanceof Button)
                node.setDisable(true);
    }

    public void wrongGuess() {
        lives--;
        livesText.setText(lives + "");
        if (lives == 0)
            showWinScreen(false);
        for (Node body : hangmanBody.getChildren()) {
            if (!body.isVisible()) {
                body.setVisible(true);
                break;
            }
        }
    }

    public void goBack(ActionEvent actionEvent) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/Views/hangmanDifficulty.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void playAgain(ActionEvent actionEvent) throws Exception {
        Parent page = FXMLLoader.load(getClass().getResource("/Views/hangman.fxml"));
        Scene scene = new Scene(page, 900, 600);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void hintButtonClicked(MouseEvent event) throws Exception {
        Random randomHintGenerator = new Random();

        putHintsInArrayLists(); // filling the ArrayLists with the hints in them for each of the possible words

        if (word.equalsIgnoreCase("School")) { // if the word randomly chosen is school do the following
            if (HangmanDifficultyController.getDifficulty().equals("easy")) { // if the difficulty is on easy, do the following
                for (String hint : schoolHints) {
                    hintsTextArea.appendText("- " + hint); // write the hint in the text area
                    hintsTextArea.appendText("\n");
                    System.out.println(hint);
                }

            } else if (HangmanDifficultyController.getDifficulty().equals("medium")) { // if the difficulty is on medium, do the following
                String firstHint = schoolHints.get(randomHintGenerator.nextInt(schoolHints.size())); // getting randomly two different hints from the hint ArrayList
                String secondHint = schoolHints.get(randomHintGenerator.nextInt(schoolHints.size())); // getting randomly two different hints from the hint ArrayList

                while (firstHint.equals(secondHint)) { // if the difficulty is on medium, do the following
                    firstHint = schoolHints.get(randomHintGenerator.nextInt(schoolHints.size())); // getting randomly two different hints from the hint ArrayList
                    secondHint = schoolHints.get(randomHintGenerator.nextInt(schoolHints.size())); // getting randomly two different hints from the hint ArrayList
                }

                hintsTextArea.appendText("- " + firstHint); // write the hint in the text area
                hintsTextArea.appendText("\n");
                hintsTextArea.appendText("- " + secondHint); // write the hint in the text area
                hintsTextArea.appendText("\n");

                System.out.println(firstHint);
                System.out.println(secondHint);
            } else if (HangmanDifficultyController.getDifficulty().equalsIgnoreCase("hard")) { // if the difficulty level is on hard do the following
                String hint = schoolHints.get(randomHintGenerator.nextInt(schoolHints.size())); // since it is on hard mode, only generate one random hint
                hintsTextArea.appendText("- " + hint); // write the hint in the text area
                hintsTextArea.appendText("\n");

                System.out.println(hint);
            }
        } else if (word.equalsIgnoreCase("Laundry")) { // if the word randomly chosen is laundry do the following
            if (HangmanDifficultyController.getDifficulty().equalsIgnoreCase("easy")) { /// if the difficulty is on easy, do the following
                for (String hint : laundryHints) {
                    hintsTextArea.appendText("- " + hint); // write the hint in the text area
                    hintsTextArea.appendText("\n");
                }
            } else if (HangmanDifficultyController.getDifficulty().equals("medium")) { // if the difficulty is on medium, do the following
                String firstHint = laundryHints.get(randomHintGenerator.nextInt(laundryHints.size())); // getting randomly two different hints from the hint ArrayList
                String secondHint = laundryHints.get(randomHintGenerator.nextInt(laundryHints.size())); // getting randomly two different hints from the hint ArrayList

                while (firstHint.equals(secondHint)) { // if the difficulty is on medium, do the following
                    firstHint = laundryHints.get(randomHintGenerator.nextInt(laundryHints.size())); // getting randomly two different hints from the hint ArrayList
                    secondHint = laundryHints.get(randomHintGenerator.nextInt(laundryHints.size())); // getting randomly two different hints from the hint ArrayList
                }

                hintsTextArea.appendText("- " + firstHint); // write the hint in the text area
                hintsTextArea.appendText("\n");
                hintsTextArea.appendText("- " + secondHint); // write the hint in the text area

                System.out.println(firstHint);
                System.out.println(secondHint);

            } else if (HangmanDifficultyController.getDifficulty().equals("hard")) { // if the difficulty level is on hard do the following
                String hint = laundryHints.get(randomHintGenerator.nextInt(laundryHints.size())); // since it is on hard mode, only generate one random hint
                hintsTextArea.appendText("- " + hint); // write the hint in the text area
                hintsTextArea.appendText("\n");

                System.out.println(hint);
            }
        } else if (word.equalsIgnoreCase("House")) { // if the word randomly chosen is house do the following
            if (HangmanDifficultyController.getDifficulty().equals("easy")) { // if the difficulty is on easy, do the following
                for (String hint : houseHints) {
                    hintsTextArea.appendText("- " + hint); // write the hint in the text area
                    hintsTextArea.appendText("\n");

                    System.out.println(hint);

                }
            } else if (HangmanDifficultyController.getDifficulty().equals("medium")) { // if the difficulty is on medium, do the following
                String firstHint = houseHints.get(randomHintGenerator.nextInt(houseHints.size())); // getting randomly two different hints from the hint ArrayList
                String secondHint = houseHints.get(randomHintGenerator.nextInt(houseHints.size())); // getting randomly two different hints from the hint ArrayList

                while (firstHint.equals(secondHint)) { // if the difficulty is on medium, do the following
                    firstHint = houseHints.get(randomHintGenerator.nextInt(houseHints.size())); // getting randomly two different hints from the hint ArrayList
                    secondHint = houseHints.get(randomHintGenerator.nextInt(houseHints.size())); // getting randomly two different hints from the hint ArrayList
                }

                hintsTextArea.appendText("- " + firstHint); // write the hint in the text area
                hintsTextArea.appendText("\n");
                hintsTextArea.appendText("- " + secondHint); // write the hint in the text area
                hintsTextArea.appendText("\n");

                System.out.println(firstHint);
                System.out.println(secondHint);

            } else if (HangmanDifficultyController.getDifficulty().equals("hard")) { // if the difficulty level is on hard do the following
                String hint = houseHints.get(randomHintGenerator.nextInt(houseHints.size())); // since it is on hard mode, only generate one random hint
                hintsTextArea.appendText("- " + hint); // write the hint in the text area
                hintsTextArea.appendText("\n");

                System.out.println(hint);
            }
        } else if (word.equalsIgnoreCase("Gameboy Advanced")) { // if the word randomly chosen is Gameboy Advanced do the following
            if (HangmanDifficultyController.getDifficulty().equals("easy")) { // if the difficulty is on easy, do the following
                for (String hint : gameBoyAdvancedHints) {
                    hintsTextArea.appendText("- " + hint); // write the hint in the text area
                    hintsTextArea.appendText("\n");

                    System.out.println(hint);
                }
            } else if (HangmanDifficultyController.getDifficulty().equals("medium")) { // if the difficulty is on medium, do the following
                String firstHint = gameBoyAdvancedHints.get(randomHintGenerator.nextInt(gameBoyAdvancedHints.size())); // getting randomly two different hints from the hint ArrayList
                String secondHint = gameBoyAdvancedHints.get(randomHintGenerator.nextInt(gameBoyAdvancedHints.size())); // getting randomly two different hints from the hint ArrayList

                while (firstHint.equals(secondHint)) { // if the difficulty is on medium, do the following
                    firstHint = gameBoyAdvancedHints.get(randomHintGenerator.nextInt(gameBoyAdvancedHints.size())); // getting randomly two different hints from the hint ArrayList
                    secondHint = gameBoyAdvancedHints.get(randomHintGenerator.nextInt(gameBoyAdvancedHints.size())); // getting randomly two different hints from the hint ArrayList
                }

                hintsTextArea.appendText("- " + firstHint); // write the hint in the text area
                hintsTextArea.appendText("\n");
                hintsTextArea.appendText("- " + secondHint); // write the hint in the text area
                hintsTextArea.appendText("\n");

                System.out.println(firstHint);
                System.out.println(secondHint);
            } else if (HangmanDifficultyController.getDifficulty().equals("hard")) { // if the difficulty level is on hard do the following
                String hint = gameBoyAdvancedHints.get(randomHintGenerator.nextInt(gameBoyAdvancedHints.size())); // since it is on hard mode, only generate one random hint

                hintsTextArea.appendText("- " + hint); // write the hint in the text area
                hintsTextArea.appendText("\n");

                System.out.println(hint);
            }
        } else if (word.equalsIgnoreCase("Amazing")) { // if the word randomly chosen is Amazing do the following
            if (HangmanDifficultyController.getDifficulty().equals("easy")) { // if the difficulty is on easy, do the following
                for (String hint : amazingHints) {
                    hintsTextArea.appendText("- " + hint); // write the hint in the text area
                    hintsTextArea.appendText("\n");

                    System.out.println(hint);
                }
            } else if (HangmanDifficultyController.getDifficulty().equals("medium")) { // if the difficulty is on medium, do the following
                String firstHint = amazingHints.get(randomHintGenerator.nextInt(amazingHints.size())); // getting randomly two different hints from the hint ArrayList
                String secondHint = amazingHints.get(randomHintGenerator.nextInt(amazingHints.size())); // getting randomly two different hints from the hint ArrayList

                while (firstHint.equals(secondHint)) { // if the difficulty is on medium, do the following
                    firstHint = amazingHints.get(randomHintGenerator.nextInt(amazingHints.size())); // getting randomly two different hints from the hint ArrayList
                    secondHint = amazingHints.get(randomHintGenerator.nextInt(amazingHints.size())); // getting randomly two different hints from the hint ArrayList
                }
                hintsTextArea.appendText("- " + firstHint); // write the hint in the text area
                hintsTextArea.appendText("\n");
                hintsTextArea.appendText("- " + secondHint); // write the hint in the text area
                hintsTextArea.appendText("\n");

                System.out.println(firstHint);
                System.out.println(secondHint);
            } else if (HangmanDifficultyController.getDifficulty().equals("hard")) { // if the difficulty level is on hard do the following
                String hint = amazingHints.get(randomHintGenerator.nextInt(amazingHints.size())); // since it is on hard mode, only generate one random hint

                hintsTextArea.appendText("- " + hint); // write the hint in the text area
                hintsTextArea.appendText("\n");

                System.out.println(hint);
            }
        } else if (word.equalsIgnoreCase("Educational")) { // if the word randomly chosen is educational do the following
            if (HangmanDifficultyController.getDifficulty().equals("easy")) { // if the difficulty is on easy, do the following
                for (String hint : educationalHints) {
                    hintsTextArea.appendText("- " + hint); // write the hint in the text area
                    hintsTextArea.appendText("\n");

                    System.out.println(hint);
                }
            } else if (HangmanDifficultyController.getDifficulty().equals("medium")) { // if the difficulty is on medium, do the following
                String firstHint = educationalHints.get(randomHintGenerator.nextInt(educationalHints.size())); // getting randomly two different hints from the hint ArrayList
                String secondHint = educationalHints.get(randomHintGenerator.nextInt(educationalHints.size())); // getting randomly two different hints from the hint ArrayList

                while (firstHint.equals(secondHint)) { // if the difficulty is on medium, do the following
                    firstHint = educationalHints.get(randomHintGenerator.nextInt(educationalHints.size())); // getting randomly two different hints from the hint ArrayList
                    secondHint = educationalHints.get(randomHintGenerator.nextInt(educationalHints.size())); // getting randomly two different hints from the hint ArrayList
                }
                hintsTextArea.appendText("- " + firstHint); // write the hint in the text area
                hintsTextArea.appendText("\n");
                hintsTextArea.appendText("- " + secondHint); // write the hint in the text area
                hintsTextArea.appendText("\n");

                System.out.println(firstHint);

                System.out.println(secondHint);
            } else if (HangmanDifficultyController.getDifficulty().equals("hard")) { // if the difficulty level is on hard do the following
                String hint = educationalHints.get(randomHintGenerator.nextInt(educationalHints.size())); // since it is on hard mode, only generate one random hint

                hintsTextArea.appendText("- " + hint); // write the hint in the text area
                hintsTextArea.appendText("\n");

                System.out.println(hint);
            }
        } else if (word.equalsIgnoreCase("Puzzle")) { // if the word randomly chosen is puzzle do the following
            if (HangmanDifficultyController.getDifficulty().equals("easy")) { // if the difficulty is on easy, do the following
                for (String hint : puzzleHints) {
                    hintsTextArea.appendText("- " + hint); // write the hint in the text area
                    hintsTextArea.appendText("\n");

                    System.out.println(hint);
                }
            } else if (HangmanDifficultyController.getDifficulty().equals("medium")) { // if the difficulty is on medium, do the following
                String firstHint = puzzleHints.get(randomHintGenerator.nextInt(puzzleHints.size())); // getting randomly two different hints from the hint ArrayList
                String secondHint = puzzleHints.get(randomHintGenerator.nextInt(puzzleHints.size())); // getting randomly two different hints from the hint ArrayList

                while (firstHint.equals(secondHint)) { // if the difficulty is on medium, do the following
                    firstHint = puzzleHints.get(randomHintGenerator.nextInt(puzzleHints.size())); // getting randomly two different hints from the hint ArrayList
                    secondHint = puzzleHints.get(randomHintGenerator.nextInt(puzzleHints.size())); // getting randomly two different hints from the hint ArrayList
                }

                hintsTextArea.appendText("- " + firstHint); // write the hint in the text area
                hintsTextArea.appendText("\n");
                hintsTextArea.appendText("- " + secondHint); // write the hint in the text area
                hintsTextArea.appendText("\n");

                System.out.println(firstHint);
                System.out.println(secondHint);
            } else if (HangmanDifficultyController.getDifficulty().equals("hard")) { // if the difficulty level is on hard do the following
                String hint = puzzleHints.get(randomHintGenerator.nextInt(puzzleHints.size())); // since it is on hard mode, only generate one random hint

                hintsTextArea.appendText("- " + hint); // write the hint in the text area
                hintsTextArea.appendText("\n");

                System.out.println(hint);
            }
        } else if (word.equalsIgnoreCase("Blue Puzzles")) { // if the word randomly chosen is Blue Puzzles do the following
            if (HangmanDifficultyController.getDifficulty().equals("easy")) { // if the difficulty is on easy, do the following
                for (String hint : bluePuzzleHints) {
                    hintsTextArea.appendText("- " + hint); // write the hint in the text area
                    hintsTextArea.appendText("\n");

                    System.out.println(hint);
                }
            } else if (HangmanDifficultyController.getDifficulty().equals("medium")) { // if the difficulty is on medium, do the following
                String firstHint = bluePuzzleHints.get(randomHintGenerator.nextInt(bluePuzzleHints.size())); // getting randomly two different hints from the hint ArrayList
                String secondHint = bluePuzzleHints.get(randomHintGenerator.nextInt(bluePuzzleHints.size())); // getting randomly two different hints from the hint ArrayList

                while (firstHint.equals(secondHint)) { // if the difficulty is on medium, do the following
                    firstHint = bluePuzzleHints.get(randomHintGenerator.nextInt(bluePuzzleHints.size())); // getting randomly two different hints from the hint ArrayList
                    secondHint = bluePuzzleHints.get(randomHintGenerator.nextInt(bluePuzzleHints.size())); // getting randomly two different hints from the hint ArrayList
                }

                hintsTextArea.appendText("- " + firstHint); // write the hint in the text area
                hintsTextArea.appendText("\n");
                hintsTextArea.appendText("- " + secondHint); // write the hint in the text area
                hintsTextArea.appendText("\n");

                System.out.println(firstHint);
                System.out.println(secondHint);
            } else if (HangmanDifficultyController.getDifficulty().equals("hard")) { // if the difficulty level is on hard do the following
                String hint = bluePuzzleHints.get(randomHintGenerator.nextInt(bluePuzzleHints.size())); // since it is on hard mode, only generate one random hint

                hintsTextArea.appendText("- " + hint); // write the hint in the text area
                hintsTextArea.appendText("\n");

                System.out.println(hint);
            }
        } else if (word.equalsIgnoreCase("Smoke")) { // if the word randomly chosen is smoke do the following
            if (HangmanDifficultyController.getDifficulty().equals("easy")) { // if the difficulty is on easy, do the following
                for (String hint : smokeHints) {
                    hintsTextArea.appendText("- " + hint); // write the hint in the text area
                    hintsTextArea.appendText("\n");

                    System.out.println(hint);
                }
            } else if (HangmanDifficultyController.getDifficulty().equals("medium")) { // if the difficulty is on medium, do the following
                String firstHint = smokeHints.get(randomHintGenerator.nextInt(smokeHints.size())); // getting randomly two different hints from the hint ArrayList
                String secondHint = smokeHints.get(randomHintGenerator.nextInt(smokeHints.size())); // getting randomly two different hints from the hint ArrayList
                while (firstHint.equals(secondHint)) {
                    firstHint = smokeHints.get(randomHintGenerator.nextInt(smokeHints.size())); // getting randomly two different hints from the hint ArrayList
                    secondHint = smokeHints.get(randomHintGenerator.nextInt(smokeHints.size())); // getting randomly two different hints from the hint ArrayList
                }

                hintsTextArea.appendText("- " + firstHint); // write the hint in the text area
                hintsTextArea.appendText("\n");
                hintsTextArea.appendText("- " + secondHint); // write the hint in the text area
                hintsTextArea.appendText("\n");

                System.out.println(firstHint);
                System.out.println(secondHint);
            } else if (HangmanDifficultyController.getDifficulty().equals("hard")) { // if the difficulty level is on hard do the following
                String hint = smokeHints.get(randomHintGenerator.nextInt(smokeHints.size())); // since it is on hard mode, only generate one random hint

                hintsTextArea.appendText("- " + hint); // write the hint in the text area
                hintsTextArea.appendText("\n");

                System.out.println(hint);
            }
        } else if (word.equalsIgnoreCase("Maple Syrup")) { // if the word randomly chosen is maple syrup do the following
            if (HangmanDifficultyController.getDifficulty().equals("easy")) { // if the difficulty is on easy, do the following
                for (String hint : mapleSyrupHints) {
                    hintsTextArea.appendText("- " + hint); // write the hint in the text area
                    hintsTextArea.appendText("\n");


                    System.out.println(hint);
                }
            } else if (HangmanDifficultyController.getDifficulty().equals("medium")) { // if the difficulty is on medium, do the following
                String firstHint = mapleSyrupHints.get(randomHintGenerator.nextInt(mapleSyrupHints.size())); // getting randomly two different hints from the hint ArrayList
                String secondHint = mapleSyrupHints.get(randomHintGenerator.nextInt(mapleSyrupHints.size())); // getting randomly two different hints from the hint ArrayList

                while (firstHint.equals(secondHint)) { // if the hints are the same, keep trying to get two unique random hints
                    firstHint = mapleSyrupHints.get(randomHintGenerator.nextInt(mapleSyrupHints.size())); // getting randomly two different hints from the hint ArrayList
                    secondHint = mapleSyrupHints.get(randomHintGenerator.nextInt(mapleSyrupHints.size())); // getting randomly two different hints from the hint ArrayList
                }

                hintsTextArea.appendText("- " + firstHint); // write the hint in the text area
                hintsTextArea.appendText("\n");
                hintsTextArea.appendText("- " + secondHint); // write the hint in the text area
                hintsTextArea.appendText("\n");

                System.out.println(firstHint);
                System.out.println(secondHint);
            } else if (HangmanDifficultyController.getDifficulty().equals("hard")) { // if the difficulty level is on hard do the following
                String hint = mapleSyrupHints.get(randomHintGenerator.nextInt(mapleSyrupHints.size())); // since it is on hard mode, only generate one random hint

                hintsTextArea.appendText("- " + hint); // write the hint in the text area
                hintsTextArea.appendText("\n");

                System.out.println(hint);
            }

        }
        hintButton.setDisable(true); // disable the hint button once it has been clicked, to prevent users from both getting more hints then they were supposed to and to make sure the text area looks readable
    }

    /**
     * A method to put all of the hints, into the appropriate ArrayList for the word
     */
    private void putHintsInArrayLists() {
        // Adding school hints
        schoolHints.add("A place to learn.");
        schoolHints.add("A place where teachers teach.");
        schoolHints.add("A place where students are present.");

        // Adding laundry hints
        laundryHints.add("You clean clothes here.");
        laundryHints.add("This is in a laundromat.");
        laundryHints.add("A annoying chore that no one likes to do.");

        // Adding house hints
        houseHints.add("People live in this.");
        houseHints.add("You can buy or sell this.");
        houseHints.add("There are rooms in this.");

        // Adding Gameboy Advanced Hints
        gameBoyAdvancedHints.add("A portable video game system.");
        gameBoyAdvancedHints.add("A portable video game system made by Nintendo.");
        gameBoyAdvancedHints.add("Mario Kart is a game on this system.");

        // Adding hints for the word amazing
        amazingHints.add("A word to describe how great something is.");
        amazingHints.add("A synonym of the word is superb.");
        amazingHints.add("An antonym of the word is awful.");

        // Adding Educational hints
        educationalHints.add("Schools are a part of this system.");
        educationalHints.add("It is used in the context of learning.");
        educationalHints.add("Sesame Street is this word for kids.");

        // Adding Puzzle hints
        puzzleHints.add("You're playing one these right now.");
        puzzleHints.add("Newspapers contain these.");
        puzzleHints.add("These things help your brain and make it less likely for you to get Alzheimer's.");

        // Adding Blue Puzzle hints
        bluePuzzleHints.add("The name of the organization who created the game.");
        bluePuzzleHints.add("In this word, there is a color.");
        bluePuzzleHints.add("In this word, there is a type of activity.");

        // Adding Smoke hints
        smokeHints.add("When there is this, there is fire.");
        smokeHints.add("You do this when using a cigarette.");
        smokeHints.add("When this stains walls, it turns yellow.");

        // Adding Maple Syrup hints
        mapleSyrupHints.add("You put this on waffles.");
        mapleSyrupHints.add("You put this on french toast.");
        mapleSyrupHints.add("This has sugar in it.");
    }
}



