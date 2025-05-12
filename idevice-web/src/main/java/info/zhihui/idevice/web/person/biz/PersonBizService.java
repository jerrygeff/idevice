package info.zhihui.idevice.web.person.biz;

import info.zhihui.idevice.common.utils.FileUtil;
import info.zhihui.idevice.core.module.common.service.DeviceModuleContext;
import info.zhihui.idevice.core.module.concrete.foundation.bo.PersonAddBo;
import info.zhihui.idevice.core.module.concrete.foundation.bo.PersonFaceUpdateBo;
import info.zhihui.idevice.core.module.concrete.foundation.service.Person;
import info.zhihui.idevice.web.person.mapstruct.PersonWebMapper;
import info.zhihui.idevice.web.person.vo.PersonAddVo;
import info.zhihui.idevice.web.person.vo.PersonDeleteVo;
import info.zhihui.idevice.web.person.vo.PersonFaceUpdateVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 人员业务服务
 */
@Service
@RequiredArgsConstructor
public class PersonBizService {

    private final PersonWebMapper personWebMapper;
    private final DeviceModuleContext deviceModuleContext;

    private Person getPersonService(Integer areaId) {
        return deviceModuleContext.getService(Person.class, areaId);
    }


    /**
     * 添加本地人员到第三方系统
     * 添加人员时，人脸信息可以为空
     */
    public String addLocalPersonToThirdParty(PersonAddVo personAddVo) {
        Person personService = getPersonService(personAddVo.getLocalPerson().getAreaId());

        PersonAddBo personAddBo = personWebMapper.personAddVoToBo(personAddVo);
        if (personAddVo.getFaceImageResource() != null) {
            personAddBo.setFaceImageResource(List.of(FileUtil.buildFileResource(personAddVo.getFaceImageResource())));
        }
        return personService.addLocalPersonToThirdParty(personAddBo);
    }

    /**
     * 删除第三方人员
     */
    public void deleteThirdPartyPerson(PersonDeleteVo personDeleteVo) {
        Person personService = getPersonService(personDeleteVo.getLocalPerson().getAreaId());
        personService.deleteThirdPartyPerson(personWebMapper.localPersonVoToBo(personDeleteVo.getLocalPerson()));
    }

    /**
     * 更新人脸信息
     */
    public void updatePersonFace(PersonFaceUpdateVo personFaceUpdateVo) {
        Person personService = getPersonService(personFaceUpdateVo.getLocalPerson().getAreaId());
        PersonFaceUpdateBo personFaceUpdateBo = personWebMapper.personFaceUpdateVoToBo(personFaceUpdateVo);
        personFaceUpdateBo.setFaceImageResource(FileUtil.buildFileResource(personFaceUpdateVo.getFaceImageResource()));

        personService.updatePersonFace(personFaceUpdateBo);
    }

}