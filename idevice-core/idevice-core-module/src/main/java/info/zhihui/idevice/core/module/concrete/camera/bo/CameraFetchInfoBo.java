package info.zhihui.idevice.core.module.concrete.camera.bo;

import info.zhihui.idevice.core.module.concrete.camera.enums.CameraTypeEnum;
import info.zhihui.idevice.core.module.common.bo.DeviceThirdPartyFetchBasicInfoBo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class CameraFetchInfoBo extends DeviceThirdPartyFetchBasicInfoBo {
    /**
     * 所属录像机编号
     */
    private String deviceParentCode;

    /**
     * 设备能力
     */
    private List<String> deviceCapacity;

    /**
     * 通道序号
     */
    private Integer channelSeq;

    /**
     * 节点地址
     */
    private List<String> regionPath;

    /**
     * 节点地址名称
     */
    private List<String> regionPathName;

    /**
     * @see CameraTypeEnum
     */
    private Integer cameraType;

    /**
     * 是否支持云台能力   false、不支持   true、支持
     */
    private Boolean isPtz;

    /**
     * 设备所属组织
     */
    private String ownerCode;

}
