import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3307/v1_base";
        String _username = "root";
        String _password = "root";
        Connection conn = null;

        try {
            // 显式加载驱动类
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, _username, _password);
            System.out.println("数据库连接成功！");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from user");
            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String email = rs.getString("email");
                String pwd = rs.getString("password");  // 实际应用中不应直接显示密码
                Timestamp createdAt = rs.getTimestamp("created_at");
                boolean isActive = rs.getBoolean("is_active");
                // 格式化输出
                System.out.printf("| %-2d | %-8s | %-23s | %-14s | %-19s | %-8b |\n",
                        id, username, email, maskPassword(pwd), createdAt, isActive);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("找不到MySQL驱动类");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("数据库连接失败");
            e.printStackTrace();
        }
    }
    // 密码掩码处理（安全考虑）
    private static String maskPassword(String password) {
        return password.substring(0, 2) + "******";
    }
}