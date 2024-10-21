
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class NavigationBar {
    private HBox navigationBar;
    private Main mainApp;

    // Constructor now takes Main as a parameter
    public NavigationBar(StackPane homePageContent, StackPane toDoPageContent, StackPane loginPageContent, Main mainApp) {
        this.mainApp = mainApp;  // Save reference to the Main class

        // Bottom Navigation Bar Sections (Home, To-Do, Login)
        navigationBar = new HBox();
        navigationBar.setPadding(new Insets(10));
        navigationBar.setSpacing(20);
        navigationBar.setStyle("-fx-background-color:  #FFCDE1;");  // Light pink color
        navigationBar.setAlignment(Pos.CENTER);

        // Create navigation sections
        StackPane homeSection = createNavSection("🏠", homePageContent);
        StackPane toDoSection = createNavSection("📝", toDoPageContent);
        StackPane loginSection = createNavSection("👤", loginPageContent);

        // Add sections to the navigation bar
        navigationBar.getChildren().addAll(homeSection, toDoSection, loginSection);
    }

    // Method to create a navigation section
    private StackPane createNavSection(String title, StackPane pageContent) {
        StackPane section = new StackPane();
        section.setPadding(new Insets(10));
        section.setStyle("-fx-background-color: #FFCDE1; -fx-border-color: transparent; -fx-border-width: 5px;");
        section.setMinWidth(100);
        section.setMaxWidth(150);
        section.setOnMouseClicked(e -> mainApp.getMainLayout().setCenter(pageContent)); // Update center content on click

        // 하단바 텍스트
        Text text = new Text(title);
        text.setFont(Font.font(30));
        text.setFill(Color.WHITE);

        section.getChildren().add(text);
        return section;
    }

    public HBox getLayout() {
        return navigationBar;
    }
}
