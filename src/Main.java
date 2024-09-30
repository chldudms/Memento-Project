import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 레이블 생성
        Label label = new Label("버튼을 클릭하세요!");

        // 버튼 생성
        Button button = new Button("클릭 Me!");

        // 버튼 클릭 이벤트 처리
        button.setOnAction(e -> {
            label.setText("클릭되었습니다!");
        });

        // 레이아웃 설정
        VBox layout = new VBox(10); // 10px 간격
        layout.getChildren().addAll(label, button);

        // 장면 설정
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX GUI 예제");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
