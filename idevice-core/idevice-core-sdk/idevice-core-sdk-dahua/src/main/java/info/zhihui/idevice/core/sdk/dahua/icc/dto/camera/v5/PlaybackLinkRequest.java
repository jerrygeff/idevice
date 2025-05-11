package info.zhihui.idevice.core.sdk.dahua.icc.dto.camera.v5;

import com.dahuatech.hutool.http.Method;
import com.dahuatech.icc.brm.exception.BusinessException;
import com.dahuatech.icc.common.ParamValidEnum;
import com.dahuatech.icc.oauth.http.AbstractIccRequest;
import com.dahuatech.icc.oauth.profile.IccProfile;
import org.apache.commons.lang3.StringUtils;

import static info.zhihui.idevice.core.sdk.dahua.icc.constants.IccCameraConstant.CAMERA_URL_PLAYBACK;

/**
 * 回放视频流请求
 *
 * @author jerryge
 */
public class PlaybackLinkRequest extends AbstractIccRequest<PlaybackLinkResponse> {
    private static final String ERROR_DATA_EMPTY = "data不能为空";
    private static final String ERROR_CHANNEL_ID_EMPTY = "channelId不能为空";
    private static final String ERROR_STREAM_TYPE_EMPTY = "streamType不能为空";
    private static final String ERROR_TYPE_EMPTY = "type不能为空";
    private static final String ERROR_RECORD_TYPE_EMPTY = "recordType不能为空";
    private static final String ERROR_BEGIN_TIME_EMPTY = "beginTime不能为空";
    private static final String ERROR_END_TIME_EMPTY = "endTime不能为空";
    private static final String ERROR_RECORD_SOURCE_EMPTY = "recordSource不能为空";

    private PlaybackLinkRequestData data;

    private PlaybackLinkRequest(Builder builder) {
        super(String.format(IccProfile.URL_SCHEME + CAMERA_URL_PLAYBACK), Method.POST);
        this.data = PlaybackLinkRequestData.builder()
                .channelId(builder.channelId)
                .streamType(builder.streamType)
                .type(builder.type)
                .recordType(builder.recordType)
                .beginTime(builder.beginTime)
                .endTime(builder.endTime)
                .recordSource(builder.recordSource)
                .build();
        putBodyParameter("data", data);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Class<PlaybackLinkResponse> getResponseClass() {
        return PlaybackLinkResponse.class;
    }

    public static final class Builder {
        private String channelId;
        private String streamType;
        private String type;
        private String recordType;
        private String beginTime;
        private String endTime;
        private String recordSource;

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

        public Builder recordType(String recordType) {
            this.recordType = recordType;
            return this;
        }

        public Builder beginTime(String beginTime) {
            this.beginTime = beginTime;
            return this;
        }

        public Builder endTime(String endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder recordSource(String recordSource) {
            this.recordSource = recordSource;
            return this;
        }

        public PlaybackLinkRequest build() {
            if (StringUtils.isBlank(channelId)) {
                throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), ERROR_CHANNEL_ID_EMPTY);
            }

            if (StringUtils.isBlank(streamType)) {
                throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), ERROR_STREAM_TYPE_EMPTY);
            }

            if (StringUtils.isBlank(type)) {
                throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), ERROR_TYPE_EMPTY);
            }

            if (StringUtils.isBlank(recordType)) {
                throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), ERROR_RECORD_TYPE_EMPTY);
            }

            if (StringUtils.isBlank(beginTime)) {
                throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), ERROR_BEGIN_TIME_EMPTY);
            }

            if (StringUtils.isBlank(endTime)) {
                throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), ERROR_END_TIME_EMPTY);
            }

            if (StringUtils.isBlank(recordSource)) {
                throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), ERROR_RECORD_SOURCE_EMPTY);
            }

            return new PlaybackLinkRequest(this);
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