package info.zhihui.idevice.core.sdk.hikvision.isecure.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 海康分页响应基类
 * @author jerryge
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ISecurePageResponse<T> extends ISecureResponse {
    private ISecurePageData<T> data;
}