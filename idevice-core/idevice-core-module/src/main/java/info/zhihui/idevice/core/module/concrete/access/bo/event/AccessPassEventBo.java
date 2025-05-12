package info.zhihui.idevice.core.module.concrete.access.bo.event;

import info.zhihui.idevice.common.dto.FileResource;
import info.zhihui.idevice.core.module.concrete.access.enums.AccessOpenTypeEnum;
import info.zhihui.idevice.core.module.event.bo.EventBo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 门禁的开门事件
 *
 * @author jerryge
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class AccessPassEventBo extends EventBo {

    /**
     * 本地人员id
     */
    private Integer localPersonId;

    /**
     * 开门方式
     */
    private AccessOpenTypeEnum openType;

    /**
     * 卡号
     */
    private String cardNumber;

    /**
     * 通行图片
     */
    private FileResource fileResource;
}
