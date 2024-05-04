/*
 Navicat Premium Data Transfer

 Source Server         : windows_mysql
 Source Server Type    : MySQL
 Source Server Version : 80035
 Source Host           : localhost:3306
 Source Schema         : xianbao

 Target Server Type    : MySQL
 Target Server Version : 80035
 File Encoding         : 65001

 Date: 04/05/2024 21:55:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系人',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系地址',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系电话',
  `user_id` int NULL DEFAULT NULL COMMENT '关联用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '收货地址' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of address
-- ----------------------------
INSERT INTO `address` VALUES (1, '姬如雪', '江西省南昌市', '18170413233', 3);
INSERT INTO `address` VALUES (2, '李星云', '江西省南昌市江西农业大学东区', '18170413231', 2);
INSERT INTO `address` VALUES (3, '李星云', '江西省九江市柴桑区', '18170413231', 2);

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色标识',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '管理员' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin', 'admin', '管理员', 'http://localhost:9090/files/1697438073596-avatar.png', 'ADMIN', '13677889922', 'admin@xm.com');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '分类' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '穿搭');
INSERT INTO `category` VALUES (2, '家居');
INSERT INTO `category` VALUES (3, '笔记本平板');
INSERT INTO `category` VALUES (4, '手机');
INSERT INTO `category` VALUES (5, '电动车');
INSERT INTO `category` VALUES (6, '自行车');
INSERT INTO `category` VALUES (7, '足球');
INSERT INTO `category` VALUES (8, '篮球');
INSERT INTO `category` VALUES (9, '乐器');
INSERT INTO `category` VALUES (10, '耳机音箱');
INSERT INTO `category` VALUES (11, '图书');
INSERT INTO `category` VALUES (12, '游戏');
INSERT INTO `category` VALUES (14, 'IT');
INSERT INTO `category` VALUES (16, '动漫');

-- ----------------------------
-- Table structure for chat_group
-- ----------------------------
DROP TABLE IF EXISTS `chat_group`;
CREATE TABLE `chat_group`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `chat_user_id` int NULL DEFAULT NULL COMMENT '聊天用户ID',
  `user_id` int NULL DEFAULT NULL COMMENT '当前用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '聊天组' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of chat_group
-- ----------------------------
INSERT INTO `chat_group` VALUES (2, 4, 2);
INSERT INTO `chat_group` VALUES (3, 2, 4);
INSERT INTO `chat_group` VALUES (4, 3, 4);
INSERT INTO `chat_group` VALUES (5, 4, 3);

-- ----------------------------
-- Table structure for chat_info
-- ----------------------------
DROP TABLE IF EXISTS `chat_info`;
CREATE TABLE `chat_info`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `chat_user_id` int NULL DEFAULT NULL COMMENT '聊天用户ID',
  `user_id` int NULL DEFAULT NULL COMMENT '当前用户ID',
  `text` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '聊天内容',
  `isread` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '否' COMMENT '是否已读',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '聊天信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of chat_info
-- ----------------------------
INSERT INTO `chat_info` VALUES (1, 2, 4, '你好，在吗？', '是', '2024-05-02 23:19:03');
INSERT INTO `chat_info` VALUES (2, 4, 2, '在的，需要什么吗？', '是', '2024-05-02 23:19:45');
INSERT INTO `chat_info` VALUES (3, 2, 4, '那个篮球很不错，我想要了。', '是', '2024-05-02 23:20:49');
INSERT INTO `chat_info` VALUES (4, 4, 2, '直接买就行。', '是', '2024-05-02 23:21:07');

-- ----------------------------
-- Table structure for circles
-- ----------------------------
DROP TABLE IF EXISTS `circles`;
CREATE TABLE `circles`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '缩略图',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '圈子' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of circles
-- ----------------------------
INSERT INTO `circles` VALUES (1, 'http://localhost:9090/files/1712478997560-全部.png', '全部');
INSERT INTO `circles` VALUES (2, 'http://localhost:9090/files/1712479010534-生活服务.png', '生活服务');
INSERT INTO `circles` VALUES (3, 'http://localhost:9090/files/1712479026612-图书馆.png', '图书教材');
INSERT INTO `circles` VALUES (4, 'http://localhost:9090/files/1712479033216-音乐.png', '音乐');
INSERT INTO `circles` VALUES (5, 'http://localhost:9090/files/1712479047560-鼠标.png', '数码产品');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '内容',
  `user_id` int NULL DEFAULT NULL COMMENT '评论人',
  `pid` int NULL DEFAULT NULL COMMENT '父级ID',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '评论时间',
  `fid` int NULL DEFAULT NULL COMMENT '关联ID',
  `module` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '模块',
  `root_id` int NULL DEFAULT NULL COMMENT '根节点ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '评论表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, '这本书怎么样?', 2, NULL, '2024-4-22', 19, 'goods', 1);
INSERT INTO `comment` VALUES (2, '非常不错', 3, 1, '2024-4-22', 19, 'goods', 1);
INSERT INTO `comment` VALUES (3, '喜欢的可以直接拍下哦', 3, NULL, '2024-4-22', 19, 'goods', 3);
INSERT INTO `comment` VALUES (4, '喜欢吉他的宝子们，这个非常不错哦，喜欢的话，可以点击我想要哦。', 2, NULL, '2024-04-24 11:14:06', 7, 'goods', 4);
INSERT INTO `comment` VALUES (5, '看起来很不错的。', 3, NULL, '2024-04-24 11:14:52', 7, 'goods', 5);
INSERT INTO `comment` VALUES (6, '我很喜欢，可以优惠吗？', 3, 4, '2024-04-24 12:05:46', 7, 'goods', 4);
INSERT INTO `comment` VALUES (7, '可以哦，点击我需要和我聊一聊吧。', 2, 6, '2024-04-24 12:06:42', 7, 'goods', 4);
INSERT INTO `comment` VALUES (14, '太帅了', 3, NULL, '2024-04-28 16:31:29', 20, 'goods', 14);
INSERT INTO `comment` VALUES (15, '是的，非常帅。', 2, 14, '2024-04-28 16:31:46', 20, 'goods', 14);
INSERT INTO `comment` VALUES (16, '欢迎欢迎，我也非常喜欢海贼王！', 2, NULL, '2024-04-30 18:14:36', 9, 'posts', 16);
INSERT INTO `comment` VALUES (17, '感谢！！！', 4, 16, '2024-04-30 18:26:48', 9, 'posts', 16);
INSERT INTO `comment` VALUES (18, '非常的nice', 4, NULL, '2024-04-30 18:33:32', 10, 'posts', 18);
INSERT INTO `comment` VALUES (20, '太帅了，就是我想要的！！！', 4, NULL, '2024-05-01 16:40:15', 6, 'goods', 20);
INSERT INTO `comment` VALUES (21, '哈哈哈，需要的话就直接下单吧。', 2, 20, '2024-05-01 16:41:01', 6, 'goods', 20);
INSERT INTO `comment` VALUES (36, '哈哈哈，我有篮球哦。\n需要的话点击下面这个链接看看吧。\nhttp://localhost:8080/front/goodsDetail?id=6', 2, NULL, '2024-05-01 17:16:21', 5, 'help', 36);
INSERT INTO `comment` VALUES (37, '好的，谢谢！', 4, 36, '2024-05-01 17:16:53', 5, 'help', 36);
INSERT INTO `comment` VALUES (38, '测试1', 5, NULL, '2024-05-04 21:50:19', 22, 'goods', 38);
INSERT INTO `comment` VALUES (39, '测试2', 5, 38, '2024-05-04 21:50:26', 22, 'goods', 38);

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '主题',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '内容',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系方式',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  `reply` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '回复',
  `createtime` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '创建时间',
  `user_id` int NULL DEFAULT NULL COMMENT '提交人ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '反馈信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of feedback
-- ----------------------------
INSERT INTO `feedback` VALUES (8, '测试', '测试', '18170413233', 'jrx@xianbao.com', '测试', '2024-04-09 22:56:40', 3);
INSERT INTO `feedback` VALUES (9, '怎么发布商品', '我不能发布商品，是出了问题吗？', '18170413686', 'lf@xianbao.com', '好的，现在修复了，你可以试一下。', '2024-05-02 21:19:34', 4);

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '详情',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发货地址',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片',
  `date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '上架日期',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核状态',
  `category` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '分类',
  `user_id` int NULL DEFAULT NULL COMMENT '所属用户ID',
  `sale_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '上架状态',
  `read_count` int NULL DEFAULT 0 COMMENT '浏览量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '二手商品' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (2, '影石Insta360 Ace Pro运动相机8k徕卡摄像头', 1998.00, '<p><img src=\"https://img.alicdn.com/imgextra/i3/2208654990728/O1CN014fxPBV1HFTGmsusBp_!!2208654990728.png\" width=\"100%\"/><img src=\"http://localhost:9090/files/1710732191721-image.png\" contenteditable=\"false\" style=\"font-size: 14px; max-width: 100%;\" width=\"100%\"/></p><br/>', '安徽合肥', 'http://localhost:9090/files/1710732176479-微信截图_20240318112155.png', '2024-01-10', '通过', '耳机音箱', 2, '上架', 1);
INSERT INTO `goods` VALUES (3, 'SONY索尼WH-1000XM4/5头戴式无线蓝牙耳机主动降噪头戴4', 1999.00, '<p><img src=\"https://img.alicdn.com/imgextra/i2/2214661621088/O1CN01SJ2vWm1JuLmc8Migo_!!2214661621088-0-scmitem1000.jpg\" width=\"100%\"/><br/></p>', '安徽合肥', 'http://localhost:9090/files/1710732446620-微信截图_20240318112705.png', '2024-01-10', '通过', '耳机音箱', 2, '上架', 11);
INSERT INTO `goods` VALUES (4, '2022年卡塔尔世界杯足球官方正品5号专业训练比赛专用球儿童1355', 40.00, '<p><img src=\"https://img.alicdn.com/imgextra/i1/2616970884/O1CN01SPbEn61IOuwP1G1oE_!!2616970884.jpg\" width=\"100%\"/><img src=\"http://localhost:9090/files/1710732567587-image.png\" contenteditable=\"false\" style=\"font-size: 14px; max-width: 100%;\"/></p><br/>', '安徽合肥', 'http://localhost:9090/files/1710732543650-微信截图_20240318112858.png', '2024-01-10', '通过', '足球', 2, '上架', 1);
INSERT INTO `goods` VALUES (5, '足球五号初中生中考训练专用球', 199.00, '<p><img src=\"https://img.alicdn.com/imgextra/i2/2193975096/O1CN010RF9HT1nW1HJmulLe_!!2193975096-0-scmitem176000.jpg\" width=\"100%\"/><img src=\"http://localhost:9090/files/1710732634056-image.png\" contenteditable=\"false\" style=\"font-size: 14px; max-width: 100%;\"/></p><br/>', '安徽合肥', 'http://localhost:9090/files/1710732630892-微信截图_20240318113020.png', '2024-01-10', '通过', '足球', 2, '上架', 61);
INSERT INTO `goods` VALUES (6, '斯伯丁篮球官方正品中考学生专用7号成人橡胶室外篮球耐磨防滑', 199.00, '<p><img src=\"https://img.alicdn.com/imgextra/i4/2215726198477/O1CN01uDQHZo2CUWMouy2f0_!!2215726198477.jpg\" width=\"100%\"/><img src=\"https://img.alicdn.com/imgextra/i2/2215726198477/O1CN01suxVZE2CUWHaf2Ms0_!!2215726198477.jpg\" style=\"font-size: 14px;\" width=\"100%\"/></p><br/>', '安徽合肥', 'http://localhost:9090/files/1710732691542-微信截图_20240318113120.png', '2024-01-10', '通过', '篮球', 2, '上架', 198);
INSERT INTO `goods` VALUES (7, '震撼大师手工全单古典吉他', 1998.00, '<p><img src=\"https://img.alicdn.com/imgextra/i2/2289123357/O1CN01gFdEtC1afYLPtjh3X_!!2289123357.jpg\" width=\"100%\"/></p><br/>', '安徽合肥', 'http://localhost:9090/files/1710732810749-微信截图_20240318113309.png', '2024-01-10', '通过', '乐器', 2, '上架', 197);
INSERT INTO `goods` VALUES (8, '专业笛子初学者零基础竹笛入门苦竹f演奏级c调成人e儿童g横笛乐器', 199.00, '<p><img src=\"https://img.alicdn.com/imgextra/i1/2417340146/O1CN01nNji0v1CwugZ4snBM_!!2417340146.jpg\" width=\"100%\"/><img src=\"http://localhost:9090/files/1710732892032-image.png\" contenteditable=\"false\" style=\"font-size: 14px; max-width: 100%;\"/></p><br/>', '安徽合肥', 'http://localhost:9090/files/1710732896200-微信截图_20240318113443.png', '2024-01-10', '通过', '乐器', 2, '上架', 185);
INSERT INTO `goods` VALUES (9, 'kepma卡普马吉他卡玛民谣电箱d1c/a1c初学入门男女学生儿童木吉他', 648.00, '<p><img src=\"https://img.alicdn.com/imgextra/i1/850320452/O1CN01Me5Rvy1FD3xMD4EOK_!!850320452.jpg\" width=\"100%\"/><img src=\"http://localhost:9090/files/1710732996103-image.png\" contenteditable=\"false\" style=\"font-size: 14px; max-width: 100%;\"/></p><br/>', '安徽合肥', 'http://localhost:9090/files/1710733003156-微信截图_20240318113621.png', '2024-01-10', '通过', '乐器', 3, '上架', 186);
INSERT INTO `goods` VALUES (13, '品彩新年红半高领针', 199.00, '<div><img src=\"https://img30.360buyimg.com/sku/jfs/t1/99760/24/43651/54323/64e2cde4Fbece5bd3/ac96a198fb27780a.jpg\"/>	</div>\n<div><img src=\"https://img30.360buyimg.com/sku/jfs/t1/189762/23/36644/55229/64e2cde4F8786c6ee/8dadfcf2f1c54a99.jpg\"/>	</div>', '安徽合肥', 'http://localhost:9090/files/1704875358717-微信截图_20240110162417.png', '2024-01-10', '通过', '穿搭', 3, '上架', 173);
INSERT INTO `goods` VALUES (15, '二手商品', 10.00, '<p><img src=\"http://localhost:9090/files/1708400286601-image.png\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', '东校区', 'http://localhost:9090/files/1708399512763-蛋糕.png', '2024-02-20', '通过', '耳机音箱', 3, '下架', 186);
INSERT INTO `goods` VALUES (16, '键盘', 200.00, '<p><img src=\"https://img.alicdn.com/imgextra/i4/2212972276013/O1CN013AucSC1uI0SlSDkvD_!!2212972276013.jpg\" width=\"100%\"/><img src=\"http://localhost:9090/files/1710756960569-image.png\" contenteditable=\"false\" style=\"font-size: 14px; max-width: 100%;\"/></p><br/>', '合肥', 'http://localhost:9090/files/1710756941461-微信截图_20240318181524.png', '2024-03-18', '通过', '笔记本平板', 3, '上架', 187);
INSERT INTO `goods` VALUES (17, '【官方正版】高等数学同济七版 教材上册+下册 同济大学高等数学第七版 大一课本高等数学第7版 高数考研数学教材 高等教育出版社', 30.00, '<p><img src=\"https://img.alicdn.com/imgextra/i2/101450072/O1CN01QxR1em1CP1Gj2vT4A-101450072.jpg\" width=\"100%\"/><img src=\"http://localhost:9090/files/1710756616895-image.png\" contenteditable=\"false\" style=\"font-size: 14px; max-width: 100%;\"/></p><br/>', '安徽合肥', 'http://localhost:9090/files/1710756599260-微信截图_20240318180909.png', '2024-03-18', '通过', '图书', 3, '上架', 177);
INSERT INTO `goods` VALUES (18, '新华正版】工程数学 线性代数第六版 高等教育出版社 线性代数同济六版6版 工程数学线性代数同济大学线代 考研数学教材用书', 28.00, '<p><img src=\"https://img.alicdn.com/imgextra/i3/101450072/O1CN01fT1YYK1CP19H32Ph4-101450072.jpg\" width=\"100%\"/><img src=\"http://localhost:9090/files/1710756841433-image.png\" contenteditable=\"false\" style=\"font-size: 14px; max-width: 100%;\"/></p><br/>', '安徽合肥', 'http://localhost:9090/files/1710756827594-微信截图_20240318180926.png', '2024-03-18', '通过', '图书', 3, '上架', 190);
INSERT INTO `goods` VALUES (19, '【官方正版】明朝那些事儿 原著原版无删减中文译本 ', 39.60, '<p><img src=\"https://img.alicdn.com/imgextra/i3/2129856610/O1CN01z5eg4C1yhQfDUYBh7-2129856610.jpg\" width=\"100%\"/>', '安徽省合肥市政务区111号', 'http://localhost:9090/files/mc.jpg', '2024-03-26', '通过', '图书', 3, '上架', 224);
INSERT INTO `goods` VALUES (20, 'xbox联名星空手柄', 599.00, '<p><br/></p><p><img src=\"https://img.alicdn.com/imgextra/i2/4138019020/O1CN01NkmBpX2GVDOiO0Gbg_!!4138019020.jpg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p><p><img src=\"https://img.alicdn.com/imgextra/i3/4138019020/O1CN01Ik14o22GVDOmlp2EU_!!4138019020.jpg\" style=\"max-width:100%;\" contenteditable=\"false\"/><img src=\"https://img.alicdn.com/imgextra/i2/4138019020/O1CN016caG5d2GVDOkSKuYR_!!4138019020.jpg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', '江西省南昌市', 'http://localhost:9090/files/1714034130815-xbox-xk.jpg', '2024-04-25 16:42:44', '通过', '游戏', 2, '下架', 159);
INSERT INTO `goods` VALUES (21, '海贼王万里阳光号海盗船永不翻船手办摆件', 59.00, '<p><img src=\"https://img.alicdn.com/imgextra/i4/63725874/O1CN01sIwLuk1tGLLpDnr9W_!!63725874.jpg\" style=\"max-width:100%;\" contenteditable=\"false\"/></p>', '江西省南昌市江西农业大学', 'http://localhost:9090/files/1714468022979-wlygh.jpg', '2024-04-30 17:08:27', '通过', '动漫', 4, '上架', 84);
INSERT INTO `goods` VALUES (22, '测试', 99.00, '<p>测试</p>', '测试地区', 'http://localhost:9090/files/1714830422382-xm.gif', '2024-05-04 21:49:21', '通过', 'IT', 5, '上架', 0);

-- ----------------------------
-- Table structure for help
-- ----------------------------
DROP TABLE IF EXISTS `help`;
CREATE TABLE `help`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '内容',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '状态',
  `user_id` int NULL DEFAULT NULL COMMENT '用户ID',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发布时间',
  `solved` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '是否解决',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '求助信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of help
-- ----------------------------
INSERT INTO `help` VALUES (5, '我想要一个篮球，闲友们有推荐的吗', '我也想跟我家哥哥，一起打篮球。求闲友推荐。', 'http://localhost:9090/files/1714551191365-cxk.jpg', '通过', 4, '2024-05-01 16:10:44', '已解决');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '内容',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建时间',
  `user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '公告信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, '今天系统正式上线，开始内测', '今天系统正式上线，开始内测', '2023-09-05', 'admin');
INSERT INTO `notice` VALUES (2, '所有功能都已完成，可以正常使用', '所有功能都已完成，可以正常使用', '2023-09-05', 'admin');
INSERT INTO `notice` VALUES (3, '今天天气很不错，可以出去一起玩了', '今天天气很不错，可以出去一起玩了', '2023-09-05', 'admin');
INSERT INTO `notice` VALUES (4, '测试', '测试', '2024-05-04', 'admin');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `goods_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品名称',
  `goods_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '商品图片',
  `order_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单编号',
  `total` decimal(10, 2) NULL DEFAULT NULL COMMENT '总价',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '下单时间',
  `pay_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '支付单号',
  `pay_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '支付时间',
  `user_id` int NULL DEFAULT NULL COMMENT '下单人ID',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货地址',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '联系方式',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '收货人名称',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '订单状态',
  `sale_id` int NULL DEFAULT NULL COMMENT '卖家ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '订单信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1, '【官方正版】明朝那些事儿 原著原版无删减中文译本 ', 'http://localhost:9090/files/mc.jpg', '2024042721714211092613', 39.60, '2024-04-27 17:44:52', NULL, NULL, 2, '江西省南昌市江西农业大学东区', '18170413231', '李星云', '已取消', 3);
INSERT INTO `orders` VALUES (2, 'kepma卡普马吉他卡玛民谣电箱d1c/a1c初学入门男女学生儿童木吉他', 'http://localhost:9090/files/1710733003156-微信截图_20240318113621.png', '2024042721714211558900', 648.00, '2024-04-27 17:52:38', '2024042822001424940502883813', '2024-04-28 15:53:29', 2, '江西省九江市柴桑区', '18170413231', '李星云', '已完成', 3);
INSERT INTO `orders` VALUES (3, 'xbox联名星空手柄', 'http://localhost:9090/files/1714034130815-xbox-xk.jpg', '2024042831714293117637', 599.00, '2024-04-28 16:31:57', '2024042822001424940502885463', '2024-04-28 16:33:10', 3, '江西省南昌市', '18170413233', '姬如雪', '已完成', 2);

-- ----------------------------
-- Table structure for posts
-- ----------------------------
DROP TABLE IF EXISTS `posts`;
CREATE TABLE `posts`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '内容',
  `img` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片',
  `user_id` int NULL DEFAULT NULL COMMENT '用户ID',
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '发布时间',
  `circle` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '圈子',
  `descr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '简介',
  `read_count` int NULL DEFAULT 0 COMMENT '浏览量',
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '审核状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '帖子' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of posts
-- ----------------------------
INSERT INTO `posts` VALUES (9, '欢迎热爱海贼王的闲友们！！！', '<p>大家好，我是新入驻闲宝的闲友，热爱海贼王，在这里如果也有喜欢海贼王的，可以一起交流哦。在这里我推荐我发布的商品。</p><p><a target=\"_blank\" href=\"http://localhost:8080/front/goodsDetail?id=21\">万里阳光号小手办</a><br/></p><p>欢迎大家购买哦！</p>', 'http://localhost:9090/files/1714468756665-wlygh.jpg', 4, '2024-04-30 17:19:19', '2', '新人报道', 6, '通过');
INSERT INTO `posts` VALUES (10, '一起读书吧', '<p><span style=\"font-size: 14px;\">粗缯大布裹生涯，腹有诗书气自华。</span>——<span style=\"font-size: 14px;\">苏轼《和董传留别》</span></p><p><span style=\"font-size: 14px;\">读书不觉已春深，一寸光阴一寸金。</span>——<span style=\"font-size: 14px;\">王贞白《白鹿洞二首·其一》</span></p><p><span style=\"font-size: 14px;\">三更灯火五更鸡，正是男儿读书时。</span>——<span style=\"font-size: 14px;\">颜真卿《劝学诗》</span></p><p><span style=\"font-size: 14px;\">黑发不知勤学早，白首方悔读书迟。</span>——<span style=\"font-size: 14px;\">颜真卿《劝学诗》</span></p><p><span style=\"font-size: 14px;\">古人学问无遗力，少壮工夫老始成。</span>——<span style=\"font-size: 14px;\">陆游《冬夜读书示子聿》</span></p><p><span style=\"font-size: 14px;\">江山代有才人出，各领风骚数百年。</span>——<span style=\"font-size: 14px;\">赵翼《论诗五首·其二》</span></p><p><span style=\"font-size: 14px;\">学而不思则罔，思而不学则殆。</span>——<span style=\"font-size: 14px;\">《论语·为政篇》</span></p>', 'http://localhost:9090/files/1714471205490-book.jpg', 2, '2024-04-30 18:02:03', '3', '读书丰富自我。', 5, '通过');
INSERT INTO `posts` VALUES (11, '测试', '<p>测试</p>', 'http://localhost:9090/files/1714830708440-ayst.gif', 5, '2024-05-04 21:52:03', '1', '测试', 0, '通过');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '角色标识',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '电话',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_username_uindex`(`username` ASC) USING BTREE COMMENT '用户名称唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, 'lxy', '1234', '李星云', 'http://localhost:9090/files/1712305417399-lxy.png', 'USER', '18170413231', 'lxy@xianbao.com');
INSERT INTO `user` VALUES (3, 'jrx', '123456', '姬如雪', 'http://localhost:9090/files/1713779764685-jrx.png', 'USER', '18170413234', 'jrx@xianbao.com');
INSERT INTO `user` VALUES (4, 'lf', '123', '路飞', 'http://localhost:9090/files/1714466199150-lf.jpg', 'USER', '18170413686', 'lf@xianbao.com');
INSERT INTO `user` VALUES (5, 'test', 'test', '测试人员', 'http://localhost:9090/files/1714830365509-1001.jpg', 'USER', '11111111111', 'test@xianbao.com');

SET FOREIGN_KEY_CHECKS = 1;
