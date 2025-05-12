package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.access.v2;

import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.core.sdk.hikvision.isecure.constants.ISecureFoundationConstant;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecureJsonRequest;
import lombok.Builder;
import lombok.Getter;

/**
 * @author jerryge
 */
@Getter
@Builder(builderClassName = "Builder")
public class ConfigDownloadTaskStartRequest extends ISecureJsonRequest<ConfigDownloadTaskStartResponse> {
    private static final String VERSION = "v1";
    private static final String API_URL = String.format(ISecureFoundationConstant.CONFIG_DOWNLOAD_TASK_START, VERSION);
    private static final String TASK_ID_ERROR_MSG = "任务ID不能为空";

    /**
     * 任务ID
     */
    private final String taskId;

    private ConfigDownloadTaskStartRequest(String taskId) {
        super(API_URL);
        this.taskId = taskId;
    }

    @Override
    public Class<ConfigDownloadTaskStartResponse> getResponseClass() {
        return ConfigDownloadTaskStartResponse.class;
    }

    @Override
    public void validate() {
        super.validate();

        if (taskId == null || taskId.isEmpty()) {
            throw new ParamException(TASK_ID_ERROR_MSG);
        }
    }
}