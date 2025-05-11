package info.zhihui.idevice.core.sdk.hikvision.isecure.dto;

import lombok.Data;

/**
 * @author jerryge
 */
@Data
public class ISecureResponse {
    private String code;
    private String msg;

    public Boolean isSuccess() {
        return "0".equals(code);
    }
}
