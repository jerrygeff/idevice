package info.zhihui.idevice.core.sdk.dahua.icc.enums.brm;

import lombok.Getter;

/**
 * @author jerryge
 */
@Getter
public enum CardType {

    NORMAL_CARD("0", "普通卡"),
    VIP_CARD("1", "VIP卡"),
    GUEST_CARD("2", "来宾卡"),
    PATROL_CARD("3", "巡逻卡"),
    DURESS_CARD("5", "胁迫卡"),
    INSPECTION_CARD("6", "巡检卡"),
    BLACKLIST_CARD("7", "黑名单卡"),
    MANAGER_CARD("11", "管理员卡"),
    SUPPORT_CARD("13", "辅助卡"),
    UNDEFINED_CARD("-1", "未知卡类型");

    // 卡类型对应的编码
    private final String code;
    // 卡类型名称
    private final String name;

    CardType(String code, String name) {
        this.code = code;
        this.name = name;
    }

}