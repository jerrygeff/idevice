package info.zhihui.idevice.core.module.relation.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import info.zhihui.idevice.core.module.relation.entity.ThirdPartyRelationEntity;
import org.springframework.stereotype.Repository;

/**
 * @author jerryge
 */
@Repository
public interface ThirdPartyRelationRepository extends BaseMapper<ThirdPartyRelationEntity> {
    int deleteUnique(ThirdPartyRelationEntity entity);

    ThirdPartyRelationEntity getRelation(ThirdPartyRelationEntity entity);

    ThirdPartyRelationEntity getRelationIncludeDeleted(ThirdPartyRelationEntity entity);
}
