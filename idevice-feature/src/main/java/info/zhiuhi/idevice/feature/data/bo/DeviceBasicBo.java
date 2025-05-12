package info.zhiuhi.idevice.feature.data.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 设备基础信息
 *
 * @author jerryge
 */
@Data
@Accessors(chain = true)
public class DeviceBasicBo {

    private String id;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备编号
     */
    private String deviceNumber;

    /**
     * 设备型号
     */
    private Integer deviceModelId;

    /**
     * 设备所在园区
     */
    private Integer deviceAreaId;

    /**
     * 设备所在空间
     */
    public Integer deviceSpaceId;

}
