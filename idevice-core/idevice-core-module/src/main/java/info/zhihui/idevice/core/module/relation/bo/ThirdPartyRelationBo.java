package info.zhihui.idevice.core.module.relation.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 第三方平台关联信息
 *
 * @author jerryge
 */
@Data
@Accessors(chain = true)
public class ThirdPartyRelationBo {
    private Integer id;

    /**
     * 自身平台业务key
     */
    private String localBusinessKey;

    /**
     * 自身平台的模块名称
     */
    private String localModuleName;

    /**
     * 使用areaId通过DeviceModuleConfigService可以获取实际对接的第三方平台信息
     */
    private Integer areaId;

    /**
     * 第三方平台对接的模块名称
     */
    private String thirdPartyModuleName;

    /**
     * 第三方平台业务key
     */
    private String thirdPartyBusinessKey;

}
