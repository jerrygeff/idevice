package info.zhihui.idevice.common.utils.testdata;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import info.zhihui.idevice.common.utils.JacksonUtil;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TestJsonValueClass {
    @JsonDeserialize(using = JacksonUtil.ObjectToStringDeserializer.class)
    @JsonRawValue
    private String jsonValue;
    private LocalDateTime localDateTime;

    public TestJsonValueClass() {
    }

    public TestJsonValueClass(String key, LocalDateTime localDateTime) {
        this.jsonValue = key;
        this.localDateTime = localDateTime;
    }

}
