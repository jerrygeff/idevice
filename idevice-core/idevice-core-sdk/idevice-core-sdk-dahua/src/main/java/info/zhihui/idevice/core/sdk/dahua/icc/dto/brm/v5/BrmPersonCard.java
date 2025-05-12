package info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5;

import com.dahuatech.icc.exception.ClientException;

/**
 * 人员所属卡片
 *
 * @author 232676
 * @since 1.0.0 2020/11/10 10:26
 */
public class BrmPersonCard {
  private String cardNumber;
  private String category;
  private Long departmentId;
  private String startDate;
  private String endDate;
  private String updateAuth;
  private String deleteAuth;
  private String cardPassword;

  private BrmPersonCard(Builder builder) throws ClientException {
    this.cardNumber = builder.cardNumber;
    this.category = builder.category;
    this.startDate = builder.startDate;
    this.endDate = builder.endDate;
    this.departmentId = builder.departmentId;
  }

  public static Builder builder() {
    return new Builder();
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
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

  @Override
  public String toString() {
    return "BrmPersonCard{"
        + "cardNumber='"
        + cardNumber
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
        + '}';
  }

  public static class Builder {
    private String cardNumber;
    private String category;
    private Long departmentId;
    private String startDate;
    private String endDate;

    public Builder cardNumber(String cardNumber) {
      this.cardNumber = cardNumber;
      return this;
    }

    public Builder category(String category) {
      this.category = category;
      return this;
    }

    public Builder departmentId(Long departmentId) {
      this.departmentId = departmentId;
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

    public BrmPersonCard build() throws ClientException {
      return new BrmPersonCard(this);
    }

    @Override
    public String toString() {
      return "Builder{"
          + "cardNumber='"
          + cardNumber
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
          + '}';
    }
  }
}
