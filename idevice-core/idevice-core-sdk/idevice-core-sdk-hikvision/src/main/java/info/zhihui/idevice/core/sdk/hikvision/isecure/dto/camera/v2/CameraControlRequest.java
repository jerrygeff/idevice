package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.camera.v2;

import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.core.sdk.hikvision.isecure.constants.ISecureCameraConstant;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecureJsonRequest;
import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.camera.ActionEnum;
import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.camera.CommandEnum;
import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.camera.SpeedEnum;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 摄像头控制请求
 */
@Getter
@Builder(builderClassName = "Builder")
@EqualsAndHashCode(callSuper = true)
public class CameraControlRequest extends ISecureJsonRequest<CameraControlResponse> {
    private static final String VERSION = "v1";
    private static final String API_URL = String.format(ISecureCameraConstant.CAMERA_URL_CONTROL, VERSION);
    private static final String CAMERA_INDEX_CODE_ERROR_MSG = "cameraIndexCode不能为空";
    private static final String ACTION_ERROR_MSG = "action不能为空";
    private static final String COMMAND_ERROR_MSG = "command不能为空";
    private static final String ACTION_INVALID_ERROR_MSG = "action必须在ActionEnum范围内";
    private static final String COMMAND_INVALID_ERROR_MSG = "command必须在CommandEnum范围内";
    private static final String SPEED_INVALID_ERROR_MSG = "speed必须在%d到%d之间";

    /** 摄像头唯一标识 */
    private final String cameraIndexCode;
    /** 动作（0-开始;1-停止） */
    private final Integer action;
    /** 控制命令 @see CommandEnum */
    private final String command;
    /** 云台速度 */
    private final Integer speed;
    /** 预置点索引 */
    private final Integer presetIndex;

    private CameraControlRequest(String cameraIndexCode, Integer action, String command, Integer speed, Integer presetIndex) {
        super(API_URL);
        this.cameraIndexCode = cameraIndexCode;
        this.action = action;
        this.command = command;
        this.speed = speed;
        this.presetIndex = presetIndex;
    }

    @Override
    public void validate() {
        super.validate();
        if (StringUtils.isBlank(cameraIndexCode)) {
            throw new ParamException(CAMERA_INDEX_CODE_ERROR_MSG);
        }
        if (action == null) {
            throw new ParamException(ACTION_ERROR_MSG);
        }
        if (StringUtils.isBlank(command)) {
            throw new ParamException(COMMAND_ERROR_MSG);
        }

        // action验证
        boolean validAction = false;
        for (ActionEnum actionEnum : ActionEnum.values()) {
            if (Objects.equals(actionEnum.getCode(), action)) {
                validAction = true;
                break;
            }
        }
        if (!validAction) {
            throw new ParamException(ACTION_INVALID_ERROR_MSG);
        }

        // command验证
        boolean validCommand = false;
        for (CommandEnum commandEnum : CommandEnum.values()) {
            if (commandEnum.getValue().equals(command)) {
                validCommand = true;
                break;
            }
        }
        if (!validCommand) {
            throw new ParamException(COMMAND_INVALID_ERROR_MSG);
        }

        // speed验证
        if (speed != null) {
            if (speed < SpeedEnum.SLOWEST.getCode() || speed > SpeedEnum.FASTEST.getCode()) {
                throw new ParamException(String.format(SPEED_INVALID_ERROR_MSG,
                    SpeedEnum.SLOWEST.getCode(), SpeedEnum.FASTEST.getCode()));
            }
        }
    }

    @Override
    public Class<CameraControlResponse> getResponseClass() {
        return CameraControlResponse.class;
    }
}