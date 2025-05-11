package info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5;

import com.dahuatech.icc.brm.model.v202010.person.PersonBioSignatures;
import com.dahuatech.icc.brm.model.v202010.person.PersonDepartment;

import java.util.Date;
import java.util.List;

/**
 * 人员数据
 *
 * @author 232676
 * @since 1.0.0 2020/11/9 16:04
 */
public class PersonData {
  private Long id;
  private String code;
  private int sex;
  private Date birthday;
  private String name;
  private int age;
  private String personType;
  private int isAdmin;
  private int paperType;
  private String paperTypeName;
  private String paperNumber;
  private String paperAddress;
  private String country;
  private int nation;
  private String nationName;
  private String phone;
  private String email;
  private int departmentId;
  private int status;
  private String password;
  private String ownerCode;
  private List<PersonBioSignatures> personBiosignatures;
  private int personIdentityId;
  private List<PersonDepartment> departmentList;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public int getSex() {
    return sex;
  }

  public void setSex(int sex) {
    this.sex = sex;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getPersonType() {
    return personType;
  }

  public void setPersonType(String personType) {
    this.personType = personType;
  }

  public int getIsAdmin() {
    return isAdmin;
  }

  public void setIsAdmin(int isAdmin) {
    this.isAdmin = isAdmin;
  }

  public int getPaperType() {
    return paperType;
  }

  public void setPaperType(int paperType) {
    this.paperType = paperType;
  }

  public String getPaperTypeName() {
    return paperTypeName;
  }

  public void setPaperTypeName(String paperTypeName) {
    this.paperTypeName = paperTypeName;
  }

  public String getPaperNumber() {
    return paperNumber;
  }

  public void setPaperNumber(String paperNumber) {
    this.paperNumber = paperNumber;
  }

  public String getPaperAddress() {
    return paperAddress;
  }

  public void setPaperAddress(String paperAddress) {
    this.paperAddress = paperAddress;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public int getNation() {
    return nation;
  }

  public void setNation(int nation) {
    this.nation = nation;
  }

  public String getNationName() {
    return nationName;
  }

  public void setNationName(String nationName) {
    this.nationName = nationName;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(int departmentId) {
    this.departmentId = departmentId;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getOwnerCode() {
    return ownerCode;
  }

  public void setOwnerCode(String ownerCode) {
    this.ownerCode = ownerCode;
  }

  public List<PersonBioSignatures> getPersonBiosignatures() {
    return personBiosignatures;
  }

  public void setPersonBiosignatures(List<PersonBioSignatures> personBiosignatures) {
    this.personBiosignatures = personBiosignatures;
  }

  public int getPersonIdentityId() {
    return personIdentityId;
  }

  public void setPersonIdentityId(int personIdentityId) {
    this.personIdentityId = personIdentityId;
  }

  public List<PersonDepartment> getDepartmentList() {
    return departmentList;
  }

  public void setDepartmentList(List<PersonDepartment> departmentList) {
    this.departmentList = departmentList;
  }

  @Override
  public String toString() {
    return "PersonData{"
        + "id="
        + id
        + ", code='"
        + code
        + '\''
        + ", sex="
        + sex
        + ", birthday="
        + birthday
        + ", name='"
        + name
        + '\''
        + ", age="
        + age
        + ", personType='"
        + personType
        + '\''
        + ", isAdmin="
        + isAdmin
        + ", paperType="
        + paperType
        + ", paperTypeName='"
        + paperTypeName
        + '\''
        + ", paperNumber='"
        + paperNumber
        + '\''
        + ", paperAddress='"
        + paperAddress
        + '\''
        + ", country='"
        + country
        + '\''
        + ", nation="
        + nation
        + ", nationName='"
        + nationName
        + '\''
        + ", phone='"
        + phone
        + '\''
        + ", email='"
        + email
        + '\''
        + ", departmentId="
        + departmentId
        + ", status="
        + status
        + ", password='"
        + password
        + '\''
        + ", ownerCode='"
        + ownerCode
        + '\''
        + ", personBiosignatures="
        + personBiosignatures
        + ", personIdentityId="
        + personIdentityId
        + ", departmentList="
        + departmentList
        + '}';
  }
}
