package info.zhihui.idevice.core.sdk.dahua.icc.dto;

import com.dahuatech.icc.oauth.http.IccResponse;

/**
 * 自检返回结果的response
 *
 * @author jerryge
 */
public abstract class IccSelfCheckResponse extends IccResponse {
    public abstract void checkResult();
}
