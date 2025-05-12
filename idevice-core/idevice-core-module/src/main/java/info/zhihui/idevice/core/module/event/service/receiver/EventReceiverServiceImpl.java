package info.zhihui.idevice.core.module.event.service.receiver;

import info.zhihui.idevice.core.module.event.bo.EventBo;
import info.zhihui.idevice.core.module.event.bo.RawEventBo;
import info.zhihui.idevice.core.module.event.service.parser.EventParser;
import info.zhihui.idevice.core.module.event.service.processor.EventProcessorService;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Collections;
import java.util.List;

//@Service
public class EventReceiverServiceImpl implements EventReceiverService {
    private final EventProcessorService<EventBo> recordSaveProcessor;

//    @Autowired
    public EventReceiverServiceImpl(@Qualifier("recordSaveProcessorImpl") EventProcessorService<EventBo> recordSaveProcessor) {
        this.recordSaveProcessor = recordSaveProcessor;
    }

    @Override
    public <T extends EventBo> void receive(RawEventBo rawEventBo, EventParser<T> eventParser, List<EventProcessorService<T>> moduleProcessorList) {
        List<T> eventList = eventParser.parseEvent(rawEventBo);
        for (EventProcessorService<T> service : moduleProcessorList) {
            for (T bo : eventList) {
                service.process(bo);
            }
        }

        List<EventProcessorService<EventBo>> defaultProcessorList = getDefaultProcessorList();

        for (EventProcessorService<EventBo> service : defaultProcessorList) {
            for (EventBo bo : eventList) {
                service.process(bo);
            }
        }
    }

    protected List<EventProcessorService<EventBo>> getDefaultProcessorList() {
        return Collections.singletonList(recordSaveProcessor);
    }
}
