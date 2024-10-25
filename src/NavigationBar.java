import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

public class NavigationBar {
    private HBox navigationBar;
    private Main mainApp;
    private StackPane selectedSection;  // 현재 선택된 섹션을 저장하는 변수
    private ImageView homeImageView;
    private ImageView toDoImageView;
    private ImageView loginImageView;

    // Constructor now takes Main as a parameter
    public NavigationBar(StackPane homepage, StackPane toDopage, StackPane loginpage, Main mainApp) {
        this.mainApp = mainApp;  // Save reference to the Main class

        // Bottom Navigation Bar Sections (Home, To-Do, Login)
        navigationBar = new HBox();
        navigationBar.setPadding(new Insets(10));
        navigationBar.setSpacing(20);
        navigationBar.setStyle("-fx-background-color:  #FFCDE1;");  // Light pink color
        navigationBar.setAlignment(Pos.CENTER);

        // Create navigation sections with images
        StackPane homeSection = createNavSection("styles/home.png", "styles/homePink.png", homepage, true);
        StackPane toDoSection = createNavSection("styles/todo.png", "styles/todoPink.png", toDopage, false);
        StackPane loginSection = createNavSection("styles/login.png", "styles/loginPink.png", loginpage, false);

        // Add sections to the navigation bar
        navigationBar.getChildren().addAll(homeSection, toDoSection, loginSection);

        // Initialize with the home section selected
        selectSection(homeSection, homeImageView, "styles/homePink.png");
    }

    // Method to create a navigation section with an image
    private StackPane createNavSection(String imagePath, String selectedImagePath, StackPane page, boolean isHome) {
        StackPane section = new StackPane();
        section.setPadding(new Insets(10));
        section.setStyle("-fx-background-color: #FFCDE1; -fx-border-color: transparent; -fx-border-width: 5px;");
        section.setMinWidth(100);
        section.setMaxWidth(150);

        // 이미지 추가
        Image iconImage = new Image(imagePath);
        ImageView imageView = new ImageView(iconImage);

        // 로그인 아이콘만 이미지 크기가 작아서 조정
        if (imagePath.contains("login")) {
            imageView.setFitHeight(50);  // 로그인 아이콘의 크기 조정
            imageView.setFitWidth(50);
        } else {
            imageView.setFitHeight(30);  // 다른 아이콘 크기
            imageView.setFitWidth(30);
        }

        section.getChildren().add(imageView);

        // 아이콘에 대한 참조 저장
        if (isHome) {
            homeImageView = imageView;
        } else if (imagePath.contains("todo")) {
            toDoImageView = imageView;
        } else if (imagePath.contains("login")) {
            loginImageView = imageView;
        }

        // 섹션 클릭 시 선택 상태로 변경
        section.setOnMouseClicked(e -> {
            mainApp.getMainLayout().setCenter(page); // Update center content on click
            selectSection(section, imageView, selectedImagePath);  // 클릭된 섹션을 선택 상태로 설정
        });

        return section;
    }

    // 선택된 섹션을 설정하는 메서드
    private void selectSection(StackPane section, ImageView currentImageView, String selectedImagePath) {
        // 이전 섹션의 이미지를 원래 상태로 돌림
        if (selectedSection != null && selectedSection != section) {
            if (selectedSection == homeImageView.getParent()) {
                homeImageView.setImage(new Image("styles/home.png"));
            } else if (selectedSection == toDoImageView.getParent()) {
                toDoImageView.setImage(new Image("styles/todo.png"));
            } else if (selectedSection == loginImageView.getParent()) {
                loginImageView.setImage(new Image("styles/login.png"));
            }
        }

        // 현재 선택된 섹션의 이미지를 선택된 이미지로 변경
        currentImageView.setImage(new Image(selectedImagePath));
        selectedSection = section;  // 선택된 섹션을 현재 섹션으로 업데이트
    }

    public HBox getLayout() {
        return navigationBar;
    }
}