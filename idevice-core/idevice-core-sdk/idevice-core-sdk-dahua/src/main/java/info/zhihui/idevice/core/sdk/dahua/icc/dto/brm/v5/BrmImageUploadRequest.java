package info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5;

import com.dahuatech.hutool.http.Method;
import com.dahuatech.icc.brm.exception.BusinessException;
import com.dahuatech.icc.common.ParamValidEnum;
import com.dahuatech.icc.oauth.http.AbstractIccRequest;
import com.dahuatech.icc.oauth.profile.IccProfile;
import info.zhihui.idevice.core.sdk.dahua.icc.constants.IccBrmConstant;

/**
 * 批量删除人员权限请求
 *
 * @author jerryge
 */
public class BrmImageUploadRequest extends AbstractIccRequest<BrmImageUploadResponse> {
    private static final String VERSION = "1.0.0";
    private static final String BASE64_PREFIX = "data:%s;base64,";
    private static final Long MAX_FILE_SIZE = 4L * 1024 * 1024;
    private final String imageBase64;

    private BrmImageUploadRequest(Builder builder) {
        super(String.format(IccProfile.URL_SCHEME + IccBrmConstant.BRM_THIRD_UPLOAD_IMG_POST, VERSION), Method.POST);

        this.imageBase64 = builder.imageBase64;
        putBodyParameter("imageBase64", this.imageBase64);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Class<BrmImageUploadResponse> getResponseClass() {
        return BrmImageUploadResponse.class;
    }

    @Override
    public void valid() {
        super.valid();

        if (imageBase64 == null || imageBase64.isEmpty()) {
            throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), "imageBase64不能为空");
        }
    }

    public static final class Builder {
        private String contentType;
        private Long fileSize;
        private String originBase64;
        private String imageBase64;

        public Builder contentType(String contentType) {
            this.contentType = contentType;
            return this;
        }

        public Builder fileSize(Long fileSize) {
            this.fileSize = fileSize;
            return this;
        }

        public Builder originBase64(String originBase64) {
            this.originBase64 = originBase64;
            return this;
        }

        public BrmImageUploadRequest build() {
            if (this.fileSize > MAX_FILE_SIZE) {
                throw new BusinessException("-1", "图片大小不能超过4M");
            }

            this.imageBase64 = String.format(BASE64_PREFIX, this.contentType) + this.originBase64;

            return new BrmImageUploadRequest(this);
        }
    }
}
