package info.zhihui.idevice.core.module.event.service;

import info.zhihui.idevice.core.module.event.bo.RawEventBo;

/**
 * 通用事件处理接口
 *
 */
public interface CommonEquipmentEventService {
    /**
     * 处理离线事件
     * @param rawEventBo 原始事件
     */
    void offlineEvent(RawEventBo rawEventBo);
}
