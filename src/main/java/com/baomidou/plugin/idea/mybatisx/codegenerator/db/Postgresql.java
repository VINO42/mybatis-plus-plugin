package com.baomidou.plugin.idea.mybatisx.codegenerator.db;

import com.baomidou.plugin.idea.mybatisx.codegenerator.domain.vo.ColumnInfo;
import com.baomidou.plugin.idea.mybatisx.codegenerator.domain.vo.PluginTableInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Postgresql extends BaseDb {

    private final Logger logger = LoggerFactory.getLogger(Postgresql.class);

    @Override
    String getTableSql() {
        return "select relname as table_name,cast(obj_description(relfilenode,'pg_class') as varchar) as table_comment from pg_class c \n" +
            "where  relkind = 'r' and relname not like 'pg_%' and relname not like 'sql_%' order by relname";
    }

    @Override
    String getColumnSql(String tableName) {
        return "SELECT col_description(a.attrelid,a.attnum) as column_comment,format_type(a.atttypid,a.atttypmod) as data_type,a.attname as column_name, a.attnotnull as is_nullable   \n" +
            "FROM pg_class as c,pg_attribute as a where c.relname = '" + tableName + "' and a.attrelid = c.oid and a.attnum>0";
    }

    @Override
    public String dbName() {
        return "postgresql";
    }

    @Override
    public List<PluginTableInfo> getTableInfo() {
        List<PluginTableInfo> tableInfos = new ArrayList<>();
        Connection conn;
        Statement stmt;
        try {
            Class.forName(getJdbcDriver());
            conn = DriverManager.getConnection(getDbUrl(), getUsername(), getPassword());
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getTableSql());
            while (rs.next()) {
                String tableName = rs.getString("table_name");
                String remark = rs.getString("table_comment");
                tableInfos.add(new PluginTableInfo()
                    .setTableName(tableName)
                    .setRemark(remark)
                );
            }
            stmt.close();
            conn.close();
            return tableInfos;
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e.toString());
        }
        return new ArrayList<>();
    }

    @Override
    public List<ColumnInfo> getColumns(String tableName) {
        List<ColumnInfo> columnInfoList = new ArrayList<>();
        Connection conn;
        Statement stmt;
        try {
            Class.forName(getJdbcDriver());
            conn = DriverManager.getConnection(getDbUrl(), getUsername(), getPassword());
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(getColumnSql(tableName));
            while (rs.next()) {
                String columnName = rs.getString("column_name");
                String isNullable = rs.getString("is_nullable");
                String dataType = rs.getString("data_type");
                String columnComment = rs.getString("column_comment");
//                String columnKey = rs.getString("column_key");
//                String extra = rs.getString("extra");
                columnInfoList.add(new ColumnInfo().setColumnName(columnName)
                    .setIsNullable(isNullable)
                    .setColumnType(dataType)
                    .setColumnComment(columnComment));
//                    .setColumnKey(columnKey)
//                    .setExtra(extra));
            }
            stmt.close();
            conn.close();
            return columnInfoList;
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e.toString());
        }
        return new ArrayList<>();
    }

    @Override
    public String getJdbcDriver() {
        return "org.postgresql.Driver";
    }
}
