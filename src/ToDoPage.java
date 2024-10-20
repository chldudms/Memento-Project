import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.geometry.Pos;

public class ToDoPage {
    private StackPane layout;
    private VBox checklistContainer; // To hold the checklist items
    private TextField inputField;    // Input field for new tasks
    private Button addButton;        // Button to add checklist items

    public ToDoPage() {
        // Initialize layout and container for checklists
        layout = new StackPane();
        checklistContainer = new VBox(10); // 10 pixels spacing between items
        checklistContainer.setAlignment(Pos.TOP_CENTER); // Align items to the top

        // Create a text field for inputting new tasks
        inputField = new TextField();
        inputField.setPromptText("Enter a new task...");
        inputField.setMaxWidth(300);

        // Create a button to add checklist items
        addButton = new Button("Add Task");
        addButton.setOnAction(e -> addChecklistItem()); // Add action for button click

        // Add input field, button, and checklist container to the main layout
        VBox mainLayout = new VBox(10); // Use VBox for vertical layout
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(inputField, addButton, checklistContainer); // Add input, button, and checklist
        layout.getChildren().add(mainLayout); // Set the main layout
        layout.setStyle("-fx-background-color: #f0f8ff; -fx-padding: 20px;"); // Set background color and padding
    }

    // Method to add a checklist item
    private void addChecklistItem() {
        String taskText = inputField.getText();
        if (!taskText.isEmpty()) {
            // Create a new HBox for the task and delete button
            HBox taskItem = new HBox(10); // Horizontal layout for task + delete button
            taskItem.setAlignment(Pos.CENTER_LEFT);

            // Create a checkbox for the task
            CheckBox checklistItem = new CheckBox(taskText);
            checklistItem.setStyle("-fx-font-size: 14px;");

            // Create a delete button for the task
            Button deleteButton = new Button("❌");
            deleteButton.setStyle("-fx-background-color: #ff6347; -fx-text-fill: white;");
            deleteButton.setOnAction(e -> checklistContainer.getChildren().remove(taskItem)); // Remove the task when clicked

            // Add the checkbox and delete button to the task item
            taskItem.getChildren().addAll(checklistItem, deleteButton);

            // Add the task item to the checklist container
            checklistContainer.getChildren().add(taskItem);

            // Clear the input field after adding the task
            inputField.clear();
        }
    }

    public StackPane getLayout() {
        return layout;
    }
}
