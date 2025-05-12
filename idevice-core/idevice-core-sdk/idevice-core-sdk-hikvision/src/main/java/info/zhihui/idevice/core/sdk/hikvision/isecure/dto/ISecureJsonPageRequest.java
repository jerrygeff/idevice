package info.zhihui.idevice.core.sdk.hikvision.isecure.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import info.zhihui.idevice.common.utils.JacksonUtil;
import info.zhihui.idevice.core.sdk.hikvision.isecure.config.CurrentAndParentClassPropertiesSerializerModifier;

/**
 * @author jerryge
 */
public abstract class ISecureJsonPageRequest<T extends ISecurePageResponse<?>> extends ISecureJsonRequest<T> {
    protected Integer pageNo;

    protected Integer pageSize;

    public ISecureJsonPageRequest(String path) {
        super(path);
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String buildStringBody() {
        ObjectMapper mapper = JacksonUtil.getObjectMapper();
        SimpleModule module = new SimpleModule();
        module.setSerializerModifier(new CurrentAndParentClassPropertiesSerializerModifier());
        mapper.registerModule(module);

        try {
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert hik object to JSON", e);
        }
    }

}
