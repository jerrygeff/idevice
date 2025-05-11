package info.zhihui.idevice.core.module.concrete.access.bo;

import info.zhihui.idevice.core.module.common.bo.DeviceThirdPartyFetchBasicInfoBo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 从第三方获取的门禁信息
 *
 * @author jerryge
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class AccessFetchInfoBo extends DeviceThirdPartyFetchBasicInfoBo {
    /**
     * 父级设备编号
     */
    private String deviceParentCode;

    /**
     * 父级设备名称
     */
    private String deviceParentName;

    /**
     * 第三方通道序号
     */
    private String thirdPartyChannelNo;

    /**
     * 设备能力
     */
    private List<String> deviceCapacity;

    /**
     * 所属节点编码
     */
    private String ownerCode;

    /**
     * 节点地址
     */
    private List<String> regionPath;

    /**
     * 节点地址名称
     */
    private List<String> regionPathName;
}
