package com.github.yugb.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * jdbc工具类
 *
 * @author xiaoyuge
 * @date 2022/05/20
 */
public class JdbcClient {

    private static final Logger logger = LoggerFactory.getLogger(JdbcClient.class);

    /**
     * 关闭流
     *
     * @param conn 连接
     * @param stmt statement
     * @param rs   结果集
     */
    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.error("something has gone terribly wrong", e);
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                logger.error("something has gone terribly wrong", e);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error("something has gone terribly wrong", e);
            }
        }
    }
}
