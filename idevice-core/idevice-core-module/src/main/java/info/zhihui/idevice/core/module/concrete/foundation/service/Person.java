package info.zhihui.idevice.core.module.concrete.foundation.service;

import info.zhihui.idevice.common.exception.BusinessRuntimeException;
import info.zhihui.idevice.core.module.common.service.CommonDeviceModule;
import info.zhihui.idevice.core.module.concrete.foundation.bo.LocalPersonBo;
import info.zhihui.idevice.core.module.concrete.foundation.bo.PersonAddBo;
import info.zhihui.idevice.core.module.concrete.foundation.bo.PersonFaceUpdateBo;
import info.zhihui.idevice.core.module.common.enums.ModuleEnum;
import info.zhihui.idevice.core.module.event.bo.EventBo;
import info.zhihui.idevice.core.module.event.service.parser.EventParser;

/**
 * 人员模块
 */
public interface Person extends CommonDeviceModule {

    /**
     * 获取模块名称
     */
    default String getModuleName() {
        return ModuleEnum.PERSON.getModuleName();
    }

    /**
     * 获取事件解析器
     *
     * @param eventTypeClass 事件类型
     * @return E
     * @param <T> 事件对象，标识事件类型
     * @param <E> 解析器实的类型
     */
    default <T extends EventBo, E extends EventParser<T>> E getEventParser(Class<T> eventTypeClass) {
        throw new BusinessRuntimeException("解析器未配置");
    }

    String getThirdPartyPersonId(LocalPersonBo localPersonBo);

    /**
     * 将本地系统的人员映射到第三方系统，并返回第三方系统的 PersonId
     *
     * @param personAddBo 三方人员需要的信息
     */
    String addLocalPersonToThirdParty(PersonAddBo personAddBo);

    /**
     * 删除第三方人员
     * @param localPersonBo 本地人员信息
     */
    void deleteThirdPartyPerson(LocalPersonBo localPersonBo);

    /**
     * 更新人脸信息
     * @param personFaceUpdateBo 人脸信息更新对象
     */
    void updatePersonFace(PersonFaceUpdateBo personFaceUpdateBo);
}
