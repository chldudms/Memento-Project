import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.paint.Color;
import javafx.geometry.*;
import javafx.stage.*;
import javafx.scene.input.*;
import javafx.scene.image.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.*;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DiaryPage {
    private StackPane root;
    private Scene scene;
    private Pane stickerPane; // 스티커를 추가할 공용 Pane
    private boolean isStickerPanelVisible = false; // 스티커 판의 가시성 여부

    public DiaryPage(String diaryTitle, Runnable onBack) {
        // 새 Stage 생성
        Stage newStage = new Stage();
        StackPane root1 = new StackPane();
        root1.setStyle("-fx-background-color: #FFFFFF;");

        // 공용 Pane 설정
        stickerPane = new Pane();
        stickerPane.setPrefSize(800, 600);

        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_CENTER);

        // 상단 영역 추가
        HBox topLayout = new HBox();
        topLayout.setAlignment(Pos.TOP_RIGHT);
        topLayout.setPadding(new Insets(10));
        topLayout.setSpacing(400); // 날짜와 버튼 간의 간격 설정

        // 날짜 및 요일 표시
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd EEEE", Locale.ENGLISH);
        String formattedDate = today.format(formatter); // 영어로 요일을 표시

        Label dateLabel = new Label(formattedDate);
        dateLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18)); // Arial 글꼴, Bold, 크기 18
        dateLabel.setTextFill(Color.DEEPSKYBLUE); // 하늘색 텍스트 색상

        // X 버튼 생성
        Button backButton = createIconButton("styles/xBtn.png", "돌아가기");

        backButton.setOnAction(e -> {
            try {
                newStage.close(); // 새 창을 닫는 방식으로 돌아가기
                onBack.run(); // 메인 페이지로 돌아가기
            } catch (Exception ex) {
                System.out.println("응 아니야");
                ex.printStackTrace(); // 예외 발생 시 스택 트레이스를 출력
            }
        });

        // 날짜 레이블과 X 버튼을 레이아웃에 추가
        topLayout.getChildren().addAll(dateLabel, backButton);
        mainLayout.getChildren().addAll(topLayout, stickerPane);

        // 하단 버튼 영역
        HBox bottomButtonBox = new HBox(300);
        bottomButtonBox.setAlignment(Pos.BOTTOM_CENTER);
        bottomButtonBox.setPadding(new Insets(10));

        // 경계선 이미지 추가 (중앙에 배치)
        Image borderImage = new Image("styles/line1.png"); // 경계선 이미지 경로 설정
        ImageView borderImageView = new ImageView(borderImage);
        // 경계선 이미지 초기 위치 설정
        borderImageView.setLayoutX(400); // X축 위치 설정
        borderImageView.setLayoutY(-150); // Y축 위치 설정

        // 경계선 이미지를 스티커 패널에 추가 (중앙 배치)
        stickerPane.getChildren().add(borderImageView);

        // 경계선 이미지 추가 (중앙에 배치)
        Image borderImage2 = new Image("styles/line2.png"); // 경계선 이미지 경로 설정
        ImageView borderImageView2 = new ImageView(borderImage2);
        // 경계선 이미지 초기 위치 설정
        borderImageView2.setLayoutX(-122); // X축 위치 설정
        borderImageView2.setLayoutY(-50); // Y축 위치 설정

        // 경계선 이미지를 스티커 패널에 추가 (중앙 배치)
        stickerPane.getChildren().add(borderImageView2);

        // 공유 버튼
        HBox leftButtonBox = new HBox(15); // 간격
        leftButtonBox.setAlignment(Pos.BOTTOM_LEFT);
        Button shareButton = createIconButton("styles/share.png", "공유");
        shareButton.setOnAction(e -> {
            try {
                System.out.println("공유 버튼 클릭!");
            } catch (Exception ex) {
                System.out.println("응 아니야");
                ex.printStackTrace();
            }
        });
        leftButtonBox.getChildren().add(shareButton);

        // 우측 버튼: 텍스트, 스티커, 사진, 저장
        HBox rightButtonBox = new HBox(10);
        rightButtonBox.setAlignment(Pos.BOTTOM_RIGHT);
        Button textButton = createIconButton("styles/text.png", "텍스트");
        Button stickerButton = createIconButton("styles/sticker.png", "스티커");
        Button photoButton = createIconButton("styles/upload.png", "사진");
        Button saveButton = createIconButton("styles/save.png", "저장");
        rightButtonBox.getChildren().addAll(textButton, stickerButton, photoButton, saveButton);

        // 버튼 박스 배치
        bottomButtonBox.getChildren().addAll(leftButtonBox, rightButtonBox);
        mainLayout.getChildren().add(bottomButtonBox);
        root1.getChildren().add(mainLayout);

        // 사진 추가 버튼 이벤트
        photoButton.setOnAction(e -> {
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters()
                        .add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
                File selectedFile = fileChooser.showOpenDialog(newStage); // 새 창 대신 기존 Stage 사용
                if (selectedFile != null) {
                    Image image = new Image("file:" + selectedFile.getAbsolutePath());
                    addStickerToDiary(image);
                }
            } catch (Exception ex) {
                System.out.println("응 아니야");
                ex.printStackTrace();
            }
        });

        // 스티커 추가 버튼 이벤트
        stickerButton.setOnAction(e -> {
            try {
                toggleStickerPanel();
            } catch (Exception ex) {
                System.out.println("응 아니야");
                ex.printStackTrace();
            }
        });

        // 텍스트 추가 버튼 이벤트
        textButton.setOnAction(e -> {
            try {
                addTextBoxToDiary();
            } catch (Exception ex) {
                System.out.println("응 아니야");
                ex.printStackTrace();
            }
        });

        this.scene = new Scene(root1, 800, 600);
        newStage.setScene(scene); // 새 창에 Scene 설정
        newStage.setTitle(diaryTitle); // 창 제목 설정
        newStage.show(); // 새 창을 띄우기
    } // 다이어리 페이지 생성자
      // 다이어리 페이지 생성자

    private void toggleStickerPanel() {
        StackPane root2 = (StackPane) scene.getRoot();

        // 스티커 판 가시성 조정
        if (isStickerPanelVisible) {
            root2.getChildren().removeIf(node -> node instanceof VBox && "sticker-panel".equals(node.getId()));
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
        root2.getChildren().add(stickerPanel);

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

    // 화면에 스티커 추가
    private void addStickerToDiary(Image stickerImage) {
        ImageView sticker = new ImageView(stickerImage);
        sticker.setPreserveRatio(true);
        sticker.setFitWidth(100);
        sticker.setFitHeight(100);

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

    // 드래그와 크기 조정을 위한 클래스
    private static class Delta {
        double x, y;
    }

    // 레이아웃을 반환하는 getLayout() 메서드 추가
    public StackPane getLayout() {
        return root; // DiaryPage의 레이아웃을 반환
    }
}