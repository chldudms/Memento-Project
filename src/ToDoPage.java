import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public class ToDoPage {
    private StackPane layout;
    private VBox checklistContainer; // To hold the checklist items
    private Button addButton;         // Button to add checklist items

    public ToDoPage() {
        // Initialize layout and container for checklists
        layout = new StackPane();
        checklistContainer = new VBox(10); // 10 pixels spacing between items

        // Create a button to add checklist items
        addButton = new Button("+");
        addButton.setOnAction(e -> addChecklistItem()); // Add action for button click

        // Add button and checklist container to the main layout
        VBox mainLayout = new VBox(10); // Use VBox for vertical layout
        mainLayout.getChildren().addAll(checklistContainer, addButton); // Add checklist and button
        layout.getChildren().add(mainLayout); // Set the main layout
        layout.setStyle("-fx-background-color: #FFFFFF;"); // Set background color
    }

    // Method to add a checklist item
    private void addChecklistItem() {
        CheckBox checklistItem = new CheckBox("Checklist Item " + (checklistContainer.getChildren().size() + 1));
        checklistContainer.getChildren().add(checklistItem); // Add the checkbox to the container
    }

    public StackPane getLayout() {
        return layout;
    }
}
