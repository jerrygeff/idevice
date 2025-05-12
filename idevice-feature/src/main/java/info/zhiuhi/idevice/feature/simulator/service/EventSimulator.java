package info.zhiuhi.idevice.feature.simulator.service;

import info.zhihui.idevice.common.exception.BusinessRuntimeException;
import info.zhihui.idevice.core.module.common.service.CommonDeviceModule;
import info.zhihui.idevice.core.module.event.bo.EventBo;
import info.zhihui.idevice.core.module.event.bo.RawEventBo;
import info.zhihui.idevice.core.module.event.service.parser.EventParser;
import info.zhihui.idevice.core.module.event.service.parser.EventParserAndBuilder;
import info.zhiuhi.idevice.feature.simulator.bo.EventSimulatorBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 模拟外部事件发送
 */
@Slf4j
@Service
public class EventSimulator {

    /**
     * 触发模拟生成一个事件
     *
     * @param eventSimulatorBo 模拟事件对象
     * @param <E> 原始事件类型
     */
    public <E extends EventBo> void triggerEvent(EventSimulatorBo<E> eventSimulatorBo) {

        CommonDeviceModule moduleService = eventSimulatorBo.getModuleService();
        EventParser<E> eventParser = moduleService.getEventParser(eventSimulatorBo.getEventClass());
        if (!(eventParser instanceof EventParserAndBuilder<E> builderService)) {
            throw new BusinessRuntimeException("解析器类型不匹配");
        }
        Object eventOriginalData = builderService.buildEventOriginalData(eventSimulatorBo.getEventSourceObject());

        // 发送原始事件
        RawEventBo rawEventBo = new RawEventBo().setOriginalData(eventOriginalData).setAreaId(eventSimulatorBo.getAreaId());
        eventSimulatorBo.getEventHandleService().accept(rawEventBo);
    }

}
