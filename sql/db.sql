/**
 * 设备相关
 */
CREATE TABLE `device_third_party_relation`
(
    `id`                       int unsigned NOT NULL AUTO_INCREMENT,
    `local_business_key`       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '自身平台业务key',
    `local_module_name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '自身平台模块名称',
    `area_id`                  int unsigned NOT NULL COMMENT '区域id',
    `third_party_module_name`  varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '第三方平台对接的模块名称',
    `third_party_business_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '第三方平台业务key',
    `is_deleted`               bit(1)                                                        NOT NULL DEFAULT b'0' COMMENT '是否被删除：0未删除；1已删除',
    PRIMARY KEY (`id`) USING BTREE,
    KEY                        `index_local_business_key` (`local_business_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE `sys_config`
(
    `id`                 int unsigned NOT NULL AUTO_INCREMENT,
    `config_module_name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置所属模块name',
    `config_key`         varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置key',
    `config_name`        varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '配置name',
    `config_value`       text COLLATE utf8mb4_unicode_ci        NOT NULL COMMENT '配置value',
    `is_system`          bit(1)                                          DEFAULT b'1' COMMENT '是否内置，内置配置项可在系统配置列表修改',
    `is_deleted`         bit(1)                                 NOT NULL DEFAULT b'0' COMMENT '是否被删除：0未删除；1已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_config_key` (`config_key`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统参数配置';
