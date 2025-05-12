package info.zhihui.idevice.core.sdk.hikvision.isecure.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static info.zhihui.idevice.core.sdk.hikvision.isecure.constants.ISecureFoundationConstant.CAPABILITIES;


/**
 * @author jerryge
 */
public class ISecureBrmUtil {
    public static List<String> getCapacityList(String capacityString) {
        if (StringUtils.isBlank(capacityString)) {
            return List.of();
        }

        List<String> list = new ArrayList<>();
        for (String s : StringUtils.strip(capacityString, "@").split("@")) {
            if (StringUtils.isNoneBlank(CAPABILITIES.get(s))) {
                list.add(CAPABILITIES.get(s));
            }
        }
        return list;
    }

    public static List<String> getPathList(String pathString, String stripChar) {
        if (StringUtils.isBlank(pathString)) {
            return List.of();
        }

        return Arrays.stream(StringUtils.strip(pathString, stripChar).split(stripChar)).toList();
    }
}
