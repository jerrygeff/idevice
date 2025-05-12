package info.zhihui.idevice.core.sdk.dahua.icc.dto.access.v5;

import com.dahuatech.hutool.http.Method;
import com.dahuatech.icc.brm.exception.BusinessException;
import com.dahuatech.icc.common.ParamValidEnum;
import com.dahuatech.icc.oauth.http.AbstractIccRequest;
import com.dahuatech.icc.oauth.profile.IccProfile;
import com.dahuatech.icc.util.CollectionUtil;
import info.zhihui.idevice.core.sdk.dahua.icc.constants.IccAccessConstant;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class AuthPersonBatchAddRequest extends AbstractIccRequest<AuthPersonBatchAddResponse> {
    private static final String VERSION = "1.0.0";
    private static final Integer MAX_AUTH_COLLECTION_SIZE = 50 * 10000;
    private List<String> personCodes;
    private Long timeQuantumId;
    private String startDate;
    private String endDate;
    private List<PrivilegeDetail> privilegeDetails;

    private AuthPersonBatchAddRequest(Builder builder) {
        super(String.format(IccProfile.URL_SCHEME + IccAccessConstant.ASSESS_CONTROL_URL_AUTH_PERSON_BATCH_ADD_POST, VERSION), Method.POST);

        setPersonCodes(builder.personCodes);
        setStartDate(builder.startDate);
        setEndDate(builder.endDate);
        setTimeQuantumId(builder.timeQuantumId);
        setPrivilegeDetails(builder.privilegeDetails);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Class<AuthPersonBatchAddResponse> getResponseClass() {
        return AuthPersonBatchAddResponse.class;
    }

    public List<String> getPersonCodes() {
        return personCodes;
    }

    public void setPersonCodes(List<String> personCodes) {
        this.personCodes = personCodes;
        putBodyParameter("personCodes", personCodes);
    }

    public Long getTimeQuantumId() {
        return timeQuantumId;
    }

    public void setTimeQuantumId(Long timeQuantumId) {
        this.timeQuantumId = timeQuantumId;
        putBodyParameter("timeQuantumId", timeQuantumId);
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
        putBodyParameter("startDate", startDate);
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
        putBodyParameter("endDate", endDate);
    }

    public List<PrivilegeDetail> getPrivilegeDetails() {
        return privilegeDetails;
    }

    public void setPrivilegeDetails(List<PrivilegeDetail> privilegeDetails) {
        this.privilegeDetails = privilegeDetails;
        putBodyParameter("privilegeDetails", privilegeDetails);
    }

    public static final class Builder {
        private List<String> personCodes;
        private Long timeQuantumId;
        private String startDate;
        private String endDate;
        private List<PrivilegeDetail> privilegeDetails;

        public Builder personCodes(List<String> personCodes) {
            this.personCodes = personCodes;
            return this;
        }

        public Builder timeQuantumId(Long timeQuantumId) {
            this.timeQuantumId = timeQuantumId;
            return this;
        }

        public Builder startDate(String startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(String endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder privilegeDetails(List<PrivilegeDetail> privilegeDetails) {
            this.privilegeDetails = privilegeDetails;
            return this;
        }

        public AuthPersonBatchAddRequest build() {
            return new AuthPersonBatchAddRequest(this);
        }
    }


    @Override
    public void valid() {
        super.valid();

        if (CollectionUtil.isEmpty(personCodes)) {
            throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), "personCodes不能为空");
        }

        if (CollectionUtil.isEmpty(privilegeDetails)) {
            throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), "privilegeDetails不能为空");
        }

        if (timeQuantumId == null) {
            throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), "timeQuantumId不能为空");
        }

        if (personCodes.size() * privilegeDetails.size() > MAX_AUTH_COLLECTION_SIZE) {
            throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), "人员数量*门禁通道数量不能超过" + MAX_AUTH_COLLECTION_SIZE);
        }

        for (PrivilegeDetail detail : privilegeDetails) {
            if (detail.getPrivilegeType() == null) {
                throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), "privilegeType必须指定");
            }
            if (StringUtils.isBlank(detail.getResourceCode())) {
                throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), "resourceCode不能为空");
            }
        }
    }
}
