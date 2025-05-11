package info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5;

import com.dahuatech.icc.oauth.http.IccResponse;

/**
 * 人员(包含车辆、卡片信息)返回结果
 *
 * @author 232676
 * @since 1.0.0 2020/11/9 11:19
 */
public class BrmPersonUpdateResponse extends IccResponse {
  private PersonIdData data;

  public PersonIdData getData() {
    return data;
  }

  public void setData(PersonIdData data) {
    this.data = data;
  }

  public static class PersonIdData {
    private Long id;

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    @Override
    public String toString() {
      return "PersonIdData{" + "id=" + id + '}';
    }
  }
}
