-- ========================= rbc 管理系统数据表=======================
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_admin
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE `sys_admin` (
  `admin_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `login_name` varchar(30) NOT NULL COMMENT '登录名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `salt` varchar(255) NOT NULL COMMENT '密码盐',
  `real_name` varchar(20) NOT NULL COMMENT '真实姓名',
  `mobile` varchar(11) NOT NULL COMMENT '手机',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `sex` char(1) DEFAULT NULL COMMENT 'M ： 男士 W ：女士',
  `dept_id` int(11) unsigned DEFAULT '0' COMMENT '部门id',
  `dept_name` varchar(20) DEFAULT NULL COMMENT '部门名称',
  `remark` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  `login_num` int(11) unsigned DEFAULT '0' COMMENT '登录次数',
  `last_login_time` timestamp NULL DEFAULT NULL COMMENT '上次登录时间',
  `this_login_time` timestamp NULL DEFAULT NULL COMMENT '此处登录时间',
  `state` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '用户状态 0: 启用  1: 禁用',
  `creator_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '创建者id',
  `modifier_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '更新者id',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`admin_id`),
  UNIQUE KEY `uk_login_name` (`login_name`) USING BTREE,
  UNIQUE KEY `idx_mobile` (`mobile`) USING BTREE,
  KEY `idx_real_name` (`real_name`) USING BTREE,
  KEY `idx_state` (`state`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COMMENT='后台系统管理员表';

-- ----------------------------
-- Table structure for sys_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin_role`;
CREATE TABLE `sys_admin_role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `admin_id` int(11) unsigned NOT NULL COMMENT '管理员id',
  `role_id` int(11) unsigned NOT NULL COMMENT '角色id',
  `creator_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '创建者id',
  `modifier_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '更新者id',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_adminid_roleid` (`admin_id`,`role_id`) USING BTREE,
  KEY `idx_role_id` (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8 COMMENT='管理员-角色关联表';

-- ----------------------------
-- Table structure for sys_application
-- ----------------------------
DROP TABLE IF EXISTS `sys_application`;
CREATE TABLE `sys_application` (
  `application_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '应用id',
  `application_code` varchar(20) NOT NULL COMMENT '应用编码',
  `application_name` varchar(255) NOT NULL DEFAULT '' COMMENT '应用名称',
  `is_close` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否关闭，0：未关闭  1：已关闭',
  `creator_id` int(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
  `modifier_id` int(11) DEFAULT '0' COMMENT '更新者id',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`application_id`),
  UNIQUE KEY `idx_app_id` (`application_code`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department` (
  `dept_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `dept_name` varchar(20) NOT NULL COMMENT '部门名称',
  `parent_dept_id` int(11) unsigned NOT NULL DEFAULT '0',
  `state` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '部门状态 0: 启用  1: 禁用 ',
  `remark` varchar(100) NOT NULL DEFAULT '' COMMENT '备注',
  `creator_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '创建者id',
  `modifier_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '更新者id',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`dept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(20) NOT NULL COMMENT '菜单名称',
  `path` varchar(100) DEFAULT NULL COMMENT '前端路由',
  `url` varchar(255) DEFAULT NULL COMMENT '菜单href',
  `parent_menu_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '父id',
  `sort_id` int(11) DEFAULT NULL COMMENT '排序值',
  `state` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '状态  0: 启用  1: 禁用',
  `icon` varchar(50) DEFAULT NULL COMMENT '图标',
  `description` varchar(100) NOT NULL DEFAULT '' COMMENT '描述',
  `creator_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '创建者id',
  `modifier_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '修改者id',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_hide` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:不隐藏 1：隐藏',
  `application_code` varchar(20) NOT NULL DEFAULT '' COMMENT '应用编码',
  PRIMARY KEY (`menu_id`),
  KEY `idx_pid` (`parent_menu_id`),
  KEY `idx_url` (`url`),
  KEY `idx_name` (`menu_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8 COMMENT='系统菜单表';

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `permission_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'permisson_id',
  `permission_code` varchar(50) NOT NULL COMMENT '权限编码',
  `permission_name` varchar(20) NOT NULL COMMENT '权限名称',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '资源URL',
  `method` varchar(20) NOT NULL COMMENT 'POST、PUT 、DELETE 、GET',
  `permission_type` char(1) NOT NULL DEFAULT '0' COMMENT '类型  0: button  1:menu',
  `menu_id` int(11) DEFAULT NULL COMMENT '菜单id',
  `log_flag` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否记录日志；0：是 1：否 ',
  `state` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '状态 0: 启用  1: 禁用',
  `description` varchar(100) NOT NULL DEFAULT '' COMMENT '描述',
  `creator_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '创建者id',
  `modifier_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '修改者id',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_check` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0:不需要校验 1:需要校验',
  `application_code` varchar(20) NOT NULL DEFAULT '' COMMENT '应用编码',
  PRIMARY KEY (`permission_id`),
  UNIQUE KEY `uk_code` (`permission_code`) USING BTREE,
  KEY `idx_url` (`url`),
  KEY `idx_method` (`method`),
  KEY `idx_name` (`permission_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=548 DEFAULT CHARSET=utf8 COMMENT='系统权限表';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_name` varchar(20) NOT NULL DEFAULT '' COMMENT '角色名称',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '备注',
  `state` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '角色状态 0: 启用  1: 禁用',
  `creator_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '创建者id',
  `modifier_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '修改者id',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `application_code` varchar(20) NOT NULL DEFAULT '' COMMENT '应用编码',
  PRIMARY KEY (`role_id`),
  KEY `idx_role_name` (`role_name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='系统管理角色表';

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `menu_id` int(11) NOT NULL COMMENT '菜单id',
  `creator_id` int(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
  `modifier_id` int(11) NOT NULL DEFAULT '0' COMMENT '更新者id',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_roleid_menuid` (`role_id`,`menu_id`) USING BTREE,
  KEY `idx_menu_id` (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4068 DEFAULT CHARSET=utf8 COMMENT='角色-菜单表';

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int(11) unsigned NOT NULL COMMENT '角色id',
  `permission_code` varchar(50) NOT NULL COMMENT '权限编码',
  `creator_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '创建者id',
  `modifier_id` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '更新者id',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `is_check` tinyint(4) NOT NULL DEFAULT '1' COMMENT '0:不需要校验 1:需要校验',
  PRIMARY KEY (`id`),
  KEY `idx_roleid_permcode` (`role_id`,`permission_code`),
  KEY `idx_perssion_id` (`permission_code`)
) ENGINE=InnoDB AUTO_INCREMENT=14420 DEFAULT CHARSET=utf8 COMMENT='角色-权限关联表';
