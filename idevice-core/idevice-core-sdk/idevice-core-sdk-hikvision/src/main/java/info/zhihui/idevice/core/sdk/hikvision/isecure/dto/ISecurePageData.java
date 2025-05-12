package info.zhihui.idevice.core.sdk.hikvision.isecure.dto;

import lombok.Data;

import java.util.List;

/**
 *
 * @author jerryge
 */
@Data
public class ISecurePageData<T>  {
    /**
     * 总记录数
     */
    private Integer total;

    /**
     * 当前页码
     */
    private Integer pageNo;

    /**
     * 每页大小
     */
    private Integer pageSize;

    /**
     * 数据列表
     */
    private List<T> list;
}