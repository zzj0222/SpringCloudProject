package com.demo.admin.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "api_safety_config")
public class ApiSafetyConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 应用编号
     */
    @Column(name = "app_id")
    private String appId;

    /**
     * 应用名称
     */
    @Column(name = "app_name")
    private String appName;

    /**
     * 应用key
     */
    @Column(name = "app_key")
    private String appKey;

    /**
     * 应用密钥
     */
    @Column(name = "app_secret")
    private String appSecret;

    /**
     * App描述
     */
    @Column(name = "app_description")
    private String appDescription;

    /**
     * 创建者id
     */
    @Column(name = "creator_id")
    private Integer creatorId;

    /**
     * 更新者id
     */
    @Column(name = "modifier_id")
    private Integer modifierId;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取应用编号
     *
     * @return app_id - 应用编号
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 设置应用编号
     *
     * @param appId 应用编号
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * 获取应用名称
     *
     * @return app_name - 应用名称
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 设置应用名称
     *
     * @param appName 应用名称
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * 获取应用key
     *
     * @return app_key - 应用key
     */
    public String getAppKey() {
        return appKey;
    }

    /**
     * 设置应用key
     *
     * @param appKey 应用key
     */
    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    /**
     * 获取应用密钥
     *
     * @return app_secret - 应用密钥
     */
    public String getAppSecret() {
        return appSecret;
    }

    /**
     * 设置应用密钥
     *
     * @param appSecret 应用密钥
     */
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    /**
     * 获取App描述
     *
     * @return app_description - App描述
     */
    public String getAppDescription() {
        return appDescription;
    }

    /**
     * 设置App描述
     *
     * @param appDescription App描述
     */
    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    /**
     * 获取创建者id
     *
     * @return creator_id - 创建者id
     */
    public Integer getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建者id
     *
     * @param creatorId 创建者id
     */
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 获取更新者id
     *
     * @return modifier_id - 更新者id
     */
    public Integer getModifierId() {
        return modifierId;
    }

    /**
     * 设置更新者id
     *
     * @param modifierId 更新者id
     */
    public void setModifierId(Integer modifierId) {
        this.modifierId = modifierId;
    }

    /**
     * 获取创建时间
     *
     * @return gmt_create - 创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 设置创建时间
     *
     * @param gmtCreate 创建时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 获取更新时间
     *
     * @return gmt_modified - 更新时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 设置更新时间
     *
     * @param gmtModified 更新时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}