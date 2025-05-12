package info.zhihui.idevice.core.module.common.enums;

import lombok.Getter;

/**
 * 系统模块的枚举
 *
 * @author jerryge
 */

@Getter
public enum ModuleEnum {

    ENERGY("energy"),
    ACCESS("access"),
    CAR("car"),
    CAMERA("camera"),
    PERSON("person"),
    VISITOR("visitorMachine"),
    ;

    private final String moduleName;

    ModuleEnum(String moduleName) {
        this.moduleName = moduleName;
    }

}
