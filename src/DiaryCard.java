import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class DiaryCard {
    private StackPane layout;
    private String title;
    private String coverColor;

    public DiaryCard(String title, String coverColor) {
        this.title = title;
        this.coverColor = coverColor;

        layout = new StackPane();
        Rectangle cover = new Rectangle(150, 200); // 다이어리 표지 크기
        cover.setFill(Color.web(coverColor)); // 커버 색상 설정

        Text titleText = new Text(title);
        titleText.setFill(Color.WHITE); // 제목 텍스트 색상 설정
        titleText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        layout.getChildren().addAll(cover, titleText);
        layout.setStyle("-fx-padding: 5; -fx-alignment: center;");

        // 다이어리 카드 클릭 이벤트
        layout.setOnMouseClicked(event -> handleCardClick(event));
    }

    private void handleCardClick(MouseEvent event) {
        Stage currentStage = (Stage) layout.getScene().getWindow();
        new DiaryPage(title, currentStage); // DiaryPage로 이동
    }

    public StackPane getLayout() {
        return layout;
    }
}
