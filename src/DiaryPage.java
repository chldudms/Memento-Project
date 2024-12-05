import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.imageio.ImageIO;

public class DiaryPage {
    private Scene scene;
    private StackPane layout;
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
        
        // 선을 그릴 Canvas 생성
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.web("#F875AA")); // 선의 색상 설정
        gc.setLineWidth(2); // 선의 두께 설정

        // 화면을 선으로 나누기
        // 세로로 중앙을 나누는 선
        gc.strokeLine(400, 0, 400, 600);

        // 왼쪽 상단에 가로로 나누는 선 (80px 아래)
        gc.strokeLine(0, 80, 400, 80);

        // 현재 날짜 정보
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 날짜 형식: yyyy-MM-dd
        String dateText = today.format(dateFormatter); // 예: "2024-12-06"

        // 요일 텍스트 (영어로 표시)
        String dayOfWeekText = today.getDayOfWeek().getDisplayName(java.time.format.TextStyle.FULL, Locale.ENGLISH); // 예: "Friday"

        // 텍스트 스타일 설정
        gc.setFill(Color.web("#6FC8FF")); // 텍스트 색상
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 25)); // 날짜 텍스트 폰트 및 크기

        // 텍스트 크기 계산
        Text tempDateText = new Text(dateText);
        tempDateText.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        double dateTextWidth = tempDateText.getLayoutBounds().getWidth();

        Text tempDayOfWeekText = new Text(dayOfWeekText);
        tempDayOfWeekText.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        double dayOfWeekTextWidth = tempDayOfWeekText.getLayoutBounds().getWidth();

        // 상단 공간 크기
        double upperSpaceWidth = 400; // 상단 공간의 너비
        double upperSpaceHeight = 80; // 상단 공간의 높이

        // 중앙 좌표 계산
        double dateX = (upperSpaceWidth - dateTextWidth) / 2; // 날짜 텍스트의 X 좌표 (중앙)
        double dateY = upperSpaceHeight / 2 - 5;             // 날짜 텍스트의 Y 좌표 (상단 공간 중앙 약간 위쪽)
        double dayOfWeekX = (upperSpaceWidth - dayOfWeekTextWidth) / 2; // 요일 텍스트의 X 좌표 (중앙)
        double dayOfWeekY = upperSpaceHeight / 2 + 25;                  // 요일 텍스트의 Y 좌표 (날짜 아래)

        // 날짜 텍스트 출력
        gc.fillText(dateText, dateX, dateY);

        // 요일 텍스트 출력
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 22)); // 요일은 더 작은 크기로 설정
        gc.fillText(dayOfWeekText, dayOfWeekX, dayOfWeekY);

        // Canvas를 메인 레이아웃에 추가
        root.getChildren().add(canvas);

        // 하단 버튼 영역
        HBox bottomButtonBox = new HBox(300);
        bottomButtonBox.setAlignment(Pos.BOTTOM_CENTER);
        bottomButtonBox.setPadding(new Insets(-10));

        // 다운로드 버튼 (좌측)
        HBox leftButtonBox = new HBox();
        leftButtonBox.setAlignment(Pos.BOTTOM_LEFT);
        Button downButton = createIconButton("styles/down.png", "다운로드");

        downButton.setTranslateX(30);

        // 다운로드 버튼 이벤트
        downButton.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("이미지 저장");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG 파일", "*.png"));
            
            // 저장할 파일 선택
            File saveFile = fileChooser.showSaveDialog(currentStage);
            if (saveFile != null) {
                try {
                    // stickerPane을 캡처하여 이미지로 저장
                    WritableImage snapshot = stickerPane.snapshot(null, null);
                    ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", saveFile);
                    System.out.println("이미지가 저장되었습니다: " + saveFile.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("이미지 저장 중 오류 발생");
                }
            }
        });
        
        leftButtonBox.getChildren().addAll(downButton);

        // 텍스트, 스티커, 사진, 저장 버튼 (우측)
        HBox rightButtonBox = new HBox(5);
        rightButtonBox.setAlignment(Pos.BOTTOM_RIGHT);

        Image popUpImage = new Image("styles/popUP.png");  // 이미지 파일 경로 수정
        ImageView popUpImageView = new ImageView(popUpImage);

        Button editButton = createIconButton("styles/edit.png", "편집");
        Button textButton = createIconButton("styles/text.png", "텍스트");
        Button stickerButton = createIconButton("styles/sticker.png", "스티커");
        Button photoButton = createIconButton("styles/upload.png", "사진");
        Button saveButton = createIconButton("styles/save.png", "저장");

        // StackPane에 이미지와 버튼 추가 (버튼이 이미지 위로 위치)
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(popUpImageView, textButton, stickerButton, photoButton, saveButton);  // 버튼이 이미지 위에 위치
        stackPane.setVisible(false);

        editButton.setTranslateX(330);

        // 초기 상태
        rightButtonBox.getChildren().add(editButton);

        // 나머지 버튼들 숨기기
        textButton.setVisible(false);
        stickerButton.setVisible(false);
        photoButton.setVisible(false);
        saveButton.setVisible(false);

        textButton.setTranslateX(-35);  
        stickerButton.setTranslateX(-35);
        photoButton.setTranslateX(-35); 
        saveButton.setTranslateX(-35); 

        rightButtonBox.getChildren().addAll(textButton, stickerButton, photoButton, saveButton);

        // Edit 버튼 클릭 시 나머지 버튼들 보이기/숨기기
        editButton.setOnAction(event -> {
            textButton.setVisible(true);
            stickerButton.setVisible(true);
            photoButton.setVisible(true);
            saveButton.setVisible(true);
            stackPane.setVisible(true);


            // Edit 버튼 가시성 전환
            editButton.setVisible(false);  // 나머지 버튼들이 보일 때, editButton은 숨김
        });

        // 저장 버튼 클릭 시 나머지 버튼들 숨기고 edit 버튼 다시 보이기
        saveButton.setOnAction(event -> {
            // 나머지 버튼 숨기기
            textButton.setVisible(false);
            stickerButton.setVisible(false);
            photoButton.setVisible(false);
            saveButton.setVisible(false);

            // editButton 다시 보이기
            editButton.setVisible(true);

            // "저장되었습니다" 메시지 추가
            Label saveMessage = new Label("저장되었습니다");
            saveMessage.setStyle("-fx-font-size: 20px; -fx-text-fill: #F875AA; -fx-background-color: transparent;");
            saveMessage.setTranslateX(300);
            saveMessage.setTranslateY(200);

            // 메시지를 화면에 추가
            root.getChildren().add(saveMessage);
            
            // 메시지를 화면에 표시한 후 일정 시간 뒤 자동으로 사라지도록 설정
            Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2), e -> {
                    root.getChildren().remove(saveMessage); // 메시지 제거
                })
            );
            timeline.play();  // 타이머 시작
        });

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

        // 텍스트 버튼 이벤트 추가
        textButton.setOnAction(e -> addTextBoxToDiary());
        
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

        // 스티커 판 생성
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

    private void addTextBoxToDiary() {
        Label textLabel = new Label("텍스트 입력...");
        textLabel.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: black;");
        textLabel.setWrapText(true); // 텍스트 줄바꿈 설정
        textLabel.setMaxWidth(300); // 최대 너비 설정
        textLabel.setPrefHeight(50); // 초기 높이 설정
        textLabel.setLayoutX(300);
        textLabel.setLayoutY(300);

        // 텍스트 추가 후, 드래그, 크기 조정, 삭제 기능 적용
        addDraggableAndResizable(textLabel);
        addDeletable(textLabel);

        // 텍스트 수정 및 삭제 이벤트 추가
        textLabel.setOnMouseClicked(event -> {
            if (event.isShiftDown()) {
                // Shift 키를 누른 상태에서 클릭 시 삭제
                stickerPane.getChildren().remove(textLabel);
            } else if (event.getClickCount() == 2) {
                // 더블 클릭 시 텍스트 수정
                TextField textField = new TextField(textLabel.getText());
                textField.setLayoutX(textLabel.getLayoutX());
                textField.setLayoutY(textLabel.getLayoutY());
                textField.setPrefWidth(300); // 입력 필드 너비 설정

                // 텍스트 길이 제한 및 동적 크기 조정
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue.length() > 500) {
                        textField.setText(oldValue); // 500자 제한
                    } else {
                        textLabel.setPrefHeight(calculateLabelHeight(newValue, textField.getPrefWidth()));
                    }
                });

                textField.setOnAction(e -> {
                    textLabel.setText(textField.getText());
                    textLabel.setPrefHeight(calculateLabelHeight(textField.getText(), textField.getPrefWidth()));
                    stickerPane.getChildren().remove(textField);
                    stickerPane.getChildren().add(textLabel);
                });

                stickerPane.getChildren().remove(textLabel);
                stickerPane.getChildren().add(textField);
                textField.requestFocus();
            }
        });

        // 크기 조정 기능 추가 (마우스 휠)
        textLabel.setOnScroll(event -> {
            double scale = event.getDeltaY() > 0 ? 1.1 : 0.9;
            textLabel.setScaleX(textLabel.getScaleX() * scale);
            textLabel.setScaleY(textLabel.getScaleY() * scale);
        });

        stickerPane.getChildren().add(textLabel);
    }

    // 드래그 위치 조정, 크기 조정, 삭제 기능을 하나의 함수로 관리
    private void addDraggableAndResizable(Node node) {
    // 드래그로 위치 조정
    node.setOnMousePressed(event -> {
        node.setUserData(new double[] {
            event.getSceneX() - node.getLayoutX(),
            event.getSceneY() - node.getLayoutY()
        });
    });

    node.setOnMouseDragged(event -> {
        double[] offsets = (double[]) node.getUserData();
        node.setLayoutX(event.getSceneX() - offsets[0]);
        node.setLayoutY(event.getSceneY() - offsets[1]);
    });

    // 크기 조정 (마우스 휠)
    node.setOnScroll(event -> {
        double scale = event.getDeltaY() > 0 ? 1.1 : 0.9;
        if (node instanceof ImageView) {
            // 이미지 크기 조정
            ImageView imageView = (ImageView) node;
            imageView.setFitWidth(imageView.getFitWidth() * scale);
            imageView.setFitHeight(imageView.getFitHeight() * scale);
        } else if (node instanceof TextArea) {
            // 텍스트 크기 조정
            TextArea textArea = (TextArea) node;
            textArea.setScaleX(textArea.getScaleX() * scale);
            textArea.setScaleY(textArea.getScaleY() * scale);
        }
    });
}

// 삭제 기능 추가
private void addDeletable(Node node) {
    node.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2) { // 더블 클릭 시 삭제
            stickerPane.getChildren().remove(node);
        }
    });
}

    // 텍스트 길이에 따라 Label 높이 계산
    private double calculateLabelHeight(String text, double maxWidth) {
        Text tempText = new Text(text);
        tempText.setWrappingWidth(maxWidth); // Label과 동일한 최대 너비 적용
        new Scene(new Group(tempText)); // 크기 계산을 위한 Scene 생성
        return tempText.getLayoutBounds().getHeight() + 10; // 텍스트 높이 + 여백
    }

   //화면에 스티커 추가
    private void addStickerToDiary(Image stickerImage) {
        ImageView sticker = new ImageView(stickerImage);
        sticker.setPreserveRatio(true);
        sticker.setFitWidth(100);
        sticker.setFitHeight(100);
        sticker.setLayoutX(300);
        sticker.setLayoutY(300);

        // 스티커 추가 후, 드래그, 크기 조정, 삭제 기능 적용
        addDraggableAndResizable(sticker);
        addDeletable(sticker);

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