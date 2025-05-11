package info.zhihui.idevice.web.person.biz;

import info.zhihui.idevice.common.dto.FileResource;
import info.zhihui.idevice.common.utils.FileUtil;
import info.zhihui.idevice.common.vo.FileResourceVo;
import info.zhihui.idevice.core.module.common.service.DeviceModuleContext;
import info.zhihui.idevice.core.module.concrete.foundation.bo.LocalPersonBo;
import info.zhihui.idevice.core.module.concrete.foundation.bo.PersonAddBo;
import info.zhihui.idevice.core.module.concrete.foundation.bo.PersonFaceUpdateBo;
import info.zhihui.idevice.core.module.concrete.foundation.service.Person;
import info.zhihui.idevice.web.person.mapstruct.PersonWebMapper;
import info.zhihui.idevice.web.person.vo.LocalPersonVo;
import info.zhihui.idevice.web.person.vo.PersonAddVo;
import info.zhihui.idevice.web.person.vo.PersonDeleteVo;
import info.zhihui.idevice.web.person.vo.PersonFaceUpdateVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonBizServiceTest {

    @InjectMocks
    private PersonBizService personBizService;

    @Mock
    private PersonWebMapper personWebMapper;

    @Mock
    private DeviceModuleContext deviceModuleContext;

    @Mock
    private Person personService;

    private static final Integer AREA_ID = 1;
    private static final String THIRD_PARTY_ID = "third-party-id-123";

    @BeforeEach
    public void setup() {
        when(deviceModuleContext.getService(eq(Person.class), any(Integer.class)))
                .thenReturn(personService);
    }

    @Test
    public void testAddLocalPersonToThirdParty() {
        // 准备测试数据
        PersonAddVo personAddVo = new PersonAddVo();
        LocalPersonVo localPersonVo = new LocalPersonVo();
        localPersonVo.setAreaId(AREA_ID);
        personAddVo.setLocalPerson(localPersonVo);

        // 创建合适的 FileResourceVo 对象
        FileResourceVo fileResourceVo = new FileResourceVo()
                .setBase64Content("face-base64-data")
                .setContentType("image/jpeg");
        personAddVo.setFaceImageResource(fileResourceVo);

        PersonAddBo personAddBo = new PersonAddBo();
        when(personWebMapper.personAddVoToBo(any())).thenReturn(personAddBo);
        when(personService.addLocalPersonToThirdParty(any())).thenReturn(THIRD_PARTY_ID);

        // 使用 try-with-resources 模式来模拟静态方法
        try (var fileUtilMock = Mockito.mockStatic(FileUtil.class)) {
            // 创建一个适当的 FileResource 返回值
            FileResource fileResource = new FileResource()
                    .setContentType("image/jpeg")
                    .setFileSize(1024L)
                    .setInputStream(new ByteArrayInputStream("processed-face-data".getBytes()));

            fileUtilMock.when(() -> FileUtil.buildFileResource(any(FileResourceVo.class))).thenReturn(fileResource);

            // 执行方法
            String result = personBizService.addLocalPersonToThirdParty(personAddVo);

            // 验证交互
            verify(deviceModuleContext).getService(Person.class, AREA_ID);
            verify(personWebMapper).personAddVoToBo(personAddVo);
            verify(personService).addLocalPersonToThirdParty(personAddBo);
            fileUtilMock.verify(() -> FileUtil.buildFileResource(fileResourceVo));

            // 验证结果
            assertEquals(THIRD_PARTY_ID, result);
        }
    }

    @Test
    public void testDeleteThirdPartyPerson() {
        // 准备测试数据
        PersonDeleteVo personDeleteVo = new PersonDeleteVo();
        LocalPersonVo localPersonVo = new LocalPersonVo();
        localPersonVo.setAreaId(AREA_ID);
        personDeleteVo.setLocalPerson(localPersonVo);

        LocalPersonBo localPersonBo = new LocalPersonBo();
        when(personWebMapper.localPersonVoToBo(any())).thenReturn(localPersonBo);

        // 执行方法
        personBizService.deleteThirdPartyPerson(personDeleteVo);

        // 验证交互
        verify(deviceModuleContext).getService(Person.class, AREA_ID);
        verify(personWebMapper).localPersonVoToBo(localPersonVo);
        verify(personService).deleteThirdPartyPerson(localPersonBo);
    }

    @Test
    public void testUpdatePersonFace() {
        // 准备测试数据
        PersonFaceUpdateVo personFaceUpdateVo = new PersonFaceUpdateVo();
        LocalPersonVo localPersonVo = new LocalPersonVo();
        localPersonVo.setAreaId(AREA_ID);
        personFaceUpdateVo.setLocalPerson(localPersonVo);

        // 创建合适的 FileResourceVo 对象
        FileResourceVo fileResourceVo = new FileResourceVo()
                .setBase64Content("face-base64-data")
                .setContentType("image/jpeg");
        personFaceUpdateVo.setFaceImageResource(fileResourceVo);

        PersonFaceUpdateBo personFaceUpdateBo = new PersonFaceUpdateBo();
        when(personWebMapper.personFaceUpdateVoToBo(any())).thenReturn(personFaceUpdateBo);

        // 使用 try-with-resources 模式来模拟静态方法
        try (var fileUtilMock = Mockito.mockStatic(FileUtil.class)) {
            // 创建一个适当的 FileResource 返回值
            FileResource fileResource = new FileResource()
                    .setContentType("image/jpeg")
                    .setFileSize(1024L)
                    .setInputStream(new ByteArrayInputStream("processed-face-data".getBytes()));

            fileUtilMock.when(() -> FileUtil.buildFileResource(any(FileResourceVo.class))).thenReturn(fileResource);

            // 执行方法
            personBizService.updatePersonFace(personFaceUpdateVo);

            // 验证交互
            verify(deviceModuleContext).getService(Person.class, AREA_ID);
            verify(personWebMapper).personFaceUpdateVoToBo(personFaceUpdateVo);
            verify(personService).updatePersonFace(personFaceUpdateBo);
            fileUtilMock.verify(() -> FileUtil.buildFileResource(fileResourceVo));
        }
    }
}