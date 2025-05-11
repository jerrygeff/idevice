package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.foundation.v2;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder(builderClassName = "Builder")
public class Expression {
    private final String key;
    private final Integer operator;
    private final List<String> values;
}