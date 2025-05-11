package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.access.v2;

import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.core.sdk.hikvision.isecure.constants.ISecureFoundationConstant;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecureJsonRequest;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2.ResourceInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * @author jerryge
 */
@Getter
@Builder(builderClassName = "Builder")
public class ConfigDownloadTaskAddDeviceRequest extends ISecureJsonRequest<ConfigDownloadTaskAddDeviceResponse> {
    private static final String VERSION = "v1";
    private static final String API_URL = String.format(ISecureFoundationConstant.CONFIG_DOWNLOAD_TASK_ADD_DEVICE, VERSION);
    private static final String TASK_ID_ERROR_MSG = "任务ID不能为空";

    /**
     * 任务ID
     */
    private final String taskId;

    /**
     * 资源信息列表
     */
    private final List<ResourceInfo> resourceInfos;

    private ConfigDownloadTaskAddDeviceRequest(String taskId, List<ResourceInfo> resourceInfos) {
        super(API_URL);
        this.taskId = taskId;
        this.resourceInfos = resourceInfos;
    }

    @Override
    public Class<ConfigDownloadTaskAddDeviceResponse> getResponseClass() {
        return ConfigDownloadTaskAddDeviceResponse.class;
    }

    @Override
    public void validate() {
        super.validate();

        if (taskId == null || taskId.isEmpty()) {
            throw new ParamException(TASK_ID_ERROR_MSG);
        }

    }
}