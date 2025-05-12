package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.camera.v2;

import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.core.sdk.hikvision.isecure.constants.ISecureCameraConstant;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecureJsonPageRequest;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2.Expression;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 摄像头搜索请求
 */
@Getter
@Builder(builderClassName = "Builder")
public class CameraSearchRequest extends ISecureJsonPageRequest<CameraSearchResponse> {
    private static final String VERSION = "v2";
    private static final String API_URL = String.format(ISecureCameraConstant.CAMERA_SEARCH, VERSION);
    private static final String PAGE_NO_ERROR_MSG = "pageNo不能为空";
    private static final String PAGE_SIZE_ERROR_MSG = "pageSize不能为空";

    /** 资源名称 */
    private final String name;
    /** 区域编号列表 */
    private final List<String> regionIndexCodes;
    /** 是否查询下级区域 */
    private final Boolean isSubRegion;
    /** 权限码列表 */
    private final List<String> authCodes;
    /** 排序字段 */
    private final String orderBy;
    /** 排序方式 */
    private final String orderType;
    /** 查询表达式 */
    private final List<Expression> expressions;

    private CameraSearchRequest(String name, List<String> regionIndexCodes, Boolean isSubRegion,
                              List<String> authCodes, String orderBy, String orderType,
                              List<Expression> expressions) {
        super(API_URL);
        this.name = name;
        this.regionIndexCodes = regionIndexCodes;
        this.isSubRegion = isSubRegion;
        this.authCodes = authCodes;
        this.orderBy = orderBy;
        this.orderType = orderType;
        this.expressions = expressions;
    }

    @Override
    public Class<CameraSearchResponse> getResponseClass() {
        return CameraSearchResponse.class;
    }

    @Override
    public void validate() {
        super.validate();

        if (pageNo == null) {
            throw new ParamException(PAGE_NO_ERROR_MSG);
        }
        if (pageSize == null) {
            throw new ParamException(PAGE_SIZE_ERROR_MSG);
        }
    }
} 