import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;

public class DiaryPage {
    private Scene scene;
    Main main = new Main();

    public DiaryPage(String diaryTitle, Stage currentStage, Runnable onBack) {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #FFFFFF;");

        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_CENTER);

        Text diaryTitleText = new Text(diaryTitle);
        diaryTitleText.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        mainLayout.getChildren().add(diaryTitleText);

        // 여백 추가
        Region spacer = new Region();
        spacer.setPrefHeight(300);   //버튼 밑으로 더 내리기 
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // 하단 버튼 영역
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

        // 스페이서와 버튼 박스 추가
        mainLayout.getChildren().addAll(spacer, bottomButtonBox);

        // 돌아가기 버튼 (오른쪽 상단)
        Button backButton = new Button("뒤로");
        backButton.setStyle("-fx-background-color: #FFC0CB; -fx-text-fill: white;");
        backButton.setOnAction(e -> onBack.run());

        root.getChildren().addAll(mainLayout, backButton);
        StackPane.setAlignment(backButton, Pos.TOP_RIGHT);
        StackPane.setMargin(backButton, new Insets(10));

        this.scene = new Scene(root, 800, 600);
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