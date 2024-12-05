import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPage {
    private static StackPane layout;
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
        joinLink.setStyle("-fx-text-fill: #6FC8FF; -fx-font-size: 20px;");
        grid.add(joinLink, 0, 3, 2, 1);
        GridPane.setHalignment(joinLink, javafx.geometry.HPos.CENTER);

        // 링크 클릭 시 JoinPage 레이어 표시
        joinLink.setOnAction(e -> {
            if (joinPageLayer == null) { // JoinPage 레이어를 처음 클릭 시 생성
                joinPageLayer = new StackPane();
                JoinPage joinPage = new JoinPage(primaryStage);
                joinPageLayer.getChildren().add(joinPage.getLayout());
                layout.getChildren().add(joinPageLayer);
            }
            joinPageLayer.setVisible(true); // JoinPage 레이어 표시
        });

        // 로그인 버튼 클릭 처리
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            DB db = new DB();
            if (db.checkUser(username, password)) {
                System.out.println("로그인 성공!");

                // 로그인 성공 시 Session에 username 저장
                Session.login(username);
                
                String regDate = db.getUserRegDate(username);
                if (regDate != null) {
                    // MyPage로 전환 (공통 레이아웃 유지)
                    MyPage myPage = new MyPage(username, regDate, primaryStage);
                    layout.getChildren().clear(); // 기존 콘텐츠 제거
                    layout.getChildren().add(myPage.getLayout()); // MyPage 콘텐츠 추가
                } else {
                    System.out.println("가입 날짜를 가져오지 못했습니다.");
                }
            } else {
                System.out.println("아이디 또는 비밀번호가 잘못되었습니다.");
            }
            db.close();
        });

        // 그리드 추가
        mainContent.getChildren().add(grid);

        // 메인 레이아웃 구성
        layout.getChildren().add(mainContent);
        layout.setStyle("-fx-background-color: #FFFFFF;");
    }

    // 사용자 정보를 데이터베이스에서 검증하는 메서드
    private boolean validateUser(String username, String password) {
        String url = "jdbc:mysql://localhost:3306/userdb"; // 데이터베이스 URL
        String user = "user"; // 데이터베이스 사용자명
        String pass = "1111"; // 데이터베이스 비밀번호

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?"; // 쿼리

        try (Connection conn = DriverManager.getConnection(url, user, pass);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery(); // 쿼리 실행

            // 사용자 존재 여부 확인
            return rs.next(); // 결과가 있으면 true 반환
        } catch (SQLException e) {
            System.err.println("로그인 실패: " + e.getMessage());
            return false; // 예외 발생 시 false 반환
        }
    }

    public static StackPane getLayout() {
        return layout;
    }
}