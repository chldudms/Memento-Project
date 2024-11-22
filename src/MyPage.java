import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MyPage {
    private VBox layout;
    private String username;
    private String regDate; // 가입 날짜 필드

    // 사용자 정보를 위한 클래스
    public class User {
        private String username;
        private String profileImage;
        private String regDate; // 가입 날짜 필드

        public User(String username, String profileImage, String regDate) {
            this.username = username;
            this.profileImage = profileImage;
            this.regDate = regDate;
        }

        public String getUsername() {
            return username;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public String getRegDate() {
            return regDate; // 가입 날짜 반환
        }
    }

    // MyPage 생성자에서 username과 regDate 전달받음
    public MyPage(String username, String regDate) {
        this.username = username;
        this.regDate = regDate;

        // 레이아웃 설정
        layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setSpacing(15);

        // "My Page" 제목
        Text title = new Text("My Page");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 30));

        // 사용자 아이디 표시
        Text userInfo = new Text("User: " + username);
        userInfo.setFont(Font.font("Arial", FontWeight.NORMAL, 18));

        // 프로필 이미지 표시 (예시로 텍스트로 표시)
        Text profileText = new Text("Profile Image: default.jpg");
        profileText.setFont(Font.font("Arial", FontWeight.NORMAL, 18));

        // 가입 날짜 표시
        Text joinDateText = new Text("Join Date: " + regDate);
        joinDateText.setFont(Font.font("Arial", FontWeight.NORMAL, 18));

        // 로그아웃 버튼
        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(e -> {
            System.out.println("Logout clicked!");
            // 로그아웃 동작 추가 (예: 로그인 페이지로 돌아가기)
        });

        // 레이아웃에 컴포넌트 추가
        layout.getChildren().addAll(title, userInfo, profileText, joinDateText, logoutButton);
    }

    // 레이아웃 반환 메서드
    public VBox getLayout() {
        return layout;
    }
}
