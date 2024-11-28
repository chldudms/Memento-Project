import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.geometry.HPos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JoinPage {
        private StackPane layout;
        private StackPane loginPageLayer;

        public JoinPage(Stage primaryStage) {
                // Join Page content
                layout = new StackPane();

                // GridPane을 사용하여 입력 필드 및 버튼 배치
                GridPane grid = new GridPane();
                grid.setPadding(new Insets(20));
                grid.setVgap(15); // 요소 사이 간격 크게 조정
                grid.setHgap(10);
                grid.setAlignment(Pos.CENTER); // 중앙 정렬

                // 아이디 입력 필드
                TextField usernameField = new TextField();
                usernameField.setPromptText("아이디");
                usernameField.setStyle("-fx-font-size: 16px; -fx-border-color: #FFD8E4; -fx-border-width: 2px; -fx-background-color: #FFD8E4");
                usernameField.setPrefWidth(250);
                usernameField.setPrefHeight(50);
                grid.add(usernameField, 0, 0, 2, 1);

                // 비밀번호 입력 필드
                PasswordField passwordField = new PasswordField();
                passwordField.setPromptText("비밀번호");
                passwordField.setStyle("-fx-font-size: 16px; -fx-border-color: #FFD8E4; -fx-border-width: 2px; -fx-background-color: #FFD8E4");
                passwordField.setPrefWidth(250);
                passwordField.setPrefHeight(50);
                grid.add(passwordField, 0, 1, 2, 1);

                // 비밀번호 확인 필드
                PasswordField passwdCheckField = new PasswordField();
                passwdCheckField.setPromptText("비밀번호 확인");
                passwdCheckField.setStyle("-fx-font-size: 16px; -fx-border-color: #FFD8E4; -fx-border-width: 2px; -fx-background-color: #FFD8E4");
                passwdCheckField.setPrefWidth(250);
                passwdCheckField.setPrefHeight(50);
                grid.add(passwdCheckField, 0, 2, 2, 1);

                // 가입 버튼
                Button joinButton = new Button("Join");
                joinButton.setStyle("-fx-background-color: #FFCDE1; -fx-text-fill: #F875AA; -fx-font-size:20px; -fx-font-weight: bold; -fx-background-radius: 30px;");
                joinButton.setPrefWidth(120);
                joinButton.setPrefHeight(50);
                grid.add(joinButton, 0, 3, 2, 1);

                // 로그인 링크
                Hyperlink loginLink = new Hyperlink("Login");
                loginLink.setStyle("-fx-text-fill: #6FC8FF; -fx-font-size: 20px;");
                grid.add(loginLink, 0, 4, 2, 1);
                GridPane.setHalignment(loginLink, HPos.CENTER);

                // 가입 버튼 클릭 시 처리
                joinButton.setOnAction(e -> {
                        String username = usernameField.getText();
                        String password = passwordField.getText();
                        String confirmPassword = passwdCheckField.getText();
                
                        // 비밀번호 일치 여부 확인
                        if (!password.equals(confirmPassword)) {
                        System.out.println("비밀번호가 일치하지 않습니다.");
                        return; // 비밀번호 불일치 시 종료
                        }
                
                        // 데이터베이스에 사용자 정보 저장
                        saveUserToDatabase(username, password);
                
                        // 회원가입이 완료된 후 로그인 페이지로 이동
                        System.out.println("가입 완료! 로그인 페이지로 이동합니다.");
                
                        // LoginPage 레이어 생성
                        if (loginPageLayer == null) { // LoginPage 레이어가 없을 때만 새로 생성
                                loginPageLayer = new StackPane();
                                LoginPage loginPage = new LoginPage(primaryStage); // 새로운 로그인 페이지 생성
                                loginPageLayer.getChildren().add(loginPage.getLayout());
                                layout.getChildren().add(loginPageLayer);
                        }
                
                        // 로그인 페이지를 표시
                        loginPageLayer.setVisible(true);
                });
    

                // 링크 클릭 시 loginPage 레이어 표시
                loginLink.setOnAction(e -> {
                        if (loginPageLayer == null) { // JoinPage 레이어를 처음 클릭 시 생성
                                loginPageLayer = new StackPane();
                                LoginPage loginPage = new LoginPage(primaryStage);
                                loginPageLayer.getChildren().add(loginPage.getLayout());
                                layout.getChildren().add(loginPageLayer);
                        }
                        loginPageLayer.setVisible(true);
                });

                GridPane.setHalignment(joinButton, HPos.CENTER);
                layout.getChildren().add(grid);
                layout.setStyle("-fx-background-color: #FFFFFF;"); // 배경색 설정
        }

        // 사용자 정보를 데이터베이스에 저장하는 메서드
        private void saveUserToDatabase(String username, String password) {
                String url = "jdbc:mysql://localhost:3306/userdb"; // 데이터베이스 URL
                String user = "user"; // 데이터베이스 사용자명
                String pass = "1111"; // 데이터베이스 비밀번호 (실제 비밀번호로 변경)

                String sql = "INSERT INTO users (username, password) VALUES (?, ?)"; // 쿼리

                try (Connection conn = DriverManager.getConnection(url, user, pass);
                                PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, username);
                        pstmt.setString(2, password);
                        pstmt.executeUpdate(); // 쿼리 실행
                        System.out.println("회원가입 성공!");
                } catch (SQLException e) {
                        System.err.println("회원가입 실패: " + e.getMessage());
                }
        }

        public StackPane getLayout() {
                return layout;
        }
}