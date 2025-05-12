package info.zhihui.idevice.core.module.event.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 通用事件信息
 *
 * @author jerryge
 */
@Data
@Accessors(chain = true)
public class EventBo {

    /**
     * 设备编码
     */
    private String deviceNumber;

    /**
     * 事件时间
     */
    private LocalDateTime eventTime;

    /**
     * 事件编号
     */
    private String eventNumber;

    /**
     * 原始数据
     */
    private Object originalData;

}
