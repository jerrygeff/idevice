package info.zhihui.idevice.common.utils;

import info.zhihui.idevice.common.dto.FileResource;
import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.common.vo.FileResourceVo;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

/**
 * @author jerryge
 */
public class FileUtil {
    /**
     * 构建FileResource对象
     */
    public static FileResource buildFileResource(FileResourceVo fileResourceVo) {
        if (StringUtils.isBlank(fileResourceVo.getBase64Content())) {
            throw new ParamException("资源内容不能为空");
        }

        byte[] decodedBytes = Base64.getDecoder().decode(fileResourceVo.getBase64Content());

        return new FileResource()
                .setInputStream(new ByteArrayInputStream(decodedBytes))
                .setContentType(fileResourceVo.getContentType())
                .setFileSize((long) decodedBytes.length);
    }

    public static String getBase64FromInputStream(InputStream inputStream) throws IOException {
        try (inputStream) {
            byte[] allImageBytes = inputStream.readAllBytes();
            return Base64.getEncoder().encodeToString(allImageBytes);
        }
    }
}
