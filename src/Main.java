import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.shape.Line;

public class Main extends Application {
        private BorderPane mainLayout;
        private HomePage homePage; // HomePage 인스턴스를 클래스 변수로 설정

        @Override
        public void start(Stage primaryStage) {
                // Cover 화면 생성 및 클릭 이벤트 설정
                Cover cover = new Cover(this::switchToHomePage);

                // 페이지 생성 (홈, 할 일, 로그인, 가입)
                homePage = new HomePage(this); // 초기화된 HomePage 인스턴스 생성
                ToDoPage toDoPage = new ToDoPage();
                LoginPage loginPage = new LoginPage(primaryStage);
                JoinPage joinPage = new JoinPage(primaryStage);

                // 하단 네비게이션 바 생성
                NavigationBar navigationBar = new NavigationBar(
                                homePage.getLayout(),
                                toDoPage.getLayout(),
                                loginPage.getLayout(),
                                this);

                // 레이아웃 설정 및 초기화면을 Cover로 설정
                mainLayout = new BorderPane();
                mainLayout.setBottom(navigationBar.getLayout());
                mainLayout.setCenter(cover); // 초기화면을 Cover로 설정
                mainLayout.setStyle("-fx-background-color: #FFFFFF;");

                // 메인 레이아웃으로 씬 생성
                Scene mainScene = new Scene(mainLayout, 360, 640);

                // 스테이지 설정
                primaryStage.setTitle("MementoDiary");
                primaryStage.setScene(mainScene);
                primaryStage.show();
        }

        // HomePage로 전환하는 메소드
        private void switchToHomePage() {
                // 상단 로고 및 경계선이 포함된 레이아웃 생성
                VBox topLayout = createTopLayout();

                // HomePage로 전환할 때 로고와 선도 함께 나타나도록 설정
                mainLayout.setTop(topLayout); // HomePage로 전환할 때 상단 레이아웃을 추가
                mainLayout.setCenter(homePage.getLayout()); // 이미 생성된 homePage 인스턴스를 사용
        }

        // 상단 로고 및 경계선 생성
        private VBox createTopLayout() {
                VBox topLayout = new VBox();
                topLayout.setPadding(new Insets(20));
                topLayout.setAlignment(Pos.CENTER);

                // "Memento" 로고 생성
                Text logo = new Text("Memento");
                logo.setFont(Font.font("", FontWeight.NORMAL, 35));
                logo.setFill(Color.SKYBLUE);

                // 로고 아래에 경계선 생성
                Line borderLine = new Line();
                borderLine.setStartX(0);
                borderLine.setEndX(320);
                borderLine.setStroke(Color.PINK);
                borderLine.setStrokeWidth(2);

                // 중앙 정렬을 위한 HBox 생성
                HBox lineContainer = new HBox();
                lineContainer.setAlignment(Pos.CENTER);
                lineContainer.getChildren().add(borderLine);

                VBox.setMargin(lineContainer, new Insets(10, 0, 0, 0));

                // 로고와 경계선을 레이아웃에 추가
                topLayout.getChildren().addAll(logo, lineContainer);

                return topLayout;
        }

        // getMainLayout() 메서드 추가
        public BorderPane getMainLayout() {
                return mainLayout;
        }

        public static void main(String[] args) {
                launch(args);
        }
}