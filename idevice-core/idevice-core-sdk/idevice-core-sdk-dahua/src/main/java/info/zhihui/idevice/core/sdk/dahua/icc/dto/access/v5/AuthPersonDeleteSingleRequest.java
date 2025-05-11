package info.zhihui.idevice.core.sdk.dahua.icc.dto.access.v5;

import com.dahuatech.hutool.http.Method;
import com.dahuatech.icc.brm.exception.BusinessException;
import com.dahuatech.icc.common.ParamValidEnum;
import com.dahuatech.icc.oauth.http.AbstractIccRequest;
import com.dahuatech.icc.oauth.profile.IccProfile;
import info.zhihui.idevice.core.sdk.dahua.icc.constants.IccAccessConstant;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 删除单个人员权限请求
 *
 * @author jerryge
 */
public class AuthPersonDeleteSingleRequest extends AbstractIccRequest<AuthPersonDeleteSingleResponse> {
    private static final String VERSION = "1.0.0";
    private String personCode;
    private List<DeleteDetail> deleteDetails;

    private AuthPersonDeleteSingleRequest(Builder builder) {
        super(String.format(IccProfile.URL_SCHEME + IccAccessConstant.ASSESS_CONTROL_URL_AUTH_PERSON_DELETE_SINGLE_POST, VERSION), Method.POST);

        setPersonCode(builder.personCode);
        setDeleteDetails(builder.deleteDetails);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Class<AuthPersonDeleteSingleResponse> getResponseClass() {
        return AuthPersonDeleteSingleResponse.class;
    }

    public String getPersonCode() {
        return personCode;
    }

    public void setPersonCode(String personCode) {
        this.personCode = personCode;
        putBodyParameter("personCode", personCode);
    }

    public List<DeleteDetail> getDeleteDetails() {
        return deleteDetails;
    }

    public void setDeleteDetails(List<DeleteDetail> deleteDetails) {
        this.deleteDetails = deleteDetails;
        putBodyParameter("deleteDetails", deleteDetails);
    }

    @Override
    public void valid() {
        super.valid();

        if (StringUtils.isBlank(personCode)) {
            throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), "personCode不能为空");
        }
        if (deleteDetails == null || deleteDetails.isEmpty()) {
            throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), "deleteDetails不能为空");
        }
        for (DeleteDetail detail : deleteDetails) {
            if (detail.getPrivilegeType() == null) {
                throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), "privilegeType必须指定");
            }
            if (StringUtils.isBlank(detail.getResourceCode())) {
                throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), "resourceCode不能为空");
            }
        }
    }

    public static final class Builder {
        private String personCode;
        private List<DeleteDetail> deleteDetails;

        public Builder personCode(String personCode) {
            this.personCode = personCode;
            return this;
        }

        public Builder deleteDetails(List<DeleteDetail> deleteDetails) {
            this.deleteDetails = deleteDetails;
            return this;
        }

        public AuthPersonDeleteSingleRequest build() {
            return new AuthPersonDeleteSingleRequest(this);
        }
    }
}