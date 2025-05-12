package info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5;

import com.dahuatech.icc.exception.ClientException;

/**
 * 人员所属车辆
 *
 * @author 232676
 * @since 1.0.0 2020/11/10 10:26
 */
public class BrmPersonCar {
  private String carNum;
  private String carCode;
  private Long carNumColor;
  private String carType;
  private String carBrand;
  private String carColor;
  private String deleteAuth;
  private String updateAuth;

  private BrmPersonCar(Builder builder) throws ClientException {
    this.carNum = builder.carNum;
  }

  public static Builder builder() {
    return new Builder();
  }

  public String getCarNum() {
    return carNum;
  }

  public void setCarNum(String carNum) {
    this.carNum = carNum;
  }

  public String getCarCode() {
    return carCode;
  }

  public void setCarCode(String carCode) {
    this.carCode = carCode;
  }

  public Long getCarNumColor() {
    return carNumColor;
  }

  public void setCarNumColor(Long carNumColor) {
    this.carNumColor = carNumColor;
  }

  public String getCarType() {
    return carType;
  }

  public void setCarType(String carType) {
    this.carType = carType;
  }

  public String getCarBrand() {
    return carBrand;
  }

  public void setCarBrand(String carBrand) {
    this.carBrand = carBrand;
  }

  public String getCarColor() {
    return carColor;
  }

  public void setCarColor(String carColor) {
    this.carColor = carColor;
  }

  public String getDeleteAuth() {
    return deleteAuth;
  }

  public void setDeleteAuth(String deleteAuth) {
    this.deleteAuth = deleteAuth;
  }

  public String getUpdateAuth() {
    return updateAuth;
  }

  public void setUpdateAuth(String updateAuth) {
    this.updateAuth = updateAuth;
  }

  @Override
  public String toString() {
    return "BrmPersonCar{"
        + "carNum='"
        + carNum
        + '\''
        + ", carCode='"
        + carCode
        + '\''
        + ", carNumColor="
        + carNumColor
        + ", carType='"
        + carType
        + '\''
        + ", carBrand='"
        + carBrand
        + '\''
        + ", carColor='"
        + carColor
        + '\''
        + ", deleteAuth='"
        + deleteAuth
        + '\''
        + ", updateAuth='"
        + updateAuth
        + '\''
        + '}';
  }

  public static class Builder {
    private String carNum;

    public Builder carNum(String carNum) {
      this.carNum = carNum;
      return this;
    }

    public BrmPersonCar build() throws ClientException {
      return new BrmPersonCar(this);
    }

    @Override
    public String toString() {
      return "Builder{" + "carNum='" + carNum + '\'' + '}';
    }
  }
}
