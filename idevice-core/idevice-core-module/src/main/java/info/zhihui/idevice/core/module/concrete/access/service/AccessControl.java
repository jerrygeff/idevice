package info.zhihui.idevice.core.module.concrete.access.service;


import info.zhihui.idevice.common.exception.BusinessRuntimeException;
import info.zhihui.idevice.core.module.common.service.CommonDeviceModule;
import info.zhihui.idevice.core.module.concrete.access.bo.*;
import info.zhihui.idevice.core.module.common.enums.ModuleEnum;
import info.zhihui.idevice.core.module.event.bo.EventBo;
import info.zhihui.idevice.core.module.event.service.parser.EventParser;

import java.util.List;

public interface AccessControl extends CommonDeviceModule {

    /**
     * 获取模块名称
     */
    default String getModuleName() {
        return ModuleEnum.ACCESS.getModuleName();
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
     * 授权人员访问门禁设备
     *
     * @param accessPersonAuthBo 门禁人员授权信息
     *
     */
    void grantAccess(AccessPersonAuthBo accessPersonAuthBo);

    /**
     * 撤销员工访问门禁设备权限
     *
     * @param accessPersonRevokeBo 门禁人员撤销授权信息
     *
     */
    void revokeAccess(AccessPersonRevokeBo accessPersonRevokeBo);

    /**
     * 同步权限
     *
     * @param accessPermissionSyncBo 门禁同步信息
     */
    void syncPermissions(AccessPermissionSyncBo accessPermissionSyncBo);

    /**
     * 修改员工门禁的照片
     *
     * @param accessPersonFaceUpdateBo 门禁人员授权信息
     *
     */
    void updateAccessImg(AccessPersonFaceUpdateBo accessPersonFaceUpdateBo);

    /**
     * 绑定门禁卡
     *
     * @param cardBo 门禁卡授权信息
     */
    void cardBinding(AccessCardBindingBo cardBo);

    /**
     * 门禁卡解绑
     *
     * @param cardBo 门禁卡授权信息
     */
    void cardUnbind(AccessCardUnBindingBo cardBo);

    /**
     * 查询门禁设备通道
     *
     * @param accessDeviceQueryBo 门禁通道查询对象
     * @return List<AccessFetchDeviceInfoBo> 门禁设备通道信息
     */
    List<AccessFetchInfoBo> findAllDeviceChannelList(AccessDeviceQueryBo accessDeviceQueryBo);

    /**
     * 远程开门
     *
     * @param accessOpenDoorBo 远程开门请求参数
     */
    void openDoor(AccessOpenBo accessOpenDoorBo);
}
