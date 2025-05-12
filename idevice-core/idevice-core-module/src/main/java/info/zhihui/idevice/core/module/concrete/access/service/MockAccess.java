package info.zhihui.idevice.core.module.concrete.access.service;

import info.zhihui.idevice.core.module.concrete.access.bo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author jerryge
 */
@Service
@Slf4j
public class MockAccess implements AccessControl {
    @Override
    public void grantAccess(AccessPersonAuthBo accessPersonAuthBo) {
        log.info("mock access grant access, param: {}", accessPersonAuthBo);
    }

    @Override
    public void revokeAccess(AccessPersonRevokeBo accessPersonRevokeBo) {
        log.info("mock access revoke access, param: {}", accessPersonRevokeBo);
    }

    @Override
    public void syncPermissions(AccessPermissionSyncBo accessPermissionSyncBo) {
        log.info("mock access sync permissions, param: {}", accessPermissionSyncBo);
    }

    @Override
    public void updateAccessImg(AccessPersonFaceUpdateBo accessPersonFaceUpdateBo) {
        log.info("mock access update face image, param: {}", accessPersonFaceUpdateBo);
    }

    @Override
    public void cardBinding(AccessCardBindingBo cardBo) {
        log.info("mock access card binding, param: {}", cardBo);
    }

    @Override
    public void cardUnbind(AccessCardUnBindingBo cardBo) {
        log.info("mock access card unbind, param: {}", cardBo);
    }

    @Override
    public List<AccessFetchInfoBo> findAllDeviceChannelList(AccessDeviceQueryBo accessDeviceQueryBo) {
        List<AccessFetchInfoBo> accessList = new ArrayList<>();

        // 模拟三个不同类型的门禁设备
        accessList.add(createMockAccess("acc001", "前门门禁", true, "NVR001", 1, "org001"));
        accessList.add(createMockAccess("acc002", "后门门禁", false, "NVR001", 2, "org001"));
        accessList.add(createMockAccess("acc003", "侧门门禁", true, "NVR002", 1, "org002"));

        return accessList;
    }

    @Override
    public void openDoor(AccessOpenBo accessOpenDoorBo) {
        log.info("mock access open door, param: {}", accessOpenDoorBo);
    }

    /**
     * 创建模拟门禁设备信息
     */
    private AccessFetchInfoBo createMockAccess(String code, String name, Boolean isOnline,
                                             String parentCode, Integer channelSeq,
                                             String ownerCode) {
        AccessFetchInfoBo access = new AccessFetchInfoBo();
        // 设置基本信息
        access.setThirdPartyDeviceUniqueCode(code);
        access.setThirdPartyDeviceName(name);
        access.setOnlineStatus(isOnline ? 1 : 0);

        // 设置门禁特有信息
        access.setDeviceParentCode(parentCode);
        access.setThirdPartyChannelNo(String.valueOf(channelSeq));
        access.setOwnerCode(ownerCode);
        access.setDeviceCapacity(Arrays.asList("access", "card", "face"));
        access.setRegionPath(Arrays.asList("region1", "region2"));
        access.setRegionPathName(Arrays.asList("区域1", "区域2"));

        return access;
    }
}