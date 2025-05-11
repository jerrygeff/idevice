package info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation;

/**
 * @author jerryge
 * 人员属性类型
 */
public enum PersonPropertyEnum {
    COMMON_PERSON(1), // 普通人，默认类型
    VISITOR(2), // 来宾，来宾人员比对通过或者时，会上报来宾事件，可正常开门
    BLACKLIST(3), // 黑名单，黑名单人员比对通过时，会上报黑名单事件，且无法开门
    SUPER_USER(4); // 超级用户，为需要超级权限的用户而设置。不受限于门常闭、反潜回、刷卡+密码认证需要密码确认的规则，刷卡直接开门，但要遵循多门互锁及多重认证规则。

    private final int value;

    PersonPropertyEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}