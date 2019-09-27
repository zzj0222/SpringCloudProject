package com.demo.member.model;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "clock_target")
public class ClockTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名称
     */
    @Column(name = "target_name")
    @ApiModelProperty(value = "名称")
    private String targetName;

    /**
     * 目标图片
     */
    @Column(name = "target_pic")
    @ApiModelProperty(value = "目标图片")
    private String targetPic;

    /**
     * 目标分类
     */
    @Column(name = "clock_target_type_id")
    @ApiModelProperty(value = "目标分类")
    private Integer clockTargetTypeId;

    /**
     * 活动状态 0禁用 1启用
     */
    @ApiModelProperty(value = "活动状态 0禁用 1启用")
    private Byte status;

    /**
     * 排序号
     */
    @Column(name = "sort_id")
    @ApiModelProperty(value = "排序号")
    private Integer sortId;

    /**
     * 创建者id
     */
    @Column(name = "creator_id")
    @ApiModelProperty(value = "创建者id")
    private Integer creatorId;

    /**
     * 更新者id
     */
    @Column(name = "modifier_id")
    @ApiModelProperty(value = "更新者id")
    private Integer modifierId;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @Column(name = "gmt_modified")
    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;
}
