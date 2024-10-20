import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // Label 생성
        Label label = new Label("안녕하세요, JavaFX!");
        
        // 버튼 생성
        Button button = new Button("클릭하세요!");
        button.setOnAction(event -> {
            label.setText("버튼이 클릭되었습니다!");
        });
        
        // 레이아웃 설정
        VBox vbox = new VBox(10, label, button);
        vbox.setPrefSize(300, 200);
        
        // Scene 생성 및 Stage에 설정
        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX 예제");
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
