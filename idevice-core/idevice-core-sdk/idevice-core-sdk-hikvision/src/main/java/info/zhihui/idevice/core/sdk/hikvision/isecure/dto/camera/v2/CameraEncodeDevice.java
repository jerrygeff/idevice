package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.camera.v2;

import lombok.Data;

/**
 * 编码设备信息
 */
@Data
public class CameraEncodeDevice {
    /** 所属资源编号 */
    private String belongIndexCode;
    /** 能力集 */
    private String capability;
    /** 设备类型 */
    private String deviceType;
    /** 设备序列号 */
    private String devSerialNum;
    /** 设备编号 */
    private String deviceCode;
    /** 资源编号 */
    private String indexCode;
    /** 厂商 */
    private String manufacturer;
    /** 资源名称 */
    private String name;
    /** 区域编号 */
    private String regionIndexCode;
    /** 区域路径 */
    private String regionPath;
    /** 资源类型 */
    private String resourceType;
    /** 协议类型 */
    private String treatyType;
    /** 创建时间 */
    private String createTime;
    /** 更新时间 */
    private String updateTime;
} 