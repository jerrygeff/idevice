package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.access.v2;

import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.core.sdk.hikvision.isecure.constants.ISecureAccessConstant;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecureJsonRequest;
import info.zhihui.idevice.core.sdk.hikvision.isecure.enums.access.DoorControlTypeEnum;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Getter
@Builder(builderClassName = "Builder")
public class DoorControlRequest extends ISecureJsonRequest<DoorControlResponse> {
    private static final String VERSION = "v1";
    private static final String API_URL = String.format(ISecureAccessConstant.ASSESS_DOOR_CONTROL, VERSION);
    private static final Integer MAX_DOOR_INDEX_CODE_SIZE = 10;
    private static final String DOOR_EMPTY_MSG = "门禁点不能为空";
    private static final String DOOR_EXCEED_MSG = "门禁点不能超过：%d个";

    private final List<String> doorIndexCodes;

    /**
     * @see DoorControlTypeEnum
     */
    private final Integer controlType;

    private DoorControlRequest(List<String> doorIndexCodes, Integer controlType) {
        super(API_URL);
        this.doorIndexCodes = doorIndexCodes;
        this.controlType = controlType;
    }

    @Override
    public Class<DoorControlResponse> getResponseClass() {
        return DoorControlResponse.class;
    }

    @Override
    public void validate() {
        super.validate();

        if (CollectionUtils.isEmpty(doorIndexCodes)) {
            throw new ParamException(DOOR_EMPTY_MSG);
        }
        if (doorIndexCodes.size() > MAX_DOOR_INDEX_CODE_SIZE) {
            throw new ParamException(String.format(DOOR_EXCEED_MSG, MAX_DOOR_INDEX_CODE_SIZE));
        }
    }
}
