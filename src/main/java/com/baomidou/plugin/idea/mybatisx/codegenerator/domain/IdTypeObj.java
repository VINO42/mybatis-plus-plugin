package com.baomidou.plugin.idea.mybatisx.codegenerator.domain;


import com.mybatisflex.annotation.KeyType;

public class IdTypeObj {
    public IdTypeObj(KeyType idType, String remark) {
        this.idType = idType;
        this.remark = remark;
    }

    private KeyType idType;
    private String remark;

    public KeyType getIdType() {
        return idType;
    }

    public void setIdType(KeyType idType) {
        this.idType = idType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
