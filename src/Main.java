import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 기본 레이아웃
        BorderPane borderPane = new BorderPane();

        // 각 탭의 콘텐츠를 생성
        StackPane homeContent = createHomeContent();
        StackPane todoContent = createTodoContent();
        StackPane myPageContent = createMyPageContent();

        // 기본적으로 홈 콘텐츠로 시작
        borderPane.setCenter(homeContent); // 기본 콘텐츠 설정

        // 하단 내비게이션 바 생성
        HBox navigationBar = createNavigationBar(borderPane, homeContent, todoContent, myPageContent);
        borderPane.setBottom(navigationBar);

        // Scene 생성, 폰 비율로 크기 조정
        Scene scene = new Scene(borderPane, 360, 640); // 예: 360x640 폰 화면 비율
        scene.getStylesheets().add(getClass().getResource("styles/styles.css").toExternalForm());

        primaryStage.setTitle("Memento Diary");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // 홈 콘텐츠 생성
    private StackPane createHomeContent() {
        StackPane homeContent = new StackPane();
        homeContent.getChildren().add(new Label("홈 페이지"));
        return homeContent;
    }

    // 투두 콘텐츠 생성
    private StackPane createTodoContent() {
        StackPane todoContent = new StackPane();
        todoContent.getChildren().add(new Label("투두 리스트"));
        return todoContent;
    }

    // 마이페이지 콘텐츠 생성
    private StackPane createMyPageContent() {
        StackPane myPageContent = new StackPane();
        myPageContent.getChildren().add(new Label("마이 페이지"));
        return myPageContent;
    }

    // 내비게이션 바 생성
    private HBox createNavigationBar(BorderPane borderPane, StackPane home, StackPane todo, StackPane myPage) {
        HBox navigationBar = new HBox();
        navigationBar.setSpacing(50); // 탭 간 간격 설정

        // 각 탭 버튼을 생성
        Text homeButton = createTab("홈", e -> borderPane.setCenter(home));
        Text todoButton = createTab("투두", e -> borderPane.setCenter(todo));
        Text myPageButton = createTab("마이페이지", e -> borderPane.setCenter(myPage));

        // 내비게이션 바에 버튼 추가
        navigationBar.getChildren().addAll(homeButton, todoButton, myPageButton);
        navigationBar.setStyle("-fx-background-color: #3f51b5;"); // 배경색 설정
        navigationBar.setPrefHeight(60); // 바 높이 설정

        // 중앙 정렬 설정
        navigationBar.setAlignment(javafx.geometry.Pos.CENTER); // 중앙 정렬

        // 바가 화면 하단을 차지하도록 설정
        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        navigationBar.getChildren().add(spacer);

        return navigationBar;
    }

    // 탭 생성 메소드
    private Text createTab(String text, javafx.event.EventHandler<javafx.scene.input.MouseEvent> handler) {
        Text tab = new Text(text);
        tab.setFill(Color.WHITE);
        // 오른쪽으로 이동을 위한 패딩 추가
        tab.setStyle("-fx-padding: 30px 30px; -fx-font-size: 16px;"); // 텍스트 스타일
        tab.setOnMouseClicked(handler);
        return tab;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
