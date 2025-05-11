package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2;

import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.core.sdk.hikvision.isecure.constants.ISecureFoundationConstant;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecureJsonRequest;
import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation.CertificateTypeEnum;
import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.foundation.GenderEnum;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Getter
@Builder(builderClassName = "Builder")
public class PersonAddRequest extends ISecureJsonRequest<PersonAddResponse> {
    private static final String VERSION = "v2";
    private static final String API_URL = String.format(ISecureFoundationConstant.PERSON_ADD, VERSION);
    private static final Integer MAX_PERSON_NAME_LENGTH = 32;
    private static final String PERSON_NAME_ERROR_MSG = "人员名称不能为空，且不能大于%d个字符";
    private static final Integer MAX_FACES_SIZE = 10;
    private static final String FACES_EXCEED_MSG = "人脸信息不能超过：%d个";
    private static final String GENDER_ERROR_MSG = "性别设置不正确";
    private static final String CERTIFICATE_TYPE_ERROR_MSG = "证件类型设置不正确";
    private static final Integer MAX_CERTIFICATE_LENGTH = 10;
    private static final String CERTIFICATE_LENGTH_ERROR_MSG = "证件号码不超过：%d位";

    private final String personName;

    /**
     * 性别
     * @see GenderEnum
     */
    private final String gender;

    /**
     * 所属组织标识，必须是已存在组织
     * {@see /api/resource/v2/org/advance/orgList}
     */
    private final String orgIndexCode;

    /**
     * 出生日期，举例：1992-09-12
     */
    private final String birthday;

    /**
     * 电话号码
     * 非必填，但需要保证唯一
     */
    private final String phoneNo;

    /**
     * 邮箱
     */
    private final String email;

    /**
     * 证件类型
     * @see CertificateTypeEnum
     */
    private final String certificateType;

    /**
     * 证件号码
     * 非必填，但需要保证唯一
     */
    private final String certificateNo;

    /**
     * 工号，1-32个字符，
     * 非必填，但需要保证唯一
     */
    private final String jobNo;

    /**
     * 人脸信息
     */
    private final List<FaceData> faces;

    /**
     * 人员类型(COMMON普通人GUEST访客BLACKLIST黑名单SUPER管理员DISABLED行动不便人员)
     * 实际对接中是必填
     */
    private String personType;

    private PersonAddRequest(String personName, String gender, String orgIndexCode, String birthday, String phoneNo, String email, String certificateType, String certificateNo, String jobNo, List<FaceData> faces, String personType) {
        super(API_URL);
        this.personName = personName;
        this.gender = gender;
        this.orgIndexCode = orgIndexCode;
        this.birthday = birthday;
        this.phoneNo = phoneNo;
        this.email = email;
        this.certificateType = certificateType;
        this.certificateNo = certificateNo;
        this.jobNo = jobNo;
        this.faces = faces;
        this.personType = personType;
    }

    @Override
    public Class<PersonAddResponse> getResponseClass() {
        return PersonAddResponse.class;
    }

    @Override
    public void validate() {
        super.validate();

        if (StringUtils.isBlank(personName) || personName.length() > MAX_PERSON_NAME_LENGTH) {
            throw new ParamException(String.format(PERSON_NAME_ERROR_MSG, MAX_PERSON_NAME_LENGTH));
        }

        if (faces != null && faces.size() > MAX_FACES_SIZE) {
            throw new ParamException(String.format(FACES_EXCEED_MSG, MAX_FACES_SIZE));
        }

        if (!GenderEnum.isValid(gender)) {
            throw new ParamException(GENDER_ERROR_MSG);
        }

        if (!CertificateTypeEnum.isValid(certificateType)) {
            throw new ParamException(CERTIFICATE_TYPE_ERROR_MSG);
        }

        if (certificateNo != null && certificateNo.length() > MAX_CERTIFICATE_LENGTH) {
            throw new ParamException(String.format(CERTIFICATE_LENGTH_ERROR_MSG, MAX_CERTIFICATE_LENGTH));
        }

    }

}