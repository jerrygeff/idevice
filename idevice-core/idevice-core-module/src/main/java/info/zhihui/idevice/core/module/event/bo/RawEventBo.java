package info.zhihui.idevice.core.module.event.bo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 事件原始信息
 *
 * @author jerryge
 */
@Data
@Accessors(chain = true)
public class RawEventBo {
    private Object originalData;

    private Integer areaId;
}
