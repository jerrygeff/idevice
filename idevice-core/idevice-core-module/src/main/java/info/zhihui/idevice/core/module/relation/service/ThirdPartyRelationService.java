package info.zhihui.idevice.core.module.relation.service;

import info.zhihui.idevice.common.exception.NotFoundException;
import info.zhihui.idevice.core.module.relation.bo.ThirdPartyRelationBo;
import info.zhihui.idevice.core.module.relation.qo.ThirdPartyRelationQuery;

/**
 * 处理与第三方平台的关联信息
 *
 * @author jerryge
 */
public interface ThirdPartyRelationService {
    /**
     * 增加第三方关联
     * @param thirdPartyRelationBo 关联信息
     */
    void add(ThirdPartyRelationBo thirdPartyRelationBo);

    /**
     * 删除第三方关联
     * @param thirdPartyRelationQuery 关联信息
     */
    void delete(ThirdPartyRelationQuery thirdPartyRelationQuery);

    /**
     * 查询关联信息
     * @param thirdPartyRelationQuery 关联信息查询条件
     * @return 关联信息
     */
    ThirdPartyRelationBo getThirdPartyRelation(ThirdPartyRelationQuery thirdPartyRelationQuery) throws NotFoundException;

    /**
     * 获取关联信息，包含已删除的
     * @param thirdPartyRelationQuery 关联信息
     * @return 关联信息
     */
    ThirdPartyRelationBo getRelationIncludeDeleted(ThirdPartyRelationQuery thirdPartyRelationQuery);
}
