package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.camera.v2;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 摄像头在线信息
 */
@Getter
@Setter
@Accessors(chain = true)
public class CameraOnlineInfo {
    /** 设备型号 */
    private String deviceType;

    /** 设备唯一编码 */
    private String deviceIndexCode;

    /** 区域编码 */
    private String regionIndexCode;

    /** 采集时间 */
    private String collectTime;

    /** 区域名称 */
    private String regionName;

    /** 资源唯一编码 */
    private String indexCode;

    /** 设备名称 */
    private String cn;

    /** 码流传输协议，0：UDP，1：TCP */
    private String treatyType;

    /** 厂商，hikvision-海康，dahua-大华 */
    private String manufacturer;

    /** ip地址，监控点无此值 */
    private String ip;

    /** 端口，监控点无此值 */
    private Integer port;

    /** 在线状态，0离线，1在线 */
    private Integer online;

}