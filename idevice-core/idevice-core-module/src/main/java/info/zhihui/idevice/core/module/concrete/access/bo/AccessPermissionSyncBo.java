package info.zhihui.idevice.core.module.concrete.access.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 门禁权限同步
 *
 * @author jerryge
 */
@Data
@Accessors(chain = true)
public class AccessPermissionSyncBo {

    /**
     * 区域id
     */
    private Integer areaId;

    /**
     * 任务类型
     */
    private Integer taskType;

    /**
     * 第三方设备唯一码
     * 某些平台，需要手动下发权限变更
     */
    private List<String> thirdPartyDeviceUniqueCodes;

}
