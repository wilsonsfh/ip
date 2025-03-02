package caviar.gui;

import caviar.Caviar;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.jpg"));
    private final Image caviarImage = new Image(this.getClass().getResourceAsStream("/images/Caviar.png"));

    /**
     * Initializes the controller after its root element has been completely processed.
     *
     * <p>This method sets up the necessary bindings between the scroll pane and the dialog container
     * and displays a welcome message to the user upon application startup.</p>
     */
    @FXML
    public void initialize() {
        bindScrollPaneToDialogContainer();
        displayWelcomeMessage();
    }

    /**
     * Sets the Caviar instance to interact with.
     *
     * @param c The chatbot instance.
     */
    public void setCaviar(Caviar c) {
        this.caviar = c;
    }

    @FXML
    private void handleUserInput() {
        String input = getUserInput();
        String response = caviar.getResponseFromCaviar(input);

        displayUserMessage(input);

        if (isBye(response)) {
            displayExitMessage();
            closeWindow();
            return;
        }

        displayCaviarMessage(response);
        clearUserInput();
    }

    private void displayWelcomeMessage() {
        String welcomeMessage = "Hello! I'm Caviar. Roe!\nWhat can I do for you?";
        displayCaviarMessage(welcomeMessage);
    }

    /**
     * Binds the scrollPane to follow dialogContainer height.
     */
    private void bindScrollPaneToDialogContainer() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    private String getUserInput() {
        return userInput.getText().trim();
    }

    private boolean isBye(String response) {
        return "bye".equalsIgnoreCase(response);
    }

    private void displayUserMessage(String message) {
        addUserDialog(message, userImage);
    }

    private void displayCaviarMessage(String message) {
        addCaviarDialog(message, caviarImage);
    }

    private void displayExitMessage() {
        displayCaviarMessage("Roe. Hope to see you again soon!");
    }

    private void clearUserInput() {
        userInput.clear();
    }

    private void addUserDialog(String message, Image image) {
        dialogContainer.getChildren().add(DialogBox.getUserDialog(message, image));
    }

    private void addCaviarDialog(String message, Image image) {
        dialogContainer.getChildren().add(DialogBox.getCaviarDialog(message, image));
    }

    private void closeWindow() {
        Stage stage = (Stage) sendButton.getScene().getWindow();
        stage.close();
    }
}
