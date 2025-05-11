package info.zhihui.idevice.core.module.event.service.processor;

import info.zhihui.idevice.core.module.event.bo.EventBo;

/**
 * 事件处理器
 * 用来编写对某种类型事件的处理，可供选择使用
 *
 * @param <T> 待处理的事件类型
 */
public interface EventProcessorService<T extends EventBo> {
    void process(T event);
}
