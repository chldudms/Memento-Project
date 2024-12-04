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

public class DiaryPage {
    private Scene scene;
    private Pane stickerPane; // 스티커를 추가할 공용 Pane
    private boolean isStickerPanelVisible = false; // 스티커 판의 가시성 여부

    public DiaryPage(String diaryTitle, Stage currentStage, Runnable onBack) {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #FFFFFF;");

        // 스티커 및 사진을 표시할 공용 Pane
        stickerPane = new Pane();
        stickerPane.setPrefSize(800, 600);

        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.getChildren().add(stickerPane);

        // 하단 버튼 영역
        HBox bottomButtonBox = new HBox(300);
        bottomButtonBox.setAlignment(Pos.BOTTOM_CENTER);
        bottomButtonBox.setPadding(new Insets(10));

        // 공유 버튼 (좌측)
        HBox leftButtonBox = new HBox();
        leftButtonBox.setAlignment(Pos.BOTTOM_LEFT);
        Button shareButton = createIconButton("styles/share.png", "공유");
        shareButton.setOnAction(e -> System.out.println("공유 버튼 클릭!"));
        leftButtonBox.getChildren().add(shareButton);

        // 텍스트, 스티커, 사진, 저장 버튼 (우측)
        HBox rightButtonBox = new HBox(10);
        rightButtonBox.setAlignment(Pos.BOTTOM_RIGHT);
        Button textButton = createIconButton("styles/text.png", "텍스트");
        Button stickerButton = createIconButton("styles/sticker.png", "스티커");
        Button photoButton = createIconButton("styles/upload.png", "사진");
        Button saveButton = createIconButton("styles/save.png", "저장");
        rightButtonBox.getChildren().addAll(textButton, stickerButton, photoButton, saveButton);

        // 버튼 박스를 하단에 배치
        bottomButtonBox.getChildren().addAll(leftButtonBox, rightButtonBox);

        mainLayout.getChildren().add(bottomButtonBox);
        root.getChildren().add(mainLayout);

        // 사진 버튼 이벤트
        photoButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters()
                    .add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
            File selectedFile = fileChooser.showOpenDialog(currentStage);
            if (selectedFile != null) {
                Image image = new Image("file:" + selectedFile.getAbsolutePath());
                addStickerToDiary(image); // 사진도 스티커처럼 추가
            }
        });

        // 스티커 버튼 이벤트
        stickerButton.setOnAction(e -> toggleStickerPanel());

        this.scene = new Scene(root, 800, 600);
    }

    private void toggleStickerPanel() {
        StackPane root = (StackPane) scene.getRoot();

        // 스티커 판 가시성 조정
        if (isStickerPanelVisible) {
            root.getChildren().removeIf(node -> node instanceof VBox && "sticker-panel".equals(node.getId()));
            isStickerPanelVisible = false;
            return;
        }

        // 새 스티커 판 생성
        VBox stickerPanel = new VBox(10);
        stickerPanel.setMaxSize(300, 200); // 최대 크기 설정
        stickerPanel.setId("sticker-panel");
        stickerPanel.setPadding(new Insets(10));
        stickerPanel.setStyle("-fx-background-color: #FFD8E4;");
        stickerPanel.setAlignment(Pos.CENTER);

        GridPane stickerGrid = new GridPane();
        stickerGrid.setHgap(10);
        stickerGrid.setVgap(10);
        stickerGrid.setAlignment(Pos.CENTER);

        // 스티커 추가
        for (int i = 1; i <= 6; i++) {
            String imagePath = "/styles/sticker" + i + ".png"; // 클래스 경로
            Image image;
            try {
                image = new Image(getClass().getResource(imagePath).toExternalForm());
            } catch (Exception ex) {
                System.err.println("스티커 로드 실패: " + imagePath);
                continue;
            }

            ImageView sticker = new ImageView(image);
            sticker.setFitWidth(100);
            sticker.setFitHeight(100);

            sticker.setOnMouseClicked(event -> addStickerToDiary(image));

            int col = (i - 1) % 3;
            int row = (i - 1) / 3;
            stickerGrid.add(sticker, col, row);
        }

        stickerPanel.getChildren().add(stickerGrid);
        root.getChildren().add(stickerPanel);

        StackPane.setAlignment(stickerPanel, Pos.BOTTOM_CENTER);
        StackPane.setMargin(stickerPanel, new Insets(30, 0, 120, 400)); // 위, 오른쪽, 아래, 왼쪽

        isStickerPanelVisible = true;
    }

    private void addStickerToDiary(Image stickerImage) {
        ImageView sticker = new ImageView(stickerImage);
        sticker.setPreserveRatio(true);
        sticker.setFitWidth(100);
        sticker.setFitHeight(100);

        // 드래그 및 위치 조정
        sticker.setOnMousePressed(event -> {
            sticker.setUserData(new double[] { event.getSceneX() - sticker.getLayoutX(),
                    event.getSceneY() - sticker.getLayoutY() });
        });
        sticker.setOnMouseDragged(event -> {
            double[] offsets = (double[]) sticker.getUserData();
            sticker.setLayoutX(event.getSceneX() - offsets[0]);
            sticker.setLayoutY(event.getSceneY() - offsets[1]);
        });

        // 크기 조정
        sticker.setOnScroll(event -> {
            double scale = event.getDeltaY() > 0 ? 1.1 : 0.9;
            sticker.setFitWidth(sticker.getFitWidth() * scale);
            sticker.setFitHeight(sticker.getFitHeight() * scale);
        });

        stickerPane.getChildren().add(sticker);
    }

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
