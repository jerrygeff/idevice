package info.zhihui.idevice.web.person.mapstruct;

import info.zhihui.idevice.core.module.concrete.foundation.bo.LocalPersonBo;
import info.zhihui.idevice.core.module.concrete.foundation.bo.PersonAddBo;
import info.zhihui.idevice.core.module.concrete.foundation.bo.PersonFaceUpdateBo;
import info.zhihui.idevice.core.module.concrete.foundation.enums.CommonCertificateTypeEnum;
import info.zhihui.idevice.web.person.vo.LocalPersonVo;
import info.zhihui.idevice.web.person.vo.PersonAddVo;
import info.zhihui.idevice.web.person.vo.PersonFaceUpdateVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * 人员Web层对象映射器
 */
@Mapper(componentModel = "spring")
public interface PersonWebMapper {

    PersonWebMapper INSTANCE = Mappers.getMapper(PersonWebMapper.class);

    /**
     * 本地人员VO转BO
     */
    LocalPersonBo localPersonVoToBo(LocalPersonVo vo);


    /**
     * 人员添加VO转BO
     */
    @Mapping(source = "localPerson", target = "localPersonBo")
    @Mapping(source = "certificateType", target = "certificateType", qualifiedByName = "certificateTypeIntToEnum")
    @Mapping(target = "faceImageResource", ignore = true)
    PersonAddBo personAddVoToBo(PersonAddVo vo);

    /**
     * 人员头像更新VO转BO
     */
    @Mapping(source = "localPerson", target = "localPersonBo")
    @Mapping(target = "faceImageResource", ignore = true)
    PersonFaceUpdateBo personFaceUpdateVoToBo(PersonFaceUpdateVo vo);

    /**
     * 将certificateType整数转换为枚举
     */
    @Named("certificateTypeIntToEnum")
    default CommonCertificateTypeEnum certificateTypeIntToEnum(Integer certificateType) {
        if (certificateType == null) {
            return null;
        }
        for (CommonCertificateTypeEnum type : CommonCertificateTypeEnum.values()) {
            if (type.getCode().equals(certificateType)) {
                return type;
            }
        }
        return CommonCertificateTypeEnum.OTHER;
    }
}