package com.wzj.spring01.service.impl;


import com.wzj.spring01.entity.Account;
import com.wzj.spring01.util.JdbcDataUtils;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * （必做）研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法：
 *		1）使用 JDBC 原生接口，实现数据库的增删改查操作
 * 		2）使用事务，PrepareStatement 方式，批处理方式，改进上述操作
 * @author wangzhijie
 */
public class JdbcAccountImpl {

    /**
     * 新增账户
     * @param account
     * @return
     * @throws SQLException
     */
    public static Long insert(final Account account) throws SQLException {
        String sql = "INSERT INTO t_account (user_id, status) VALUES (?, ?)";
        try (Connection connection = JdbcDataUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, account.getUserId());
            preparedStatement.setString(2, account.getStatus());
            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    account.setAccountId(resultSet.getLong(1));
                }
            }
        }
        return account.getAccountId();
    }

    /**
     * 删除账户
     * @param accountId
     * @throws SQLException
     */
    public static void delete(final Long accountId) throws SQLException {
        String sql = "DELETE FROM t_account WHERE account_id=?";
        try (Connection connection = JdbcDataUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, accountId);
            preparedStatement.executeUpdate();
        }
    }

    /**
     * 更新账户
     * @param account
     * @throws SQLException
     */
    public static void update(final Account account) throws SQLException {
        String sql = "UPDATE t_account SET status=? WHERE account_id=?";
        try (Connection connection = JdbcDataUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, account.getStatus());
            preparedStatement.setLong(2, account.getAccountId());
            preparedStatement.executeUpdate();
        }
    }

    /**
     * 查询账户
     * @return
     * @throws SQLException
     */
    public static List<Account> selectAll() throws SQLException {
        String sql = "SELECT * FROM t_account";
        return getAccounts(sql);
    }

    protected static List<Account> getAccounts(final String sql) throws SQLException {
        List<Account> result = new LinkedList<>();
        try (Connection connection = JdbcDataUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Account account = new Account();
                account.setAccountId(resultSet.getLong(1));
                account.setUserId(resultSet.getInt(2));
                account.setStatus(resultSet.getString(3));
                result.add(account);
            }
        }
        return result;
    }

    /**
     * 批量更新账户
     * @param listAccount
     * @return
     * @throws SQLException
     */
    public static void batchUpdate(final List<Account> listAccount) throws SQLException {
        String sql = "UPDATE t_account SET status=? WHERE account_id=?";
        try (Connection connection = JdbcDataUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for(Account account : listAccount){
                preparedStatement.setString(1, account.getStatus());
                preparedStatement.setLong(2, account.getAccountId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    public static void main(String[] args) throws SQLException {
        // insert(new Account());
        // delete(1L);
        // update(new Account().setStatus("sss").setAccountId(2));
        List<Account> listAccount = selectAll();
//        List<Account> list = listAccount.stream().map(account -> {
//            account.setStatus("wwwww");
//            return account;
//        }).collect(Collectors.toList());
//        batchUpdate(list);
        System.out.println(listAccount);
    }


}
