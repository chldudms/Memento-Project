import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class DiaryCard {
    private StackPane layout;
    private String title;
    private String coverColor;

    // 다이어리카드 생성자
    public DiaryCard(String title, String coverColor) {
        this.title = title;
        this.coverColor = coverColor;

        layout = new StackPane();
        Rectangle cover = new Rectangle(150, 200); // 다이어리 표지 크기
        cover.setFill(Color.web(coverColor)); // 선택된 색상으로 표지 색상 설정

        Text titleText = new Text(title);
        titleText.setFill(Color.WHITE); // 제목 텍스트 색상 설정
        titleText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;"); // 제목 텍스트 스타일

        layout.getChildren().addAll(cover, titleText); // 표지와 제목 추가
        layout.setStyle("-fx-padding: 7; -fx-alignment: center;"); // 패딩 및 정렬 설정

        // 다이어리 카드 클릭 이벤트 설정
        layout.setOnMouseClicked(event -> handleCardClick(event));
    }

    private void handleCardClick(MouseEvent event) {
        // 현재 Stage 가져오기
        Stage currentStage = (Stage) layout.getScene().getWindow();

        DiaryPage diaryPage = new DiaryPage(title, currentStage, () -> {

        }); 

         // 새로운 씬으로 다이어리 페이지 설정
         currentStage.setScene(diaryPage.getScene());
    }

    public StackPane getLayout() {
        return layout; // 레이아웃 반환
    }
}
