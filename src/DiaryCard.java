
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DiaryCard {
    private StackPane layout;

    public DiaryCard(String title, String coverColor) {
        layout = new StackPane();
        Rectangle cover = new Rectangle(150, 180); // 다이어리 표지 크기
        cover.setFill(Color.web(coverColor)); // 선택된 색상으로 표지 색상 설정

        Text titleText = new Text(title);
        titleText.setFill(Color.WHITE); // 제목 텍스트 색상 설정
        titleText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;"); // 제목 텍스트 스타일

        layout.getChildren().addAll(cover, titleText); // 표지와 제목 추가
        layout.setStyle("-fx-padding: 5; -fx-alignment: center;"); // 패딩 및 정렬 설정
    }

    public StackPane getLayout() {
        return layout; // 레이아웃 반환
    }
}