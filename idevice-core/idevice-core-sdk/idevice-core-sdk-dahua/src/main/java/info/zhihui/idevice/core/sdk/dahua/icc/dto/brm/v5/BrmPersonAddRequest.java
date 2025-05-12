package info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5;

import com.dahuatech.hutool.http.Method;
import com.dahuatech.icc.brm.exception.BusinessException;
import com.dahuatech.icc.brm.model.v202010.FieldExt;
import com.dahuatech.icc.brm.model.v202010.person.BrmPersonCar;
import com.dahuatech.icc.brm.model.v202010.person.BrmPersonCard;
import com.dahuatech.icc.brm.model.v202010.person.PersonBioSignatures;
import com.dahuatech.icc.brm.model.v202010.person.PersonDepartment;
import com.dahuatech.icc.common.ParamValidEnum;
import com.dahuatech.icc.exception.ClientException;
import com.dahuatech.icc.oauth.http.AbstractIccRequest;
import com.dahuatech.icc.oauth.profile.IccProfile;
import com.dahuatech.icc.util.StringUtils;
import info.zhihui.idevice.core.sdk.dahua.icc.constants.IccBrmConstant;

import java.util.List;
import java.util.Map;

/**
 * 人员(包含车辆、卡片信息)新增
 */
public class BrmPersonAddRequest extends AbstractIccRequest<BrmPersonAddResponse> {

    private static final String VERSION = "1.2.0";

    private Long id;
    private String code;
    private String name;
    private Long departmentId;
    /**
     * 身份类型
     */
    private Integer paperType;
    /**
     * 证件类型名称
     */
    private String paperTypeName;
    /**
     * 证件地址
     */
    private String paperAddress;

    private String paperNumber;
    /**
     * 有效刷卡次数 仅来宾用户有效
     */
    private int availableTimes;
    /**
     * 电话
     */
    private String phone;
    /**
     * 电子邮箱
     */
    private String email;
    /**
     * 生日
     */
    private String birthday;

    private String country;
    /**
     * 民族
     */
    private int nation;
    /**
     * 民族名称
     */
    private String nationName;
    /**
     * 人员自编号
     */
    private String selfCode;
    /**
     * 组织编码
     */
    private String ownerCode;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 权限有效开始时间
     */
    private String validStartTime;
    /**
     * 权限有效结束时间
     */
    private String validEndTime;
    /**
     * 所属部门列表,一人多部门场景使用：若departmentId、departmentList同时传值，以departmentId传参为准，该参数无效
     */
    private List<PersonDepartment> departmentList;
    /**
     * 生物特征数据:人脸头像、人脸特征、指纹特征
     */
    private List<PersonBioSignatures> personBiosignatures;
    /**
     * 人员所属卡片
     */
    private List<BrmPersonCar> cars;
    /**
     * 人员所属车辆
     */
    private List<BrmPersonCard> cards;

    /**
     * 扩展属性值
     */
    private Map<String, String> ext;

    private String service;
    /**
     * 自定义字段信息
     */
    private FieldExt fieldExt;

    private BrmPersonAddRequest(Builder builder) {
        super(String.format(IccProfile.URL_SCHEME + IccBrmConstant.BRM_URL_PERSON_ADD, VERSION), Method.POST);

        this.id = builder.id;
        this.code = builder.code;
        this.name = builder.name;
        this.departmentId = builder.departmentId;
        this.paperType = builder.paperType;
        this.paperTypeName = builder.paperTypeName;
        this.paperAddress = builder.paperAddress;
        this.paperNumber = builder.paperNumber;
        this.availableTimes = builder.availableTimes;
        this.phone = builder.phone;
        this.email = builder.email;
        this.birthday = builder.birthday;
        this.country = builder.country;
        this.nation = builder.nation;
        this.nationName = builder.nationName;
        this.selfCode = builder.selfCode;
        this.ownerCode = builder.ownerCode;
        this.age = builder.age;
        this.sex = builder.sex;
        this.validStartTime = builder.validStartTime;
        this.validEndTime = builder.validEndTime;
        this.departmentList = builder.departmentList;
        this.personBiosignatures = builder.personBiosignatures;
        this.cars = builder.cars;
        this.cards = builder.cards;
        this.ext = builder.ext;
        this.service = builder.service;
        this.fieldExt = builder.fieldExt;

        putBodyParameter("id", id);
        putBodyParameter("name", name);
        putBodyParameter("code", code);
        putBodyParameter("paperType", paperType);
        putBodyParameter("departmentId", departmentId);
        putBodyParameter("paperTypeName", paperTypeName);
        putBodyParameter("paperNumber", paperNumber);
        putBodyParameter("paperAddress", paperAddress);
        putBodyParameter("availableTimes", availableTimes);
        putBodyParameter("phone", phone);
        putBodyParameter("email", email);
        putBodyParameter("birthday", birthday);
        putBodyParameter("country", country);
        putBodyParameter("nation", nation);
        putBodyParameter("nationName", nationName);
        putBodyParameter("selfCode", selfCode);
        putBodyParameter("ownerCode", ownerCode);
        putBodyParameter("age", age);
        putBodyParameter("sex", sex);
        putBodyParameter("validStartTime", validStartTime);
        putBodyParameter("validEndTime", validEndTime);
        putBodyParameter("departmentList", departmentList);
        putBodyParameter("personBiosignatures", personBiosignatures);
        putBodyParameter("cars", cars);
        putBodyParameter("cards", cards);
        putBodyParameter("ext", ext);
        putBodyParameter("service", service);
        putBodyParameter("fieldExt", fieldExt);
    }

    public BrmPersonAddRequest() {
        super(String.format(IccProfile.URL_SCHEME + IccBrmConstant.BRM_URL_PERSON_ADD, VERSION), Method.POST);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getPaperTypeName() {
        return paperTypeName;
    }

    public String getPaperAddress() {
        return paperAddress;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getAvailableTimes() {
        return availableTimes;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getCountry() {
        return country;
    }

    public int getNation() {
        return nation;
    }

    public String getNationName() {
        return nationName;
    }

    public String getSelfCode() {
        return selfCode;
    }

    public String getOwnerCode() {
        return ownerCode;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getSex() {
        return sex;
    }

    public String getValidStartTime() {
        return validStartTime;
    }

    public String getValidEndTime() {
        return validEndTime;
    }

    public List<PersonDepartment> getDepartmentList() {
        return departmentList;
    }

    public List<PersonBioSignatures> getPersonBiosignatures() {
        return personBiosignatures;
    }

    public Map<String, String> getExt() {
        return ext;
    }

    public String getService() {
        return service;
    }

    public FieldExt getFieldExt() {
        return fieldExt;
    }

    public Integer getPaperType() {
        return paperType;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public String getPaperNumber() {
        return paperNumber;
    }

    public List<BrmPersonCar> getCars() {
        return cars;
    }

    public List<BrmPersonCard> getCards() {
        return cards;
    }

    @Override
    public Class<BrmPersonAddResponse> getResponseClass() {
        return BrmPersonAddResponse.class;
    }

    public static class Builder {
        private Long id;
        private String name;
        private String code;
        /**
         * 证件类型
         */
        private Integer paperType;
        /**
         * 证件号码
         */
        private String paperNumber;
        /**
         * 部门ID
         */
        private Long departmentId;
        /**
         * 证件类型名称
         */
        private String paperTypeName;
        /**
         * 证件地址
         */
        private String paperAddress;
        /**
         * 有效刷卡次数
         */
        private int availableTimes;
        /**
         * 电话
         */
        private String phone;
        /**
         * 电子邮箱
         */
        private String email;
        /**
         * 生日
         */
        private String birthday;
        /**
         * 国籍
         */
        private String country;
        /**
         * 民族
         */
        private int nation;
        /**
         * 民族名称
         */
        private String nationName;
        /**
         * 人员自编号
         */
        private String selfCode;
        /**
         * 组织编码
         */
        private String ownerCode;
        /**
         * 年龄
         */
        private Integer age;
        /**
         * 性别
         */
        private Integer sex;
        /**
         * 权限有效开始时间
         */
        private String validStartTime;
        /**
         * 权限有效结束时间
         */
        private String validEndTime;
        /**
         * 所属部门列表
         */
        private List<PersonDepartment> departmentList;
        /**
         * 生物特征数据
         */
        private List<PersonBioSignatures> personBiosignatures;
        /**
         * 人员所属卡片
         */
        private List<BrmPersonCar> cars;
        /**
         * 人员所属车辆
         */
        private List<BrmPersonCard> cards;
        /**
         * 扩展属性值
         */
        private Map<String, String> ext;
        /**
         * 服务
         */
        private String service;
        /**
         * 自定义字段信息
         */
        private FieldExt fieldExt;

        public Builder departmentId(Long departmentId) {
            this.departmentId = departmentId;
            return this;
        }

        public Builder paperNumber(String paperNumber) {
            this.paperNumber = paperNumber;
            return this;
        }

        public Builder paperType(Integer paperType) {
            this.paperType = paperType;
            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder paperTypeName(String paperTypeName) {
            this.paperTypeName = paperTypeName;
            return this;
        }

        public Builder paperAddress(String paperAddress) {
            this.paperAddress = paperAddress;
            return this;
        }

        public Builder availableTimes(int availableTimes) {
            this.availableTimes = availableTimes;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder birthday(String birthday) {
            this.birthday = birthday;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder nation(int nation) {
            this.nation = nation;
            return this;
        }

        public Builder nationName(String nationName) {
            this.nationName = nationName;
            return this;
        }

        public Builder selfCode(String selfCode) {
            this.selfCode = selfCode;
            return this;
        }

        public Builder ownerCode(String ownerCode) {
            this.ownerCode = ownerCode;
            return this;
        }

        public Builder age(Integer age) {
            this.age = age;
            return this;
        }

        public Builder sex(Integer sex) {
            this.sex = sex;
            return this;
        }

        public Builder validStartTime(String validStartTime) {
            this.validStartTime = validStartTime;
            return this;
        }

        public Builder validEndTime(String validEndTime) {
            this.validEndTime = validEndTime;
            return this;
        }

        public Builder departmentList(List<PersonDepartment> departmentList) {
            this.departmentList = departmentList;
            return this;
        }

        public Builder personBiosignatures(List<PersonBioSignatures> personBiosignatures) {
            this.personBiosignatures = personBiosignatures;
            return this;
        }

        public Builder cars(List<BrmPersonCar> cars) {
            this.cars = cars;
            return this;
        }

        public Builder cards(List<BrmPersonCard> cards) {
            this.cards = cards;
            return this;
        }

        public Builder ext(Map<String, String> ext) {
            this.ext = ext;
            return this;
        }

        public Builder service(String service) {
            this.service = service;
            return this;
        }

        public Builder fieldExt(FieldExt fieldExt) {
            this.fieldExt = fieldExt;
            return this;
        }

        public BrmPersonAddRequest build() throws ClientException {
            return new BrmPersonAddRequest(this);
        }
    }

    @Override
    public void valid() {
        super.valid();

        if (StringUtils.isEmpty(service)) {
            throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getErrMsg(), "service");
        }

        if (id == null) {
            throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getErrMsg(), "id");
        }

        if (StringUtils.isEmpty(name)) {
            throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getErrMsg(), "name");
        }
        if (StringUtils.isEmpty(code)) {
            throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getErrMsg(), "code");
        }
        if (paperType == null) {
            throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getErrMsg(), "paperType");
        }
        if (StringUtils.isEmpty(paperNumber)) {
            throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getErrMsg(), "paperNumber");
        }
        if (StringUtils.isEmpty(paperAddress)) {
            throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getErrMsg(), "paperAddress");
        }
    }
}