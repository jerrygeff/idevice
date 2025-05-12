package info.zhihui.idevice.core.module.concrete.camera.enums;

import lombok.Getter;

@Getter
public enum CameraDirectionEnum {

    UP("1", "上"),
    DOWN("2", "下"),
    LEFT("3", "左"),
    RIGHT("4", "右"),
    LEFT_UP("5", "左上"),
    LEFT_DOWN("6", "左下"),
    RIGHT_UP("7", "右上"),
    RIGHT_DOWN("8", "右下"),

    ;

    private final String value;

    private final String info;

    CameraDirectionEnum(String value, String info) {
        this.value = value;
        this.info = info;
    }
}
