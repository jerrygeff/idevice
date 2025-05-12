package info.zhihui.idevice.core.module.concrete.foundation.service;

import info.zhihui.idevice.core.module.concrete.foundation.bo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author jerryge
 */
@Service
@Slf4j
public class MockPerson implements Person {
    @Override
    public String getThirdPartyPersonId(LocalPersonBo localPersonBo) {
        log.info("mock person get third party person id, param: {}", localPersonBo);
        return "mock_person_" + UUID.randomUUID().toString().substring(0, 8);
    }

    @Override
    public String addLocalPersonToThirdParty(PersonAddBo personAddBo) {
        log.info("mock person add local person to third party, param: {}", personAddBo);
        return "mock_person_" + UUID.randomUUID().toString().substring(0, 8);
    }

    @Override
    public void deleteThirdPartyPerson(LocalPersonBo localPersonBo) {
        log.info("mock person delete third party person, param: {}", localPersonBo);
    }

    @Override
    public void updatePersonFace(PersonFaceUpdateBo personFaceUpdateBo) {
        log.info("mock person update face, param: {}", personFaceUpdateBo);
    }
} 