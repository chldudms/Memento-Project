import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    // 사용자 인증 메서드
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

    // 사용자의 가입 날짜를 가져오는 메서드 (yyyy-MM-dd 형식으로 반환)
    public String getUserRegDate(String username) {
        String query = "SELECT regDate FROM users WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            // 결과가 존재하면 가입 날짜 반환
            if (rs.next()) {
                String regDate = rs.getString("regDate");

                // 날짜 형식을 yyyy-MM-dd로 변환
                SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 원본 형식
                SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd"); // 목표 형식

                try {
                    Date date = originalFormat.parse(regDate); // 원본 형식에서 날짜 객체로 변환
                    return targetFormat.format(date); // 변환된 날짜 반환
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // 사용자 정보가 없거나 오류 발생 시 null 반환
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
        boolean isAuthenticated = db.checkUser("testuser", "testpassword");
        System.out.println("사용자 인증 결과: " + (isAuthenticated ? "성공" : "실패"));
        String regDate = db.getUserRegDate("testuser");
        System.out.println("가입 날짜: " + regDate);
        
        // 작업 후 연결 종료
        db.close();
    }
}
