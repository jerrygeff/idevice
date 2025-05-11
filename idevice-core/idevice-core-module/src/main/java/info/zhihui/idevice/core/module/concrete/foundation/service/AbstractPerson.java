package info.zhihui.idevice.core.module.concrete.foundation.service;

import info.zhihui.idevice.core.module.concrete.foundation.bo.LocalPersonBo;
import info.zhihui.idevice.core.module.relation.bo.ThirdPartyRelationBo;
import info.zhihui.idevice.core.module.relation.qo.ThirdPartyRelationQuery;
import info.zhihui.idevice.core.module.relation.service.ThirdPartyRelationService;
import lombok.RequiredArgsConstructor;

/**
 * @author jerryge
 */
@RequiredArgsConstructor
public abstract class AbstractPerson implements Person {
    protected final ThirdPartyRelationService thirdPartyRelationService;

    public String getThirdPartyPersonId(LocalPersonBo localPersonBo) {
        ThirdPartyRelationQuery query = new ThirdPartyRelationQuery()
                .setAreaId(localPersonBo.getAreaId())
                .setThirdPartyModuleName(this.getModuleName())
                .setLocalModuleName(localPersonBo.getLocalModuleName())
                .setLocalBusinessKey(localPersonBo.getLocalPersonId().toString());
        ThirdPartyRelationBo relation = thirdPartyRelationService.getThirdPartyRelation(query);

        return relation.getThirdPartyBusinessKey();
    }
}
