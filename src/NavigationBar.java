import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class NavigationBar {
    private HBox navigationBar;
    private Main mainApp;

    // Constructor now takes Main as a parameter
    public NavigationBar(StackPane homepage, StackPane toDopage, StackPane loginpage, Main mainApp) {
        this.mainApp = mainApp;  // Save reference to the Main class

        // Bottom Navigation Bar Sections (Home, To-Do, Login)
        navigationBar = new HBox();
        navigationBar.setPadding(new Insets(10));
        navigationBar.setSpacing(20);
        navigationBar.setStyle("-fx-background-color:  #FFCDE1;");  // Light pink color
        navigationBar.setAlignment(Pos.CENTER);

        // Create navigation sections with images
        StackPane homeSection = createNavSection("styles/home.png", homepage);
        StackPane toDoSection = createNavSection("styles/todo.png", toDopage);
        StackPane loginSection = createNavSection("styles/login.png", loginpage);

        // Add sections to the navigation bar
        navigationBar.getChildren().addAll(homeSection, toDoSection, loginSection);
    }

    // Method to create a navigation section with an image
    private StackPane createNavSection(String imagePath, StackPane page) {
        StackPane section = new StackPane();
        section.setPadding(new Insets(10));
        section.setStyle("-fx-background-color: #FFCDE1; -fx-border-color: transparent; -fx-border-width: 5px;");
        section.setMinWidth(100);
        section.setMaxWidth(150);
        section.setOnMouseClicked(e -> mainApp.getMainLayout().setCenter(page)); // Update center content on click

        // 이미지 추가
        Image iconImage = new Image(imagePath);
        ImageView imageView = new ImageView(iconImage);

            // 로그인 아이콘만 이미지 크기가 작아서 조정
        if (imagePath.contains("login")) {
            imageView.setFitHeight(50);  // 로그인 아이콘의 크기 조정
            imageView.setFitWidth(50);
        } else {
            imageView.setFitHeight(30);  // 다른 아이콘 크기
            imageView.setFitWidth(30);
        }

        section.getChildren().add(imageView);
        return section;
    }

    public HBox getLayout() {
        return navigationBar;
    }
}
