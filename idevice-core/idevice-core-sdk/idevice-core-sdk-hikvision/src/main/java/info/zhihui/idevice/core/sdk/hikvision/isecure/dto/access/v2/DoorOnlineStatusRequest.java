package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.access.v2;

import info.zhihui.idevice.core.sdk.hikvision.isecure.constants.ISecureAccessConstant;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecureJsonPageRequest;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(builderClassName = "Builder")
public class DoorOnlineStatusRequest extends ISecureJsonPageRequest<DoorOnlineStatusResponse> {
    private static final String VERSION = "v1";
    private static final String API_URL = String.format(ISecureAccessConstant.DOOR_ONLINE_STATUS, VERSION);

    private final String regionId;
    private final String ip;
    private final List<String> indexCodes;
    private final String status;
    private final String includeSubNode;

    private DoorOnlineStatusRequest(String regionId, String ip, List<String> indexCodes,
                                   String status, String includeSubNode) {
        super(API_URL);
        this.regionId = regionId;
        this.ip = ip;
        this.indexCodes = indexCodes;
        this.status = status;
        this.includeSubNode = includeSubNode;
    }

    @Override
    public Class<DoorOnlineStatusResponse> getResponseClass() {
        return DoorOnlineStatusResponse.class;
    }
}