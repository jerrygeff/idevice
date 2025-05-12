package info.zhihui.idevice.common.paging;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PageResult<T> {
    private Integer pageNum;
    private Integer pageSize;
    private Long totalSize;
    private List<T> list;
}
