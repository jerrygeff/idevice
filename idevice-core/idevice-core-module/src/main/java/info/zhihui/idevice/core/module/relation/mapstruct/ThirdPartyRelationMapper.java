package info.zhihui.idevice.core.module.relation.mapstruct;

import info.zhihui.idevice.core.module.relation.bo.ThirdPartyRelationBo;
import info.zhihui.idevice.core.module.relation.entity.ThirdPartyRelationEntity;
import info.zhihui.idevice.core.module.relation.qo.ThirdPartyRelationQuery;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author jerryge
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ThirdPartyRelationMapper {
    ThirdPartyRelationEntity toEntity(ThirdPartyRelationBo thirdPartyRelationBo);

    ThirdPartyRelationEntity queryToEntity(ThirdPartyRelationQuery thirdPartyRelationQuery);

    ThirdPartyRelationBo toBo(ThirdPartyRelationEntity thirdPartyRelationEntity);

}
