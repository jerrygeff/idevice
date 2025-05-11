package info.zhihui.idevice.core.module.concrete.camera.enums;

import lombok.Getter;

@Getter
public enum CameraTypeEnum {

    GUN_CAMERA(0, "枪机"),
    HEMISPHERE_CAMERA(1, "半球机"),
    FASTBALL(2, "快球"),
    GIMBAL_GUN_CAMERA(3, "带云台枪机"),
    SPEED_DOME_CAMERA(4, "球机"),
    LOCAL_COLLECTION(5, "本地采集输入"),;


    private final Integer code;

    private final String info;

    CameraTypeEnum(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

}
