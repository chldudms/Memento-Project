import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class LoginPage {
    private StackPane layout;

    public LoginPage() {
        // Login Page content
        layout = new StackPane();
        
        // GridPane을 사용하여 입력 필드 및 버튼 배치
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER); // 중앙 정렬
        // 아이디 입력 필드
        Label usernameLabel = new Label("아이디:");
        TextField usernameField = new TextField();
        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);
        // 비밀번호 입력 필드
        Label passwordLabel = new Label("비밀번호:");
        PasswordField passwordField = new PasswordField();
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);
        // 로그인 버튼
        Button loginButton = new Button("로그인");
        grid.add(loginButton, 0, 2);
        // 가입 버튼
        Button joinButton = new Button("가입하기");
        grid.add(joinButton, 1, 2);
        // 그리드를 레이아웃에 추가
        layout.getChildren().add(grid);
        layout.setStyle("-fx-background-color:  #FFFFFF;"); // 배경색 설정
    }

    public StackPane getLayout() {
        return layout;
    }
}