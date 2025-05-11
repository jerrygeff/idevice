package info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5;

import com.dahuatech.icc.brm.model.v202010.FieldExt;

/**
 * 卡片类
 *
 * @author 232676
 * @since 1.0.0 2020/11/11 9:08
 */
public class BrmCardDetail {
  private Long id;
  /** 卡号 */
  private String cardNumber;
  /** 卡类型 0-普通卡 1-VIP卡 2-来宾卡 3-巡逻卡 5-胁迫卡 6-巡检卡 7-黑名单卡 -1-未知卡类型 */
  private String cardType;

  /** 卡介质 0-IC卡 1-有源RFID 2-CPU卡 */
  private String category;
  /** 部门id */
  private Long departmentId;
  /** 卡片有效期-开始时间 */
  private String startDate;
  /** 卡片有效期-结束时间 */
  private String endDate;
  /** 1-限制，2-无限制 */
  private String updateAuth;
  /** 1-限制，2-无限制 */
  private String deleteAuth;

  private String cardPassword;
  private String passwordKey;
  private Long personId;
  private String phone;
  private String cardStatus;
  private String validFlag;
  private String availableTimes;
  private int isMainCard;
  private String description;
  private int isVirtual;
  private Integer isCoercion;
  private FieldExt pageFieldExt;
  private FieldExt fieldExt;
  private String departmentName;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public String getCardType() {
    return cardType;
  }

  public void setCardType(String cardType) {
    this.cardType = cardType;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Long getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(Long departmentId) {
    this.departmentId = departmentId;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getUpdateAuth() {
    return updateAuth;
  }

  public void setUpdateAuth(String updateAuth) {
    this.updateAuth = updateAuth;
  }

  public String getDeleteAuth() {
    return deleteAuth;
  }

  public void setDeleteAuth(String deleteAuth) {
    this.deleteAuth = deleteAuth;
  }

  public String getCardPassword() {
    return cardPassword;
  }

  public void setCardPassword(String cardPassword) {
    this.cardPassword = cardPassword;
  }

  public String getPasswordKey() {
    return passwordKey;
  }

  public void setPasswordKey(String passwordKey) {
    this.passwordKey = passwordKey;
  }

  public Long getPersonId() {
    return personId;
  }

  public void setPersonId(Long personId) {
    this.personId = personId;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getCardStatus() {
    return cardStatus;
  }

  public void setCardStatus(String cardStatus) {
    this.cardStatus = cardStatus;
  }

  public String getValidFlag() {
    return validFlag;
  }

  public void setValidFlag(String validFlag) {
    this.validFlag = validFlag;
  }

  public String getAvailableTimes() {
    return availableTimes;
  }

  public void setAvailableTimes(String availableTimes) {
    this.availableTimes = availableTimes;
  }

  public int getIsMainCard() {
    return isMainCard;
  }

  public void setIsMainCard(int isMainCard) {
    this.isMainCard = isMainCard;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getIsVirtual() {
    return isVirtual;
  }

  public void setIsVirtual(int isVirtual) {
    this.isVirtual = isVirtual;
  }

  public Integer getIsCoercion() {
    return isCoercion;
  }

  public void setIsCoercion(Integer isCoercion) {
    this.isCoercion = isCoercion;
  }

  public FieldExt getPageFieldExt() {
    return pageFieldExt;
  }

  public void setPageFieldExt(FieldExt pageFieldExt) {
    this.pageFieldExt = pageFieldExt;
  }

  public FieldExt getFieldExt() {
    return fieldExt;
  }

  public void setFieldExt(FieldExt fieldExt) {
    this.fieldExt = fieldExt;
  }

  public String getDepartmentName() {
    return departmentName;
  }

  public void setDepartmentName(String departmentName) {
    this.departmentName = departmentName;
  }

  @Override
  public String toString() {
    return "BrmCardDetail{"
        + "id="
        + id
        + ", cardNumber='"
        + cardNumber
        + '\''
        + ", cardType='"
        + cardType
        + '\''
        + ", category='"
        + category
        + '\''
        + ", departmentId="
        + departmentId
        + ", startDate='"
        + startDate
        + '\''
        + ", endDate='"
        + endDate
        + '\''
        + ", updateAuth='"
        + updateAuth
        + '\''
        + ", deleteAuth='"
        + deleteAuth
        + '\''
        + ", cardPassword='"
        + cardPassword
        + '\''
        + ", passwordKey='"
        + passwordKey
        + '\''
        + ", personId="
        + personId
        + ", phone='"
        + phone
        + '\''
        + ", cardStatus='"
        + cardStatus
        + '\''
        + ", validFlag='"
        + validFlag
        + '\''
        + ", availableTimes='"
        + availableTimes
        + '\''
        + ", isMainCard="
        + isMainCard
        + ", description='"
        + description
        + '\''
        + ", isVirtual="
        + isVirtual
        + ", isCoercion="
        + isCoercion
        + ", pageFieldExt="
        + pageFieldExt
        + ", fieldExt="
        + fieldExt
        + ", departmentName='"
        + departmentName
        + '\''
        + '}';
  }
}
