import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MyPage {
    private StackPane layout; // MyPage 레이아웃
    private StackPane loginPageLayer; // LoginPage 레이아웃을 담을 StackPane

    public MyPage(String username, String regDate, Stage primaryStage) {
        // 최상위 레이아웃
        layout = new StackPane();

        // 콘텐츠 레이아웃: VBox
        VBox contentBox = new VBox();
        contentBox.setPadding(new Insets(20));
        contentBox.setSpacing(20); // 요소 간 간격
        contentBox.setAlignment(Pos.CENTER); // 중앙 정렬

        // 이미지 경로 
        Image profileImage = new Image("file:C:/Users/ungesxy.BOOK-CCUUTHN80B.000/Desktop/Memento-Project/src/styles/default.jpg");

        // ImageView에 이미지 설정
        ImageView profileImageView = new ImageView(profileImage);
        profileImageView.setFitWidth(100); // 이미지 크기 조정
        profileImageView.setFitHeight(100); // 이미지 크기 조정
        profileImageView.setPreserveRatio(true); // 비율 유지

        // 이미지 동그랗게 만들기 위해 Circle로 클리핑
        Circle clip = new Circle(50, 50, 50); // 반지름을 50으로 설정하여 동그란 모양
        profileImageView.setClip(clip);

        // 사용자 아이디 표시
        Text userInfo = new Text("Name: " + username);
        userInfo.setFont(Font.font("Arial", FontWeight.NORMAL, 18));

        // 가입 날짜 표시
        Text joinDateText = new Text("Join Date: " + regDate);
        joinDateText.setFont(Font.font("Arial", FontWeight.NORMAL, 18));

        // 로그아웃 버튼
        Button logoutButton = new Button("Logout");
        logoutButton.setStyle("-fx-font-size: 16px; -fx-background-color: #FFCDE1; -fx-text-fill: #F875AA;");

        // 로그아웃 버튼 클릭 이벤트
        logoutButton.setOnAction(e -> {
            // LoginPage 레이아웃을 처음 클릭 시 생성
            if (loginPageLayer == null) { 
                loginPageLayer = new StackPane();
                LoginPage loginPage = new LoginPage(primaryStage);
                loginPageLayer.getChildren().add(loginPage.getLayout());
                layout.getChildren().add(loginPageLayer); // layout에 loginPageLayer 추가
            }

            // LoginPage 레이아웃을 표시
            loginPageLayer.setVisible(true);
        });

        // VBox에 요소 추가
        contentBox.getChildren().addAll(profileImageView, userInfo,  joinDateText, logoutButton); 

        // StackPane에 VBox 추가
        layout.getChildren().add(contentBox);
    }

    public StackPane getLayout() {
        return layout;
    }
}
