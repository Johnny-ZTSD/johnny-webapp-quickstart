package cn.johnnyzen.common.dto.page;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Johnny
 * @project JohnnyWebappQuickstart
 * @create-time 2022/9/27  00:50:52
 * @description ...
 * @reference-doc
 */

@ApiModel(value = "分页响应对象", description = "分页响应 Bean")
public class PageResponse<T> extends BasePage {
    private final static Logger LOG = LoggerFactory.getLogger(PageResponse.class);

    private static final long serialVersionUID = 3568235295235L;

    @ApiModelProperty(
        value = "总记录数 (含义: 本次请求共有 totalCount 条记录)",
        example = "149",
        hidden = false
    )
    @JsonAlias({"totalCount", "totalRecords"}) // 反序列化
    private Integer totalCount;

    @ApiModelProperty(
        value = "总页面数 (含义: 本次请求共有 totalPages 页)",
        example = "15",
        hidden = false
    )
    private Integer totalPages;

    @ApiModelProperty(
        value = "响应内容 (含义: 本页响应的数据集)",
        hidden = false
    )
    private List<T> records;

    @ApiModelProperty(
            value = "非分页内的响应数据集",
            hidden = false
    )
    private Map<String, Object> extData;

    @ApiModelProperty(
        value = "当前页面记录数 (含义: 本页面响应了 currentSize 条记录; 0 <= currentSize <= pageSize )",
        example = "15",
        hidden = false
    )
    private Integer currentSize;

    public PageResponse(Integer currentPage, Integer pageSize, List<T> records, Integer totalCount, Map<String, Object> extData) {
        super(currentPage, pageSize);
        this.totalCount = totalCount;
        this.records = records;

        setTotalPages(super.getPageSize(), this.totalCount);
        initCurrentSize(this.records);

        this.extData = extData;
    }

    public PageResponse(Integer currentPage, Integer pageSize, List<T> records, Integer totalCount) {
        this(currentPage, pageSize, records, totalCount, null);
    }

    private void setTotalPages(Integer pageSize, Integer totalPages){
        this.totalPages = calculateTotalPages(pageSize, totalPages);
    }

    public void setRecords(List<T> records){
        this.records = records;
        initCurrentSize(this.records);
    }

    private void initCurrentSize(List<T> records){
        if(this.records != null){
            this.currentSize = this.records.size();
        } else {
            this.currentSize = 0;
        }
    }

    /**
     * 对外提供工具方法，计算总的页面数
     * @param pageSize 每页的页面大小
     * @param totalCount 所有页面合起来的总记录数
     * @return
     */
    public static int calculateTotalPages(int pageSize, int totalCount){
        pageSize = pageSize < 1? 1 : pageSize;
        int flag = totalCount % pageSize == 0 ? 0 : 1;
        // 向上取整
        return (totalCount / pageSize) + flag;
    }

    /**
     * 对外提供工具方法，计算当前页面理论的实际记录数
     * @note 根据 totalCount、currentPage、pageSize 计算所得、理论的当前页面大小(可能为0————异常值)
     *  currentSize = currentPage >= totalPages ? ( totalCount - pageSize*(currentPage-1) ) * pageSize
     * @param currentPage (startIndex=1)
     */
    public static int calculateCurrentSize(int currentPage, int pageSize, int totalCount){
        int totalPgaes = calculateTotalPages(pageSize, totalCount);
        int currentSize = currentPage >= totalPgaes ? (totalCount - pageSize*(currentPage-1) ) : pageSize;
        currentSize = currentSize < 0 ? 0 : currentSize;
        return currentSize;
    }

    /**
     * @function 伪分页处理
     *      := 不通过数据库分页
     * @param currentPage 当前页码 (startIndex=1)
     * @param pageSize 每页的页面大小
     * @param totalRecordsList 所有页面合起来的总数据集
     */
    public static <T> PageResponse fakePagingHandle(Integer currentPage, Integer pageSize, List<T> totalRecordsList){
        if(currentPage==null || pageSize == null){
            currentPage = BasePage.MIN_CURRENT_PAGE;
            pageSize = BasePage.MAX_PAGE_SIZE;
        }
        int totalRecords = totalRecordsList.size();
        //根据 totalRecords、currentPage、pageSize 计算出的、理论的当前页面大小（可能为0————异常值）
        int currentSize = PageResponse.calculateCurrentSize(currentPage, pageSize, totalRecords);

        List<T> records = null;
        if(currentSize==0){//0为异常值 => 请求的 currentPage 、 pageSize 超出正常范围
            records = new ArrayList<>();
        } else {
            // [fromIndex, endIndex)
            int fromIndex = (currentPage-1)*pageSize; // note: subList.fromIndex.startIndex=0
            int endIndex = fromIndex+currentSize;
            records = totalRecordsList.subList(fromIndex, endIndex);
        }
        PageResponse pageResponse = new PageResponse(currentPage, pageSize, records, totalRecords);
        return pageResponse;
    }

    /**
     * 分页处理
     * @param isRequestSQLSupportAutoPaging
     * @param isProcessMethodChangeOriginResponsePageCurrentSize
     * @param pageRequest
     * @param oldPageResponse
     * @param newRecords
     */
    public static <T> PageResponse pagingHanlde(
        boolean isRequestSQLSupportAutoPaging,
        boolean isProcessMethodChangeOriginResponsePageCurrentSize,
        PageRequest pageRequest,
        PageResponse oldPageResponse,
        List<Map<String, T>> newRecords
    ){
        boolean isRequestNeedPaging = pageRequest.isNeedPaging();
        if(pageRequest == null || (pageRequest.getPageSize()==null && pageRequest.getCurrentPage() == null ) ){//避免空指针异常
            pageRequest = new PageRequest();
            pageRequest.setCurrentPage(BasePage.MIN_CURRENT_PAGE);
            pageRequest.setPageSize(BasePage.MAX_PAGE_SIZE);
        }
        if(isRequestNeedPaging == false){//step1 request 不要求分页
            if(isProcessMethodChangeOriginResponsePageCurrentSize == false){
                return new PageResponse(oldPageResponse.getCurrentPage(), oldPageResponse.getPageSize(), newRecords, oldPageResponse.getTotalCount());
            } else {
                return PageResponse.fakePagingHandle( 1 , newRecords.size(), newRecords);
            }
        } else {// request 要求分页
            if(isRequestSQLSupportAutoPaging){ // step2 原业务 SQL 支持自动分页
                if(isProcessMethodChangeOriginResponsePageCurrentSize == false){// step3 本 process 的 parse 方法未改变 原 page 的一级元素数量
                    return new PageResponse(pageRequest.getCurrentPage(), pageRequest.getPageSize(), newRecords, oldPageResponse.getTotalCount());
                } else {
                    return PageResponse.fakePagingHandle(pageRequest.getCurrentPage(), pageRequest.getPageSize(), newRecords);
                }
            } else {// step2 原业务 SQL 不支持自动分页 => 只能伪分页
                return PageResponse.fakePagingHandle(pageRequest.getCurrentPage(), pageRequest.getPageSize(), newRecords);
            }
        }
    }

    public Integer getTotalCount() {
        return totalCount;
    }


    public Integer getTotalPages() {
        return totalPages;
    }

    public List<T> getRecords() {
        return records;
    }

    public Map<String, Object> getExtData() {
        return extData;
    }

    public void setExtData(Map<String, Object> extData) {
        this.extData = extData;
    }

    public Integer getCurrentSize() {
        return currentSize;
    }

    @Override
    public String toString() {
        return "PageResponse{" +
                "currentPage=" + super.getCurrentPage() +
                ", pageSize=" + super.getPageSize() +
                ", totalCount=" + totalCount +
                ", totalPages=" + totalPages +
                ", records=" + records +
                ", currentSize=" + currentSize +
                ", extData=" + extData +
                '}';
    }
}
