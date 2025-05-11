package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.access.v2;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AccessDevice {
    private String indexCode;
    private String resourceType;
    private String name;
    private String parentIndexCode;
    private String devTypeCode;
    private String devTypeDesc;
    private String deviceCode;
    private String manufacturer;
    private String regionIndexCode;
    private String regionPath;
    private String treatyType;
    private Integer cardCapacity;
    private Integer fingerCapacity;
    private Integer veinCapacity;
    private Integer faceCapacity;
    private Integer doorCapacity;
    private String deployId;
    private String netZoneId;
    private String createTime;
    private String updateTime;
    private String description;
    private String acsReaderVerifyModeAbility;
    private String regionName;
    private String regionPathName;
    private String ip;
    private String port;
    private String capability;
    private String devSerialNum;
    private String dataVersion;
}