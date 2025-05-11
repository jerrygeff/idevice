package info.zhihui.idevice.core.module.common.bo;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import info.zhihui.idevice.common.utils.JacksonUtil;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 模块配置信息
 *
 * @author jerryge
 */
@Data
@Accessors(chain = true)
public class DeviceModuleConfigBo {
    /**
     * 模块接口名称
     */
    private String moduleServiceName;

    /**
     * 实现类名称
     */
    private String implName;

    /**
     * 配置信息
     * 约定为json字符串
     */
    @JsonDeserialize(using = JacksonUtil.ObjectToStringDeserializer.class)
    @JsonRawValue
    private String configValue;
}
