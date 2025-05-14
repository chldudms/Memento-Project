import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.util.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ToDoPage {
    private StackPane layout;  // 전체 레이아웃을 담는 StackPane
    private VBox checklistContainer; // 체크리스트 항목을 담는 컨테이너
    private ImageView addImageView;  // 체크리스트 추가 버튼 이미지
    private VBox mainLayout; // 주요 레이아웃을 관리하는 VBox
    private Label dateLabel; // 현재 날짜와 시간을 표시하는 레이블

    public ToDoPage() {
       // 레이아웃과 체크리스트 컨테이너 초기화
        layout = new StackPane();
        checklistContainer = new VBox(10); // 항목 간 간격을 10픽셀로 설정
        checklistContainer.setAlignment(Pos.CENTER);  // 체크리스트를 중앙 정렬

        // 추가 버튼 이미지를 로드
        Image addImage = null;
        try {
            addImage = new Image("styles/plus.png");
        } catch (Exception e) {
            System.out.println("Image loading failed: " + e.getMessage());
        }
        // 이미지가 정상적으로 로드되었을 경우
        if (addImage != null) {
            addImageView = new ImageView(addImage);
            addImageView.setFitWidth(40);  // 이미지 너비 조정
            addImageView.setFitHeight(35);  // 이미지 높이 조정
            addImageView.setOnMouseClicked(e -> addChecklistItem()); // 이미지 클릭 시 체크리스트 항목 추가
        } else {
            System.out.println("Image could not be loaded."); // 이미지가 null일 경우 메시지 출력
        }

        // 메인 레이아웃을 위한 VBox 생성
        mainLayout = new VBox(8); // 요소 간 간격을 8픽셀로 설정
        mainLayout.setAlignment(Pos.TOP_CENTER);// 메인 레이아웃을 위쪽 중앙으로 정렬

        // 제목 박스를 위한 BorderPane 생성
        BorderPane titleBox = new BorderPane();
        Label titleLabel = new Label("To Do"); // 제목 레이블
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-padding: 10; -fx-text-fill: white;"); // Title styling
        titleBox.setCenter(titleLabel); // 제목을 박스 중앙에 배치
        titleBox.setStyle("-fx-background-color: #F875AA; -fx-border-width: 0; -fx-pref-height: 30; -fx-pref-width: 140;"); // Box styling

        // 현재 날짜와 시간을 표시하는 레이블 생성 및 스타일 설정
        dateLabel = new Label();
        dateLabel.setStyle("-fx-font-size: 25px; -fx-text-fill: #FF3EA5; -fx-text-weight: bold;"); // 스타일 설정
        updateDateTime(); // 현재 날짜와 시간으로 초기화
         // 체크리스트 컨테이너를 위한 ScrollPane 생성
        ScrollPane scrollPane = new ScrollPane(checklistContainer);
        scrollPane.setFitToWidth(true); // ScrollPane 너비에 맞춤
        scrollPane.setPrefHeight(350); // ScrollPane 높이 설정

        scrollPane.setStyle("-fx-background-color: white; -fx-border-color: transparent; -fx-background: white;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // 세로 스크롤바 숨김
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // 가로 스크롤바 숨김

       
        checklistContainer.setStyle("-fx-background-color: white;"); // 체크리스트 컨테이너 배경색 설정

         // 메인 레이아웃에 제목, 날짜, 추가 버튼, 스크롤 패인을 추가
        mainLayout.getChildren().addAll(titleBox, dateLabel, addImageView, scrollPane); // Add title, date, add button, and scroll pane to the main layout

        layout.getChildren().add(mainLayout); // 전체 레이아웃에 메인 레이아웃 추가 
        layout.setStyle("-fx-background-color: #FFFFFF;"); 

        // 1초마다 날짜와 시간을 업데이트
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateDateTime()));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    // 체크리스트 항목을 추가하는 메서드
    private void addChecklistItem() {
        // 로그인 상태가 아닌 경우 메시지 출력
        if(!Session.isLoggedIn()){
            System.out.println("로그인 후 이용할 수 있습니다.");
            return;
        }
         // 체크리스트 항목과 관련된 레이아웃 생성
        HBox checklistItemContainer = new HBox(10); 
        checklistItemContainer.setAlignment(Pos.CENTER); 
        CheckBox checklistItem = new CheckBox();
        checklistItem.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2; -fx-min-width: 12; -fx-min-height: 12;"); // Make sure the CheckBox is white

        // 체크박스와 텍스트를 위한 StackPane 생성
        StackPane lineTextContainer = new StackPane();

        Line line = new Line(0, 0, 200, 0); // 텍스트 아래 줄
        line.setStrokeWidth(2);
        line.setStroke(Color.BLACK);

        line.setTranslateY(10); // 줄을 아래로 이동

        TextField textField = new TextField(); // 사용자가 텍스트를 입력할 수 있는 필드
        textField.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;"); // 투명 배경 및 테두리 설정
        textField.setFont(Font.font("Arial", 17));
        textField.setPrefWidth(190); // 텍스트 필드 너비 설정

        lineTextContainer.getChildren().addAll(line, textField); // Line 위에 텍스트 필드를 추가

        checklistItemContainer.getChildren().addAll(checklistItem, lineTextContainer); // HBox에 항목 추가
        checklistContainer.getChildren().add(checklistItemContainer); // 전체 체크리스트에 항목 추가

        // 체크박스가 선택될 때 항목을 제거하는 이벤트 처리기 추가
        checklistItem.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // 1초 후에 항목을 제거
                Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(1), 
                    event -> {
                        checklistContainer.getChildren().remove(checklistItemContainer);
                        
                        // 체크리스트 항목이 7개 이하일 경우, 추가 버튼을 체크리스트 내에 추가
                        if (checklistContainer.getChildren().size() <= 7) {
                            checklistContainer.getChildren().remove(addImageView); // 기존에 있는 버튼 제거
                            checklistContainer.getChildren().add(addImageView); // 새롭게 추가
                            addImageView.setTranslateY(-5); // 버튼 위치 약간 위로 이동
                        }

                        else {
                            // 체크리스트 항목이 8개 이상일 경우, 추가 버튼을 메인 레이아웃으로 이동
                            if (!mainLayout.getChildren().contains(addImageView)) {
                                checklistContainer.getChildren().remove(addImageView);
                                mainLayout.getChildren().add(addImageView); // 메인 레이아웃에 추가
                            }
                        }
                    }
                ));

                // 1초 후에 실행될 타임라인을 시작
                timeline.play();
            }
        });

        // 새로운 체크리스트 항목이 8개 이하일 때, 추가 버튼을 항목 아래로 배치
        if (checklistContainer.getChildren().size() <= 8) {
            checklistContainer.getChildren().remove(addImageView); // 추가 버튼을 체크리스트 컨테이너에서 제거
            checklistContainer.getChildren().add(addImageView);  // 체크리스트 아래에 추가 버튼 다시 배치
        } 
        else {
            // 체크리스트 항목이 8개 이상일 경우, 추가 버튼을 원래 위치(메인 레이아웃)으로 이동
            if (!mainLayout.getChildren().contains(addImageView)) {
                checklistContainer.getChildren().remove(addImageView); // 추가 버튼을 체크리스트에서 제거
                mainLayout.getChildren().add(addImageView); // 메인 레이아웃에 추가
            }
            addImageView.setTranslateY(-8); // 추가 버튼 위치를 약간 위로 조정
        }
    }

    // 현재 날짜와 시간을 업데이트하는 메소드
    private void updateDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // 날짜 형식을 "yyyy-MM-dd"로 지정
        LocalDateTime now = LocalDateTime.now(); // 현재 날짜와 시간을 가져옴
        dateLabel.setText(now.format(formatter)); // 날짜 레이블에 현재 날짜 설정, 날짜 레이블에 업데이트된 날짜 표시
    }

    public StackPane getLayout() {
        return layout;
    }
}