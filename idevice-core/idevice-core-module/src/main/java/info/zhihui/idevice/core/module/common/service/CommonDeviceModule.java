package info.zhihui.idevice.core.module.common.service;

import info.zhihui.idevice.core.module.event.bo.EventBo;
import info.zhihui.idevice.core.module.event.service.parser.EventParser;

/**
 * 模块通用信息处理接口
 * 抽取公共的方法放在这里
 * <br/>
 * 每个模块有自己具体的业务接口，具体的业务类需要实现自身模块的接口
 *
 * @author jerryge
 */
public interface CommonDeviceModule {

    /**
     * 获取模块名称
     *
     * @return 模块名称
     */
    String getModuleName();

    /**
     * 获取事件解析器
     *
     * @param eventTypeClass 事件类型
     * @return E
     * @param <T> 事件对象，标识事件类型
     * @param <E> 解析器实的类型
     */
    <T extends EventBo, E extends EventParser<T>> E getEventParser(Class<T> eventTypeClass);
}
