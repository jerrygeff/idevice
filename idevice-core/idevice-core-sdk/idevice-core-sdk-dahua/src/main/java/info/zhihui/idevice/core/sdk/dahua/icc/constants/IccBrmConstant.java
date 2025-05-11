package info.zhihui.idevice.core.sdk.dahua.icc.constants;

import java.util.List;

/**
 * 人员相关配置
 *
 * @author jerryge
 */
public class IccBrmConstant {

    /**
     * 默认子系统编码
     */
    public static final String DEFAULT_SERVICE = "evo-thirdParty";

    /**
     * 默认证件地址
     */
    public static final String DEFAULT_PAPER_ADDRESS = "***";

    /**
     * 人员详情
     */
    public static final String BRM_URL_PERSON_DETAIL_REST_GET =
            "/evo-apigw/evo-brm/%s/person/subsystem/%d";

    /**
     * 查询卡片详情
     */
    public static final String BRM_URL_CARD_DETAIL_REST_GET =
            "/evo-apigw/evo-brm/%s/card/%s";

    /**
     * 人员更新
     */
    public static final String BRM_URL_PERSON_UPDATE_PUT =
            "/evo-apigw/evo-brm/%s/person/subsystem/update";

    /**
     * 人员新增
     */
    public static final String BRM_URL_PERSON_ADD =
            "/evo-apigw/evo-brm/%s/person/subsystem/add";

    /**
     * 人员图片上传
     */
    public static final String BRM_THIRD_UPLOAD_IMG_POST =
            "/evo-apigw/evo-brm/%s/person/subsystem/third/upload/img";

    /**
     * 通道分页查询
     */
    public static final String BRM_URL_CHANNEL_PAGE_POST =
            "/evo-apigw/evo-brm/%s/device/channel/subsystem/page";

    public static final String BRM_URL_DEVICE_TREE_LIST_POST =
            "/evo-apigw/evo-brm/%s/tree";

    /**
     * 默认部门id
     */
    public static final Long DEFAULT_DEPARTMENT_ID = 1L;

    /**
     * 默认分页数量
     */
    public static final Integer DEFAULT_PAGE_SIZE = 500;

    /**
     * 默认卡授权年数
     */
    public static final Integer DEFAULT_CARD_VALID_YEAR = 5;

    /**
     * 默认时间计划
     */
    public static final long DEFAULT_TIME_QUANTUM_ID = 1L;

    /**
     * 卡片信息已存在
     */
    public static final String ERROR_CODE_CARD_EXIST = "28140000";

    /**
     * 卡操作失败
     */
    public static final String ERROR_CODE_CARD_OPERATE = "28140014";

    /**
     * 卡片信息不存在
     */
    public static final String ERROR_CODE_CARD_NOT_EXIST = "28140001";

    public static final String DEFAULT_DEVICE_TREE_QUERY_ID = "001";

    /**
     * 视频通道(设备作为节点展示)：type=001;;1，较为通用写法</br>
     * 视频通道(设备不作为节点展示)：type=001;00;1，较为通用写法</br>
     * 编码器下视频通道(设备不作为节点展示)：type=001;00_1;1</br>
     * 编码器与门禁设备下视频通道(设备作为节点展示)：type=001;1,8;1</br>
     * 编码器与门禁设备下视频通道(设备不作为节点展示)：type=001;00_1,00_8;1</br>
     * 门禁通道(设备作为节点展示)：type=001;;7</br>
     * 门禁通道(设备不作为节点展示)：type=001;00;7</br>
     * 道闸通道(设备作为节点展示)：type=001;;14</br>
     * 道闸通道(设备不作为节点展示)：type=001;00;14</br>
     */
    public static final String
            DEFAULT_CAMERA_TREE_QUERY_TYPE = DEFAULT_DEVICE_TREE_QUERY_ID + ";00;1";
    public static final String DEFAULT_ACCESS_TREE_QUERY_TYPE = DEFAULT_DEVICE_TREE_QUERY_ID + ";00;7";

    public static final Integer CHECK_STAT_CHECKED = 1;

    /**
     * 能力集说明
     */
    public static final List<String> CAPABILITIES = List.of(
            "智能报警",
            "鱼眼",
            "电动聚焦",
            "红外测温",
            "热度图统计",
            "过线统计",
            "区域人数统计",
            "人脸检测",
            "人员识别",
            "目标抓拍",
            "主从跟踪",
            "出入口抓拍",
            "AR",
            "多区域人数统计",
            "雷球联动",
            "行为分析",
            "云台控制",
            "-",
            "扬声器",
            "智能算法升级",
            "MAC采集",
            "自定义定时抓图",
            "人脸开门",
            "工装检测",
            "人脸质量检测",
            "人脸相似度比对",
            "客流定时订阅",
            "人脸分析",
            "AR后端打标",
            "AR广角180°",
            "AR广角360°",
            "-"
    );
}
