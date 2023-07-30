package com.baomidou.plugin.idea.mybatisx.codegenerator.utils;

import com.baomidou.plugin.idea.mybatisx.codegenerator.domain.DbTypeDriver;
import com.baomidou.plugin.idea.mybatisx.codegenerator.domain.IdTypeObj;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.core.dialect.DbType;

public interface MybatisConst {
    String PLUS_DBURL = "mybatisplus_dbUrl";
    String PLUS_DBTYPE = "mybatisplus_dbtype";
    String PLUS_USERNAME = "mybatisplus_username";
    String PLUS_PASSWORD = "mybatisplus_userpp";

    String GEN_CONFIG = "genconfig";

    IdTypeObj[] IDTYPES = new IdTypeObj[]{
        new IdTypeObj(KeyType.Auto, "AUTO(ID自增)"),
        new IdTypeObj(KeyType.None, "INPUT(用户输入ID)"),
        new IdTypeObj(KeyType.Generator, "IKeyGenerator生成器生成"),
        new IdTypeObj(KeyType.Sequence, "SQL生成")
    };

    DbTypeDriver[] DB_TYPE_DRIVERS = new DbTypeDriver[]{
        new DbTypeDriver(DbType.MYSQL, "mysql"),
        new DbTypeDriver(DbType.ORACLE, "oracle"),
        new DbTypeDriver(DbType.POSTGRE_SQL, "postgresql")
    };


}
