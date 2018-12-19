package cn.chonor.final_pro.DataBase;


import android.util.Log;


import java.sql.*;

/**
 * Created by Chonor on 2017/12/26.
 */

public class MySqlHelper {
    Connection conn = null;
    Statement stmt = null;

    public void Close() throws SQLException {
        if (stmt!=null)stmt.close();
        if (conn!=null) conn.close();
        stmt = null;
        conn = null;
    }
    //获取数据库连接的类
    public Connection getConnection() throws SQLException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {

        Connection Newconn = null;
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        String connectString = "jdbc:mysql://119.29.145.13:3306/android"
                + "?autoReconnect=true&useUnicode=true"
                + "&characterEncoding=UTF-8";
        String user = "android";
        String password = "123";
        Newconn = (Connection) DriverManager.getConnection(connectString,user,password);
        return Newconn;
    }

    //查
    public ResultSet select(String sql) throws Exception {
        conn = null;
        stmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            stmt = (Statement) conn.createStatement();
            rs = stmt.executeQuery(sql);
            return rs;
        } catch (SQLException sqle) {
            throw new SQLException("select data exception: "
                    + sqle.getMessage());
        } catch (Exception e) {
            throw new Exception("System e exception: " + e.getMessage());
        }
    }

    //增
    public void insert(String sql) throws Exception {
        conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = (PreparedStatement)conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new Exception("insert data exception: " + sqle.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                throw new Exception("ps close exception: " + e.getMessage());
            }
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            throw new Exception("connection close exception: " + e.getMessage());
        }
    }

    //改
    public void update(String sql) throws Exception {
        conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps =  (PreparedStatement)conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new Exception("update exception: " + sqle.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                throw new Exception("ps close exception: " + e.getMessage());
            }
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            throw new Exception("connection close exception: " + e.getMessage());
        }
    }

    public void delete(String sql) throws Exception {
        conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps =  (PreparedStatement)conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException sqle) {
            throw new Exception("delete data exception: " + sqle.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                throw new Exception("ps close exception: " + e.getMessage());
            }
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            throw new Exception("connection close exception: " + e.getMessage());
        }
    }
}
