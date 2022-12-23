package cn.johnnyzen.common.dto.page;

import cn.hutool.db.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @author Johnny
 * @project JohnnyWebappQuickstart
 * @create-time 2022/9/22  02:22:00
 * @description ...
 * @reference-doc
 */

@ApiModel(value = "分页请求对象", description = "分页请求 Bean")
public class PageRequest<T> extends BasePage {
    private final static Logger LOG = LoggerFactory.getLogger(PageRequest.class);

    private static final long serialVersionUID = 3256652355L;

    @ApiModelProperty(
        value = "分页请求参数",
        example = "",
        hidden = false
    )
    private T params;

    public PageRequest(){

    }

    public PageRequest(Integer currentPage, Integer pageSize){
        this(currentPage, pageSize, null);
    }

    public PageRequest(Integer currentPage, Integer pageSize, T params){
        super(currentPage, pageSize);
        this.params = params;
    }

    /**
     * 是否需要分页
     */
    public boolean isNeedPaging(){
        if(this.getCurrentPage() != null && (this.getPageSize() != null) ){
            if(this.getCurrentPage().equals(BasePage.MIN_CURRENT_PAGE) && this.getPageSize().equals(BasePage.MAX_PAGE_SIZE)){
                return false;
            }
            return true;
        }
        return false;
    }

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "PageRequest{" +
                "pageSize=" + super.getPageSize() +
                ",currentPage=" + super.getCurrentPage() +
                ",params=" + this.params +
                '}';
    }
}
