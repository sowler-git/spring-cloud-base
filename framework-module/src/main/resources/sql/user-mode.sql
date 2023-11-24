/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : localhost:3306
 Source Schema         : user-mode

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 20/10/2023 16:19:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_blog
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog`;
CREATE TABLE `tb_blog`  (
  `uid` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '唯一uid',
  `title` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '博客标题',
  `summary` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '博客简介',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '博客内容',
  `tag_uid` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标签uid',
  `click_count` int(0) NULL DEFAULT 0 COMMENT '博客点击数',
  `collect_count` int(0) NULL DEFAULT 0 COMMENT '博客收藏数',
  `file_uid` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标题图片uid',
  `status` tinyint(0) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  `admin_uid` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '管理员uid',
  `is_original` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '1' COMMENT '是否原创（0:不是 1：是）',
  `author` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '作者',
  `articles_part` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '文章出处',
  `blog_sort_uid` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '博客分类UID',
  `level` tinyint(1) NULL DEFAULT 0 COMMENT '推荐等级(0:正常)',
  `is_publish` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '1' COMMENT '是否发布：0：否，1：是',
  `sort` int(0) NOT NULL DEFAULT 0 COMMENT '排序字段',
  `open_comment` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否开启评论(0:否 1:是)',
  `type` tinyint(1) NOT NULL DEFAULT 0 COMMENT '类型【0 博客， 1：推广】',
  `outside_link` varchar(1024) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '外链【如果是推广，那么将跳转到外链】',
  `oid` int(0) NOT NULL AUTO_INCREMENT COMMENT '唯一oid',
  `user_uid` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '投稿用户UID',
  `article_source` tinyint(1) NOT NULL DEFAULT 0 COMMENT '文章来源【0 后台添加，1 用户投稿】',
  PRIMARY KEY (`uid`, `oid`) USING BTREE,
  INDEX `oid`(`oid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '博客表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_blog
-- ----------------------------
INSERT INTO `tb_blog` VALUES ('1234', '这个一个Window', '123', '123', '2a31dd6c2b1b464e9e222a1198bc739a', 0, 0, NULL, 1, NULL, NULL, NULL, '1', NULL, NULL, NULL, 0, '1', 0, 1, 0, NULL, 59, NULL, 0);
INSERT INTO `tb_blog` VALUES ('adwas21qwd', '领域设计', '2341', '2341', 'f5d458db6a044eaebc22232efa1e3b54', 0, 0, NULL, 1, NULL, NULL, NULL, '1', NULL, NULL, NULL, 0, '1', 0, 1, 0, NULL, 60, NULL, 0);

-- ----------------------------
-- Table structure for tb_blog_sort
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_sort`;
CREATE TABLE `tb_blog_sort`  (
  `uid` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '唯一uid',
  `sort_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '分类内容',
  `content` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '分类简介',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  `status` tinyint(0) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态',
  `sort` int(0) NULL DEFAULT 0 COMMENT '排序字段，越大越靠前',
  `click_count` int(0) NULL DEFAULT 0 COMMENT '点击数',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '博客分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_blog_sort
-- ----------------------------
INSERT INTO `tb_blog_sort` VALUES ('01b604bb1be10b32847f6fc64e1111bf', '测试2', '测试2', '2020-10-13 10:00:44', '2020-10-13 10:00:52', 0, 0, 0);
INSERT INTO `tb_blog_sort` VALUES ('029d80ba36a04c96a89a80e2705031a2', '测试分类', '测试分类', '2019-01-11 21:10:43', '2019-01-11 21:10:43', 0, 0, 0);
INSERT INTO `tb_blog_sort` VALUES ('043e2062e18497fc283d30657e800dac', 'JVM', 'Java虚拟机', '2020-03-10 07:28:04', '2020-03-10 07:28:04', 0, 0, 0);
INSERT INTO `tb_blog_sort` VALUES ('093d8bdd01c84890a928e923d5c235fe', '软件推荐', '软件推荐', '2018-09-25 16:14:59', '2020-10-13 10:00:23', 1, 0, 5);
INSERT INTO `tb_blog_sort` VALUES ('2c93dfab0e754006866f8ed486923a41', '慢生活', '慢生活，不是懒惰，放慢速度不是拖延时间，而是让我们在生活中寻找到平衡', '2018-09-25 15:29:33', '2020-10-13 10:00:23', 1, 0, 19);
INSERT INTO `tb_blog_sort` VALUES ('337806254f9c42999043de5c5ee09e77', '技术新闻', '发现世界的每一天', '2018-12-30 10:42:11', '2020-10-13 10:00:23', 1, 0, 58);
INSERT INTO `tb_blog_sort` VALUES ('9d2019983d91490aaa758eddd7c07caf', '机器学习', '机器学习', '2018-11-22 20:56:02', '2020-10-13 10:00:23', 1, 0, 3);
INSERT INTO `tb_blog_sort` VALUES ('a03d7290b1c04b6eaf46659661b47032', '后端开发', '后端开发专题', '2018-12-30 10:35:43', '2020-10-13 10:00:23', 1, 6, 362);
INSERT INTO `tb_blog_sort` VALUES ('ca28ffc94ea94fbda5571e0b242021e2', '前端开发', '前端开发专题', '2018-12-30 10:35:58', '2020-10-13 10:00:23', 1, 0, 56);
INSERT INTO `tb_blog_sort` VALUES ('db0d64ea7df409de5d2d747927cfa1a5', '学习笔记', '学习笔记', '2019-08-31 09:50:03', '2020-10-13 10:00:23', 1, 3, 111);
INSERT INTO `tb_blog_sort` VALUES ('e4ccfe610a5d59538836ddbf4dcb31c7', '分类名称', '分类介绍', '2020-10-13 10:00:08', '2020-10-13 10:00:14', 0, 0, 0);
INSERT INTO `tb_blog_sort` VALUES ('e60df954efcd47c48463a504bb70bbe9', '面试', '面试专题', '2018-12-20 21:16:30', '2020-10-13 10:00:23', 1, 0, 43);

-- ----------------------------
-- Table structure for tb_blog_spider
-- ----------------------------
DROP TABLE IF EXISTS `tb_blog_spider`;
CREATE TABLE `tb_blog_spider`  (
  `uid` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '唯一uid',
  `title` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '博客标题',
  `summary` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '博客简介',
  `content` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL COMMENT '博客内容',
  `tag_uid` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标签uid',
  `click_count` int(0) NULL DEFAULT 0 COMMENT '博客点击数',
  `collect_count` int(0) NULL DEFAULT 0 COMMENT '博客收藏数',
  `file_uid` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标题图片uid',
  `status` tinyint(0) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态',
  `create_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `admin_uid` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '管理员uid',
  `is_original` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '1' COMMENT '是否原创（0:不是 1：是）',
  `author` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '作者',
  `articles_part` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '文章出处',
  `blog_sort_uid` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '博客分类UID',
  `level` tinyint(1) NULL DEFAULT 0 COMMENT '推荐等级(0:正常)',
  `is_publish` varchar(1) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT '1' COMMENT '是否发布：0：否，1：是',
  `sort` int(0) NOT NULL DEFAULT 0 COMMENT '排序字段',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '博客爬取表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_blog_spider
-- ----------------------------

-- ----------------------------
-- Table structure for tb_department
-- ----------------------------
DROP TABLE IF EXISTS `tb_department`;
CREATE TABLE `tb_department`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `tenant_id` bigint(0) NOT NULL DEFAULT 1,
  `dept_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '部门编号',
  `dept_parent_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '上级部门编号',
  `display_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `display_zh_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `display_en_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `short_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '部门简称（多余的字段，删除）',
  `full_zh_name` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `full_name` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `full_en_name` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '部门名英文路径',
  `full_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '部门code路径',
  `is_deleted` tinyint(1) NULL DEFAULT 0,
  `create_time` bigint(0) NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` bigint(0) NULL DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `source` tinyint(1) NULL DEFAULT 0 COMMENT '来源：0本系统 1外部',
  `display_order` int(0) NULL DEFAULT 0 COMMENT '显示顺序',
  `is_hidden` tinyint(1) NULL DEFAULT 0 COMMENT '是否隐藏',
  `third_type` tinyint(0) NULL DEFAULT NULL,
  `third_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `is_built_in` tinyint(0) NULL DEFAULT 0 COMMENT '是否为企业',
  `external_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '外部id',
  `dept_user_count` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_full_path`(`full_path`) USING BTREE,
  INDEX `idx_dp_code`(`dept_parent_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_department
-- ----------------------------

-- ----------------------------
-- Table structure for tb_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_menu`;
CREATE TABLE `tb_menu`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限名称',
  `comment` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '描述',
  `level` tinyint(0) NULL DEFAULT 1 COMMENT '1一级 2二级',
  `parent_id` bigint(0) NULL DEFAULT NULL,
  `type` tinyint(0) NULL DEFAULT 1 COMMENT '1菜单 2按钮',
  `sort` tinyint(0) NULL DEFAULT NULL COMMENT '标题对应',
  `external_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '如果不为NULL 表示从外部系统同步的',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` bigint(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` bigint(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(0) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_menu
-- ----------------------------

-- ----------------------------
-- Table structure for tb_operation
-- ----------------------------
DROP TABLE IF EXISTS `tb_operation`;
CREATE TABLE `tb_operation`  (
  `uid` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '主键',
  `user_uid` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户uid',
  `ip` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '访问ip地址',
  `behavior` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '用户行为',
  `module_uid` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '模块uid（文章uid，标签uid，分类uid）',
  `other_data` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '附加数据(比如搜索内容)',
  `status` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  `os` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '浏览器',
  `ip_source` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT 'ip来源',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = 'Log日志访问记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_operation
-- ----------------------------

-- ----------------------------
-- Table structure for tb_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `tb_operation_log`;
CREATE TABLE `tb_operation_log`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `tenant_id` bigint(0) NULL DEFAULT 1,
  `user_id` bigint(0) UNSIGNED NULL DEFAULT NULL,
  `module_code` int(0) NULL DEFAULT NULL COMMENT '所属的模块code',
  `op_type` tinyint(0) NULL DEFAULT NULL COMMENT '操作类型1：增加、2：删除、3：修改 4、导出',
  `op_content` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '操作内容',
  `op_time` bigint(0) NULL DEFAULT NULL COMMENT '操作时间',
  `op_ip` int(0) UNSIGNED NULL DEFAULT NULL COMMENT '操作ip',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0,
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` bigint(0) NULL DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` bigint(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_op_time`(`op_time`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_operation_log
-- ----------------------------

-- ----------------------------
-- Table structure for tb_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_role`;
CREATE TABLE `tb_role`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` bigint(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` bigint(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(0) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `tenant_id` bigint(0) UNSIGNED NOT NULL COMMENT '租户id',
  `name` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '角色名',
  `en_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '英文角色名',
  `zh_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `is_user_public` tinyint(0) NOT NULL DEFAULT 0 COMMENT '是否适用所有用户 {0:不适用,1:适用}',
  `is_default` tinyint(0) NOT NULL DEFAULT 0 COMMENT '是否是默认 {0:普通,1:默认}',
  `is_modifiable` tinyint(0) NOT NULL DEFAULT 1 COMMENT '是否是可修改的 {0:不可修改,1:可修改}',
  `type` tinyint(0) NOT NULL DEFAULT 1 COMMENT '类型 {1:普通角色,2:管理角色}',
  `level` bigint(0) UNSIGNED NULL DEFAULT NULL COMMENT '层级',
  `parent_id` bigint(0) UNSIGNED NULL DEFAULT NULL COMMENT '父id',
  `remark` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色备注',
  `is_admin` tinyint(0) NULL DEFAULT 0 COMMENT '是否是超级管理员',
  `is_data_public` tinyint(0) NULL DEFAULT 1 COMMENT '是否勾选全部数据',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表 ' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role
-- ----------------------------
INSERT INTO `tb_role` VALUES (1, NULL, NULL, NULL, NULL, 0, 1, '超级管理员', 'superAdmin', '超级管理员', 0, 0, 1, 1, NULL, NULL, NULL, 0, 1);
INSERT INTO `tb_role` VALUES (2, NULL, NULL, NULL, NULL, 0, 1, '普通管理员', 'commonAdmin', '普通管理员', 0, 0, 1, 1, NULL, NULL, NULL, 0, 1);
INSERT INTO `tb_role` VALUES (3, NULL, NULL, NULL, NULL, 0, 1, '楼层管理员', 'buildingAdmin', '楼层管理员', 0, 0, 1, 1, NULL, NULL, NULL, 0, 1);

-- ----------------------------
-- Table structure for tb_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `tb_role_menu`;
CREATE TABLE `tb_role_menu`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` bigint(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` bigint(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(0) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `role_id` bigint(0) UNSIGNED NOT NULL COMMENT '角色id',
  `menu_id` bigint(0) UNSIGNED NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for tb_sys_config
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_config`;
CREATE TABLE `tb_sys_config`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `tenant_id` bigint(0) UNSIGNED NOT NULL DEFAULT 1,
  `conf_key` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '配置项的名称',
  `conf_val` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `is_enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否开启',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0,
  `create_time` bigint(0) NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` bigint(0) NULL DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_conf_key`(`conf_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '系统配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_sys_config
-- ----------------------------

-- ----------------------------
-- Table structure for tb_tag
-- ----------------------------
DROP TABLE IF EXISTS `tb_tag`;
CREATE TABLE `tb_tag`  (
  `uid` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '唯一uid',
  `content` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '标签内容',
  `status` tinyint(0) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态',
  `click_count` int(0) NULL DEFAULT 0 COMMENT '标签简介',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '更新时间',
  `sort` int(0) NULL DEFAULT 0 COMMENT '排序字段，越大越靠前',
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '标签表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_tag
-- ----------------------------
INSERT INTO `tb_tag` VALUES ('0b51c75ed5744cdcadefe0ad947be9b6', '数据库', 1, 78, '2020-10-13 10:01:00', '2020-12-26 11:24:33', 0);
INSERT INTO `tb_tag` VALUES ('15721a34adba068763b5a2fb1991fc57', 'JVM', 1, 33, '2020-10-13 10:01:00', '2021-01-24 10:00:58', 3);
INSERT INTO `tb_tag` VALUES ('188396dc0efcae369856fe472df1ed09', '标签2', 0, 0, '2020-10-13 10:01:10', '2020-10-13 10:01:15', 6);
INSERT INTO `tb_tag` VALUES ('1c76b9848f5f4d71a5e88b20dbaf38f4', 'RabbitMQ', 1, 106, '2020-10-13 10:01:00', '2020-12-26 11:24:33', 0);
INSERT INTO `tb_tag` VALUES ('1d1fd6d26c8e40a38637ef6126c45cd0', 'Linux', 1, 336, '2020-10-13 10:01:00', '2020-10-13 10:01:03', 1);
INSERT INTO `tb_tag` VALUES ('2a31dd6c2b1b464e9e222a1198bc739a', '虚拟机', 1, 58, '2020-10-13 10:01:00', '2020-12-26 11:24:34', 0);
INSERT INTO `tb_tag` VALUES ('2f5779e877da48958c985d69b311d0d6', '大数据', 1, 72, '2020-10-13 10:01:00', '2020-12-26 11:24:34', 0);
INSERT INTO `tb_tag` VALUES ('3c16b9093e9b1bfddbdfcb599b23d835', 'Nginx', 1, 32, '2020-10-13 10:01:00', '2020-12-26 11:24:36', 0);
INSERT INTO `tb_tag` VALUES ('53c5a0f3142e4f54820315936f78383b', 'Spring Boot', 1, 73, '2020-10-13 10:01:00', '2020-12-26 11:24:35', 0);
INSERT INTO `tb_tag` VALUES ('5626932d452c2ad863d9b3cb0b69d22d', '学习笔记', 1, 237, '2020-10-13 10:01:00', '2021-06-13 07:48:23', 5);
INSERT INTO `tb_tag` VALUES ('5c939107ddb746b989156737805df625', '机器学习', 1, 109, '2020-10-13 10:01:00', '2020-10-13 10:01:03', 0);
INSERT INTO `tb_tag` VALUES ('618346963de0fc724e44c6f9120aea9c', 'Github', 1, 17, '2020-10-13 10:01:00', '2020-10-13 10:01:03', 0);
INSERT INTO `tb_tag` VALUES ('6d35ddd5075f4c0e885ffb2e3b3a0365', 'Tomcat', 1, 58, '2020-10-13 10:01:00', '2020-10-13 10:01:03', 0);
INSERT INTO `tb_tag` VALUES ('7e0e93ea6cdb44ae92e58f48e6496ed7', 'Java', 1, 1644, '2020-10-13 10:01:00', '2021-06-13 07:47:58', 4);
INSERT INTO `tb_tag` VALUES ('8c9d43de144245eb8176854eca5ae244', 'AI', 1, 20, '2020-10-13 10:01:00', '2020-10-13 10:01:03', 0);
INSERT INTO `tb_tag` VALUES ('8d5ce3e0c0784b95adb7f9e7b76dca93', '建站系统', 1, 100, '2020-10-13 10:01:00', '2020-10-13 10:01:03', 0);
INSERT INTO `tb_tag` VALUES ('a9a747d944c24845815356f72723ef8e', '前端开发', 1, 114, '2020-10-13 10:01:00', '2020-10-13 10:01:03', 0);
INSERT INTO `tb_tag` VALUES ('b22b9bdc32a442dd65dee82cdc5cf800', '计算机网络', 1, 16, '2020-10-13 10:01:00', '2020-10-13 10:01:03', 0);
INSERT INTO `tb_tag` VALUES ('ca928e8718654aa5a802e2f69277b137', '面试', 1, 193, '2020-10-13 10:01:00', '2020-10-13 10:01:03', 0);
INSERT INTO `tb_tag` VALUES ('d3c3fc43f38445389c970ff0732a6586', 'NLP', 1, 39, '2020-10-13 10:01:00', '2020-10-13 10:01:03', 0);
INSERT INTO `tb_tag` VALUES ('dececd440fdc4fa28dffe6404e696dd4', 'Python', 1, 19, '2020-10-13 10:01:00', '2020-10-13 10:01:03', 0);
INSERT INTO `tb_tag` VALUES ('e2c7913050cf4ab9aa92902316aaf075', '校园生活', 1, 169, '2020-10-13 10:01:00', '2020-10-13 10:01:03', 0);
INSERT INTO `tb_tag` VALUES ('e81bc2dca42c4031be7d66fef4a71e16', 'Spring Cloud', 1, 110, '2020-10-13 10:01:00', '2020-12-26 11:24:32', 2);
INSERT INTO `tb_tag` VALUES ('ebf63989f11741bc89494c52fc6bae4c', 'Docker', 1, 96, '2020-10-13 10:01:00', '2020-10-13 10:01:03', 0);
INSERT INTO `tb_tag` VALUES ('f5d458db6a044eaebc22232efa1e3b54', '深度学习', 1, 66, '2020-10-13 10:01:00', '2020-10-13 10:01:03', 0);
INSERT INTO `tb_tag` VALUES ('f90d3c2fd9434302951130e897a89164', 'Vue', 1, 90, '2020-10-13 10:01:00', '2020-10-13 10:01:03', 0);
INSERT INTO `tb_tag` VALUES ('fb72516226474cf0bfa0f310bfa75426', 'Redis', 1, 61, '2020-10-13 10:01:00', '2020-10-13 10:01:03', 0);

-- ----------------------------
-- Table structure for tb_template
-- ----------------------------
DROP TABLE IF EXISTS `tb_template`;
CREATE TABLE `tb_template`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '模板名称',
  `index` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '发布模板表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_template
-- ----------------------------

-- ----------------------------
-- Table structure for tb_tenant
-- ----------------------------
DROP TABLE IF EXISTS `tb_tenant`;
CREATE TABLE `tb_tenant`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `zh_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `en_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name_zh` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '中文名称',
  `name_en` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '英文名称',
  `postal_code` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮编',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `logo_hash` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'logo_hash',
  `background_hash` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'background_hash',
  `address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  `en_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `zh_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `is_active` tinyint(0) NOT NULL DEFAULT 1,
  `is_deleted` tinyint(0) NOT NULL DEFAULT 0,
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `update_time` bigint(0) NULL DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` bigint(0) NULL DEFAULT NULL,
  `dsp_id` bigint(0) NULL DEFAULT NULL COMMENT 'dsp的tenant id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '企业信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_tenant
-- ----------------------------

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `tenant_id` bigint(0) UNSIGNED NULL DEFAULT 1,
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `second_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '英文名选填',
  `en_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '英文名称',
  `zh_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '中文名称',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `position` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '中文职位',
  `en_position` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '英文职位',
  `zh_position` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `staff_num` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '员工编号',
  `stuff_type` tinyint(0) NOT NULL DEFAULT 0 COMMENT '员工类型',
  `last_login_time` bigint(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `user_source` tinyint(0) NOT NULL DEFAULT 1 COMMENT '用户来源',
  `is_built_in` tinyint(0) NOT NULL DEFAULT 0 COMMENT '内置账号',
  `avatar_hash` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `building_id` bigint(0) NULL DEFAULT NULL COMMENT '办公地点',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `status` tinyint(0) NOT NULL DEFAULT 1 COMMENT '状态{0:禁用,1:正常}',
  `is_deleted` tinyint(0) NOT NULL DEFAULT 0,
  `update_time` bigint(0) NULL DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` bigint(0) NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `data_permission` tinyint(0) NULL DEFAULT 0 COMMENT '是否开启数据权限--该字段后期删除，用于测试',
  `is_registered_face` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否注册了人脸识别',
  `external_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '外部id',
  `em_status` tinyint(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_name`(`user_name`) USING BTREE,
  INDEX `idx_account`(`account`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1661943957501730819 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (4, 1, '掌声', NULL, '20', NULL, 'aaaaa@qq.com', 'SDWAR!@', 'aaaaa@qq.com', '12345', NULL, NULL, NULL, NULL, 0, NULL, 1, 0, NULL, NULL, '测试优化信息', 0, 0, NULL, NULL, NULL, NULL, 0, 0, NULL, 1);
INSERT INTO `tb_user` VALUES (5, 1, 'hibernateJPa09', 'hibernateJPa03', '1', NULL, 'hibernateJPa03', 'ASWDEWSAW', 'hibernateJPa03@hbm.com', '12345', NULL, NULL, NULL, NULL, 0, NULL, 1, 0, NULL, NULL, '测试优化b信息', 1, 0, NULL, NULL, NULL, NULL, 0, 0, NULL, 1);
INSERT INTO `tb_user` VALUES (7, 1, 'hibernateTest', NULL, '5', NULL, 'hibernateTest', 'ASWDEWSA', 'hibernate01@hbm.com', NULL, NULL, NULL, NULL, NULL, 0, NULL, 1, 0, NULL, NULL, NULL, 1, 0, NULL, NULL, NULL, NULL, 0, 0, NULL, 1);
INSERT INTO `tb_user` VALUES (9, 1, 'hibernateTest01', 'hibernateTest01', '6', NULL, 'hibernateTest01', 'ASWDEWSA', 'hibernate02@hbm.com', NULL, NULL, NULL, NULL, NULL, 0, NULL, 1, 0, NULL, NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, 0, 0, NULL, 1);
INSERT INTO `tb_user` VALUES (11, 1, 'hibernateJPa06', 'hibernateJPa03', '4', NULL, 'hibernateJPa03', 'ASWDEWSAW', 'hibernateJPa03@hbm.com', NULL, NULL, NULL, NULL, NULL, 0, NULL, 1, 0, NULL, NULL, NULL, 1, 0, NULL, NULL, NULL, NULL, 0, 0, NULL, 1);
INSERT INTO `tb_user` VALUES (12, 1, 'hibernateJPa03', 'hibernateJPa03', '3', NULL, 'hibernateJPa03', 'ASWDEWSAW', 'hibernateJPa03@hbm.com', NULL, NULL, NULL, NULL, NULL, 0, NULL, 1, 0, NULL, NULL, NULL, 1, 1, NULL, NULL, NULL, NULL, 0, 0, NULL, 1);
INSERT INTO `tb_user` VALUES (13, 1, '小红', 'hibernateJPa03', '2', NULL, 'xiaohong', 'COG+UWYBzzwiBYvREhp5ig==', 'xiaohong@test.com', '13673599321', NULL, NULL, NULL, NULL, 0, NULL, 1, 0, NULL, NULL, NULL, 0, 0, 1697175288130, 'System', NULL, NULL, 0, 0, NULL, 1);
INSERT INTO `tb_user` VALUES (14, 1, 'hibernateTest05', 'hibernateTest01', '9', NULL, 'hibernateTest01', 'ASWDEWSA', 'hibernate02@hbm.com', NULL, NULL, NULL, NULL, NULL, 0, NULL, 1, 0, NULL, NULL, NULL, 0, 1, NULL, NULL, NULL, NULL, 0, 0, NULL, 1);
INSERT INTO `tb_user` VALUES (1661943957501730817, 1, 'mybatis-plus', NULL, NULL, NULL, 'mybatis-plus@qq.com', 'ASWQASDQ', 'mybatis-plus@qq.com', '1234567890', NULL, NULL, NULL, NULL, 0, NULL, 1, 0, NULL, NULL, NULL, 1, 0, NULL, NULL, NULL, NULL, 0, 0, NULL, 1);
INSERT INTO `tb_user` VALUES (1661943957501730818, 1, '超级管理员', NULL, NULL, NULL, 'superAdmin', 'TN9ATbwKFPYtAffzc6RIFg==', 'superAdmin@test.com', '13673599012', NULL, NULL, NULL, NULL, 0, 1697785839420, 1, 0, NULL, NULL, NULL, 1, 0, 1697785839432, 'System', 1697173699678, 'System', 0, 0, NULL, 1);
INSERT INTO `tb_user` VALUES (1661943957501730819, 1, '小明', NULL, NULL, NULL, 'xiaoming', 'COG+UWYBzzwiBYvREhp5ig==', 'xiaoming@test.com', '13673599411', NULL, NULL, NULL, NULL, 0, NULL, 1, 0, NULL, NULL, NULL, 1, 0, 1697175277321, 'System', 1697173851377, 'System', 0, 0, NULL, 1);

-- ----------------------------
-- Table structure for tb_user_dept
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_dept`;
CREATE TABLE `tb_user_dept`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT,
  `department_id` bigint(0) UNSIGNED NULL DEFAULT NULL COMMENT '部门id',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户id',
  `is_deleted` tinyint(0) NULL DEFAULT 0,
  `create_time` bigint(0) NULL DEFAULT NULL,
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `update_time` bigint(0) NULL DEFAULT NULL,
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_department_id`(`department_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 307121 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户部门关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_dept
-- ----------------------------

-- ----------------------------
-- Table structure for tb_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_role`;
CREATE TABLE `tb_user_role`  (
  `id` bigint(0) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(0) UNSIGNED NOT NULL COMMENT '用户id',
  `role_id` bigint(0) UNSIGNED NOT NULL COMMENT '角色id',
  `type` tinyint(0) NOT NULL DEFAULT 1 COMMENT '类型 {1:普通角色,2:管理角色}',
  `weight` tinyint(0) NOT NULL DEFAULT 1 COMMENT '权重',
  `create_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `create_time` bigint(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '更新人',
  `update_time` bigint(0) NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` tinyint(0) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_role
-- ----------------------------
INSERT INTO `tb_user_role` VALUES (1, 1, 1, 1, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO `tb_user_role` VALUES (2, 4, 2, 1, 1, NULL, NULL, NULL, NULL, 0);
INSERT INTO `tb_user_role` VALUES (3, 5, 3, 1, 1, NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for tenant_dingtalk
-- ----------------------------
DROP TABLE IF EXISTS `tenant_dingtalk`;
CREATE TABLE `tenant_dingtalk`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `is_sync` tinyint(1) NULL DEFAULT 0 COMMENT '是否同步 0-否 1-是',
  `app_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '应用名称',
  `tenant_id` bigint(0) UNSIGNED NULL DEFAULT 0 COMMENT '租户ID',
  `type` tinyint(1) NULL DEFAULT 0 COMMENT '应用类型 0-内部应用 1-外部应用',
  `corp_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'corp_id',
  `sso_secret` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'sso_secret',
  `app_agent_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'AgentId',
  `app_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'app_key',
  `app_secret` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'app_secret',
  `suite_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'suiteId',
  `app_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'appId',
  `suite_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'suiteKey',
  `suite_secret` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'suiteSecret',
  `token` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'Token',
  `secret_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '数据加密密钥',
  `suite_ticket` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'suiteTicket',
  `permanent_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '永久授权码',
  `login_app_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '扫码登录appId',
  `login_app_secret` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '扫码登录appSecret',
  `call_back_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '钉钉通讯录回调URL',
  `login_level` int(0) NULL DEFAULT 0 COMMENT '登录方式级别',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '对接钉钉表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_dingtalk
-- ----------------------------

-- ----------------------------
-- Table structure for tenant_wechat
-- ----------------------------
DROP TABLE IF EXISTS `tenant_wechat`;
CREATE TABLE `tenant_wechat`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `is_sync` tinyint(1) NULL DEFAULT 0 COMMENT '是否同步 0-否 1-是',
  `app_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '应用名称',
  `tenant_id` bigint(0) UNSIGNED NULL DEFAULT 0 COMMENT '租户ID',
  `type` tinyint(1) NULL DEFAULT 0 COMMENT '应用类型 0-内部应用 2-外部应用',
  `corp_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '企业ID',
  `app_agent_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'AgentId',
  `app_secret` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'AgentId',
  `login_redirect_uri` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '回调URL',
  `token` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '回调token',
  `encoding_aes_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '回调EncodingAESKey',
  `login_level` int(0) NULL DEFAULT 0 COMMENT '登录方式级别',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '对接企业微信表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tenant_wechat
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
