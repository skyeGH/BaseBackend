package com.skye.libra.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类
 * @author gaih
 * @since 2022-12-06 18:30:19
 */
public class GhMenu implements Serializable {

private static final long serialVersionUID = 818073458799804833L;

    /**
    * 主键id
    */
    @TableId(type = IdType.ASSIGN_UUID)
    @TableField(value = "id")
    private String id;
    /**
    * 菜单名称
    */
    @TableField(value = "m_name")
    private String mName;
    /**
    * 上级节点id
    */
    @TableField(value = "m_parent")
    private String mParent;
    /**
    * 排序
    */
    @TableField(value = "sort")
    private Integer sort;
    /**
    * 删除状态 0-未删 1-已删
    */
    @TableLogic
    @JsonIgnore
    @TableField(value = "deleted")
    private Integer deleted;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getMName() {
        return mName;
    }

    public void setMName(String mName) {
        this.mName = mName;
    }
    
    public String getMParent() {
        return mParent;
    }

    public void setMParent(String mParent) {
        this.mParent = mParent;
    }
    
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
    
    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}
