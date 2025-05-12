package info.zhihui.idevice.web.access.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "门禁设备通道信息")
public class AccessFetchInfoVo {

    @Schema(description = "上级设备名称")
    private String deviceParentName;

    @Schema(description = "上级设备编码")
    private String deviceParentCode;

    @Schema(description = "第三方通道序号")
    private String channelNo;

    @Schema(description = "设备唯一编码")
    private String deviceCode;

    @Schema(description = "设备名称")
    private String deviceName;

    @Schema(description = "在线状态:0、离线;1、在线")
    private Integer onlineStatus;

    @Schema(description = "设备能力")
    private List<String> deviceCapacity;

    @Schema(description = "节点地址")
    private List<String> regionPath;

    @Schema(description = "节点地址名称")
    private List<String> regionPathName;
}