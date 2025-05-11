package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.camera.v2;

import lombok.Getter;
import lombok.Setter;

/**
 * 摄像头信息
 */
@Getter
@Setter
public class CameraInfo {
    /** 资源唯一标识 */
    private String indexCode;
    /** 资源类型 */
    private String resourceType;
    /** 外部编码 */
    private String externalIndexCode;
    /** 资源名称 */
    private String name;
    /** 通道号 */
    private Integer chanNum;
    /** 级联编码 */
    private String cascadeCode;
    /** 父级资源标识 */
    private String parentIndexCode;
    /** 经度 */
    private Double longitude;
    /** 纬度 */
    private Double latitude;
    /** 海拔 */
    private String elevation;
    /** 摄像机类型 */
    private Integer cameraType;
    /** 能力集 */
    private String capability;
    /** 存储位置 */
    private String recordLocation;
    /** 通道类型 */
    private String channelType;
    /** 所属区域 */
    private String regionIndexCode;
    /** 区域路径 */
    private String regionPath;
    /** 传输类型 */
    private Integer transType;
    /** 接入协议 */
    private String treatyType;
    /** 安装位置 */
    private String installLocation;
    /** 创建时间 */
    private String createTime;
    /** 更新时间 */
    private String updateTime;
    /** 显示顺序 */
    private Integer disOrder;
    /** 资源唯一标识 */
    private String resourceIndexCode;
    /** 解码标签 */
    private String decodeTag;
    /** 关联对讲通道 */
    private String cameraRelateTalk;
    /** 区域名称 */
    private String regionName;
    /** 区域路径名称 */
    private String regionPathName;
} 