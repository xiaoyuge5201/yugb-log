package com.github.yugb.dao;

import com.github.yugb.bean.RequestLog;
import com.github.yugb.config.DruidConfig;
import com.github.yugb.config.LogConfigProperties;
import com.github.yugb.util.JdbcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 源数据源的Dao操作
 */
@Repository
public class RequestLogDao {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LogConfigProperties properties;

    /**
     * 保存
     *
     * @param requestLog 实体对象
     */
    public void save(RequestLog requestLog) {
        String sql = "INSERT INTO " + properties.getLogTableName() + " (\n" +
                "\t`create_date`,\n" +
                "\t`username`,\n" +
                "\t`operate_type`,\n" +
                "\t`log_type`,\n" +
                "\t`module`,\n" +
                "\t`description`,\n" +
                "\t`remote_addr`,\n" +
                "\t`request_uri`,\n" +
                "\t`method`,\n" +
                "\t`params`,\n" +
                "\t`exception`\n" +
                ")\n" +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        try {
            conn = DruidConfig.getConnection();
            assert conn != null;
            prep = conn.prepareStatement(sql);
            prep.setString(1, requestLog.getCreateDate());
            prep.setString(2, requestLog.getUsername());
            prep.setString(3, requestLog.getOperateType());
            prep.setString(4, requestLog.getLogType());
            prep.setString(5, requestLog.getModule());
            prep.setString(6, requestLog.getDescription());
            prep.setString(7, requestLog.getRemoteAddr());
            prep.setString(8, requestLog.getRequestUri());
            prep.setString(9, requestLog.getMethod());
            prep.setString(10, requestLog.getParams());
            prep.setString(11, requestLog.getException());
            prep.execute();
        } catch (SQLException e) {
            logger.error("something has gone terribly wrong", e);
        } finally {
            JdbcClient.close(conn, prep, rs);
        }
    }

    /**
     * 检查日志表是否存在
     *
     * @return 结果
     */
    public boolean validateTableExist() {
        Connection conn = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        try {
            conn = DruidConfig.getConnection();
            assert conn != null;
            prep = conn.prepareStatement("select * from information_schema.TABLES where TABLE_NAME = '" + properties.getLogTableName() + "' ");
            rs = prep.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            logger.error("something has gone terribly wrong", e);
        } finally {
            JdbcClient.close(conn, prep, rs);
        }
        return false;
    }

    /**
     * 创建日志表
     *
     * @return 结果
     */
    @SuppressWarnings("CatchAndPrintStackTrace")
    public boolean createLogTable() {
        Connection conn = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        try {
            conn = DruidConfig.getConnection();
            assert conn != null;
            prep = conn.prepareStatement("CREATE TABLE " + properties.getLogTableName() + " (\n" +
                    "  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志id',\n" +
                    "  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作名称',\n" +
                    "  `create_date` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作时间',\n" +
                    "  `operate_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作类型',\n" +
                    "  `module` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '模块',\n" +
                    "  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '描述',\n" +
                    "  `remote_addr` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作IP',\n" +
                    "  `request_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '请求地址',\n" +
                    "  `method` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '请求方式',\n" +
                    "  `params` text CHARACTER SET utf8 COLLATE utf8_bin COMMENT '请求参数',\n" +
                    "  `log_type` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '日志级别',\n" +
                    "  `exception` text COLLATE utf8_bin COMMENT '异常信息',\n" +
                    "  PRIMARY KEY (`id`) USING BTREE\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=COMPACT;");
            return prep.execute();
        } catch (SQLException e) {
            logger.error("something has gone terribly wrong", e);
        } finally {
            JdbcClient.close(conn, prep, rs);
        }
        return false;
    }
}
