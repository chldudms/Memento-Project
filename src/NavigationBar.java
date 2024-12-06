import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class NavigationBar {
    private HBox navigationBar; // 네비게이션 바 전체를 포함하는 HBox
    private Main mainApp; // Main 클래스의 인스턴스를 저장
    private StackPane selectedSection; // 현재 선택된 섹션을 저장하는 변수
    private ImageView homeImageView; // 홈 섹션의 이미지 뷰
    private ImageView toDoImageView; // To-Do 섹션의 이미지 뷰
    private ImageView loginImageView; // 로그인 섹션의 이미지 뷰

    // 생성자는 Main 클래스를 매개변수로 받음
    public NavigationBar(StackPane homepage, StackPane toDopage, StackPane loginpage, Main mainApp) {
        this.mainApp = mainApp; // Main 클래스의 참조 저장

        // 하단 네비게이션 바 섹션 (홈, To-Do, 로그인)
        navigationBar = new HBox(); // HBox를 생성하여 네비게이션 바 구성
        navigationBar.setPadding(new Insets(5)); // 패딩 설정
        navigationBar.setSpacing(40); // 섹션 사이 간격 설정
        navigationBar.setStyle("-fx-background-color:  #FFCDE1; -fx-pref-height: 40px;"); // 배경색과 높이 설정
        navigationBar.setAlignment(Pos.CENTER); // 네비게이션 바 정렬 설정

        // 각각의 네비게이션 섹션 생성 (홈, To-Do, 로그인)
        StackPane homeSection = createNavSection("styles/home.png", "styles/homePink.png", homepage, true); // 홈 섹션
        StackPane toDoSection = createNavSection("styles/todo.png", "styles/todoPink.png", toDopage, false); // To-Do 섹션
        StackPane loginSection = createNavSection("styles/login.png", "styles/loginPink.png", loginpage, false); // 로그인
                                                                                                                 // 섹션

        // 네비게이션 바에 섹션 추가
        navigationBar.getChildren().addAll(homeSection, toDoSection, loginSection);

        // 초기에는 홈 섹션을 선택 상태로 설정
        selectSection(homeSection, homeImageView, "styles/homePink.png");
    }

    // 네비게이션 섹션 생성 메서드
    private StackPane createNavSection(String imagePath, String selectedImagePath, StackPane page, boolean isHome) {
        StackPane section = new StackPane(); // 섹션을 생성
        section.setPadding(new Insets(5)); // 패딩 설정
        section.setStyle("-fx-background-color: #FFCDE1; -fx-border-color: transparent; -fx-border-width: 5px;");
        section.setMinWidth(80); // 최소 너비 설정
        section.setMaxWidth(120); // 최대 너비 설정

        // 아이콘 이미지 추가
        Image iconImage = new Image(imagePath);
        ImageView imageView = new ImageView(iconImage);

        // 로그인 아이콘만 크기를 조정
        if (imagePath.contains("login")) {
            imageView.setFitHeight(50); // 로그인 아이콘의 높이 설정
            imageView.setFitWidth(50); // 로그인 아이콘의 너비 설정
        } else {
            imageView.setFitHeight(30); // 다른 아이콘의 높이 설정
            imageView.setFitWidth(30); // 다른 아이콘의 너비 설정
        }

        section.getChildren().add(imageView); // 섹션에 이미지 뷰 추가

        // 아이콘 참조를 저장
        if (isHome) {
            homeImageView = imageView; // 홈 섹션 이미지 뷰 저장
        } else if (imagePath.contains("todo")) {
            toDoImageView = imageView; // To-Do 섹션 이미지 뷰 저장
        } else if (imagePath.contains("login")) {
            loginImageView = imageView; // 로그인 섹션 이미지 뷰 저장
        }

        // 섹션 클릭 시 처리
        section.setOnMouseClicked(e -> {
            // 페이지가 null이 아닌 경우에만 실행
            if (page != null) {
                mainApp.getMainLayout().setCenter(page); // 클릭한 페이지를 메인 레이아웃의 중앙에 설정
                selectSection(section, imageView, selectedImagePath); // 클릭된 섹션을 선택 상태로 설정
            } else {
                System.out.println("Page is null!"); // 페이지가 null일 경우 디버깅 메시지 출력
            }
        });

        return section; // 생성된 섹션 반환
    }

    // 선택된 섹션을 설정하는 메서드
    private void selectSection(StackPane section, ImageView currentImageView, String selectedImagePath) {
        // 이전 섹션의 이미지를 원래 상태로 복원
        if (selectedSection != null && selectedSection != section) {
            if (selectedSection == homeImageView.getParent()) {
                homeImageView.setImage(new Image("styles/home.png"));
            } else if (selectedSection == toDoImageView.getParent()) {
                toDoImageView.setImage(new Image("styles/todo.png"));
            } else if (selectedSection == loginImageView.getParent()) {
                loginImageView.setImage(new Image("styles/login.png"));
            }
        }

        // 현재 선택된 섹션의 이미지를 선택 상태 이미지로 변경
        currentImageView.setImage(new Image(selectedImagePath));
        selectedSection = section; // 선택된 섹션을 현재 섹션으로 업데이트
    }

    // 네비게이션 바 레이아웃 반환
    public HBox getLayout() {
        return navigationBar;
    }
}
