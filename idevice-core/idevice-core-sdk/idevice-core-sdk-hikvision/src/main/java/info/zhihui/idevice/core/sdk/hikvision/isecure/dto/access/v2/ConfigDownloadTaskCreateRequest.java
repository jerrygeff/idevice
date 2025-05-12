package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.access.v2;

import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.core.sdk.hikvision.isecure.constants.ISecureFoundationConstant;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecureJsonRequest;
import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.access.ConfigDownloadTaskEnum;
import lombok.Builder;
import lombok.Getter;

/**
 * @author jerryge
 */
@Getter
@Builder(builderClassName = "Builder")
public class ConfigDownloadTaskCreateRequest extends ISecureJsonRequest<ConfigDownloadTaskCreateResponse> {
    private static final String VERSION = "v1";
    private static final String API_URL = String.format(ISecureFoundationConstant.CONFIG_DOWNLOAD_TASK_CREATE, VERSION);
    private static final String TASK_TYPE_ERROR_MSG = "任务类型不能为空";
    private static final String TASK_TYPE_INVALID_MSG = "任务类型不符合要求";

    /**
     * 任务类型
     * @see ConfigDownloadTaskEnum
     */
    private final Integer taskType;

    private ConfigDownloadTaskCreateRequest(Integer taskType) {
        super(API_URL);
        this.taskType = taskType;
    }

    @Override
    public Class<ConfigDownloadTaskCreateResponse> getResponseClass() {
        return ConfigDownloadTaskCreateResponse.class;
    }

    @Override
    public void validate() {
        super.validate();

        if (taskType == null) {
            throw new ParamException(TASK_TYPE_ERROR_MSG);
        }

        if (!ConfigDownloadTaskEnum.isValid(taskType)) {
            throw new ParamException(TASK_TYPE_INVALID_MSG);
        }

    }
}