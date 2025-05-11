package info.zhihui.idevice.core.module.concrete.camera.service;

import info.zhihui.idevice.common.exception.BusinessRuntimeException;
import info.zhihui.idevice.core.module.common.service.CommonDeviceModule;
import info.zhihui.idevice.core.module.concrete.camera.bo.*;
import info.zhihui.idevice.core.module.common.enums.ModuleEnum;
import info.zhihui.idevice.core.module.event.bo.EventBo;
import info.zhihui.idevice.core.module.event.service.parser.EventParser;

import java.util.List;

public interface Camera extends CommonDeviceModule {

    /**
     * 获取模块名称
     */
    default String getModuleName() {
        return ModuleEnum.CAMERA.getModuleName();
    }

    /**
     * 获取事件解析器
     *
     * @param eventTypeClass 事件类型
     * @return E
     * @param <T> 事件对象，标识事件类型
     * @param <E> 解析器实的类型
     */
    default <T extends EventBo, E extends EventParser<T>> E getEventParser(Class<T> eventTypeClass) {
        throw new BusinessRuntimeException("解析器未配置");
    }

    /**
     * 获取实时地址
     */
    CameraRealtimeLinkBo getCameraRealTimeLink(CameraRealtimeQueryBo realTimeQueryBo);

    /**
     * 获取回放地址
     */
    CameraPlaybackLinkBo getCameraPlaybackLink(CameraPlaybackQueryBo playbackQueryBo);

    /**
     * 获取全部视频通道列表
     */
    List<CameraFetchInfoBo> findAllCameraChannelList(CameraQueryBo queryBo);

    /**
     * 云台方向控制
     */
    void controlDirection(CameraOperateBo cameraOperateBo);

}
