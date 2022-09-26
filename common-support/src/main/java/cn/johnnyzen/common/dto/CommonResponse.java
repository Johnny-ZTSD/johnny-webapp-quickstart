package cn.johnnyzen.common.dto;

import cn.johnnyzen.common.dto.page.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @author Johnny
 * @project JohnnyWebappQuickstart
 * @create-time 2022/9/27  01:49:55
 * @description ...
 * @reference-doc
 */
@ApiModel(value = "公共响应对象", description = "分页响应对象") // [swagger] 作用范围: 模型类，如 VO、BO
public class CommonResponse<T> implements Serializable {
    private final static Logger LOG = LoggerFactory.getLogger(BasePage.class);

    private static final long serialVersionUID = 6458385342L;

    @ApiModelProperty(
        value = "响应状态", // swagger 中 的属性说明
        example = "true",
        hidden = false // swagger 中 是否隐藏本属性
    )
    private Boolean status;

    @ApiModelProperty(value = "业务数据")
    private T data;

    @ApiModelProperty(value = "错误码")
    private String errorCode;

    @ApiModelProperty(value = "错误信息")
    private String errorMessage;

    public CommonResponse(Boolean status, T data, String errorCode, String errorMessage) {
        this.status = status;
        this.data = data;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public static <T> CommonResponse create(Boolean status, T data, String errorCode, String errorMessage) {
        return new CommonResponse<T>(status, data, errorCode, errorMessage);
    }

    public static <T> CommonResponse success() {
        return create(true, null, null, null);
    }

    public static <T> CommonResponse success(T data) {
        return create(true, data, null, null);
    }

    public static <T> CommonResponse failure() {
        return create(false, null, null, null);
    }

    public static <T> CommonResponse failure(String errorCode) {
        return create(false, null, errorCode, null);
    }

    public static <T> CommonResponse failure(String errorCode, String errorMessage) {
        return create(false, null, errorCode, errorMessage);
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "CommonResponse{" +
                "status=" + status +
                ", data=" + data +
                ", errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
