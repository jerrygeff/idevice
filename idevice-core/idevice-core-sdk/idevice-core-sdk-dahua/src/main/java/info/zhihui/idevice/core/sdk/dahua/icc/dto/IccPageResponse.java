package info.zhihui.idevice.core.sdk.dahua.icc.dto;

import com.dahuatech.icc.oauth.http.IccResponse;

/**
 * 大华分页响应基类
 *
 * @author jerryge
 */
public class IccPageResponse<T> extends IccResponse {
    private IccPageVo<T> data;

    public IccPageVo<T> getData() {
        return data;
    }

    public void setData(IccPageVo<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "IccPageResponse{" +
                "data=" + data +
                '}';
    }
}