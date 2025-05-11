package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.access.v2;

import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.core.sdk.hikvision.isecure.constants.ISecureAccessConstant;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecureJsonPageRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(builderClassName = "Builder")
public class DoorSearchRequest extends ISecureJsonPageRequest<DoorSearchResponse> {
    private static final String VERSION = "v2";
    private static final String API_URL = String.format(ISecureAccessConstant.DOOR_SEARCH, VERSION);
    private static final String PAGE_NO_ERROR_MSG = "pageNo不能为空";
    private static final String PAGE_SIZE_ERROR_MSG = "pageSize不能为空";

    private final String name;
    private final List<String> regionIndexCodes;
    private final Boolean isSubRegion;
    private final List<String> authCodes;
    private final String orderBy;
    private final String orderType;

    // @NOTICE pageNo和pageSize需要单独set
    private DoorSearchRequest(String name, List<String> regionIndexCodes, Boolean isSubRegion,
                              List<String> authCodes, String orderBy, String orderType) {
        super(API_URL);
        this.name = name;
        this.regionIndexCodes = regionIndexCodes;
        this.isSubRegion = isSubRegion;
        this.authCodes = authCodes;
        this.orderBy = orderBy;
        this.orderType = orderType;
    }

    @Override
    public Class<DoorSearchResponse> getResponseClass() {
        return DoorSearchResponse.class;
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