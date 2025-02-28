package caviar.gui;

import caviar.Caviar;
import caviar.exception.CaviarException;
import caviar.parser.Parser;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI of Caviar.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Caviar caviar;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.jpg"));
    private Image caviarImage = new Image(this.getClass().getResourceAsStream("/images/Caviar.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Sets the Caviar instance to interact with.
     *
     * @param caviar The chatbot instance.
     */
    public void setCaviar(Caviar caviar) {
        this.caviar = caviar;
    }
    /**
     * Handles user input when "Enter" or "Send" is pressed.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();

        String response = caviar.getResponseFromCaviar(input);
        dialogContainer.getChildren().addAll(
            DialogBox.getUserDialog(input, userImage),
            DialogBox.getCaviarDialog(response, caviarImage)
        );
        userInput.clear();
    }

}
