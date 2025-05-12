package info.zhihui.idevice.web.access.biz;

import info.zhihui.idevice.common.utils.FileUtil;
import info.zhihui.idevice.core.module.common.service.DeviceModuleContext;
import info.zhihui.idevice.core.module.concrete.access.bo.AccessFetchInfoBo;
import info.zhihui.idevice.core.module.concrete.access.bo.AccessPersonFaceUpdateBo;
import info.zhihui.idevice.core.module.concrete.access.service.AccessControl;
import info.zhihui.idevice.web.access.mapstruct.AccessWebMapper;
import info.zhihui.idevice.web.access.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccessBizService {

    private final AccessWebMapper accessWebMapper;
    private final DeviceModuleContext deviceModuleContext;

    private AccessControl getAccessControl(Integer areaId) {
        return deviceModuleContext.getService(AccessControl.class, areaId);
    }

    public void grantAccess(AccessPersonAuthVo accessPersonAuthVo) {
        AccessControl accessControl = getAccessControl(accessPersonAuthVo.getLocalPerson().getAreaId());
        accessControl.grantAccess(accessWebMapper.accessPersonAuthVoToBo(accessPersonAuthVo));
    }

    public void revokeAccess(AccessPersonRevokeVo accessPersonRevokeVo) {
        AccessControl accessControl = getAccessControl(accessPersonRevokeVo.getLocalPerson().getAreaId());
        accessControl.revokeAccess(accessWebMapper.accessPersonRevokeVoToBo(accessPersonRevokeVo));
    }

    public void syncPermissions(AccessPermissionSyncVo accessPermissionSyncVo) {
        AccessControl accessControl = getAccessControl(accessPermissionSyncVo.getAreaId());
        accessControl.syncPermissions(accessWebMapper.accessPermissionSyncVoToBo(accessPermissionSyncVo));
    }

    public void updateAccessImg(AccessPersonFaceUpdateVo accessPersonFaceUpdateVo) {
        AccessControl accessControl = getAccessControl(accessPersonFaceUpdateVo.getLocalPerson().getAreaId());
        AccessPersonFaceUpdateBo updateBo = accessWebMapper.accessPersonFaceUpdateVoToBo(accessPersonFaceUpdateVo);
        updateBo.setFaceImageResource(FileUtil.buildFileResource(accessPersonFaceUpdateVo.getFaceImageResource()));
        accessControl.updateAccessImg(updateBo);
    }

    public void cardBinding(AccessCardBindingVo cardVo) {
        AccessControl accessControl = getAccessControl(cardVo.getLocalPerson().getAreaId());
        accessControl.cardBinding(accessWebMapper.accessCardBindingVoToBo(cardVo));
    }

    public void cardUnbind(AccessCardUnBindingVo cardVo) {
        AccessControl accessControl = getAccessControl(cardVo.getLocalPerson().getAreaId());
        accessControl.cardUnbind(accessWebMapper.accessCardUnBindingVoToBo(cardVo));
    }

    public List<AccessFetchInfoVo> findAllDeviceChannelList(AccessDeviceQueryVo accessDeviceQueryVo) {
        AccessControl accessControl = getAccessControl(accessDeviceQueryVo.getAreaId());
        List<AccessFetchInfoBo> res = accessControl.findAllDeviceChannelList(accessWebMapper.accessDeviceQueryVoToBo(accessDeviceQueryVo));

        return res.stream()
                .map(accessWebMapper::accessFetchInfoBoToVo)
                .collect(Collectors.toList());
    }

    public void openDoor(AccessOpenVo accessOpenVo) {
        AccessControl accessControl = getAccessControl(accessOpenVo.getAreaId());
        accessControl.openDoor(accessWebMapper.accessOpenVoToBo(accessOpenVo));
    }
}