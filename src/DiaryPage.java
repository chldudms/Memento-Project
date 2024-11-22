import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DiaryPage {
    private Scene diaryScene;

    public DiaryPage(String title, Stage stage) {
        // 다이어리 세부 페이지 레이아웃
        VBox diaryLayout = new VBox(20);
        diaryLayout.setAlignment(Pos.CENTER);

        // 다이어리 제목
        Text diaryTitle = new Text("다이어리 제목: " + title);
        diaryTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // 내용 작성하기 버튼
        Button writeContentButton = new Button("내용 작성하기");
        writeContentButton.setOnAction(e -> {
            System.out.println("내용 작성하기 버튼 클릭됨");
        });

        // 뒤로 가기 버튼
        Button backButton = new Button("뒤로 가기");
        backButton.setOnAction(e -> {
            // 메인 화면으로 돌아가기
            stage.setScene(HomePage.getMainScene());
        });

        diaryLayout.getChildren().addAll(diaryTitle, writeContentButton, backButton);

        // 새로운 Scene 설정
        diaryScene = new Scene(diaryLayout, 800, 600);
        stage.setScene(diaryScene);
    }

    public Scene getDiaryScene() {
        return diaryScene;
    }
}
