package info.zhihui.idevice.core.module.relation.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author jerryge
 */
@Data
@Accessors(chain = true)
@TableName("device_third_party_relation")
public class ThirdPartyRelationEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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

    /**
     * 是否删除：0-未删除，1-已删除
     */
    private Boolean isDeleted;
}
