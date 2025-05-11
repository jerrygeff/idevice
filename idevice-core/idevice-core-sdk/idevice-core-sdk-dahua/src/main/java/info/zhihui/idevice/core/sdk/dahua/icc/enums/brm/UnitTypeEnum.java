package info.zhihui.idevice.core.sdk.dahua.icc.enums.brm;

import lombok.Getter;

/**
 * 设备单元类型枚举
 *
 * @author jerryge
 */
@Getter
public enum UnitTypeEnum {
    /** 视频通道 */
    VIDEO_CHANNEL(1),

    /** 输出通道 */
    OUTPUT_CHANNEL(2),

    /** 报警输入 */
    ALARM_INPUT(3),

    /** 报警输出 */
    ALARM_OUTPUT(4),

    /** 大屏输入单元 */
    SCREEN_INPUT(5),

    /** 大屏输出单元 */
    SCREEN_OUTPUT(6),

    /** 门禁通道 */
    ACCESS_CONTROL(7),

    /** 对讲通道 */
    INTERCOM_CHANNEL(8),

    /** 转码单元 */
    TRANSCODING(9),

    /** 外设通道 */
    PERIPHERAL_CHANNEL(10),

    /** POS单元 */
    POS_UNIT(11),

    /** 智能识别 */
    INTELLIGENT_RECOGNITION(12),

    /** MAC采集通道 */
    MAC_COLLECTION(13),

    /** 道闸通道 */
    BARRIER_GATE(14),

    /** 显示通道 */
    DISPLAY_CHANNEL(16),

    /** 车位探测通道 */
    PARKING_DETECTION(19),

    /** 外设输出通道 */
    PERIPHERAL_OUTPUT(20),

    /** IP对讲通道 */
    IP_INTERCOM(21),

    /** etc通道 */
    ETC_CHANNEL(22),

    /** 消防探测器 */
    FIRE_DETECTOR(23),

    /** 扫码通道 */
    SCAN_CODE(24),

    /** 子系统通道单元 */
    SUBSYSTEM_CHANNEL(25),

    /** 智能空开通道单元 */
    SMART_SWITCH(26),

    /** 空开报警输出通道 */
    SWITCH_ALARM_OUTPUT(27),

    /** 业务库通道 */
    BUSINESS_LIBRARY(28),

    /** 隔离门通道 */
    ISOLATION_DOOR(29),

    /** 业务库对讲通道 */
    LIBRARY_INTERCOM(30),

    /** 烟感通道 */
    SMOKE_DETECTOR(40),

    /** 温感通道 */
    TEMPERATURE_DETECTOR(41),

    /** 手报通道 */
    MANUAL_ALARM(42),

    /** 智能空调通道 */
    SMART_AC(43),

    /** 电表通道 */
    ELECTRIC_METER(44),

    /** 监测模块 */
    MONITORING_MODULE(45),

    /** 剩余互感器 */
    RESIDUAL_CT(46),

    /** 互感器 */
    CT(47),

    /** 温度探测器 */
    TEMP_DETECTOR(48),

    /** 湿度探测器 */
    HUMIDITY_DETECTOR(49),

    /** 小区虚拟单元 */
    COMMUNITY_VIRTUAL(51),

    /** NFC通道 */
    NFC_CHANNEL(52),

    /** 二维码巡更设备通道 */
    QR_PATROL(53),

    /** 辅助单元通道 */
    AUXILIARY_UNIT(55),

    /** 声音单元通道 */
    SOUND_UNIT(56),

    /** 面板单元通道 */
    PANEL_UNIT(60),

    /** 话筒单元通道 */
    MIC_UNIT(61),

    /** 监控对象虚拟设备通道 */
    MONITOR_VIRTUAL(62),

    /** 塑壳空开 */
    MCCB(63),

    /** 空气微站 */
    AIR_STATION(64),

    /** 水质微站 */
    WATER_STATION(65),

    /** 机器人巡检主机 */
    ROBOT_PATROL(66),

    /** 水文数据 */
    HYDROLOGY_DATA(67),

    /** 特种通道 */
    SPECIAL_CHANNEL(68),

    /** 智慧预警通道 */
    SMART_WARNING(2022),

    /** 智慧用电系统通道 */
    SMART_ELECTRICITY(2023),

    /** 智慧用水系统通道 */
    SMART_WATER(2024),

    /** 城市消防远程监控系统通道 */
    CITY_FIRE_MONITOR(2025),

    /** 自动注册设备通道 */
    AUTO_REGISTER(2026),

    /** 智能充电桩通道 */
    SMART_CHARGING(2027);

    private final Integer code;

    UnitTypeEnum(Integer code) {
        this.code = code;
    }
}