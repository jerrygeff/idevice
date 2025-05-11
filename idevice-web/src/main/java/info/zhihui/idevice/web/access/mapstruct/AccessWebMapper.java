package info.zhihui.idevice.web.access.mapstruct;

import info.zhihui.idevice.core.module.concrete.access.bo.*;
import info.zhihui.idevice.core.module.concrete.foundation.bo.LocalPersonBo;
import info.zhihui.idevice.web.access.vo.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 访问控制Web层对象映射器
 */
@Mapper(componentModel = "spring")
public interface AccessWebMapper {

    AccessWebMapper INSTANCE = Mappers.getMapper(AccessWebMapper.class);

    /**
     * 门禁人员授权VO转BO
     */
    @Mapping(source = "localPerson", target = "localPersonBo")
    AccessPersonAuthBo accessPersonAuthVoToBo(AccessPersonAuthVo vo);

    /**
     * 门禁人员撤销授权VO转BO
     */
    @Mapping(source = "localPerson", target = "localPersonBo")
    AccessPersonRevokeBo accessPersonRevokeVoToBo(AccessPersonRevokeVo vo);

    /**
     * 门禁同步信息VO转BO
     */
    AccessPermissionSyncBo accessPermissionSyncVoToBo(AccessPermissionSyncVo vo);

    /**
     * 门禁人员照片更新VO转BO
     */
    @Mapping(source = "localPerson", target = "localPersonBo")
    @Mapping(target = "faceImageResource", ignore = true)
    AccessPersonFaceUpdateBo accessPersonFaceUpdateVoToBo(AccessPersonFaceUpdateVo vo);

    /**
     * 门禁卡绑定VO转BO
     */
    @Mapping(source = "localPerson", target = "localPersonBo")
    AccessCardBindingBo accessCardBindingVoToBo(AccessCardBindingVo vo);

    /**
     * 门禁卡解绑VO转BO
     */
    @Mapping(source = "localPerson", target = "localPersonBo")
    @Mapping(source = "cardNumber", target = "cardNumber")
    AccessCardUnBindingBo accessCardUnBindingVoToBo(AccessCardUnBindingVo vo);

    /**
     * 门禁设备查询VO转BO
     */
    AccessDeviceQueryBo accessDeviceQueryVoToBo(AccessDeviceQueryVo vo);

    /**
     * 远程开门VO转BO
     */
    AccessOpenBo accessOpenVoToBo(AccessOpenVo vo);

    /**
     * 门禁设备通道信息BO转VO
     */
    @Mapping(source = "thirdPartyDeviceUniqueCode", target = "deviceCode")
    @Mapping(source = "thirdPartyDeviceName", target = "deviceName")
    @Mapping(source = "thirdPartyChannelNo", target = "channelNo")
    AccessFetchInfoVo accessFetchInfoBoToVo(AccessFetchInfoBo bo);

    /**
     * 本地人员VO转BO
     */
    LocalPersonBo localPersonVoToBo(LocalPersonVo vo);
}