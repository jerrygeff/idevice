package info.zhihui.idevice.core.sdk.dahua.icc.dto;

import com.dahuatech.hutool.http.Method;
import com.dahuatech.icc.brm.exception.BusinessException;
import com.dahuatech.icc.common.ParamValidEnum;
import com.dahuatech.icc.oauth.http.AbstractIccRequest;
import com.dahuatech.icc.oauth.http.IccResponse;

/**
 * ICC分页请求基类
 */
public abstract class IccPageRequest<T extends IccResponse> extends AbstractIccRequest<T> {
    // 默认值
    protected Integer pageNum = 1;
    protected Integer pageSize = 10;

    protected IccPageRequest(String url, Method method) {
        super(url, method);
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
        putBodyParameter("pageNum", pageNum);
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        putBodyParameter("pageSize", pageSize);
    }

    @Override
    public void valid(){
        super.valid();

        pageRequestBusinessValid();
    }

    protected void pageRequestBusinessValid() {
        if (pageNum == null) {
            throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(),
                    ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getErrMsg(), "pageNum");
        }
        if (pageSize == null) {
            throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(),
                    ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getErrMsg(), "pageSize");
        }
    }

}