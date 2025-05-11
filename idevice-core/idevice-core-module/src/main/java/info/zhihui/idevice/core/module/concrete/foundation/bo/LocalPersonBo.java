package info.zhihui.idevice.core.module.concrete.foundation.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author jerryge
 */
@Data
@Accessors(chain = true)
public class LocalPersonBo {
    /**
     * 本地人员id
     */
    private Integer localPersonId;

    /**
     * 本地模块名
     */
    private String localModuleName;

    /**
     * 区域id
     */
    private Integer areaId;
}
