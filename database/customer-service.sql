-- 通用型智能客服数据库
DROP DATABASE IF EXISTS `ai_hub`;
CREATE DATABASE IF NOT EXISTS `ai_hub`;
USE `ai_hub`;

-- 产品/服务表
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`
(
    `id`             int unsigned   NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`           varchar(100)   NOT NULL DEFAULT '' COMMENT '产品/服务名称',
    `category`       varchar(50)    NOT NULL DEFAULT '' COMMENT '产品分类',
    `sub_category`   varchar(50)             DEFAULT '' COMMENT '子分类',
    `description`    text COMMENT '产品描述',
    `price`          decimal(10, 2) NOT NULL DEFAULT '0.00' COMMENT '价格',
    `original_price` decimal(10, 2)          DEFAULT '0.00' COMMENT '原价',
    `status`         tinyint                 DEFAULT '1' COMMENT '状态：0-下架，1-上架',
    `create_time`    datetime                DEFAULT CURRENT_TIMESTAMP,
    `update_time`    datetime                DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='产品/服务表';

-- 分支机构表
DROP TABLE IF EXISTS `branch`;
CREATE TABLE `branch`
(
    `id`             int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`           varchar(100) DEFAULT NULL COMMENT '分支机构名称',
    `city`           varchar(50)  DEFAULT NULL COMMENT '所在城市',
    `province`       varchar(50)  DEFAULT NULL COMMENT '所在省份',
    `address`        varchar(200) DEFAULT NULL COMMENT '详细地址',
    `contact_phone`  varchar(20)  DEFAULT NULL COMMENT '联系电话',
    `business_hours` varchar(100) DEFAULT NULL COMMENT '营业时间',
    `status`         tinyint      DEFAULT '1' COMMENT '状态：0-关闭，1-营业',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='分支机构表';

-- 咨询预约表
DROP TABLE IF EXISTS `consultation`;
CREATE TABLE `consultation`
(
    `id`                int          NOT NULL AUTO_INCREMENT,
    `product_id`        int                   DEFAULT NULL COMMENT '咨询产品ID',
    `product_name`      varchar(100) NOT NULL DEFAULT '' COMMENT '咨询产品名称',
    `customer_name`     varchar(50)  NOT NULL COMMENT '客户姓名',
    `contact_phone`     varchar(20)  NOT NULL COMMENT '联系电话',
    `contact_email`     varchar(100)          DEFAULT NULL COMMENT '联系邮箱',
    `consultation_type` varchar(50)           DEFAULT '产品咨询' COMMENT '咨询类型：产品咨询、技术支持、商务合作等',
    `branch_id`         int                   DEFAULT NULL COMMENT '分支机构ID',
    `branch_name`       varchar(50)           DEFAULT NULL COMMENT '分支机构名称',
    `preferred_time`    datetime              DEFAULT NULL COMMENT '期望咨询时间',
    `remark`            text COMMENT '备注信息',
    `status`            tinyint               DEFAULT '0' COMMENT '状态：0-待处理，1-已联系，2-已完成，3-已取消',
    `create_time`       datetime              DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='咨询预约表';

-- 插入产品/服务数据
INSERT INTO `product` (`id`, `name`, `category`, `sub_category`, `description`, `price`, `original_price`, `status`,
                       `create_time`, `update_time`)
VALUES (1, '企业级智能客服系统', '软件服务', 'AI客服', '基于AI的企业级智能客服解决方案，支持多渠道接入，24小时在线服务',
        9999.00, 12999.00, 1, NOW(), NOW()),
       (2, '电商运营培训课程', '教育培训', '电商培训', '从零开始的电商运营实战培训，包含淘宝、京东、拼多多等平台运营技巧',
        2999.00, 3999.00, 1, NOW(), NOW()),
       (3, '企业网站建设服务', '技术服务', '网站建设', '专业的企业网站设计与开发服务，响应式设计，SEO优化', 5000.00,
        8000.00, 1, NOW(), NOW()),
       (4, '数据分析咨询服务', '咨询服务', '数据分析', '企业数据分析与商业智能咨询，帮助企业制定数据驱动决策', 15000.00,
        20000.00, 1, NOW(), NOW()),
       (5, '移动应用开发', '技术服务', 'APP开发', 'iOS/Android移动应用定制开发，原生开发，性能优化', 30000.00, 50000.00,
        1, NOW(), NOW()),
       (6, 'SEO优化服务', '技术服务', '网络营销', '搜索引擎优化服务，提升网站排名和流量', 8000.00, 12000.00, 1, NOW(),
        NOW()),
       (7, 'UI设计培训', '教育培训', '设计培训', '专业的UI设计培训课程，包含Figma、Sketch等工具使用', 3999.00, 5999.00, 1,
        NOW(), NOW()),
       (8, '云计算解决方案', '技术服务', '云服务', '企业级云计算解决方案，包含AWS、阿里云、腾讯云等平台', 25000.00,
        35000.00, 1, NOW(), NOW()),
       (9, '财务咨询服务', '咨询服务', '财务咨询', '企业财务管理咨询，包含税务筹划、成本控制等', 12000.00, 18000.00, 1,
        NOW(), NOW()),
       (10, '人力资源培训', '教育培训', '管理培训', '企业人力资源管理培训，提升团队管理能力', 1999.00, 2999.00, 1, NOW(),
        NOW());

-- 插入分支机构数据
INSERT INTO `branch` (`id`, `name`, `city`, `province`, `address`, `contact_phone`, `business_hours`, `status`)
VALUES (1, '北京总部', '北京', '北京市', '北京市朝阳区建国门外大街1号国贸大厦A座15层', '010-12345678',
        '周一至周五 9:00-18:00', 1),
       (2, '上海分公司', '上海', '上海市', '上海市浦东新区陆家嘴环路1000号恒生银行大厦20层', '021-12345678',
        '周一至周五 9:00-18:00', 1),
       (3, '深圳分公司', '深圳', '广东省', '深圳市南山区科技园南区深圳湾科技生态园10栋A座', '0755-12345678',
        '周一至周五 9:00-18:00', 1),
       (4, '杭州分公司', '杭州', '浙江省', '杭州市西湖区文三路259号昌地火炬大厦3号楼', '0571-12345678',
        '周一至周五 9:00-18:00', 1),
       (5, '广州分公司', '广州', '广东省', '广州市天河区珠江新城花城大道85号高德置地春广场', '020-12345678',
        '周一至周五 9:00-18:00', 1),
       (6, '成都分公司', '成都', '四川省', '成都市高新区天府大道中段1388号美年广场A座', '028-12345678',
        '周一至周五 9:00-18:00', 1),
       (7, '武汉分公司', '武汉', '湖北省', '武汉市洪山区珞喻路129号华中科技大学科技园', '027-12345678',
        '周一至周五 9:00-18:00', 1),
       (8, '西安分公司', '西安', '陕西省', '西安市高新区科技路50号金桥国际广场C座', '029-12345678',
        '周一至周五 9:00-18:00', 1),
       (9, '南京分公司', '南京', '江苏省', '南京市建邺区奥体大街68号新城科技园', '025-12345678',
        '周一至周五 9:00-18:00', 1),
       (10, '重庆分公司', '重庆', '重庆市', '重庆市渝北区龙溪街道金龙路2号协信中心', '023-12345678',
        '周一至周五 9:00-18:00', 1);

-- 插入咨询预约数据
INSERT INTO `consultation` (`id`, `product_id`, `product_name`, `customer_name`, `contact_phone`, `contact_email`,
                            `consultation_type`, `branch_id`, `branch_name`, `preferred_time`, `remark`, `status`,
                            `create_time`)
VALUES (1, 1, '企业级智能客服系统', '张经理', '13800138001', 'zhang@company.com', '产品咨询', 1, '北京总部',
        '2024-01-15 14:00:00', '希望了解系统集成方案和价格', 0, NOW()),
       (2, 3, '企业网站建设服务', '李总', '13900139001', 'li@company.com', '商务合作', 2, '上海分公司',
        '2024-01-16 10:00:00', '需要定制化网站开发，预算5-8万', 1, NOW()),
       (3, 2, '电商运营培训课程', '王女士', '13700137001', 'wang@shop.com', '产品咨询', 3, '深圳分公司',
        '2024-01-17 15:00:00', '想了解课程内容和就业前景', 0, NOW()),
       (4, 4, '数据分析咨询服务', '陈总监', '13600136001', 'chen@data.com', '商务合作', 4, '杭州分公司',
        '2024-01-18 09:00:00', '公司需要建立数据分析体系', 2, NOW()),
       (5, 5, '移动应用开发', '刘老板', '13500135001', 'liu@tech.com', '产品咨询', 5, '广州分公司',
        '2024-01-19 11:00:00', '想开发一个电商APP，需要报价', 0, NOW()),
       (6, 6, 'SEO优化服务', '赵经理', '13400134001', 'zhao@seo.com', '技术支持', 6, '成都分公司',
        '2024-01-20 16:00:00', '网站排名下降，需要优化方案', 1, NOW()),
       (7, 7, 'UI设计培训', '孙小姐', '13300133001', 'sun@design.com', '产品咨询', 7, '武汉分公司',
        '2024-01-21 13:00:00', '零基础想学UI设计，咨询课程安排', 0, NOW()),
       (8, 8, '云计算解决方案', '周总', '13200132001', 'zhou@cloud.com', '商务合作', 8, '西安分公司',
        '2024-01-22 14:30:00', '公司需要上云，需要整体解决方案', 1, NOW()),
       (9, 9, '财务咨询服务', '吴会计', '13100131001', 'wu@finance.com', '产品咨询', 9, '南京分公司',
        '2024-01-23 10:00:00', '公司财务制度需要优化', 0, NOW()),
       (10, 10, '人力资源培训', '郑HR', '13000130001', 'zheng@hr.com', '产品咨询', 10, '重庆分公司',
        '2024-01-24 15:00:00', '团队管理能力需要提升', 2, NOW());