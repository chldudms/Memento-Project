import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

public class DiaryPage {
    private VBox layout;

    public DiaryPage(String title) {
        layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        // 다이어리 제목 텍스트
        Text diaryTitleText = new Text("다이어리 제목: " + title);
        diaryTitleText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // 내용 작성 버튼
        Button writeButton = new Button("내용 작성하기");
        writeButton.setOnAction(e -> {
            // 내용 작성 버튼 클릭 시 다른 로직 구현 가능
            System.out.println("내용 작성하기 버튼 클릭됨");
        });

        // 페이지로 돌아갈 수 있는 버튼 추가 (HomePage로 돌아가는 기능)
        Button backButton = new Button("뒤로 가기");
        backButton.setOnAction(e -> {
            // HomePage로 돌아가기 (예시)
            HomePage homePage = new HomePage(null); // 여기에 적절한 Main 객체를 전달해야 함
            layout.getChildren().clear(); // 다이어리 페이지 내용 지우기
            layout.getChildren().add(homePage.getLayout()); // HomePage로 전환
        });

        // 레이아웃에 버튼과 제목 추가
        layout.getChildren().addAll(diaryTitleText, writeButton, backButton);
    }

    public VBox getLayout() {
        return layout;
    }
}
