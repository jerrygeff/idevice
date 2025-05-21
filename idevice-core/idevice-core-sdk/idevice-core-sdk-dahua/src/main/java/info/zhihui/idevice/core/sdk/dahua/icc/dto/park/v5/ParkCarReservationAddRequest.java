package info.zhihui.idevice.core.sdk.dahua.icc.dto.park.v5;

import com.dahuatech.hutool.http.Method;
import com.dahuatech.icc.oauth.http.AbstractIccRequest;
import com.dahuatech.icc.oauth.profile.IccProfile;

/**
 * 添加预约车辆请求
 */
public class ParkCarReservationAddRequest extends AbstractIccRequest<ParkCarReservationAddResponse> {
    private final static String URL = "/evo-apigw/ipms/carReservation/add";

    private static final String CAR_NUM_EMPTY_MSG = "carNum不能为空";
    private static final String CAR_NUM_LENGTH_MSG = "carNum长度需小于120";
    private static final String CONSUMER_NAME_LENGTH_MSG = "consumerName长度需小于120";
    private static final String TEL_NUMBER_LENGTH_MSG = "telNumber长度需小于11";

    private String carNum;
    private String consumerName;
    private String telNumber;
    private String parkingLotCode;
    private String sluiceChns;
    private Integer enterModel;
    private Integer exitModel;
    private String reservationType;
    private Integer reservationCount;
    private String startTimeStr;
    private String endTimeStr;
    private String memo;

    private ParkCarReservationAddRequest(Builder builder) {
        super(IccProfile.URL_SCHEME + URL, Method.POST);
        setCarNum(builder.carNum);
        setConsumerName(builder.consumerName);
        setTelNumber(builder.telNumber);
        setParkingLotCode(builder.parkingLotCode);
        setSluiceChns(builder.sluiceChns);
        setEnterModel(builder.enterModel);
        setExitModel(builder.exitModel);
        setReservationType(builder.reservationType);
        setReservationCount(builder.reservationCount);
        setStartTimeStr(builder.startTimeStr);
        setEndTimeStr(builder.endTimeStr);
        setMemo(builder.memo);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Class<ParkCarReservationAddResponse> getResponseClass() {
        return ParkCarReservationAddResponse.class;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
        putBodyParameter("carNum", carNum);
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
        putBodyParameter("consumerName", consumerName);
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
        putBodyParameter("telNumber", telNumber);
    }

    public String getParkingLotCode() {
        return parkingLotCode;
    }

    public void setParkingLotCode(String parkingLotCode) {
        this.parkingLotCode = parkingLotCode;
        putBodyParameter("parkingLotCode", parkingLotCode);
    }

    public String getSluiceChns() {
        return sluiceChns;
    }

    public void setSluiceChns(String sluiceChns) {
        this.sluiceChns = sluiceChns;
        putBodyParameter("sluiceChns", sluiceChns);
    }

    public Integer getEnterModel() {
        return enterModel;
    }

    public void setEnterModel(Integer enterModel) {
        this.enterModel = enterModel;
        putBodyParameter("enterModel", enterModel);
    }

    public Integer getExitModel() {
        return exitModel;
    }

    public void setExitModel(Integer exitModel) {
        this.exitModel = exitModel;
        putBodyParameter("exitModel", exitModel);
    }

    public String getReservationType() {
        return reservationType;
    }

    public void setReservationType(String reservationType) {
        this.reservationType = reservationType;
        putBodyParameter("reservationType", reservationType);
    }

    public Integer getReservationCount() {
        return reservationCount;
    }

    public void setReservationCount(Integer reservationCount) {
        this.reservationCount = reservationCount;
        putBodyParameter("reservationCount", reservationCount);
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
        putBodyParameter("startTimeStr", startTimeStr);
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
        putBodyParameter("endTimeStr", endTimeStr);
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
        putBodyParameter("memo", memo);
    }

    public static final class Builder {
        private String carNum;
        private String consumerName;
        private String telNumber;
        private String parkingLotCode;
        private String sluiceChns;
        private Integer enterModel;
        private Integer exitModel;
        private String reservationType;
        private Integer reservationCount;
        private String startTimeStr;
        private String endTimeStr;
        private String memo;

        public Builder carNum(String carNum) {
            this.carNum = carNum;
            return this;
        }

        public Builder consumerName(String consumerName) {
            this.consumerName = consumerName;
            return this;
        }

        public Builder telNumber(String telNumber) {
            this.telNumber = telNumber;
            return this;
        }

        public Builder parkingLotCode(String parkingLotCode) {
            this.parkingLotCode = parkingLotCode;
            return this;
        }

        public Builder sluiceChns(String sluiceChns) {
            this.sluiceChns = sluiceChns;
            return this;
        }

        public Builder enterModel(Integer enterModel) {
            this.enterModel = enterModel;
            return this;
        }

        public Builder exitModel(Integer exitModel) {
            this.exitModel = exitModel;
            return this;
        }

        public Builder reservationType(String reservationType) {
            this.reservationType = reservationType;
            return this;
        }

        public Builder reservationCount(Integer reservationCount) {
            this.reservationCount = reservationCount;
            return this;
        }

        public Builder startTimeStr(String startTimeStr) {
            this.startTimeStr = startTimeStr;
            return this;
        }

        public Builder endTimeStr(String endTimeStr) {
            this.endTimeStr = endTimeStr;
            return this;
        }

        public Builder memo(String memo) {
            this.memo = memo;
            return this;
        }

        public ParkCarReservationAddRequest build() {
            return new ParkCarReservationAddRequest(this);
        }
    }

    /**
     * 参数校验
     */
    public void valid() {
        if (carNum == null || carNum.trim().isEmpty()) {
            throw new IllegalArgumentException(CAR_NUM_EMPTY_MSG);
        }
        if (carNum.length() > 120) {
            throw new IllegalArgumentException(CAR_NUM_LENGTH_MSG);
        }
        if (consumerName != null && consumerName.length() > 120) {
            throw new IllegalArgumentException(CONSUMER_NAME_LENGTH_MSG);
        }
        if (telNumber != null && telNumber.length() > 11) {
            throw new IllegalArgumentException(TEL_NUMBER_LENGTH_MSG);
        }
    }
}