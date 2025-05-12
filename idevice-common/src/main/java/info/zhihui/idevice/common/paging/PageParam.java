package info.zhihui.idevice.common.paging;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PageParam {
    private Integer page = 1;
    private Integer limit = 10;
}
