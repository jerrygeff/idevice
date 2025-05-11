package info.zhihui.idevice.core.sdk.dahua.icc.dto.access.v5;

import com.dahuatech.hutool.http.Method;
import com.dahuatech.icc.brm.exception.BusinessException;
import com.dahuatech.icc.common.ParamValidEnum;
import com.dahuatech.icc.oauth.http.AbstractIccRequest;
import com.dahuatech.icc.oauth.profile.IccProfile;
import info.zhihui.idevice.core.sdk.dahua.icc.constants.IccAccessConstant;

import java.util.List;

/**
 * 批量删除人员权限请求
 *
 * @author jerryge
 */
public class AuthPersonBatchDeleteRequest extends AbstractIccRequest<AuthPersonBatchDeleteResponse> {
    private static final String VERSION = "1.0.0";
    private List<String> personCodes;

    private AuthPersonBatchDeleteRequest(Builder builder) {
        super(String.format(IccProfile.URL_SCHEME + IccAccessConstant.ASSESS_CONTROL_URL_AUTH_PERSON_BATCH_DELETE_POST, VERSION), Method.POST);

        setPersonCodes(builder.personCodes);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Class<AuthPersonBatchDeleteResponse> getResponseClass() {
        return AuthPersonBatchDeleteResponse.class;
    }

    public List<String> getPersonCodes() {
        return personCodes;
    }

    public void setPersonCodes(List<String> personCodes) {
        this.personCodes = personCodes;
        putBodyParameter("personCodes", personCodes);
    }

    @Override
    public void valid() {
        super.valid();

        if (personCodes == null || personCodes.isEmpty()) {
            throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), "personCodes不能为空");
        }
    }

    public static final class Builder {
        private List<String> personCodes;

        public Builder personCodes(List<String> personCodes) {
            this.personCodes = personCodes;
            return this;
        }

        public AuthPersonBatchDeleteRequest build() {
            return new AuthPersonBatchDeleteRequest(this);
        }
    }
}
