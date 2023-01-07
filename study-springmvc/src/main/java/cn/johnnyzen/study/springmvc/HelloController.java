package cn.johnnyzen.study.springmvc;

import cn.johnnyzen.common.dto.CommonResponse;
import cn.johnnyzen.common.dto.ResponseUtil;
import cn.johnnyzen.common.dto.page.PageResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author johnny-zen
 * @version v1.0
 * @project johnny-webapp-quickstart
 * @create-time 2023/1/7 14:12
 * @description 基于 xml 注册 controller bean
 */
public class HelloController implements Controller {
    /**
     * @access-url
     *  http://localhost:8080/{context-name}/hello.do
     *      `hello.do` 由 application-mvc.xml:<beane name='hello.do' class='xx.yy.zz.HelloController'> 控制
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override //必须重写Controller的handleRequest方法
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelView = new ModelAndView();

        //ModelAndView#setViewName 跳转页面的名称, 对应: /WEB-INF/views/hello.jsp
        //modelView.setViewName("/WEB-INF/views/hello.jsp");
        // 配置视图解析器 org.springframework.web.servlet.view.InternalResourceViewResolver # prefix/suffix 后:
        modelView.setViewName("hello");

        //ModelAndView#addObject: 传入request作用域参数
        modelView.addObject("address", "中国.广东省广州市");
        return modelView;
    }

//    @RequestMapping(value = "/hello")
//    @ResponseBody
    public CommonResponse<PageResponse> hello(HttpServletRequest request,
                                    @RequestParam(value = "token", required = true) String token) {
        PageResponse pageResponse = null;

        Integer currentPage = PageResponse.MIN_CURRENT_PAGE;
        Integer pageSize = PageResponse.MAX_PAGE_SIZE;
        List<String> records = new ArrayList();
        records.add("hello ~~");
        Integer totalCount = records.size();
        Map<String, Object> extData = null;

        pageResponse = new PageResponse(currentPage, pageSize, records, totalCount, extData);
        return ResponseUtil.success(pageResponse);
    }
}

