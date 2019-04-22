package puyuan2019;

import java.sql.*;

/**
 * @author SpencerCJH
 * @date 2019/4/22 15:48
 */
public class SaveStudent {
    private static Connection getConn() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/test";
        String username = "root";
        String password = "000000";
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    private static int insert(StudentEntity studentEntity) {
        int i = -1;
        String sql = "insert into test.students (`name`,`age`,`birth`) values(?,?,?)";
        try (Connection conn = getConn(); PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, studentEntity.getName());
            preparedStatement.setInt(2, studentEntity.getAge());
            preparedStatement.setTimestamp(3, new Timestamp(studentEntity.getBirth()));
            i = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    class StudentEntity {
        long id;
        String name;
        int age;
        long birth;

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public long getBirth() {
            return birth;
        }

        public void setBirth(long birth) {
            this.birth = birth;
        }
    }
}
