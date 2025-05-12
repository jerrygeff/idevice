package info.zhihui.idevice.core.sdk.dahua.icc.dto.camera.v5;

import com.dahuatech.hutool.http.Method;
import com.dahuatech.icc.brm.exception.BusinessException;
import com.dahuatech.icc.common.ParamValidEnum;
import com.dahuatech.icc.oauth.http.AbstractIccRequest;
import com.dahuatech.icc.oauth.profile.IccProfile;
import info.zhihui.idevice.core.sdk.dahua.icc.enums.camera.SpeedEnum;
import org.apache.commons.lang3.StringUtils;

import static info.zhihui.idevice.core.sdk.dahua.icc.constants.IccCameraConstant.CAMERA_URL_CONTROL;

/**
 * 摄像头控制请求
 *
 * @author jerryge
 */
public class CameraControlRequest extends AbstractIccRequest<CameraControlResponse> {
    private static final String ERROR_DATA_EMPTY = "data不能为空";
    private static final String ERROR_CHANNEL_ID_EMPTY = "channelId不能为空";
    private static final String ERROR_COMMAND_EMPTY = "command不能为空";
    private static final String ERROR_DIRECT_EMPTY = "direct不能为空";
    private static final String ERROR_STEP_X_EMPTY = "stepX不能为空";
    private static final String ERROR_STEP_Y_EMPTY = "stepY不能为空";
    private static final String ERROR_EXTEND_EMPTY = "extend不能为空";

    private CameraControlRequestData data;

    private CameraControlRequest(Builder builder) {
        super(String.format(IccProfile.URL_SCHEME + CAMERA_URL_CONTROL), Method.POST);
        this.data = CameraControlRequestData.builder()
                .channelId(builder.channelId)
                .direct(builder.direct)
                .stepX(builder.stepX)
                .stepY(builder.stepY)
                .command(builder.command)
                .extend(builder.extend)
                .build();
        putBodyParameter("data", data);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Class<CameraControlResponse> getResponseClass() {
        return CameraControlResponse.class;
    }

    public CameraControlRequestData getData() {
        return data;
    }

    public static final class Builder {
        private String channelId;
        private String direct;
        private String stepX;
        private String stepY;
        private String command;
        private String extend;

        public Builder channelId(String channelId) {
            this.channelId = channelId;
            return this;
        }

        public Builder direct(String direct) {
            this.direct = direct;
            return this;
        }

        public Builder stepX(String stepX) {
            this.stepX = stepX;
            return this;
        }

        public Builder stepY(String stepY) {
            this.stepY = stepY;
            return this;
        }

        public Builder command(String command) {
            this.command = command;
            return this;
        }

        public Builder extend(String extend) {
            this.extend = extend;
            return this;
        }

        public CameraControlRequest build() {
            if (StringUtils.isBlank(channelId)) {
                throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), ERROR_CHANNEL_ID_EMPTY);
            }

            if (StringUtils.isBlank(command)) {
                throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), ERROR_COMMAND_EMPTY);
            }

            if (StringUtils.isBlank(direct)) {
                throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), ERROR_DIRECT_EMPTY);
            }

            if (StringUtils.isBlank(stepX)) {
                throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), ERROR_STEP_X_EMPTY);
            }

            if (StringUtils.isBlank(stepY)) {
                throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), ERROR_STEP_Y_EMPTY);
            }

            if (extend == null) {
                throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), ERROR_EXTEND_EMPTY);
            }

            // stepX、stepY 校验
            int stepXInt, stepYInt;
            int minSpeed = Integer.parseInt(SpeedEnum.SLOWEST.getValue());
            int maxSpeed = Integer.parseInt(SpeedEnum.FASTEST.getValue());
            try {
                stepXInt = Integer.parseInt(stepX);
            } catch (NumberFormatException e) {
                throw new BusinessException(ParamValidEnum.PARAM_SCOP_ERROR.getCode(), "stepX必须为整数");
            }
            try {
                stepYInt = Integer.parseInt(stepY);
            } catch (NumberFormatException e) {
                throw new BusinessException(ParamValidEnum.PARAM_SCOP_ERROR.getCode(), "stepY必须为整数");
            }
            if (stepXInt < minSpeed || stepXInt > maxSpeed) {
                throw new BusinessException(ParamValidEnum.PARAM_SCOP_ERROR.getCode(), String.format("stepX取值范围为%d-%d", minSpeed, maxSpeed));
            }
            if (stepYInt < minSpeed || stepYInt > maxSpeed) {
                throw new BusinessException(ParamValidEnum.PARAM_SCOP_ERROR.getCode(), String.format("stepY取值范围为%d-%d", minSpeed, maxSpeed));
            }

            return new CameraControlRequest(this);
        }
    }

    @Override
    public void valid() {
        super.valid();

        if (data == null) {
            throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), ERROR_DATA_EMPTY);
        }
    }
}