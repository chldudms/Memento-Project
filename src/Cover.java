import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cover extends VBox {  // VBox를 사용하여 이미지와 배경을 수직으로 배치
    
    public Cover(Runnable onCoverClick) {
        // 배경색이 #FFCDE1인 Rectangle 생성
        Rectangle background1 = new Rectangle(450, 667);  // 크기 설정
        Rectangle background2 = new Rectangle(65, 667);  // 크기 설정
        background1.setFill(Color.web("#FFCDE1"));
        background2.setFill(Color.web("#F875AA"));

        // 이미지 추가
        Image image = new Image("styles/logo.png");  // 이미지 경로 확인
        ImageView imageView = new ImageView(image);

        // 이미지 너비
        imageView.setFitWidth(380);  // 이미지 너비 설정
        imageView.setPreserveRatio(true);  // 비율 유지

        // 이미지가 background2와 겹치지 않도록 별도의 레이아웃을 사용
        HBox topLayout = new HBox();
        topLayout.getChildren().add(background2);  // background2는 왼쪽에 배치
        topLayout.getChildren().add(imageView);  // 그 오른쪽에 이미지 배치
        topLayout.getChildren().add(background1);  // background1은 그 오른쪽에 배치

        // topLayout의 배경색을 설정하여 이미지 배경에 색깔을 추가
        topLayout.setStyle("-fx-background-color: #FFCDE1;");  // 원하는 색상으로 설정

        // VBox에 이미지와 배경을 추가
        this.getChildren().add(topLayout);

        // 클릭 시 메인 화면으로 전환하는 이벤트 추가
        this.setOnMouseClicked(event -> onCoverClick.run());
    }
}
