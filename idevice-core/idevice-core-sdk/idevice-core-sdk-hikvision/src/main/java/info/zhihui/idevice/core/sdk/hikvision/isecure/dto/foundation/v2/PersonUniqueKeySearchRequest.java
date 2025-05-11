package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2;

import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.core.sdk.hikvision.isecure.constants.ISecureFoundationConstant;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecureJsonRequest;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Getter
@Builder(builderClassName = "Builder")
public class PersonUniqueKeySearchRequest extends ISecureJsonRequest<PersonUniqueKeySearchResponse> {
    private static final String VERSION = "v1";
    private static final String API_URL = String.format(ISecureFoundationConstant.PERSON_UNIQUE_KEY_SEARCH, VERSION);
    private static final String PARAM_NAME_ERROR_MSG = "参数名称不能为空";
    private static final String PARAM_VALUE_ERROR_MSG = "参数值不能为空";
    private static final Integer MAX_PARAM_VALUE_SIZE = 1000;
    private static final String PARAM_VALUE_SIZE_ERROR_MSG = "参数值不能超过%d个";

    private final String paramName;
    private final List<String> paramValue;

    private PersonUniqueKeySearchRequest(String paramName, List<String> paramValue) {
        super(API_URL);
        this.paramName = paramName;
        this.paramValue = paramValue;
    }

    @Override
    public Class<PersonUniqueKeySearchResponse> getResponseClass() {
        return PersonUniqueKeySearchResponse.class;
    }

    @Override
    public void validate() {
        super.validate();

        if (StringUtils.isBlank(paramName)) {
            throw new ParamException(PARAM_NAME_ERROR_MSG);
        }

        if (paramValue == null || paramValue.isEmpty()) {
            throw new ParamException(PARAM_VALUE_ERROR_MSG);
        }

        // 增加参数值不能超过1000个的校验
        if (paramValue.size() > MAX_PARAM_VALUE_SIZE) {
            throw new ParamException(String.format(PARAM_VALUE_SIZE_ERROR_MSG, MAX_PARAM_VALUE_SIZE));
        }
    }
}