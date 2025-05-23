import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos; // 중앙 정렬에 필요
import javafx.scene.control.ScrollPane;

public class HomePage {
    private StackPane layout;
    private GridPane diaryGrid;
    private Pane createDiaryPane;
    private String selectedCoverColor = "#FFB6C1";
    private VBox diaryContainer;
    private VBox mainLayout; // 메인 레이아웃
    private static Scene mainScene; // 메인 화면 Scene 저장

    public HomePage(Stage primaryStage) {
        layout = new StackPane();
        diaryGrid = new GridPane();
        diaryGrid.setHgap(10);
        diaryGrid.setVgap(10);
        diaryContainer = new VBox(10);

        // ScrollPane으로 다이어리 목록 감싸기
        ScrollPane scrollPane = new ScrollPane(diaryGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(1000);
        scrollPane.getStyleClass().clear();
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        // 메인 레이아웃 초기화
        mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));

        // 플러스 이미지 로드
        Image plusIcon = new Image("styles/plusBtn.png");
        ImageView plusImageView = new ImageView(plusIcon);

        plusImageView.setFitWidth(40);
        plusImageView.setFitHeight(40);

        // 다이어리 생성 버튼
        Button createDiaryButton = new Button();
        createDiaryButton.setGraphic(plusImageView);
        createDiaryButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");
        createDiaryButton.setOnAction(e -> openDiaryCreationPane());

        // AnchorPane으로 버튼 고정
        AnchorPane buttonContainer = new AnchorPane(createDiaryButton);
        AnchorPane.setBottomAnchor(createDiaryButton, 5.0);
        AnchorPane.setRightAnchor(createDiaryButton, 10.0);

        mainLayout.getChildren().addAll(scrollPane, buttonContainer);

        layout.getChildren().add(mainLayout);
        layout.setStyle("-fx-background-color: #FFFFFF;");

        createDiaryPane = createDiaryUI();
        layout.getChildren().add(createDiaryPane);
        createDiaryPane.setVisible(false);

        mainScene = new Scene(layout, 800, 600);
    }

    public void addDiary(String title, String cover) {
        DiaryCard diaryCard = new DiaryCard(title, cover);
        int rowCount = diaryGrid.getChildren().size() / 2; // 한 줄에 두 개씩 배치
        diaryGrid.add(diaryCard.getLayout(), diaryGrid.getChildren().size() % 2, rowCount);
        createDiaryPane.setVisible(false);
    }

    public String getSelectedCoverColor() {
        return selectedCoverColor;
    }

    public StackPane getLayout() {
        return layout;
    }

    private Pane createDiaryUI() { // 다이어리 생성창

        // 닫기(x) 이미지 아이콘
        Image xImage = new Image("styles/xBtn2.png"); // 이미지 경로 지정
        ImageView xImageView = new ImageView(xImage);
        xImageView.setFitWidth(30); // 이미지 크기 조정
        xImageView.setFitHeight(30);

        Button closeButton = new Button();
        closeButton.setGraphic(xImageView);
        closeButton.getStyleClass().add("close-button"); // CSS 클래스 추가
        closeButton.setOnAction(e -> createDiaryPane.setVisible(false));

        // 버튼의 기본 배경과 테두리 제거
        closeButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");

        // 다이어리 생성 레이아웃
        VBox diaryCreationLayout = new VBox(15); // 요소 간의 간격을 넓게
        diaryCreationLayout.setPadding(new Insets(20)); // 패딩 설정
        diaryCreationLayout.setAlignment(Pos.CENTER); // 중앙 정렬
        diaryCreationLayout.setStyle("-fx-background-color: white; "
                + "-fx-border-radius: 10; "
                + "-fx-background-radius: 10; "
                + "-fx-border-color: #A7A7A7; " // 테두리 추가
                + "-fx-border-width: 2px;"); // 테두리 두께
        diaryCreationLayout.setMaxWidth(300); // 최대 너비 설정

        // 제목 입력 필드
        TextField titleField = new TextField();
        titleField.setPromptText("다이어리 제목 입력");
        titleField.setStyle("-fx-font-size: 14px; -fx-padding: 10;"); // 스타일 지정

        // 색상 선택기
        HBox colorPickerLayout = new HBox(10);
        colorPickerLayout.setAlignment(Pos.CENTER_LEFT); // 색상 선택기 왼쪽 정렬
        Label colorLabel = new Label("커버 색상:");
        colorLabel.setStyle("-fx-font-size: 14px;");
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setValue(javafx.scene.paint.Color.PINK);
        colorPickerLayout.getChildren().addAll(colorLabel, colorPicker);

        // 다이어리 생성 버튼
        Button createButton = new Button("다이어리 생성");
        createButton.setStyle("-fx-background-color: #FFC0CB; "
                + "-fx-text-fill: white; "
                + "-fx-font-size: 14px; "
                + "-fx-padding: 10; "
                + "-fx-border-radius: 5;"); // 버튼 스타일 지정
        createButton.setTranslateY(10); // 버튼을 살짝 아래로 이동
        diaryCreationLayout.setMaxWidth(300); // 최대 너비 설정
        diaryCreationLayout.setMaxHeight(300); // 최대 높이 설정 (정사각형)
        
        createButton.setOnAction(e -> {
            if(!Session.isLoggedIn()){
                System.out.println("로그인 후 이용할 수 있습니다.");
                return;
            }
            String title = titleField.getText();
            if (!title.isEmpty()) {
                addDiary(title, toHex(colorPicker.getValue()));
                titleField.clear();
            }
        });

        // 닫기 버튼을 오른쪽 상단에 배치하기 위해 HBox 사용
        HBox header = new HBox(closeButton); // 닫기 버튼을 HBox로 감싸기
        header.setAlignment(Pos.TOP_RIGHT); // 오른쪽 상단 정렬
        header.setPadding(new Insets(-60, -5, 0, 0)); // 위쪽 패딩을 설정하여 x버튼이 더 위로 가게

        // 생성 레이아웃에 요소 추가
        diaryCreationLayout.getChildren().addAll(header, titleField, colorPickerLayout, createButton);

        StackPane container = new StackPane(diaryCreationLayout);
        container.setAlignment(Pos.CENTER); // 창 중앙 정렬

        return container;
    }

    private String toHex(javafx.scene.paint.Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    private void openDiaryCreationPane() {
        createDiaryPane.setVisible(true);
    }

    public static Scene getMainScene() {
        if (mainScene == null) {
            throw new IllegalStateException("mainScene이 초기화되지 않았습니다.");
        }

        return mainScene;
    }

    public static void setMainScene(Scene scene) {
        mainScene = scene;
    }

}