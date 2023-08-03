package com.baomidou.plugin.idea.mybatisx.codegenerator;

import com.baomidou.plugin.idea.mybatisx.codegenerator.db.BaseDb;
import com.baomidou.plugin.idea.mybatisx.codegenerator.db.MysqlDb;
import com.baomidou.plugin.idea.mybatisx.codegenerator.db.OracleDb;
import com.baomidou.plugin.idea.mybatisx.codegenerator.db.Postgresql;
import com.baomidou.plugin.idea.mybatisx.codegenerator.domain.vo.ColumnInfo;
import com.baomidou.plugin.idea.mybatisx.codegenerator.domain.vo.TableInfo;
import com.baomidou.plugin.idea.mybatisx.codegenerator.utils.MybatisConst;
import com.intellij.ide.util.PropertiesComponent;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.baomidou.plugin.idea.mybatisx.codegenerator.utils.MybatisConst.DB_TYPE_DRIVERS;

public class MysqlUtil {

    private Logger logger = LoggerFactory.getLogger(MysqlUtil.class);

    private BaseDb baseDb;

    private HikariDataSource dataSource;

    private MysqlUtil() {
        resetDbInfo();
    }

    public void resetDbInfo() {
        int dbType = PropertiesComponent.getInstance().getInt(MybatisConst.PLUS_DBTYPE, 0);
        if ("mysql".equals(DB_TYPE_DRIVERS[dbType].getName())) {
            baseDb = new MysqlDb();
        } else if ("oracle".equals(DB_TYPE_DRIVERS[dbType].getName())) {
            baseDb = new OracleDb();

        } else if ("postgresql".equals(DB_TYPE_DRIVERS[dbType].getName())) {
            baseDb = new Postgresql();
        } else {
            throw new RuntimeException("no database!!");
        }
        baseDb.setDbUrl(PropertiesComponent.getInstance().getValue(MybatisConst.PLUS_DBURL));
        baseDb.setUsername(PropertiesComponent.getInstance().getValue(MybatisConst.PLUS_USERNAME));
        baseDb.setPassword(PropertiesComponent.getInstance().getValue(MybatisConst.PLUS_PASSWORD));
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
        dataSource = new HikariDataSource();
        dataSource.setMaximumPoolSize(16);
        dataSource.setMinimumIdle(8);
        dataSource.setMaxLifetime(1500000);
        dataSource.setKeepaliveTime(60000);
        dataSource.setIdleTimeout(60000);
        dataSource.setConnectionTimeout(60000);
        dataSource.setValidationTimeout(3000);
        dataSource.setJdbcUrl(PropertiesComponent.getInstance().getValue(MybatisConst.PLUS_DBURL));
        dataSource.setUsername(PropertiesComponent.getInstance().getValue(MybatisConst.PLUS_USERNAME));
        dataSource.setPassword(PropertiesComponent.getInstance().getValue(MybatisConst.PLUS_PASSWORD));
    }

    private static MysqlUtil mysqlUtil;

    public static MysqlUtil getInstance() {
        if (mysqlUtil == null) {
            mysqlUtil = new MysqlUtil();
        }
        return mysqlUtil;
    }

    public List<TableInfo> getTableInfo() {
        return baseDb.getTableInfo();
    }

    public List<ColumnInfo> getColumns(String tableName) {
        return baseDb.getColumns(tableName);
    }

    public String getDbUrl() {
        return baseDb.getDbUrl();
    }

    public String getJdbcDriver() {
        return baseDb.getJdbcDriver();
    }

    public String getUsername() {
        return baseDb.getUsername();
    }

    public String getPassword() {
        return baseDb.getPassword();
    }

    public void testConnect() {
        baseDb.testConnect();
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }
}
