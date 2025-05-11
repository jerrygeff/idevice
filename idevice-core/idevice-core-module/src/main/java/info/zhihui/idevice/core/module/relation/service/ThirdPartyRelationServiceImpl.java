package info.zhihui.idevice.core.module.relation.service;

import info.zhihui.idevice.common.exception.NotFoundException;
import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.components.lock.core.LockTemplate;
import info.zhihui.idevice.core.module.relation.bo.ThirdPartyRelationBo;
import info.zhihui.idevice.core.module.relation.entity.ThirdPartyRelationEntity;
import info.zhihui.idevice.core.module.relation.mapstruct.ThirdPartyRelationMapper;
import info.zhihui.idevice.core.module.relation.qo.ThirdPartyRelationQuery;
import info.zhihui.idevice.core.module.relation.repository.ThirdPartyRelationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;

/**
 * @author jerryge
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ThirdPartyRelationServiceImpl implements ThirdPartyRelationService {

    private final ThirdPartyRelationMapper thirdPartyRelationMapper;
    private final ThirdPartyRelationRepository thirdPartyRelationRepository;
    private final LockTemplate lockTemplate;

    private static final String LOCK_NAME = "thirdPartyRelation";

    @Override
    public void add(ThirdPartyRelationBo thirdPartyRelationBo) {
        Lock lock = lockTemplate.getLock(LOCK_NAME);
        try {
            lock.lock();
            thirdPartyRelationBo.setId(null);
            ThirdPartyRelationEntity entity = thirdPartyRelationMapper.toEntity(thirdPartyRelationBo);

            thirdPartyRelationRepository.insert(entity);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void delete(ThirdPartyRelationQuery thirdPartyRelationQuery) {
        checkAreaNotNull(thirdPartyRelationQuery);
        ThirdPartyRelationEntity entity = thirdPartyRelationMapper.queryToEntity(thirdPartyRelationQuery);

        thirdPartyRelationRepository.deleteUnique(entity);
    }

    @Override
    public ThirdPartyRelationBo getThirdPartyRelation(ThirdPartyRelationQuery thirdPartyRelationQuery) throws NotFoundException {
        checkAreaNotNull(thirdPartyRelationQuery);
        ThirdPartyRelationEntity entity = thirdPartyRelationMapper.queryToEntity(thirdPartyRelationQuery);

        ThirdPartyRelationEntity res = thirdPartyRelationRepository.getRelation(entity);
        if (res == null) {
            log.warn("没有找到关联数据，查询条件：{}", thirdPartyRelationQuery);
            throw new NotFoundException("没有找到对应的关联关系");
        }

        return thirdPartyRelationMapper.toBo(res);
    }

    @Override
    public ThirdPartyRelationBo getRelationIncludeDeleted(ThirdPartyRelationQuery thirdPartyRelationQuery) {
        checkAreaNotNull(thirdPartyRelationQuery);
        ThirdPartyRelationEntity entity = thirdPartyRelationMapper.queryToEntity(thirdPartyRelationQuery);

        ThirdPartyRelationEntity res = thirdPartyRelationRepository.getRelationIncludeDeleted(entity);
        return thirdPartyRelationMapper.toBo(res);
    }

    private void checkAreaNotNull(ThirdPartyRelationQuery query) {
        if (query == null) {
            throw new ParamException("查询参数不能为null");
        }

        if (query.getAreaId() == null) {
            throw new ParamException("区域id不能为null");
        }
    }
}
