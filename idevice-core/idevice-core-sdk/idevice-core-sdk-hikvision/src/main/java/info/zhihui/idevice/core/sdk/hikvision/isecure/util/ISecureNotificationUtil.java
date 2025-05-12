package info.zhihui.idevice.core.sdk.hikvision.isecure.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.zhihui.idevice.common.utils.JacksonUtil;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.event.common.Event;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.event.common.Notification;

import java.util.List;

/**
 * @author jerryge
 */
public class ISecureNotificationUtil {

    /**
     * 将原始数据转换为事件列表，并指定data字段的目标类型
     *
     * @param originalData 原始数据对象
     * @param dataClass    data字段的目标类型，如果为null则不进行类型转换
     * @param <T>          事件数据类型
     * @return 事件列表
     */
    public static <T> List<Event<T>> toEventList(Object originalData, Class<T> dataClass) {
        String jsonString = JacksonUtil.toJson(originalData);
        Notification<T> notification = JacksonUtil.fromJson(jsonString, new TypeReference<>() {
        });

        List<Event<T>> events = notification.getParams().getEvents();

        // 泛型擦除了类型信息，需要手动转换
        if (dataClass != null && events != null && !events.isEmpty()) {
            ObjectMapper mapper = JacksonUtil.getObjectMapper();
            for (Event<T> event : events) {
                // 如果data是Map类型（如LinkedHashMap），则需要进行类型转换
                if (event.getData() != null && event.getData() instanceof java.util.Map) {
                    // 使用convertValue方法将Map转换为目标类型
                    T convertedData = mapper.convertValue(event.getData(), dataClass);
                    event.setData(convertedData);
                }
            }
        }

        return events;
    }
}
