package com.example.livechatgroup.model;

import com.example.livechatgroup.util.CrudUtil;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    public static boolean userVerified(String userName) throws SQLException {
        ResultSet resultSet =  CrudUtil.execute("SELECT * FROM user WHERE userName = ?",userName);
        return resultSet.next() ? true : false;
    }

    public static boolean save(String userName) throws SQLException {
        String sql = "INSERT INTO user(userName) VALUES(?)";
        return CrudUtil.execute(sql, userName);
    }
}
