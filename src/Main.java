
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.shape.Line;

public class Main extends Application {
    private StackPane homeSection, toDoSection, loginSection;
    private BorderPane mainLayout;

    @Override
    public void start(Stage primaryStage) {
        // 페이지 생성 (홈, 할 일, 로그인)
        HomePage homePage = new HomePage(this);
        ToDoPage toDoPage = new ToDoPage();
        LoginPage loginPage = new LoginPage();

        // 하단 네비게이션 바 생성
        NavigationBar navigationBar = new NavigationBar(
            homePage.getLayout(), 
            toDoPage.getLayout(), 
            loginPage.getLayout(), 
            this
        );
        
        // 로고가 있는 상단 레이아웃 생성
        VBox topLayout = createTopLayout();

        // 레이아웃 설정 및 하단 네비게이션 바 추가
        mainLayout = new BorderPane();
        mainLayout.setBottom(navigationBar.getLayout());
        mainLayout.setTop(topLayout);  // 로고가 있는 상단 레이아웃 설정
        mainLayout.setCenter(homePage.getLayout());  // 초기 화면은 홈 페이지
        mainLayout.setStyle("-fx-background-color: #FFFFFF;");

        // 메인 레이아웃으로 씬 생성
        Scene mainScene = new Scene(mainLayout, 375, 667);
        
        // 스테이지 설정
        primaryStage.setTitle("MementoDiary");
        primaryStage.setScene(mainScene);
      //  primaryStage.setResizable(false);  // 창 크기 조절 비활성화
        primaryStage.show();
    }

    // 상단 로고 및 경계선 생성
    private VBox createTopLayout() {
        VBox topLayout = new VBox();  // VBox 사용
        topLayout.setPadding(new Insets(20));
        topLayout.setAlignment(Pos.CENTER);  // 중앙 정렬로 변경

        // "Memento" 로고 생성
        Text logo = new Text("Memento");
        logo.setFont(Font.font("", FontWeight.NORMAL, 35));  // 폰트 굵기 크기 설정
        logo.setFill(Color.SKYBLUE);  // 폰트 색상 설정
        // VBox의 상하 간격 설정
        topLayout.setSpacing(10);  // 간격
 

        // 로고 아래에 경계선 생성
        Line borderLine = new Line();
        borderLine.setStartX(0);
        borderLine.setEndX(300); // 경계선 길이를 300으로 설정
        borderLine.setStroke(Color.PINK);  // 경계선 색상 설정
        borderLine.setStrokeWidth(2);  // 경계선 두께 설정

        // 중앙 정렬을 위한 HBox 생성
        HBox lineContainer = new HBox();
        lineContainer.setAlignment(Pos.CENTER); // 중앙 정렬
        lineContainer.getChildren().add(borderLine); // 경계선 추가

        // 로고와 경계선을 레이아웃에 추가
        topLayout.getChildren().addAll(logo, lineContainer);

        return topLayout;
    }

    // 다른 클래스에서 사용할 mainLayout의 Getter
    public BorderPane getMainLayout() {
        return mainLayout;
    }

    public static void main(String[] args) {
        launch(args);
    }
}