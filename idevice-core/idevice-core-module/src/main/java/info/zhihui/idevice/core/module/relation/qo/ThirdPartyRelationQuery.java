package info.zhihui.idevice.core.module.relation.qo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author jerryge
 */
@Data
@Accessors(chain = true)
public class ThirdPartyRelationQuery {

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
}
