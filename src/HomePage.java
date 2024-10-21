import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class HomePage {
    private StackPane layout;
    private GridPane diaryGrid;  // 다이어리 목록을 위한 GridPane
    private Pane createDiaryPane;  // 다이어리 생성 UI
    private String selectedCoverColor = "#FFB6C1";  // 기본 색상 (핑크색)

    public HomePage(Main mainApp) {
        layout = new StackPane();
        diaryGrid = new GridPane();  // 다이어리 목록을 GridPane으로 설정
        diaryGrid.setHgap(10);  // 가로 간격
        diaryGrid.setVgap(10);  // 세로 간격

        // 홈 페이지 제목
        Text title = new Text("다이어리 홈");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");  // 제목 스타일 설정

        // 다이어리 생성 버튼
        Button createDiaryButton = new Button("+");
        createDiaryButton.setStyle("-fx-font-size: 30px; -fx-base: lightblue;"); // 스타일 설정
        createDiaryButton.setOnAction(e -> openDiaryCreationPane()); // 오버레이 열기

        // 왼쪽 하단에 버튼 배치
        VBox vbox = new VBox(title, diaryGrid);
        AnchorPane.setBottomAnchor(createDiaryButton, 20.0);
        AnchorPane.setLeftAnchor(createDiaryButton, 20.0);
        AnchorPane buttonContainer = new AnchorPane(createDiaryButton);
        buttonContainer.setPrefSize(200, 200); // 버튼 컨테이너 크기 설정

        // 레이아웃 구성
        layout.getChildren().addAll(vbox, buttonContainer);
        layout.setStyle("-fx-padding: 20;");  // 패딩 추가
        
        // 다이어리 생성 UI 생성
        createDiaryPane = createDiaryUI();
        layout.getChildren().add(createDiaryPane);  // 다이어리 생성 UI를 레이아웃에 추가
        createDiaryPane.setVisible(false);  // 처음에는 보이지 않게 설정
    }

    // 다이어리 목록에 추가하는 메서드
    public void addDiary(String title, String cover) {
        // 다이어리 카드 생성
        DiaryCard diaryCard = new DiaryCard(title, cover); // 다이어리 제목과 색상 전달
        int rowCount = diaryGrid.getChildren().size() / 2;  // 현재 다이어리 카드 수에 따른 행 계산
        diaryGrid.add(diaryCard.getLayout(), diaryGrid.getChildren().size() % 2, rowCount);  // GridPane에 카드 추가
        createDiaryPane.setVisible(false);  // 다이어리 생성 후 UI 숨김
    }

    // 선택된 색상 반환 메서드 추가
    public String getSelectedCoverColor() {
        return selectedCoverColor;  // 선택된 색상 반환
    }

    public StackPane getLayout() {
        return layout;
    }

    // 다이어리 생성 UI를 생성하는 메서드
    private Pane createDiaryUI() {
        VBox diaryCreationLayout = new VBox();
        diaryCreationLayout.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-padding: 20; -fx-border-color: #ccc; -fx-border-radius: 10; -fx-background-radius: 10;");

        Text titleLabel = new Text("다이어리 생성");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        TextField titleField = new TextField();
        titleField.setPromptText("다이어리 제목 입력");  // 제목 입력 프롬프트

        ColorPicker colorPicker = new ColorPicker();  // 색상 선택기
        colorPicker.setValue(javafx.scene.paint.Color.PINK);  // 기본 색상 (핑크색)

        Button createButton = new Button("다이어리 생성");
        createButton.setOnAction(e -> {
            String title = titleField.getText();
            if (!title.isEmpty()) {
                addDiary(title, toHex(colorPicker.getValue()));  // 제목과 색상 전달
                titleField.clear();  // 입력 필드 초기화
            }
        });

        Button closeButton = new Button("닫기");
        closeButton.setOnAction(e -> createDiaryPane.setVisible(false));  // 다이어리 생성 UI 숨김

        diaryCreationLayout.getChildren().addAll(titleLabel, titleField, colorPicker, createButton, closeButton);
        return diaryCreationLayout;
    }

    // HEX로 변환하는 메서드
    private String toHex(javafx.scene.paint.Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    // 다이어리 생성 페이지 열기
    private void openDiaryCreationPane() {
        createDiaryPane.setVisible(true);  // 다이어리 생성 UI 보이기
    }
}
