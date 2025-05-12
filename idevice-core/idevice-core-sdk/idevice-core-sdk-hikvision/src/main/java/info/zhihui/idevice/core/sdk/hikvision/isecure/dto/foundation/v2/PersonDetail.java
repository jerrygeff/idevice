package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
public class PersonDetail {
    private String personId;
    private String personName;
    private Integer gender;
    private String orgPath;
    private String orgPathName;
    private String orgIndexCode;
    private Integer certificateType;
    private String certificateNo;
    private String jobNo;
    private String birthday;
    private String phoneNo;
    private String address;
    private List<PersonPhoto> personPhoto;
    private String email;
    private Integer education;
    private String lastName;
    private String givenName;
    private Integer age;
    private String job;
    private String staffProperty;
    private String company;
    private String employeePost;
    private String employeeNumber;
    private String postType;
    private String identityType;
    private String nationality;
    private String birthplace;
    private String censusRegister;
    private Integer marriaged;
    private String roomNum;
    private String houseHolderRel;
    private String studentId;
    private String stuStartTime;
    private String stuEndTime;
    private String stuGrade;
    private String stuClass;
    private String academy;
    private String profession;
    private String dormitory;
    private Integer lodge;
    private String personDesc;
    private Integer syncFlag;
    private String pinyin;
    private String certIssuer;
    private String certAddr;
    private String certExpireTime;
    private Date createTime;
    private Date updateTime;
}
