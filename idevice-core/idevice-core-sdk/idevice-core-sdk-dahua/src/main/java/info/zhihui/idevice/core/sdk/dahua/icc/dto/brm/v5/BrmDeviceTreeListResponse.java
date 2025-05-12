package info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5;

import com.dahuatech.icc.oauth.http.IccResponse;

import java.util.List;

/**
 * 设备树列表查询响应
 *
 * @author jerryge
 */
public class BrmDeviceTreeListResponse extends IccResponse {
    /** 响应数据 */
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        /** 设备树节点列表 */
        private List<TreeItem> value;

        public List<TreeItem> getValue() {
            return value;
        }

        public void setValue(List<TreeItem> value) {
            this.value = value;
        }
    }
}