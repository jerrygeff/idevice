package info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5;

import com.dahuatech.hutool.http.Method;
import com.dahuatech.icc.oauth.http.AbstractIccRequest;
import com.dahuatech.icc.oauth.profile.IccProfile;

import static info.zhihui.idevice.core.sdk.dahua.icc.constants.IccBrmConstant.BRM_URL_PERSON_DETAIL_REST_GET;

/**
 * 人员详情
 *
 * @author 232676
 * @since 1.0.0 2020/11/9 11:19
 */
public class BrmPersonQueryRequest extends AbstractIccRequest<BrmPersonQueryResponse> {

    private static final String VERSION = "1.2.0";
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BrmPersonQueryRequest(Long id) {
        super(String.format(IccProfile.URL_SCHEME + BRM_URL_PERSON_DETAIL_REST_GET, VERSION, id), Method.GET);
        this.id = id;
    }

    @Override
    public Class<BrmPersonQueryResponse> getResponseClass() {
        return BrmPersonQueryResponse.class;
    }

}