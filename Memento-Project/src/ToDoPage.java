import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class ToDoPage {
    private StackPane layout;
    

    public ToDoPage() {
        // To-Do Page content
        layout = new StackPane();
        layout.getChildren().add(new Text("This is your To-Do List!"));
        layout.setStyle("-fx-background-color:  #FFFFFF;");
    }

    public StackPane getLayout() {
        return layout;
    }
}
