package info.zhihui.idevice.core.sdk.hikvision.isecure.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.hikvision.artemis.sdk.Request;
import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.core.sdk.hikvision.isecure.config.CurrentClassPropertiesSerializerModifier;
import info.zhihui.idevice.common.utils.JacksonUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * @author jerryge
 */
public abstract class ISecureJsonRequest<T extends ISecureResponse> extends Request<Object> {
    private static final String MSG_PATH_BLANK = "请求路径不能为空";

    protected final Map<String, Object> body = new HashMap<>();

    public ISecureJsonRequest(String path) {
        this.setPath(path);
    }

    public abstract Class<T> getResponseClass();

    public void validate() {
        if (StringUtils.isBlank(this.getPath())) {
            throw new ParamException(MSG_PATH_BLANK);
        }
    }

    public String buildStringBody() {
        ObjectMapper mapper = JacksonUtil.getObjectMapper();
        SimpleModule module = new SimpleModule();
        module.setSerializerModifier(new CurrentClassPropertiesSerializerModifier());
        mapper.registerModule(module);

        try {
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert hik object to JSON", e);
        }
    }

}
