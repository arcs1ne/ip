package tangent.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import tangent.Tangent;

/**
 * Controller for the main GUI.
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

    private Tangent tangent;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/ena.jpg"));
    private final Image tangentImage = new Image(this.getClass().getResourceAsStream("/images/mizuki.jpg"));

    /** Scrolls the ScrollPane to the bottom whenever the dialog box is resized. */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Tangent instance. */
    public void setTangent(Tangent t){
        tangent = t;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = tangent.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getTangentDialog(response, tangentImage)
        );
        userInput.clear();
    }
}
