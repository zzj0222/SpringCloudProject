package com.demo.member.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "jiayi_user")
@Data
public class JiayiUser {
    /**
     * 会员Id
     */
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "会员Id")
    private Long userId;

    /**
     * 昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 会员帐号
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 密码
     */
    @Column(name = "user_pass")
    private String userPass;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 省份
     */
    private String province;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 性别(0未知 1男2女)
     */
    private Integer sex;

    /**
     * 邮箱是否验证(1是0否)
     */
    @Column(name = "is_email")
    private Integer isEmail;

    /**
     * 手机是否验证(1是0否)
     */
    @Column(name = "is_mobile")
    private Integer isMobile;

    /**
     * 地址
     */
    private String address;

    /**
     * 审核状态(0未通过,1通过)
     */
    @Column(name = "audit_status")
    private Integer auditStatus;

    /**
     * 登录次数
     */
    @Column(name = "login_num")
    private Integer loginNum;

    /**
     * 上次登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /**
     * 本次登录时间
     */
    @Column(name = "this_login_time")
    private Date thisLoginTime;

    /**
     * 是否关闭
     */
    @Column(name = "is_close")
    private Integer isClose;

    /**
     * 本次操作时间
     */
    @Column(name = "this_opt_time")
    private Date thisOptTime;

    /**
     * 图片
     */
    @Column(name = "user_pic")
    private String userPic;

    /**
     * 省
     */
    @Column(name = "province_id")
    private Integer provinceId;

    /**
     * 市
     */
    @Column(name = "city_id")
    private Integer cityId;

    /**
     * 街道
     */
    @Column(name = "district_id")
    private Integer districtId;

    /**
     * QQ号
     */
    private String qq;

    /**
     * 微信号
     */
    @Column(name = "wechat_number")
    private String wechatNumber;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 手机所在国家区域(中国86)
     */
    @Column(name = "mobile_area")
    private String mobileArea;

    /**
     * 注册来源 1甲乙学院 2H5 3给栗同学
     */
    @Column(name = "register_type")
    private Integer registerType;

    /**
     * 来艾号
     */
    @Column(name = "laiai_number")
    private String laiaiNumber;

    /**
     * 微信开放平台
     */
    @Column(name = "wx_open_id")
    private String wxOpenId;

    /**
     * QQ第三方登陆
     */
    @Column(name = "qq_open_id")
    private String qqOpenId;

    /**
     * 微博第三方登陆
     */
    @Column(name = "wb_open_id")
    private String wbOpenId;

    /**
     * 微信唯一UNIONID(需微信号和公众号绑定后)
     */
    @Column(name = "wx_union_id")
    private String wxUnionId;

    /**
     * 公众号授权openid
     */
    @Column(name = "wx_auth_open_id")
    private String wxAuthOpenId;

    /**
     * 收益金额
     */
    @Column(name = "earnings_amount")
    private BigDecimal earningsAmount;

    /**
     * 总学分
     */
    @Column(name = "total_credit")
    private Integer totalCredit;

    /**
     * 总积分
     */
    @Column(name = "total_point")
    private Integer totalPoint;

    /**
     * 背景图
     */
    @Column(name = "cover_pic")
    private String coverPic;

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
     * 备注
     */
    private String remarks;

    /**
     * 代理商级别  -1 普通会员 1-总代 2-省代 3-市级代理 4-县级代理
     */
    @Column(name = "agent_level")
    private String agentLevel;


    /**
     * 班级名称
     */
    @Transient
    private String classesName;


    /**
     * 课程名称列表
     */
    @Transient
    private String courseNames;

    /**
     * 班级Id
     */
    @Transient
    private Integer classesId;

    /**
     * 课程总数
     */
    @Transient
    private Integer courseSum;

    /**
     * 是否入学 0未入学 1入学
     */
    @Transient
    private Integer isSchool;


    /**
     * 是否绑定微信
     */
    @Transient
    private Integer isBindWechat;

}