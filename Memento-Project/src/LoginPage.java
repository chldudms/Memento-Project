import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class LoginPage {
    private StackPane layout;

    public LoginPage() {
        // Login Page content
        layout = new StackPane();
        layout.getChildren().add(new Text("Please log in!"));
        layout.setStyle("-fx-background-color:  #FFFFFF;");
    }

    public StackPane getLayout() {
        return layout;
    }
}
