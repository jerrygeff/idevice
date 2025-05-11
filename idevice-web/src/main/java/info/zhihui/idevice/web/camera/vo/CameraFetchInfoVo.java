package info.zhihui.idevice.web.camera.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 摄像头信息VO
 */
@Data
@Accessors(chain = true)
public class CameraFetchInfoVo {

    @Schema(description = "设备编码")
    private String deviceCode;

    @Schema(description = "设备名称")
    private String deviceName;

    @Schema(description = "设备能力")
    private List<String> deviceCapacity;

    @Schema(description = "通道序号")
    private Integer channelSeq;

    @Schema(description = "节点地址")
    private List<String> regionPath;

    @Schema(description = "节点地址名称")
    private List<String> regionPathName;

    @Schema(description = "所属录像机编号")
    private String deviceParentCode;

    @Schema(description = "摄像头类型: 0-枪机, 1-半球机, 2-快球, 3-带云台枪机, 4-球机, 5-本地采集输入")
    private Integer cameraType;

    @Schema(description = "是否支持云台能力: false-不支持, true-支持")
    private Boolean isPtz;

    @Schema(description = "设备所属组织")
    private String ownerCode;

    @Schema(description = "在线状态：1在线；0离线")
    private Integer onlineStatus;
}