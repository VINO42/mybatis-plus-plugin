package com.baomidou.plugin.idea.mybatisx.codegenerator.domain.vo;

/**
 * 表的数据信息
 */

public class PluginTableInfo {

    /**
     * 表名称
     **/
    private String tableName;

    /**
     * 创建日期
     **/
    private String createTime;

    // 数据库引擎
    private String engine;

    // 编码集
    private String coding;

    // 备注
    private String remark;

    public String getTableName() {
        return tableName;
    }

    public PluginTableInfo setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public PluginTableInfo setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getEngine() {
        return engine;
    }

    public PluginTableInfo setEngine(String engine) {
        this.engine = engine;
        return this;
    }

    public String getCoding() {
        return coding;
    }

    public PluginTableInfo setCoding(String coding) {
        this.coding = coding;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public PluginTableInfo setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    @Override
    public String toString() {
        return "TableInfo{" +
            "tableName=" + tableName +
            ", createTime=" + createTime +
            ", engine=" + engine +
            ", coding=" + coding +
            ", remark=" + remark +
            '}';
    }
}
