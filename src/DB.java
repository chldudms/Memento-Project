import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {
    // 데이터베이스 URL, 사용자명 및 비밀번호
    private static final String URL = "jdbc:mysql://localhost:3306/userdb"; // MySQL 서버의 주소와 포트
    private static final String USERNAME = "user"; // 데이터베이스 사용자명
    private static final String PASSWORD = "1111"; // 데이터베이스 비밀번호

    // 데이터베이스 연결 객체
    private Connection connection;

    public DB() {
        try {
            // MySQL JDBC 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 데이터베이스 연결
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("데이터베이스에 연결되었습니다.");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC 드라이버를 찾을 수 없습니다.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("데이터베이스 연결에 실패했습니다.");
            e.printStackTrace();
        }
    }

    // 사용자 정보 확인 메소드
    public boolean checkUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // 사용자 존재 여부 반환
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 연결 종료 메서드
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("데이터베이스 연결이 종료되었습니다.");
            }
        } catch (SQLException e) {
            System.err.println("데이터베이스 연결 종료에 실패했습니다.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DB db = new DB();
        // 예시: 사용자 인증 테스트
        boolean isAuthenticated = db.checkUser("testuser", "testpassword");
        System.out.println("사용자 인증 결과: " + (isAuthenticated ? "성공" : "실패"));
        
        // 작업 후 연결 종료
        db.close();
    }
}
