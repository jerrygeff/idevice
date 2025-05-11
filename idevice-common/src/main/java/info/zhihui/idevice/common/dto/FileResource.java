package info.zhihui.idevice.common.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.InputStream;

@Data
@Accessors(chain = true)
public class FileResource {
    private String contentType;
    private Long fileSize;
    private InputStream inputStream;
}
