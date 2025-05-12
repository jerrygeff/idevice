package info.zhiuhi.idevice.feature.data.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 设备信息通用查询对象
 *
 * @author jerryge
 */
@Data
@Accessors(chain = true)
public class DeviceCommonQuery {

    private String deviceName;

    private String deviceNo;

    /**
     * 多列匹配
     */
    private String searchKey;
}
