package info.zhiuhi.idevice.feature.data.service;

import info.zhihui.idevice.core.module.common.enums.DeviceTypeEnum;
import info.zhiuhi.idevice.feature.data.bo.DeviceBasicBo;
import info.zhiuhi.idevice.feature.data.bo.DeviceCommonQuery;
import info.zhiuhi.idevice.feature.data.bo.DeviceStateStatisticsBo;

import java.util.List;

/**
 * 设备本地信息获取/处理接口
 * 实现这个接口，可以获得feature特性
 *
 * @author jerryge
 */

public interface LocalCommonDeviceDataService {
    /**
     * 获取设备类型枚举
     * @return 设备类型枚举
     */
    DeviceTypeEnum getDeviceTypeEnum();

    /**
     * 查询设备列表
     * @param query 查询条件
     * @return 设备列表
     */
    List<DeviceBasicBo> findCommonDeviceList(DeviceCommonQuery query);

    /**
     * 根据设备id获取设备信息
     * @param id 设备id
     * @return 设备信息
     */
    DeviceBasicBo getCommonDeviceById(String id);

    /**
     * 设备状态统计
     *
     * @return 设备统计对象
     */
    DeviceStateStatisticsBo getDeviceStatus();

    /**
     * 批量标记设备为异常状态
     *
     * @param ids 设备id列表
     */
    void batchMarkDevicesAsAbnormal(List<Integer> ids);

    /**
     * 删除异常设备
     * @param id 设备id
     */
    void deleteAbnormalDevice(Integer id);

}
