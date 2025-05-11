package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.access.v2;

import lombok.Data;

/**
 * @author jerryge
 */
@Data
public class DoorInfo {
    private String indexCode;
    private String resourceType;
    private String name;
    private String doorNo;
    private String channelNo;
    private String parentIndexCode;
    private String controlOneId;
    private String controlTwoId;
    private String readerInId;
    private String readerOutId;
    private Integer doorSerial;
    private String treatyType;
    private String regionIndexCode;
    private String regionPath;
    private String createTime;
    private String updateTime;
    private String description;
    private String channelType;
    private String regionName;
    private String regionPathName;
    private String installLocation;
}