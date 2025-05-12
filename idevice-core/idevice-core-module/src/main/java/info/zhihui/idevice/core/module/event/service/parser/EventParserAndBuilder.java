package info.zhihui.idevice.core.module.event.service.parser;

import info.zhihui.idevice.core.module.event.bo.EventBo;

public interface EventParserAndBuilder<T extends EventBo> extends EventParser<T> {

    /**
     * 构建事件源数据对象
     * 说明：
     * 1、由module获取某个解析器，来反向编码行成一组接口。构建事件源数据对象和解析数据对象相对应
     * 2、构建对象后，需要将对象返回给调用者，再由相匹配的解析器进行解析
     * 3、每个厂家的对象可能不一样，每个实现类需要根据自己的需求来构建对象
     */
    Object buildEventOriginalData(T data);
}
