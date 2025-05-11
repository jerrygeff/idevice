package info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5;

/**
 * @author 232676
 * @since 1.0.0 2020/11/9 14:15
 */
public class PersonDepartment {
  private Long departmentId;
  private Integer departmentType;
  private String departmentName;

  public PersonDepartment(Long departmentId, Integer departmentType) {
    this.departmentId = departmentId;
    this.departmentType = departmentType;
  }

  public Long getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(Long departmentId) {
    this.departmentId = departmentId;
  }

  public Integer getDepartmentType() {
    return departmentType;
  }

  public void setDepartmentType(Integer departmentType) {
    this.departmentType = departmentType;
  }

  public String getDepartmentName() {
    return departmentName;
  }

  public void setDepartmentName(String departmentName) {
    this.departmentName = departmentName;
  }

  @Override
  public String toString() {
    return "PersonDepartment{"
        + "departmentId="
        + departmentId
        + ", departmentType="
        + departmentType
        + ", departmentName='"
        + departmentName
        + '\''
        + '}';
  }
}
