package com.example.livechatgroup.model;

import com.example.livechatgroup.dto.User;
import com.example.livechatgroup.util.CrudUtil;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    public static User userVerified(String userName) throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM user WHERE userName = ?", userName);
        if (resultSet.next()) {
            return new User(resultSet.getString(1));
        }
        return null;
    }

    public boolean add(User dto) throws SQLException {
        return CrudUtil.execute("INSERT INTO user(userName) VALUES (?)");
    }
}
