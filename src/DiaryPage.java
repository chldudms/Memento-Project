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

import java.io.File;

public class DiaryPage {
    private Scene scene;
    Main main = new Main();
    private Stage primaryStage; // 클래스 변수로 선언
    private ImageView imageView; // 이미지 뷰 추가
    private double xOffset = 0, yOffset = 0; // 이미지 드래그 이동을 위한 변수

    public DiaryPage(String diaryTitle, Stage currentStage, Runnable onBack) {
        this.main = main; // Main 인스턴스를 저장

        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #FFFFFF;");

        // 메인 레이아웃 (VBox로 설정)
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_CENTER);

        // 이미지 뷰 설정 (이미지 표시)
        imageView = new ImageView();
        imageView.setFitWidth(300);
        imageView.setFitHeight(300);
        imageView.setPreserveRatio(true);

        // 이미지 이동을 위한 Pane
        Pane imagePane = new Pane();
        imagePane.getChildren().add(imageView);
        imagePane.setPrefSize(800, 600); // Pane 크기 지정

        // 하단 버튼 영역 (고정 위치)
        HBox bottomButtonBox = new HBox(300);
        bottomButtonBox.setAlignment(Pos.BOTTOM_CENTER);
        bottomButtonBox.setPadding(new Insets(0, 0, 0, 0));
        // 왼쪽 공유 버튼
        HBox leftButtonBox = new HBox();
        leftButtonBox.setAlignment(Pos.BOTTOM_LEFT);
        Button shareButton = createIconButton("styles/share.png", "공유");
        shareButton.setOnAction(e -> System.out.println("공유 버튼 클릭!"));
        leftButtonBox.getChildren().add(shareButton);

        // 오른쪽 4개 버튼
        HBox rightButtonBox = new HBox(10);
        rightButtonBox.setAlignment(Pos.BOTTOM_RIGHT);
        Button textButton = createIconButton("styles/text.png", "텍스트");
        Button stickerButton = createIconButton("styles/sticker.png", "스티커");
        Button photoButton = createIconButton("styles/upload.png", "사진");
        Button saveButton = createIconButton("styles/save.png", "저장");
        rightButtonBox.getChildren().addAll(textButton, stickerButton, photoButton, saveButton);

        bottomButtonBox.getChildren().addAll(leftButtonBox, rightButtonBox);

        // 이미지와 버튼을 포함하는 메인 레이아웃에 추가
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

        // 이미지 드래그를 위해 mouse event 설정
        imageView.setOnMousePressed(this::handleMousePressed);
        imageView.setOnMouseDragged(this::handleMouseDragged);

        // 돌아가기 버튼 (오른쪽 상단)
        Button backButton = new Button("뒤로");
        backButton.setStyle("-fx-background-color: #FFC0CB; -fx-text-fill: white;");
        backButton.setOnAction(e -> {
            if (currentStage != null) {
                HomePage homePage = new HomePage(); // 홈 페이지 생성
                currentStage.setScene(homePage.getMainScene()); // 홈 페이지의 Scene으로 설정
            } else {
                System.err.println("Error: currentStage is null.");
            }
        });

        root.getChildren().addAll(mainLayout, backButton);
        StackPane.setAlignment(backButton, Pos.TOP_RIGHT);
        StackPane.setMargin(backButton, new Insets(10));

        this.scene = new Scene(root, 800, 600);
    }

    // 마우스 눌렀을 때 위치 기록
    private void handleMousePressed(MouseEvent event) {
        xOffset = event.getSceneX() - imageView.getLayoutX();
        yOffset = event.getSceneY() - imageView.getLayoutY();
    }

    // 마우스 드래그 시 이미지 이동
    private void handleMouseDragged(MouseEvent event) {
        imageView.setLayoutX(event.getSceneX() - xOffset);
        imageView.setLayoutY(event.getSceneY() - yOffset);
    }

    // createIconButton method remains the same
    private Button createIconButton(String imagePath, String tooltipText) {
        Button button = new Button();
        ImageView icon = new ImageView(new Image(imagePath));
        icon.setFitWidth(70);
        icon.setFitHeight(70);
        button.setGraphic(icon);
        button.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        button.setTooltip(new javafx.scene.control.Tooltip(tooltipText));
        return button;
    }

    public Scene getScene() {
        return scene;
    }
}
