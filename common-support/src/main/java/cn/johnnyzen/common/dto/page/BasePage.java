package cn.johnnyzen.common.dto.page;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * @project JohnnyWebappQuickstart
 * @author Johnny
 * @create-time 2022/9/22  01:59:52
 * @description 分页基础类（入参、出参）
 * @refernce-doc
 *  [1] Serializable和serialVersionUID的意义是什么？ - CSDN - https://blog.csdn.net/weixin_30953203/article/details/114208684
 */

public class BasePage implements Serializable {
    private final static Logger LOG = LoggerFactory.getLogger(BasePage.class);

    private static final long serialVersionUID = 2363895L;

    public final static Integer MIN_CURRENT_PAGE = 1;

    public final static Integer MAX_PAGE_SIZE = Integer.MAX_VALUE;

    private final static Integer DEFAULT_CURRENT_PAGE = MIN_CURRENT_PAGE;

    private final static Integer DEFAULT_PAGE_SIZE = 15;

    @ApiModelProperty(
        value = "页码 (含义: 第 currentPage 页); 起始值 := 默认值 := 1",
        example = "10",
        hidden = false
    )
    @JsonAlias({"currentPage", "index"}) //反序列化(前端/string --> 后端/bean)时，兼容多种入参的入参命名
    private Integer currentPage;

    @ApiModelProperty(
        value = "分页大小 (含义: 每页最多 pageSize 条记录); 起始值 := 默认值 := 15",
        example = "15",
        hidden = false
    )
    private Integer pageSize;

    public BasePage(){

    }

    public BasePage(Integer currentPage, Integer pageSize) {
       setCurrentPage(currentPage);
       setPageSize(pageSize);
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        if(currentPage == null){
            this.currentPage = null;
        } else {
            this.currentPage = currentPage<1?DEFAULT_CURRENT_PAGE:currentPage;
        }
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if(pageSize == null){
            this.pageSize = null;
        } else {
            this.pageSize = pageSize<1?DEFAULT_PAGE_SIZE:pageSize;
        }
    }

    @Override
    public String toString() {
        return "BasePage{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                '}';
    }
}
