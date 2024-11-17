import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String args[]) {
        try {
            // Mysql JDBC 드라이버 로드
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Mysql 데이터베이스 연결
            Connection con = DriverManager.getConnection(
                    "[url]",
                    "[username]", "[password]");
            Statement stmt = con.createStatement();
            Scanner scanner = new Scanner(System.in);
            int choice;

            while (true) {
                // 메뉴 출력
                System.out.println("\n메뉴:");
                System.out.println("1. REVIEW 테이블 생성");
                System.out.println("2. REVIEW 테이블에 데이터 삽입");
                System.out.println("3. REVIEW 테이블에서 데이터 조회");
                System.out.println("4. REVIEW 테이블 삭제");
                System.out.println("5. 종료");
                System.out.print("선택: ");
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        // REVIEW 테이블 생성
                        System.out.println("REVIEW 테이블 생성");
                        String createTableQuery = "CREATE TABLE REVIEW (" +
                                "reviewid INT AUTO_INCREMENT PRIMARY KEY, " +
                                "content VARCHAR(255) NOT NULL)";
                        stmt.executeUpdate(createTableQuery);
                        System.out.println("테이블 생성 완료");
                        break;

                    case 2:
                        // REVIEW 테이블에 데이터 삽입
                        System.out.println("삽입할 리뷰 내용을 입력하세요:");
                        String reviewContent = scanner.nextLine();
                        String insertQuery = "INSERT INTO REVIEW (content) VALUES (?)";
                        try (PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
                            pstmt.setString(1, reviewContent);
                            pstmt.executeUpdate();
                            System.out.println("데이터 삽입 완료");
                        } catch (SQLException e) {
                            System.out.println("데이터 삽입 중 오류 발생: " + e.getMessage());
                        }
                        break;

                    case 3:
                        // REVIEW 테이블에서 데이터 전체 검색
                        System.out.println("REVIEW 테이블 데이터:");
                        String selectQuery = "SELECT * FROM REVIEW";
                        try (ResultSet rs = stmt.executeQuery(selectQuery)) {
                            while (rs.next()) {
                                System.out.println(rs.getInt("reviewid") + " " + rs.getString("content"));
                            }
                        } catch (SQLException e) {
                            System.out.println("REVIEW 테이블이 존재하지 않습니다.");
                        }
                        break;

                    case 4:
                        // REVIEW 테이블 삭제
                        System.out.println("REVIEW 테이블 삭제");
                        String deleteQuery = "DROP TABLE REVIEW";
                        try {
                            stmt.executeUpdate(deleteQuery);
                            System.out.println("테이블 삭제 완료");
                        } catch (SQLException e) {
                            System.out.println("테이블 삭제 중 오류가 발생했습니다: " + e.getMessage());
                        }
                        break;

                    case 5:
                        // 종료
                        System.out.println("프로그램 종료");
                        con.close();
                        scanner.close();
                        return;

                    default:
                        System.out.println("잘못된 입력입니다. 다시 선택하세요.");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
