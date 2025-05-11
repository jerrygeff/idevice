package info.zhihui.idevice.core.sdk.hikvision.isecure.dto.access.v2;

import info.zhihui.idevice.common.exception.ParamException;
import info.zhihui.idevice.core.sdk.hikvision.isecure.constants.ISecureAccessConstant;
import info.zhihui.idevice.core.sdk.hikvision.isecure.dto.ISecureJsonRequest;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Builder(builderClassName = "Builder")
public class CardBindingRequest extends ISecureJsonRequest<CardBindingResponse> {
    private static final String VERSION = "v1";
    private static final String API_URL = String.format(ISecureAccessConstant.CARD_BINDING, VERSION);
    private static final String CARD_LIST_ERROR_MSG = "cardList不能为空";
    private static final String CARD_INFO_ERROR_MSG = "cardNo, personId, orgIndexCode不能为空";
    private static final String END_DATE_ERROR_MSG = "endDate不能大于2037年12月31日";

    private final List<CardBindingInfo> cardList;
    private final String startDate;
    private final String endDate;

    private CardBindingRequest(List<CardBindingInfo> cardList, String startDate, String endDate) {
        super(API_URL);
        this.cardList = cardList;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public Class<CardBindingResponse> getResponseClass() {
        return CardBindingResponse.class;
    }

    @Override
    public void validate() {
        super.validate();

        if (cardList == null || cardList.isEmpty()) {
            throw new ParamException(CARD_LIST_ERROR_MSG);
        }

        for (CardBindingInfo cardInfo : cardList) {
            if (StringUtils.isBlank(cardInfo.getCardNo()) || StringUtils.isBlank(cardInfo.getPersonId())) {
                throw new ParamException(CARD_INFO_ERROR_MSG);
            }
        }

        // endDate不能大于2037年12月31日
        if (endDate != null) {
            DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            LocalDate lastDate = LocalDate.parse("2037-12-31", DATE_FORMATTER);
            LocalDate endDateObj = LocalDate.parse(endDate, DATE_FORMATTER);
            if (endDateObj.isAfter(lastDate)) {
                throw new ParamException(END_DATE_ERROR_MSG);
            }
        }
    }
}