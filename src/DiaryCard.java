import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;

public class DiaryCard {
    private StackPane layout;
    private String title;
    private String coverColor;
    private HomePage homePage; // HomePage 인스턴스를 클래스 변수로 설정

    // Constructor takes in a title and cover color
    public DiaryCard(String title, String coverColor) {
        this.title = title;
        this.coverColor = coverColor;

        layout = new StackPane();
        Rectangle cover = new Rectangle(150, 200); // 다이어리 표지 크기
        cover.setFill(Color.web(coverColor)); // 선택된 색상으로 표지 색상 설정

        Text titleText = new Text(title);
        titleText.setFill(Color.WHITE); // 제목 텍스트 색상 설정
        titleText.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;"); // 제목 텍스트 스타일

        layout.getChildren().addAll(cover, titleText); // 표지와 제목 추가
        layout.setStyle("-fx-padding: 5; -fx-alignment: center;"); // 패딩 및 정렬 설정

        // 다이어리 카드 클릭 이벤트 설정
        layout.setOnMouseClicked(event -> handleCardClick(event));
    }

    private void handleCardClick(MouseEvent event) {
        // 클릭 시 새로운 페이지를 띄우는 메서드 호출
        openDiaryPage(title);
    }

    // Replace the content of the current page with the diary details
    private void openDiaryPage(String title) {
        // Assuming the HomePage class is the parent, pass this reference to it

        // Create new content to simulate the "new page" effect in the same window
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);

        // Diary title
        Text diaryTitleText = new Text("다이어리 제목: " + title);
        diaryTitleText.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Add a button to simulate writing content
        Button writeButton = new Button("내용 작성하기");
        writeButton.setOnAction(e -> {
            // Handle content writing logic here
            System.out.println("내용 작성하기 버튼 클릭됨");
        });

        vbox.getChildren().addAll(diaryTitleText, writeButton);

        // Update the content in the same Stage/Scene
        Scene newScene = new Scene(vbox, 800, 640);
        Stage currentStage = (Stage) layout.getScene().getWindow();
        currentStage.setScene(newScene); // Set the new scene to show diary details
    }

    public StackPane getLayout() {
        return layout; // 레이아웃 반환
    }
}
