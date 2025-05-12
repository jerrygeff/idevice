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
public class ConfigRateSearchRequest extends ISecureJsonRequest<ConfigRateSearchResponse> {
    private static final String VERSION = "v1";
    private static final String API_URL = String.format(ISecureFoundationConstant.CONFIG_RATE_SEARCH, VERSION);
    private static final String TASK_ID_ERROR_MSG = "任务ID不能为空";

    /**
     * 任务ID
     */
    private final String taskId;

    private ConfigRateSearchRequest(String taskId) {
        super(API_URL);
        this.taskId = taskId;
    }

    @Override
    public Class<ConfigRateSearchResponse> getResponseClass() {
        return ConfigRateSearchResponse.class;
    }

    @Override
    public void validate() {
        super.validate();

        if (taskId == null || taskId.isEmpty()) {
            throw new ParamException(TASK_ID_ERROR_MSG);
        }
    }
}