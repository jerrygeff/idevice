package info.zhihui.idevice.core.sdk.dahua.icc.dto.camera.v5;

import com.dahuatech.hutool.http.Method;
import com.dahuatech.icc.brm.exception.BusinessException;
import com.dahuatech.icc.common.ParamValidEnum;
import com.dahuatech.icc.oauth.http.AbstractIccRequest;
import com.dahuatech.icc.oauth.profile.IccProfile;
import org.apache.commons.lang3.StringUtils;

import static info.zhihui.idevice.core.sdk.dahua.icc.constants.IccCameraConstant.CAMERA_URL_REALTIME;

/**
 * 实时视频流请求
 *
 * @author jerryge
 */
public class RealTimeLinkRequest extends AbstractIccRequest<RealTimeLinkResponse> {
    private static final String ERROR_DATA_EMPTY = "data不能为空";
    private static final String ERROR_CHANNEL_ID_EMPTY = "channelId不能为空";
    private static final String ERROR_STREAM_TYPE_EMPTY = "streamType不能为空";
    private static final String ERROR_TYPE_EMPTY = "type不能为空";

    private RealTimeLinkRequestData data;

    private RealTimeLinkRequest(Builder builder) {
        super(String.format(IccProfile.URL_SCHEME + CAMERA_URL_REALTIME), Method.POST);

        this.data = RealTimeLinkRequestData.builder()
                .channelId(builder.channelId)
                .streamType(builder.streamType)
                .type(builder.type)
                .build();
        putBodyParameter("data", data);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Class<RealTimeLinkResponse> getResponseClass() {
        return RealTimeLinkResponse.class;
    }

    public static final class Builder {
        private String channelId;
        private String streamType;
        private String type;

        public Builder channelId(String channelId) {
            this.channelId = channelId;
            return this;
        }

        public Builder streamType(String streamType) {
            this.streamType = streamType;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public RealTimeLinkRequest build() {
            if (StringUtils.isBlank(channelId)) {
                throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), ERROR_CHANNEL_ID_EMPTY);
            }

            if (StringUtils.isBlank(streamType)) {
                throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), ERROR_STREAM_TYPE_EMPTY);
            }

            if (StringUtils.isBlank(type)) {
                throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), ERROR_TYPE_EMPTY);
            }

            return new RealTimeLinkRequest(this);
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