package info.zhihui.idevice.core.module.event;

import info.zhihui.idevice.common.exception.NotFoundException;
import info.zhihui.idevice.core.module.event.bo.EventBo;
import info.zhihui.idevice.core.module.event.service.parser.EventParser;

import java.util.Map;

public abstract class EventParserGetter {

    protected Map<Class<? extends EventBo>, ? extends EventParser<?>> eventParserMap;

    public EventParserGetter(Map<Class<? extends EventBo>, ? extends EventParser<?>> eventParserMap) {
        this.eventParserMap = eventParserMap;
    }

    public <T extends EventBo, E extends EventParser<T>> E getEventParser(Class<T> eventTypeClass) throws NotFoundException {
        @SuppressWarnings("unchecked")
        E res = (E) eventParserMap.get(eventTypeClass);

        if (res == null) {
            throw new NotFoundException("没有找到对应的解析器");
        }

        return res;
    }
}
