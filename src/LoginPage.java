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

    public LoginPage(Stage primaryStage) { // primaryStage를 매개변수로 받음
        // Login Page content
        layout = new StackPane();

        // GridPane을 사용하여 입력 필드 및 버튼 배치
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(15); // 요소 사이 간격 크게 조정
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER); // 중앙 정렬

        // 아이디 입력 필드 (크기 조정)
        TextField usernameField = new TextField();
        usernameField.setPromptText("아이디");
        usernameField.setStyle("-fx-font-size: 16px; -fx-border-color: #FFD8E4; -fx-border-width: 2px; -fx-background-color: #FFD8E4"); // 글꼴 크기, 테두리 설정
        usernameField.setPrefWidth(250); // 필드 너비 중앙으로 배치
        usernameField.setPrefHeight(50); // 필드 높이
        grid.add(usernameField, 0, 0, 2, 1); // Label 없이 바로 필드 추가

        // 비밀번호 입력 필드 (크기 조정)
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("비밀번호");
        passwordField.setStyle("-fx-font-size: 16px; -fx-border-color: #FFD8E4; -fx-border-width: 2px; -fx-background-color: #FFD8E4");
        passwordField.setPrefWidth(250); // 필드 너비 중앙으로 배치
        passwordField.setPrefHeight(50); // 필드 높이
        grid.add(passwordField, 0, 1, 2, 1); // Label 없이 바로 필드 추가

        // 로그인 버튼
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #FFCDE1; -fx-text-fill: #F875AA; -fx-font-size:20px; -fx-font-weight: bold; -fx-background-radius: 30px;");
        loginButton.setPrefWidth(120); // 버튼 너비 중앙으로 배치
        loginButton.setPrefHeight(50); // 버튼 높이
        grid.add(loginButton, 0, 2, 2, 1); // 버튼을 두 개의 열을 차지하도록 설정

        // 버튼을 GridPane의 중앙으로 정렬
        GridPane.setHalignment(loginButton, javafx.geometry.HPos.CENTER);

        // 가입하기 링크
        Hyperlink joinLink = new Hyperlink("Join");
        joinLink.setStyle("-fx-text-fill: #6FC8FF; -fx-font-size: 18px;");
        grid.add(joinLink, 0, 3, 2, 1); // 링크를 두 개의 열을 차지하도록 추가
        joinLink.setOnAction(e -> {
            // JoinPage로 전환
            JoinPage joinPage = new JoinPage(primaryStage);
            primaryStage.setScene(new Scene(joinPage.getLayout(), 375, 667)); // 새 장면으로 변경
        });
        

        // 링크를 GridPane의 중앙으로 정렬
        GridPane.setHalignment(joinLink, javafx.geometry.HPos.CENTER);

        // 그리드를 레이아웃에 추가
        layout.getChildren().add(grid);
        layout.setStyle("-fx-background-color: #FFFFFF;"); // 배경색 설정
    }

    public StackPane getLayout() {
        return layout;
    }
}