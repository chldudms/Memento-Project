import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;

public class LoginPage {
    private StackPane layout;
    private StackPane mainContent; // 메인 콘텐츠 StackPane
    private StackPane joinPageLayer; // JoinPage 레이어

    public LoginPage(Stage primaryStage) { // primaryStage를 매개변수로 받음
        // 메인 레이아웃 설정
        layout = new StackPane();
        mainContent = new StackPane();

        // GridPane을 사용하여 입력 필드 및 버튼 배치
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(15); // 요소 사이 간격 크게 조정
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER); // 중앙 정렬

        // 아이디 입력 필드 (크기 조정)
        TextField usernameField = new TextField();
        usernameField.setPromptText("아이디");
        usernameField.setStyle("-fx-font-size: 16px; -fx-border-color: #FFD8E4; -fx-border-width: 2px; -fx-background-color: #FFD8E4");
        usernameField.setPrefWidth(250);
        usernameField.setPrefHeight(50);
        grid.add(usernameField, 0, 0, 2, 1);

        // 비밀번호 입력 필드 (크기 조정)
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("비밀번호");
        passwordField.setStyle("-fx-font-size: 16px; -fx-border-color: #FFD8E4; -fx-border-width: 2px; -fx-background-color: #FFD8E4");
        passwordField.setPrefWidth(250);
        passwordField.setPrefHeight(50);
        grid.add(passwordField, 0, 1, 2, 1);

        // 로그인 버튼
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #FFCDE1; -fx-text-fill: #F875AA; -fx-font-size:20px; -fx-font-weight: bold; -fx-background-radius: 30px;");
        loginButton.setPrefWidth(120);
        loginButton.setPrefHeight(50);
        grid.add(loginButton, 0, 2, 2, 1);
        GridPane.setHalignment(loginButton, javafx.geometry.HPos.CENTER);

        // 가입하기 링크
        Hyperlink joinLink = new Hyperlink("Join");
        joinLink.setStyle("-fx-text-fill: #6FC8FF; -fx-font-size: 18px;");
        grid.add(joinLink, 0, 3, 2, 1);
        GridPane.setHalignment(joinLink, javafx.geometry.HPos.CENTER);

        // 링크 클릭 시 JoinPage 레이어 표시
        joinLink.setOnAction(e -> {
            if (joinPageLayer == null) { // JoinPage 레이어를 처음 클릭 시 생성
                joinPageLayer = new StackPane();
                JoinPage joinPage = new JoinPage(primaryStage);
                joinPageLayer.getChildren().add(joinPage.getLayout());
                joinPageLayer.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9);"); // 투명도 설정
                layout.getChildren().add(joinPageLayer);
            }
            joinPageLayer.setVisible(true); // JoinPage 레이어 표시
        });

        // 그리드를 메인 콘텐츠 StackPane에 추가
        mainContent.getChildren().add(grid);

        // 메인 레이아웃에 메인 콘텐츠 추가
        layout.getChildren().addAll(mainContent);
        layout.setStyle("-fx-background-color: #FFFFFF;");
    }

    public StackPane getLayout() {
        return layout;
    }
}
