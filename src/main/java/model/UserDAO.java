/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.DBConnection;

/**
 *
 * @author ADMIN
 */
public class UserDAO {
     public User checkLogin(String username, String password) {
        User  kq= null;
        String sql = "SELECT * FROM Users where username=? and password=?";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                kq =  new User(username, password, rs.getString("fullname"));
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách độc giả:");
            e.printStackTrace();
        }
        return kq;
    }

}
