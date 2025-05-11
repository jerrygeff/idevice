package info.zhihui.idevice.web.access.biz;

import info.zhihui.idevice.common.dto.FileResource;
import info.zhihui.idevice.common.utils.FileUtil;
import info.zhihui.idevice.common.vo.FileResourceVo;
import info.zhihui.idevice.core.module.common.service.DeviceModuleContext;
import info.zhihui.idevice.core.module.concrete.access.bo.*;
import info.zhihui.idevice.core.module.concrete.access.service.AccessControl;
import info.zhihui.idevice.web.access.mapstruct.AccessWebMapper;
import info.zhihui.idevice.web.access.vo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccessBizServiceTest {

    @InjectMocks
    private AccessBizService accessBizService;

    @Mock
    private AccessWebMapper accessWebMapper;

    @Mock
    private DeviceModuleContext deviceModuleContext;

    @Mock
    private AccessControl accessControl;

    private static final Integer AREA_ID = 1;

    @BeforeEach
    public void setup() {
        when(deviceModuleContext.getService(eq(AccessControl.class), any(Integer.class)))
                .thenReturn(accessControl);
    }

    @Test
    public void testGrantAccess() {
        // 准备测试数据
        AccessPersonAuthVo vo = new AccessPersonAuthVo();
        LocalPersonVo localPersonVo = new LocalPersonVo();
        localPersonVo.setAreaId(AREA_ID);
        vo.setLocalPerson(localPersonVo);

        AccessPersonAuthBo bo = new AccessPersonAuthBo();
        when(accessWebMapper.accessPersonAuthVoToBo(any())).thenReturn(bo);

        // 执行方法
        accessBizService.grantAccess(vo);

        // 验证交互
        verify(deviceModuleContext).getService(AccessControl.class, AREA_ID);
        verify(accessWebMapper).accessPersonAuthVoToBo(vo);
        verify(accessControl).grantAccess(bo);
    }

    @Test
    public void testRevokeAccess() {
        // 准备测试数据
        AccessPersonRevokeVo vo = new AccessPersonRevokeVo();
        LocalPersonVo localPersonVo = new LocalPersonVo();
        localPersonVo.setAreaId(AREA_ID);
        vo.setLocalPerson(localPersonVo);

        AccessPersonRevokeBo bo = new AccessPersonRevokeBo();
        when(accessWebMapper.accessPersonRevokeVoToBo(any())).thenReturn(bo);

        // 执行方法
        accessBizService.revokeAccess(vo);

        // 验证交互
        verify(deviceModuleContext).getService(AccessControl.class, AREA_ID);
        verify(accessWebMapper).accessPersonRevokeVoToBo(vo);
        verify(accessControl).revokeAccess(bo);
    }

    @Test
    public void testSyncPermissions() {
        // 准备测试数据
        AccessPermissionSyncVo vo = new AccessPermissionSyncVo();
        vo.setAreaId(AREA_ID);

        AccessPermissionSyncBo bo = new AccessPermissionSyncBo();
        when(accessWebMapper.accessPermissionSyncVoToBo(any())).thenReturn(bo);

        // 执行方法
        accessBizService.syncPermissions(vo);

        // 验证交互
        verify(deviceModuleContext).getService(AccessControl.class, AREA_ID);
        verify(accessWebMapper).accessPermissionSyncVoToBo(vo);
        verify(accessControl).syncPermissions(bo);
    }

    @Test
    public void testUpdateAccessImg() {
        // 准备测试数据
        AccessPersonFaceUpdateVo vo = new AccessPersonFaceUpdateVo();
        LocalPersonVo localPersonVo = new LocalPersonVo();
        localPersonVo.setAreaId(AREA_ID);
        vo.setLocalPerson(localPersonVo);

        // 创建合适的 FileResourceVo 对象
        FileResourceVo fileResourceVo = new FileResourceVo()
                .setBase64Content("image-base64-data")
                .setContentType("image/jpeg");
        vo.setFaceImageResource(fileResourceVo);

        AccessPersonFaceUpdateBo bo = new AccessPersonFaceUpdateBo();
        when(accessWebMapper.accessPersonFaceUpdateVoToBo(any())).thenReturn(bo);

        // Using mockStatic with try-with-resources pattern for static method
        try (var fileUtilMock = Mockito.mockStatic(FileUtil.class)) {
            // 创建一个适当的 FileResource 返回值
            FileResource fileResource = new FileResource()
                    .setContentType("image/jpeg")
                    .setFileSize(1024L)
                    .setInputStream(new ByteArrayInputStream("processed-image-data".getBytes()));

            fileUtilMock.when(() -> FileUtil.buildFileResource(any(FileResourceVo.class))).thenReturn(fileResource);

            // 执行方法
            accessBizService.updateAccessImg(vo);

            // 验证交互
            verify(deviceModuleContext).getService(AccessControl.class, AREA_ID);
            verify(accessWebMapper).accessPersonFaceUpdateVoToBo(vo);
            verify(accessControl).updateAccessImg(bo);
            fileUtilMock.verify(() -> FileUtil.buildFileResource(fileResourceVo));
        }
    }

    @Test
    public void testCardBinding() {
        // 准备测试数据
        AccessCardBindingVo vo = new AccessCardBindingVo();
        LocalPersonVo localPersonVo = new LocalPersonVo();
        localPersonVo.setAreaId(AREA_ID);
        vo.setLocalPerson(localPersonVo);

        AccessCardBindingBo bo = new AccessCardBindingBo();
        when(accessWebMapper.accessCardBindingVoToBo(any())).thenReturn(bo);

        // 执行方法
        accessBizService.cardBinding(vo);

        // 验证交互
        verify(deviceModuleContext).getService(AccessControl.class, AREA_ID);
        verify(accessWebMapper).accessCardBindingVoToBo(vo);
        verify(accessControl).cardBinding(bo);
    }

    @Test
    public void testCardUnbind() {
        // 准备测试数据
        AccessCardUnBindingVo vo = new AccessCardUnBindingVo();
        LocalPersonVo localPersonVo = new LocalPersonVo();
        localPersonVo.setAreaId(AREA_ID);
        vo.setLocalPerson(localPersonVo);

        AccessCardUnBindingBo bo = new AccessCardUnBindingBo();
        when(accessWebMapper.accessCardUnBindingVoToBo(any())).thenReturn(bo);

        // 执行方法
        accessBizService.cardUnbind(vo);

        // 验证交互
        verify(deviceModuleContext).getService(AccessControl.class, AREA_ID);
        verify(accessWebMapper).accessCardUnBindingVoToBo(vo);
        verify(accessControl).cardUnbind(bo);
    }

    @Test
    public void testFindAllDeviceChannelList() {
        // 准备测试数据
        AccessDeviceQueryVo vo = new AccessDeviceQueryVo();
        vo.setAreaId(AREA_ID);

        AccessDeviceQueryBo bo = new AccessDeviceQueryBo();
        when(accessWebMapper.accessDeviceQueryVoToBo(any())).thenReturn(bo);

        AccessFetchInfoBo fetchInfoBo1 = new AccessFetchInfoBo();
        fetchInfoBo1.setThirdPartyDeviceUniqueCode("aaa");
        AccessFetchInfoBo fetchInfoBo2 = new AccessFetchInfoBo();
        fetchInfoBo2.setThirdPartyDeviceUniqueCode("bbb");
        List<AccessFetchInfoBo> boList = List.of(fetchInfoBo1, fetchInfoBo2);
        when(accessControl.findAllDeviceChannelList(any())).thenReturn(boList);

        AccessFetchInfoVo fetchInfoVo1 = new AccessFetchInfoVo();
        fetchInfoVo1.setDeviceParentCode("aaa");
        AccessFetchInfoVo fetchInfoVo2 = new AccessFetchInfoVo();
        fetchInfoVo1.setDeviceParentCode("bbb");
        when(accessWebMapper.accessFetchInfoBoToVo(fetchInfoBo1)).thenReturn(fetchInfoVo1);
        when(accessWebMapper.accessFetchInfoBoToVo(fetchInfoBo2)).thenReturn(fetchInfoVo2);

        // 执行方法
        List<AccessFetchInfoVo> result = accessBizService.findAllDeviceChannelList(vo);

        // 验证交互
        verify(deviceModuleContext).getService(AccessControl.class, AREA_ID);
        verify(accessWebMapper).accessDeviceQueryVoToBo(vo);
        verify(accessControl).findAllDeviceChannelList(bo);
        verify(accessWebMapper).accessFetchInfoBoToVo(fetchInfoBo1);
        verify(accessWebMapper).accessFetchInfoBoToVo(fetchInfoBo2);

        // 验证结果
        assert result.size() == 2;
        assert result.contains(fetchInfoVo1);
        assert result.contains(fetchInfoVo2);
    }

    @Test
    public void testOpenDoor() {
        // 准备测试数据
        AccessOpenVo vo = new AccessOpenVo();
        vo.setAreaId(AREA_ID);

        AccessOpenBo bo = new AccessOpenBo();
        when(accessWebMapper.accessOpenVoToBo(any())).thenReturn(bo);

        // 执行方法
        accessBizService.openDoor(vo);

        // 验证交互
        verify(deviceModuleContext).getService(AccessControl.class, AREA_ID);
        verify(accessWebMapper).accessOpenVoToBo(vo);
        verify(accessControl).openDoor(bo);
    }
}