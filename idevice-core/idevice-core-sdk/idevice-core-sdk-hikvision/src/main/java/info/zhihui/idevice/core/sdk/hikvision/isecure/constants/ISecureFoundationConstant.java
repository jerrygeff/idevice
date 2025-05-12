package info.zhihui.idevice.core.sdk.hikvision.isecure.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jerryge
 */
public class ISecureFoundationConstant {

    // 默认的组织码
    public static final String DEFAULT_ORG_INDEX_CODE = "root000000";

    /**
     * 具备云台能力的特征
     */
    public static final String PTZ = "ptz";

    /**
     * 设备能力映射表
     */
    public static final Map<String, String> CAPABILITIES = new HashMap<>() {{
        put("vss", "视频能力");
        put("event_veh", "车辆抓拍能力");
        put("eagle_eye_2DP", "碗型鹰眼能力");
        put("eagle_eye_2DP_Z", "球型鹰眼能力");
        put("eagle_eye_panorama", "环型鹰眼能力");
        put("gis", "可视域能力");
        put("event_heat", "热成像能力");
        put("event_pdc", "客流量统计能力");
        put("ptz", "云台能力");
        put("event_person_density", "人流量密度统计能力");
        put("event_acs_compare", "人证设备人证比对能力");
        put("event_face", "人脸采集能力");
        put("face_match", "人脸建模能力");
        put("event_face_match", "人脸比对能力");
        put("motiontrack", "自动追踪能力");
        put("manualtrack", "枪球联动能力");
        put("event_body", "人体智能分析能力");
        put("event_rule", "智能应用事件能力");
        put("event_ias", "入侵报警能力（报警主机）");
        put("event_emer", "紧急报警能力（报警柱/盒）");
        put("event_gps", "GPS事件能力");
        put("io", "IO能力");
        put("videolabel", "虚拟标签能力");
        put("event_rfid", "RFID能力");
        put("event_wifi", "WIFI能力");
        put("support_card", "卡片");
        put("support_finger", "指纹");
        put("support_face", "人脸");
        put("support_doorstatus", "门常开常关");
        put("support_cardtype", "卡类型");
        put("support_multicard", "多重认证");
        put("support_leadercard", "首卡开门");
        put("support_antisneak", "反潜回");
        put("support_doorlock", "多门互锁");
        put("support_readerverify", "认证方式");
        put("support_eventlink", "一体机联动");
        put("support_pcnvr", "云存储");
        put("support_485", "支持485接线");
        put("support_m1cardEncrypt", "支持扇区加密");
        put("support_snapcapcfg", "支持抓图参数配置");
        put("support_cardmodify", "支持异动");
        put("support_ezviz", "支持萤石");
        put("support_person", "支持以人为中心");
    }};

    /**
     * 门禁反控
     */
    public static final String PERSON_AUTH = "/api/acps/%s/authDownload/special/person/diy";

    /**
     * 新增人员
     */
    public static final String PERSON_ADD = "/api/resource/%s/person/single/add";

    /**
     * 批量删除人员
     */
    public static final String PERSON_BATCH_DELETE = "/api/resource/%s/person/batch/delete";

    /**
     * 修改人员照片
     */
    public static final String PERSON_FACE_UPDATE = "/api/resource/%s/face/single/update";

    /**
     * 新增人员照片
     */
    public static final String PERSON_FACE_ADD = "/api/resource/%s/face/single/add";

    /**
     * 根据人员唯一字段获取人员详情
     */
    public static final String PERSON_UNIQUE_KEY_SEARCH = "/api/resource/%s/person/condition/personInfo";

    /**
     * 添加权限配置
     */
    public static final String CONFIG_ADD = "/api/acps/%s/auth_config/add";

    /**
     * 删除权限配置
     */
    public static final String CONFIG_DELETE = "/api/acps/%s/auth_config/delete";

    /**
     * 查询权限配置单进度
     */
    public static final String CONFIG_RATE_SEARCH = "/api/acps/%s/auth_config/rate/search";

    /**
     * 创建下载任务
     * 根据出入权限配置下载
     */
    public static final String CONFIG_DOWNLOAD_TASK_CREATE = "/api/acps/%s/download/configuration/task/add";

    /**
     * 添加待下载的设备通道
     * 根据出入权限配置下载
     */
    public static final String CONFIG_DOWNLOAD_TASK_ADD_DEVICE = "/api/acps/%s/download/configuration/data/add";

    /**
     * 启动下载任务
     */
    public static final String CONFIG_DOWNLOAD_TASK_START = "/api/acps/%s/authDownload/task/start";

}
