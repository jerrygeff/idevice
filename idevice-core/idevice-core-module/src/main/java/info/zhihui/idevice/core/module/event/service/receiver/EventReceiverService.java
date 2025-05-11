package info.zhihui.idevice.core.module.event.service.receiver;

import info.zhihui.idevice.core.module.event.bo.EventBo;
import info.zhihui.idevice.core.module.event.bo.RawEventBo;
import info.zhihui.idevice.core.module.event.service.parser.EventParser;
import info.zhihui.idevice.core.module.event.service.processor.EventProcessorService;

import java.util.List;


public interface EventReceiverService {
    /**
     * 事件接收处理
     *
     * @param rawEventBo          事件原始数据
     * @param eventParser         事件原始数据解析器
     * @param moduleProcessorList 需要对事件进行的处理
     */
    <T extends EventBo> void receive(RawEventBo rawEventBo, EventParser<T> eventParser, List<EventProcessorService<T>> moduleProcessorList);

}
