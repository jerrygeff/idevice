package info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5;

import com.dahuatech.hutool.http.Method;
import com.dahuatech.icc.brm.exception.BusinessException;
import com.dahuatech.icc.common.ParamValidEnum;
import com.dahuatech.icc.oauth.http.AbstractIccRequest;
import com.dahuatech.icc.oauth.profile.IccProfile;
import com.dahuatech.icc.util.StringUtils;
import info.zhihui.idevice.core.sdk.dahua.icc.constants.IccBrmConstant;

/**
 * 卡片详情查看
 *
 * @author 232676
 * @since 1.0.0 2020/11/9 11:19
 */
public class BrmCardQueryRequest extends AbstractIccRequest<BrmCardQueryResponse> {
    private static final String VERSION = "1.0.0";
    private String cardNumber;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public BrmCardQueryRequest(String cardNumber) {
        super(String.format(IccProfile.URL_SCHEME + IccBrmConstant.BRM_URL_CARD_DETAIL_REST_GET, VERSION, cardNumber), Method.GET);
        this.cardNumber = cardNumber;
    }

    @Override
    public Class<BrmCardQueryResponse> getResponseClass() {
        return BrmCardQueryResponse.class;
    }

    @Override
    public void valid() {
        super.valid();

        if (StringUtils.isEmpty(cardNumber)) {
            throw new BusinessException(ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getCode(), ParamValidEnum.PARAM_NOT_EMPTY_ERROR.getErrMsg(), "cardNumber");
        }
    }
}
