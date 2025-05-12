package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.access.v2;

import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.core.sdk.hikvision.isecure.constants.ISecureAccessConstant;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecureJsonRequest;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Builder(builderClassName = "Builder")
@EqualsAndHashCode(callSuper = true)
public class CardReturnRequest extends ISecureJsonRequest<CardReturnResponse> {
    private static final String VERSION = "v1";
    private static final String API_URL = String.format(ISecureAccessConstant.CARD_RETURN, VERSION);
    private static final String CARD_NUMBER_ERROR_MSG = "cardNumber不能为空";
    private static final String PERSON_ID_ERROR_MSG = "personId不能为空";

    private final String cardNumber;
    private final String personId;

    private CardReturnRequest(String cardNumber, String personId) {
        super(API_URL);
        this.cardNumber = cardNumber;
        this.personId = personId;
    }

    @Override
    public void validate() {
        super.validate();

        if (StringUtils.isBlank(cardNumber)) {
            throw new ParamException(CARD_NUMBER_ERROR_MSG);
        }
        if (StringUtils.isBlank(personId)) {
            throw new ParamException(PERSON_ID_ERROR_MSG);
        }
    }

    @Override
    public Class<CardReturnResponse> getResponseClass() {
        return CardReturnResponse.class;
    }
}