import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.control.Label; // Label 클래스 임포트

public class DiaryPage {
    private Scene scene;

    public DiaryPage(String diaryTitle, Stage currentStage) {
        // Root Layout
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #FFFFFF;");

        // Pages Layout
        HBox pagesLayout = new HBox(10);
        pagesLayout.setPadding(new Insets(20));
        pagesLayout.setStyle("-fx-border-color: #A7A7A7; -fx-border-width: 2px; -fx-border-radius: 5;");

        // Left Page
        VBox leftPage = new VBox();
        leftPage.setStyle("-fx-background-color: #F8F8F8; -fx-padding: 20;");
        leftPage.getChildren().add(new Text("Left Page: " + diaryTitle));

        // Right Page
        VBox rightPage = new VBox();
        rightPage.setStyle("-fx-background-color: #F8F8F8; -fx-padding: 20;");
        rightPage.getChildren().add(new Text("Right Page"));

        pagesLayout.getChildren().addAll(leftPage, rightPage);

        // Back Button (DiaryPage에서 홈으로 돌아가기)
        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #FFC0CB; -fx-text-fill: white;");
        backButton.setOnAction(e -> {
            if (currentStage != null) {
                HomePage homePage = new HomePage(); // 홈 페이지 생성
                currentStage.setScene(homePage.getMainScene()); // 홈 페이지의 Scene으로 설정
            } else {
                System.err.println("Error: currentStage is null.");
            }
        });

        StackPane.setAlignment(backButton, Pos.TOP_LEFT);
        StackPane.setMargin(backButton, new Insets(10));
        root.getChildren().addAll(pagesLayout, backButton);
        scene = new Scene(root, 800, 600);
    }

    public Scene getScene() {
        return scene;
    }
}
