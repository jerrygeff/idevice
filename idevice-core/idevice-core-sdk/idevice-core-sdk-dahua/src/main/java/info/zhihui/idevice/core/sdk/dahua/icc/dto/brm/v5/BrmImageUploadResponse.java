package info.zhihui.idevice.core.sdk.dahua.icc.dto.brm.v5;

import com.dahuatech.icc.oauth.http.IccResponse;

/**
 * @author jerryge
 */
public class BrmImageUploadResponse extends IccResponse {
    private ImageUploadResult data;

    public ImageUploadResult getData() {
        return data;
    }

    public void setData(ImageUploadResult data) {
        this.data = data;
    }

    public static class ImageUploadResult {
        private String result;
        private String fileUrl;

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }

    @Override
    public String toString() {
        return "BrmImageUploadResponse{" + "data=" + data + '}';
    }
}
