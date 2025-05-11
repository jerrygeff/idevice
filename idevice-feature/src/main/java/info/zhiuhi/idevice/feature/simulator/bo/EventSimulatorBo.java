package info.zhiuhi.idevice.feature.simulator.bo;

import info.zhihui.idevice.core.module.common.service.CommonDeviceModule;
import info.zhihui.idevice.core.module.event.bo.RawEventBo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.function.Consumer;

/**
 * 模拟事件对象
 *
 * @author jerryge
 */
@Data
@Accessors(chain = true)
public class EventSimulatorBo<E> {

    /**
     * 构建事件对象的源文件
     */
    private E eventSourceObject;

    /**
     * 获取构建的class
     */
    private Class<E> eventClass;

    /**
     * 获取处理器的接口
     */
    private CommonDeviceModule moduleService;

    /**
     * 事件处理器
     */
    private Consumer<RawEventBo> eventHandleService;

    /**
     * 区域id
     */
    private Integer areaId;

}




