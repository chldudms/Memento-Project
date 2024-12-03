import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Tooltip;

import java.io.File;
import java.lang.classfile.components.ClassPrinter.Node;

public class DiaryPage {
    private Scene scene;
    private Main main = new Main();
    private Stage primaryStage;
    private ImageView imageView;
    private double xOffset = 0, yOffset = 0;
    private boolean isStickerPanelVisible = false; // 스티커 판의 가시성 여부

    public DiaryPage(String diaryTitle, Stage currentStage, Runnable onBack) {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #FFFFFF;");

        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_CENTER);

        imageView = new ImageView();
        imageView.setFitWidth(300);
        imageView.setFitHeight(300);
        imageView.setPreserveRatio(true);

        Pane imagePane = new Pane();
        imagePane.getChildren().add(imageView);
        imagePane.setPrefSize(800, 600);

        // 하단 버튼 영역 (고정 위치)
        HBox bottomButtonBox = new HBox(300);
        bottomButtonBox.setAlignment(Pos.BOTTOM_CENTER);
        bottomButtonBox.setPadding(new Insets(0, 0, 0, 0));

        HBox leftButtonBox = new HBox();
        leftButtonBox.setAlignment(Pos.BOTTOM_LEFT);
        Button shareButton = createIconButton("styles/share.png", "공유");
        shareButton.setOnAction(e -> System.out.println("공유 버튼 클릭!"));
        leftButtonBox.getChildren().add(shareButton);

        HBox rightButtonBox = new HBox(10);
        rightButtonBox.setAlignment(Pos.BOTTOM_RIGHT);
        Button textButton = createIconButton("styles/text.png", "텍스트");
        Button stickerButton = createIconButton("styles/sticker.png", "스티커");
        Button photoButton = createIconButton("styles/upload.png", "사진");
        Button saveButton = createIconButton("styles/save.png", "저장");
        rightButtonBox.getChildren().addAll(textButton, stickerButton, photoButton, saveButton);

        bottomButtonBox.getChildren().addAll(leftButtonBox, rightButtonBox);

        mainLayout.getChildren().addAll(imagePane, bottomButtonBox);

        // 사진 버튼 클릭 시 이미지 파일 선택
        photoButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters()
                    .add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
            File selectedFile = fileChooser.showOpenDialog(currentStage);
            if (selectedFile != null) {
                Image image = new Image("file:" + selectedFile.getAbsolutePath());
                imageView.setImage(image);
            }
        });

        // 스티커 버튼 클릭 시 스티커판 토글
        stickerButton.setOnAction(e -> toggleStickerPanel());

        imageView.setOnMousePressed(this::handleMousePressed);
        imageView.setOnMouseDragged(this::handleMouseDragged);

        // 돌아가기 버튼 (오른쪽 상단)
        Button backButton = new Button("뒤로");
        backButton.setStyle("-fx-background-color: #FFC0CB; -fx-text-fill: white;");
        backButton.setOnAction(e -> {
            if (currentStage != null) {
                HomePage homePage = new HomePage();
                currentStage.setScene(homePage.getMainScene());
            } else {
                System.err.println("Error: currentStage is null.");
            }
        });

        root.getChildren().addAll(mainLayout, backButton);
        StackPane.setAlignment(backButton, Pos.TOP_RIGHT);
        StackPane.setMargin(backButton, new Insets(10));

        this.scene = new Scene(root, 800, 600);
    }

    private void toggleStickerPanel() {
        StackPane root = (StackPane) scene.getRoot();

        // 이미 스티커판이 보이는 경우 숨기기
        if (isStickerPanelVisible) {
            root.getChildren().removeIf(node -> node instanceof VBox && "sticker-panel".equals(node.getId()));
            isStickerPanelVisible = false;
            return;
        }

        // 새 스티커판 생성
        VBox stickerPanel = new VBox(10);
        stickerPanel.setMinSize(300, 200); // 최소 크기 설정
        stickerPanel.setMaxSize(300, 200); // 최대 크기 설정
        stickerPanel.setPrefSize(300, 200);  // 권장 크기 설정

        stickerPanel.setId("sticker-panel");
        stickerPanel.setPadding(new Insets(10));
        stickerPanel.setStyle("-fx-background-color: #FFD8E4;");
        stickerPanel.setAlignment(Pos.CENTER); 
        stickerPanel.setPrefSize(300, 200);  

        // 스티커 이미지를 담을 GridPane 생성
        GridPane stickerGrid = new GridPane();
        stickerGrid.setHgap(10); // 열 간격
        stickerGrid.setVgap(10); // 행 간격
        stickerGrid.setAlignment(Pos.CENTER);

        // 스티커 추가
        for (int i = 1; i <= 6; i++) {
            ImageView sticker = new ImageView(new Image("file:styles/sticker" + i + ".png"));
            sticker.setFitWidth(10); // 스티커 크기
            sticker.setFitHeight(10);

            // 스티커 클릭 이벤트: 클릭한 스티커를 화면에 추가
            sticker.setOnMouseClicked(event -> addStickerToDiary(sticker.getImage()));

            // GridPane에 스티커 추가
            int col = (i - 1) % 3; // 3개씩 열에 배치
            int row = (i - 1) / 3; // 행 계산
            stickerGrid.add(sticker, col, row);
        }

        stickerPanel.getChildren().add(stickerGrid);

        // 스티커판을 다이어리 화면에 추가
        root.getChildren().add(stickerPanel);

        // 스티커판 위치를 스티커 버튼 위로 설정
        StackPane.setAlignment(stickerPanel, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(stickerPanel, new Insets(30, 100, 120, 0));

        isStickerPanelVisible = true;
    }

    // 다이어리 화면에 스티커 추가
    private void addStickerToDiary(Image stickerImage) {
        ImageView sticker = new ImageView(stickerImage);
        sticker.setFitWidth(10); // 크기 조정
        sticker.setFitHeight(10);

        // 스티커를 드래그 가능하게 설정
        sticker.setOnMousePressed(this::handleMousePressed);
        sticker.setOnMouseDragged(this::handleMouseDragged);

        // 다이어리 화면에 스티커 추가
        ((StackPane) scene.getRoot()).getChildren().add(sticker);
    }

    // 마우스 눌렀을 때 위치 기록
    private void handleMousePressed(MouseEvent event) {
        javafx.scene.Node source = (javafx.scene.Node) event.getSource(); // 이벤트 발생 객체
        xOffset = event.getSceneX() - source.getLayoutX();
        yOffset = event.getSceneY() - source.getLayoutY();
    }

    private void handleMouseDragged(MouseEvent event) {
        javafx.scene.Node source = (javafx.scene.Node) event.getSource(); // 이벤트 발생 객체
        source.setLayoutX(event.getSceneX() - xOffset);
        source.setLayoutY(event.getSceneY() - yOffset);
    }

    // createIconButton method remains the same
    private Button createIconButton(String imagePath, String tooltipText) {
        Button button = new Button();
        ImageView icon = new ImageView(new Image(imagePath));
        icon.setFitWidth(70);
        icon.setFitHeight(70);
        button.setGraphic(icon);
        button.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        button.setTooltip(new Tooltip(tooltipText));
        return button;
    }

    public Scene getScene() {
        return scene;
    }
}
