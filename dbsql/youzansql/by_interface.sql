-- ----------------------------
-- Table structure for code_record
-- ----------------------------
CREATE TABLE IF NOT EXISTS `code_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(50) DEFAULT NULL,
  `createtime` varchar(50) DEFAULT NULL,
  `companyCode` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;

-- ----------------------------
-- Table structure for delivery_template
-- ----------------------------
CREATE TABLE IF NOT EXISTS `delivery_template` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `delivery_template_fee` varchar(20) DEFAULT NULL,
  `delivery_template_name` varchar(20) DEFAULT NULL,
  `delivery_template_valuation_type` int(11) DEFAULT NULL,
  `delivery_template_id` bigint(20) DEFAULT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  `createTime` varchar(20) DEFAULT NULL,
  `modifyTime` varchar(20) DEFAULT NULL,
  `companyCode` varchar(20) DEFAULT NULL,
  `authority_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;

-- ----------------------------
-- Table structure for goods_items
-- ----------------------------
CREATE TABLE IF NOT EXISTS `goods_items` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `item_id` bigint(20) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `created_time` varchar(20) DEFAULT NULL,
  `quantity` bigint(20) DEFAULT NULL,
  `item_no` varchar(50) DEFAULT NULL,
  `price` bigint(20) DEFAULT NULL,
  `update_time` varchar(20) DEFAULT NULL,
  `classid` varchar(20) DEFAULT NULL,
  `post_type` int(11) DEFAULT NULL,
  `image` varchar(200) DEFAULT NULL,
  `origin` varchar(20) DEFAULT NULL,
  `sub_title` varchar(200) DEFAULT NULL,
  `alias` varchar(200) DEFAULT NULL,
  `page_url` varchar(50) DEFAULT NULL,
  `post_fee` bigint(20) DEFAULT NULL,
  `share_detail` bigint(20) DEFAULT NULL,
  `item_type` int(11) DEFAULT NULL,
  `share_title` varchar(200) DEFAULT NULL,
  `share_icon` varchar(200) DEFAULT NULL,
  `detail_url` varchar(200) DEFAULT NULL,
  `num` bigint(20) DEFAULT NULL,
  `createTime` varchar(20) DEFAULT NULL,
  `modifyTime` varchar(20) DEFAULT NULL,
  `companyCode` varchar(20) DEFAULT NULL,
  `authority_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_itemId` (`item_id`)
) ;

-- ----------------------------
-- Table structure for goods_quantity
-- ----------------------------
CREATE TABLE IF NOT EXISTS `goods_quantity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goodsid` varchar(20) DEFAULT NULL,
  `bycode` varchar(20) DEFAULT NULL,
  `quantity` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_goodsid_bycode` (`goodsid`,`bycode`) USING BTREE
) ;

-- ----------------------------
-- Table structure for item_imgs
-- ----------------------------
CREATE TABLE IF NOT EXISTS `item_imgs` (
  `tid` bigint(20) NOT NULL AUTO_INCREMENT,
  `item_id` bigint(20) DEFAULT NULL,
  `thumbnail` varchar(200) DEFAULT NULL,
  `combine` varchar(200) DEFAULT NULL,
  `id` bigint(20) DEFAULT NULL,
  `created` varchar(20) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `medium` varchar(200) DEFAULT NULL,
  `createTime` varchar(20) DEFAULT NULL,
  `modifyTime` varchar(20) DEFAULT NULL,
  `companyCode` varchar(20) DEFAULT NULL,
  `authority_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`tid`)
);

-- ----------------------------
-- Table structure for test
-- ----------------------------
CREATE TABLE IF NOT EXISTS `test` (
  `a` char(1) DEFAULT NULL
) ;

-- ----------------------------
-- Table structure for token_info
-- ----------------------------
CREATE TABLE IF NOT EXISTS `token_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `access_token` varchar(50) DEFAULT NULL,
  `expires` bigint(20) DEFAULT NULL,
  `authority_id` varchar(20) DEFAULT NULL,
  `authority_name` varchar(50) DEFAULT NULL,
  `scope` varchar(500) DEFAULT NULL,
  `refresh_token` varchar(50) DEFAULT NULL,
  `byCode` varchar(10) DEFAULT NULL,
  `companyCode` varchar(10) DEFAULT NULL,
  `corresponding` varchar(5) DEFAULT NULL,
  `createTime` varchar(20) DEFAULT NULL,
  `modifyTime` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_authority_id` (`authority_id`),
  KEY `idx_bycode` (`byCode`)
) ;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
CREATE TABLE IF NOT EXISTS `user_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `client_id` varchar(20) DEFAULT NULL,
  `client_secret` varchar(50) DEFAULT NULL,
  `companyCode` varchar(20) DEFAULT NULL,
  `createTime` varchar(20) DEFAULT NULL,
  `modifyTime` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

-- ----------------------------
-- Table structure for youzan_message
-- ----------------------------
CREATE TABLE IF NOT EXISTS `youzan_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) DEFAULT NULL COMMENT '消息类型',
  `orderNo` varchar(50) DEFAULT NULL COMMENT '订单号',
  `skuVersionText` varchar(50) DEFAULT NULL COMMENT '套餐名称',
  `skuIntervalText` int(11) DEFAULT NULL COMMENT '套餐有效期',
  `buyerPhone` varchar(50) DEFAULT NULL COMMENT '订购人手机号',
  `payTime` varchar(50) DEFAULT NULL,
  `kdtId` varchar(50) DEFAULT NULL COMMENT '店铺ID',
  `shopDisPlayNo` varchar(50) DEFAULT NULL COMMENT '店铺编号',
  `price` int(11) DEFAULT NULL,
  `appId` varchar(50) DEFAULT NULL,
  `buyerId` varchar(50) DEFAULT NULL COMMENT '购买者身份标识',
  `status` varchar(50) DEFAULT NULL,
  `env` varchar(50) DEFAULT NULL,
  `expireTime` varchar(50) DEFAULT NULL,
  `effectTime` varchar(50) DEFAULT NULL,
  `companyCode` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `goods_relationship` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sku_unique_code` varchar(50) DEFAULT NULL,
  `goods_id` varchar(50) DEFAULT NULL,
  `corresponding` varchar(5) DEFAULT NULL,
  `sku_id` bigint(20) DEFAULT NULL,
  `item_id` bigint(20) DEFAULT NULL,
  `authority_id` varchar(20) DEFAULT NULL,
  `createTime` varchar(20) DEFAULT NULL,
  `modifyTime` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_sku_unique_code` (`sku_unique_code`)
);


