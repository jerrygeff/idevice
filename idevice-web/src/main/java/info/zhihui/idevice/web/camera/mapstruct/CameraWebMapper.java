package info.zhihui.idevice.web.camera.mapstruct;

import info.zhihui.idevice.core.module.concrete.camera.bo.*;
import info.zhihui.idevice.core.module.concrete.camera.enums.CameraDirectionEnum;
import info.zhihui.idevice.core.module.concrete.camera.enums.CameraPlayProtocolEnum;
import info.zhihui.idevice.core.module.concrete.camera.enums.CameraStreamTypeEnum;
import info.zhihui.idevice.web.camera.vo.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * 摄像头Web层对象映射器
 */
@Mapper(componentModel = "spring")
public interface CameraWebMapper {

    CameraWebMapper INSTANCE = Mappers.getMapper(CameraWebMapper.class);

    /**
     * 摄像头基础请求VO转BO
     */
    CameraQueryBo cameraRequestBaseVoToBo(CameraQueryVo vo);

    /**
     * 摄像头实时取流VO转BO
     */
    @Mapping(source = "protocol", target = "protocol", qualifiedByName = "protocolStringToEnum")
    @Mapping(source = "streamType", target = "streamType", qualifiedByName = "streamTypeIntToEnum")
    CameraRealtimeQueryBo cameraRealtimeQueryVoToBo(CameraRealtimeQueryVo vo);

    /**
     * 摄像头回放取流VO转BO
     */
    @Mapping(source = "protocol", target = "protocol", qualifiedByName = "protocolStringToEnum")
    @Mapping(source = "streamType", target = "streamType", qualifiedByName = "streamTypeIntToEnum")
    CameraPlaybackQueryBo cameraPlaybackQueryVoToBo(CameraPlaybackQueryVo vo);

    /**
     * 摄像头操作VO转BO
     */
    @Mapping(source = "operation", target = "operation", qualifiedByName = "directionStringToEnum")
    CameraOperateBo cameraOperateVoToBo(CameraOperateVo vo);

    /**
     * 摄像头实时取流BO转VO
     */
    CameraLinkVo cameraRealtimeLinkBoToVo(CameraRealtimeLinkBo bo);

    /**
     * 摄像头回放取流BO转VO
     */
    @Mapping(source = "playbackUrl", target = "url")
    CameraLinkVo cameraPlaybackLinkBoToVo(CameraPlaybackLinkBo bo);

    /**
     * 摄像头信息BO转VO
     */
    @Mapping(source = "thirdPartyDeviceUniqueCode", target = "deviceCode")
    @Mapping(source = "thirdPartyDeviceName", target = "deviceName")
    CameraFetchInfoVo cameraFetchInfoBoToVo(CameraFetchInfoBo bo);

    /**
     * 将protocol字符串转换为枚举
     */
    @Named("protocolStringToEnum")
    default CameraPlayProtocolEnum protocolStringToEnum(String protocol) {
        if (protocol == null) {
            return null;
        }
        return CameraPlayProtocolEnum.valueOf(protocol.toUpperCase());
    }

    /**
     * 将streamType整数转换为枚举
     */
    @Named("streamTypeIntToEnum")
    default CameraStreamTypeEnum streamTypeIntToEnum(Integer streamType) {
        if (streamType == null) {
            return null;
        }
        return switch (streamType) {
            case 1 -> CameraStreamTypeEnum.MAIN;
            case 2 -> CameraStreamTypeEnum.SUB;
            case 3 -> CameraStreamTypeEnum.THIRD;
            default -> null;
        };
    }

    /**
     * 将direction字符串转换为枚举
     */
    @Named("directionStringToEnum")
    default CameraDirectionEnum directionStringToEnum(String direction) {
        if (direction == null) {
            return null;
        }
        return CameraDirectionEnum.valueOf(direction);
    }
}