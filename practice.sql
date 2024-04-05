/*
Navicat MySQL Data Transfer

Source Server         : root
Source Server Version : 50612
Source Host           : localhost:3306
Source Database       : practice

Target Server Type    : MYSQL
Target Server Version : 50612
File Encoding         : 65001

Date: 2024-04-05 21:43:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for class_info
-- ----------------------------
DROP TABLE IF EXISTS `class_info`;
CREATE TABLE `class_info` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `class_name` varchar(255) DEFAULT NULL COMMENT '班级名称',
  `practice_title` varchar(255) DEFAULT NULL COMMENT '实训名称',
  `practice_content` varchar(255) DEFAULT NULL COMMENT '实训内容',
  `relation_teacher_id` bigint(20) DEFAULT NULL COMMENT '关联教师id',
  `is_deleted` int(11) DEFAULT '0' COMMENT '是否删除 0-未删除 1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='班级表';

-- ----------------------------
-- Table structure for daily_report
-- ----------------------------
DROP TABLE IF EXISTS `daily_report`;
CREATE TABLE `daily_report` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `comment` varchar(255) DEFAULT NULL COMMENT '教师评价',
  `start_time` datetime DEFAULT NULL COMMENT '创建时间',
  `relation_student_id` bigint(20) DEFAULT NULL COMMENT '关联学生id',
  `relation_class_id` bigint(20) DEFAULT NULL COMMENT '关联班级id',
  `relation_teacher_id` bigint(20) DEFAULT NULL COMMENT '关联教师id',
  `is_deleted` int(11) DEFAULT '0' COMMENT '是否删除 0-未删除 1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日报表';

-- ----------------------------
-- Table structure for discuss_info
-- ----------------------------
DROP TABLE IF EXISTS `discuss_info`;
CREATE TABLE `discuss_info` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `relation_teacher_id` bigint(20) DEFAULT NULL COMMENT '教师id',
  `relation_student_id` bigint(20) DEFAULT NULL COMMENT '关联学生id',
  `relation_class_id` bigint(20) DEFAULT NULL COMMENT '关联班级id',
  `relation_discuss_id` bigint(20) DEFAULT '-1' COMMENT '关联主答疑id 无关联则值为-1',
  `relation_imgs_id` bigint(20) DEFAULT '-1' COMMENT '关联图片集id 无关联则值为-1',
  `is_deleted` int(11) DEFAULT '0' COMMENT '是否删除 0-未删除 1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='答疑表';

-- ----------------------------
-- Table structure for imgs_info
-- ----------------------------
DROP TABLE IF EXISTS `imgs_info`;
CREATE TABLE `imgs_info` (
  `img_id` bigint(20) NOT NULL COMMENT '图片自己的id 不重复',
  `imgs_id` bigint(20) DEFAULT NULL COMMENT '图片所属图片集id',
  `img_url` varchar(255) DEFAULT NULL COMMENT '图片路径',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `is_delete` int(11) DEFAULT '0' COMMENT '是否删除 0-未删除 1-已删除',
  PRIMARY KEY (`img_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for resource_info
-- ----------------------------
DROP TABLE IF EXISTS `resource_info`;
CREATE TABLE `resource_info` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `resource_name` varchar(255) DEFAULT NULL COMMENT '资源名称',
  `resource_url` varchar(255) DEFAULT NULL COMMENT '资源链接',
  `resource_type` int(11) DEFAULT NULL COMMENT '资源类型 1-链接 2-文件',
  `relation_teacher_id` bigint(20) DEFAULT NULL,
  `relation_class_id` bigint(20) DEFAULT NULL COMMENT '关联班级id',
  `is_deleted` int(11) DEFAULT '0' COMMENT '是否删除 0-未删除 1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源表';

-- ----------------------------
-- Table structure for student_info
-- ----------------------------
DROP TABLE IF EXISTS `student_info`;
CREATE TABLE `student_info` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `student_name` varchar(255) DEFAULT NULL COMMENT '学生姓名',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `student_num` varchar(255) DEFAULT NULL COMMENT '学号（账号）',
  `gender` int(11) DEFAULT NULL COMMENT '性别 1-男 2-女',
  `birthday` varchar(255) DEFAULT '' COMMENT '生日',
  `relation_user_id` bigint(20) DEFAULT NULL COMMENT '关联用户id',
  `relation_class_id` bigint(20) DEFAULT NULL COMMENT '关联班级id',
  `is_deleted` int(11) DEFAULT '0' COMMENT '是否删除 0-未删除 1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学生表';

-- ----------------------------
-- Table structure for task_grade
-- ----------------------------
DROP TABLE IF EXISTS `task_grade`;
CREATE TABLE `task_grade` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `score` int(11) DEFAULT NULL COMMENT '分数',
  `relation_task_id` bigint(20) DEFAULT NULL COMMENT '关联任务id',
  `relation_student_id` bigint(20) DEFAULT NULL COMMENT '关联学生id',
  `relation_teacher_id` bigint(20) DEFAULT NULL COMMENT '教师id',
  `is_deleted` int(11) DEFAULT '0' COMMENT '是否删除 0-未删除 1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务评分表';

-- ----------------------------
-- Table structure for task_info
-- ----------------------------
DROP TABLE IF EXISTS `task_info`;
CREATE TABLE `task_info` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `task_name` varchar(255) DEFAULT NULL COMMENT '任务名称',
  `content` varchar(255) DEFAULT NULL COMMENT '任务内容',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `relation_class_id` bigint(20) DEFAULT NULL COMMENT '关联班级id',
  `relation_teacher_id` bigint(20) DEFAULT NULL COMMENT '教师id',
  `task_status` int(11) DEFAULT '0' COMMENT '任务状态 0-未开始 1-进行中 2-已结束',
  `is_deleted` int(11) DEFAULT '0' COMMENT '是否删除 0-未删除 1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务管理';

-- ----------------------------
-- Table structure for teacher_info
-- ----------------------------
DROP TABLE IF EXISTS `teacher_info`;
CREATE TABLE `teacher_info` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `teacher_name` varchar(255) DEFAULT NULL COMMENT '教师姓名',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `gender` int(11) DEFAULT NULL COMMENT '教师性别 1-男 2-女',
  `birthday` varchar(255) DEFAULT '' COMMENT '生日',
  `work_info` varchar(255) DEFAULT NULL COMMENT '职务',
  `relation_user_id` bigint(20) DEFAULT NULL COMMENT '关联用户id',
  `teacher_num` varchar(255) DEFAULT NULL COMMENT '教师工号（账号）',
  `is_deleted` int(11) DEFAULT '0' COMMENT '是否删除 0-未删除 1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='教师表';

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `account_num` varchar(255) DEFAULT NULL COMMENT '用户名（账号）',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `user_type` int(11) DEFAULT NULL COMMENT '用户类型 1-教师 2-学生',
  `user_status` int(11) DEFAULT '0' COMMENT '用户状态 0-正常 1-注销',
  `telephone` varchar(255) DEFAULT NULL COMMENT '手机号',
  `user_ava` varchar(1024) DEFAULT NULL COMMENT '用户头像',
  `is_deleted` int(11) DEFAULT '0' COMMENT '是否删除 0-未删除 1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Table structure for video_info
-- ----------------------------
DROP TABLE IF EXISTS `video_info`;
CREATE TABLE `video_info` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `created_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `title` varchar(255) DEFAULT NULL COMMENT '视频标题',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `video_url` varchar(255) DEFAULT NULL COMMENT '视频链接',
  `relation_student_id` bigint(20) DEFAULT NULL COMMENT '关联学生id',
  `relation_class_id` bigint(20) DEFAULT NULL COMMENT '关联班级id',
  `is_deleted` int(11) DEFAULT '0' COMMENT '是否删除 0-未删除 1-已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='视频表';
