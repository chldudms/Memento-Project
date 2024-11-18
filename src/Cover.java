import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cover extends VBox {

    public Cover(Runnable onCoverClick) {
        // 배경색이 #FFCDE1인 Rectangle 생성
        Rectangle background1 = new Rectangle(360, 640);  
        Rectangle background2 = new Rectangle(60, 640);  
        background1.setFill(Color.web("#FFCDE1"));
        background2.setFill(Color.web("#F875AA"));

        // 기본 이미지와 마우스 오버 이미지 경로
        Image image1 = new Image("styles/logo.png");  
        ImageView imageView1 = new ImageView(image1);
        
        Image defaultImage = new Image("styles/nextBtn.png");  // 기본 아이콘 이미지
        Image hoverImage = new Image("styles/pinkNextBtn.png");  // 마우스 오버 아이콘 이미지
        ImageView imageView2 = new ImageView(defaultImage);

        // 이미지 크기 설정
        imageView1.setFitWidth(300);  
        imageView1.setPreserveRatio(true);  
        imageView2.setFitWidth(50);  
        imageView2.setFitHeight(50);  
        imageView2.setPreserveRatio(true);  

        // 레이아웃 설정
        HBox topLayout = new HBox();
        topLayout.getChildren().add(background2);  
        topLayout.getChildren().add(imageView1);  
        topLayout.getChildren().add(background1);  

        topLayout.setStyle("-fx-background-color: #FFCDE1;");  

        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(360, 640);  

        // imageView2 위치 설정
        imageView2.setTranslateX(-50);  
        imageView2.setTranslateY(-70);  

        stackPane.getChildren().add(imageView2);  
        stackPane.setStyle("-fx-background-color: transparent;");  

        // VBox에 레이아웃 추가
        this.getChildren().add(topLayout);  
        this.getChildren().add(stackPane);  

        // 아이콘에 마우스 오버 이벤트 추가
        imageView2.setOnMouseEntered(event -> {
            imageView2.setImage(hoverImage);  // 마우스 오버 시 이미지 교체
        });

        // 아이콘에서 마우스가 떠났을 때 이벤트 추가
        imageView2.setOnMouseExited(event -> {
            imageView2.setImage(defaultImage);  // 마우스가 떠나면 기본 이미지로 복원
        });

        // 아이콘 클릭 시 메인 화면으로 전환하는 이벤트 추가
        imageView2.setOnMouseClicked(event -> onCoverClick.run());
        
        // VBox 클릭 이벤트 무시
        this.setOnMouseClicked(event -> { });
    }
}
