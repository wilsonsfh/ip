package caviar.gui;

import java.io.IOException;
import caviar.Caviar;
import caviar.exception.CaviarException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Caviar using JavaFX.
 */
public class Main extends Application {

    private Caviar caviar;

    @Override
    public void start(Stage stage) throws CaviarException {
        try {
            // Initialize your chatbot with a data file
            caviar = new Caviar("data/tasks.txt");

            // Load FXML
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();

            // Set the scene
            Scene scene = new Scene(ap);
            stage.setScene(scene);

            // Inject the chatbot into the MainWindow controller
            MainWindow controller = fxmlLoader.getController();
            controller.setCaviar(caviar);

            stage.setTitle("Caviar Chatbot");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
