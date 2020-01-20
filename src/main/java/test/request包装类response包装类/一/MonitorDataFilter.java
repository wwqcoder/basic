package test.request包装类response包装类.一;

/**
 * @创建人 zhaojingen
 * @创建时间 2019/8/23
 * @描述
 */
//import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
//import com.hoau.psb.entity.MonitorData;
//import com.hoau.psb.facade.IhoauMonitorDataFacade;
//import com.hoau.psb.servlet.RequestWrapper;
//import com.hoau.psb.servlet.ResponseWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/psb/*", filterName = "monitorDataFilter")
public class MonitorDataFilter implements Filter {


    //@Reference
    //IhoauMonitorDataFacade ihoauMonitorDataServiceImpl;

    Log log = LogFactory.getLog(MonitorDataFilter.class);

    //private MonitorData monitorData;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        RequestWrapper requestWrapper = new RequestWrapper(httpServletRequest);
        ResponseWrapper responseWrapper = new ResponseWrapper(httpServletResponse);

        filterChain.doFilter(requestWrapper, responseWrapper);

       //monitorData = new MonitorData();
        String params = null;
        String val = null;

        String meth = httpServletRequest.getMethod();

        //delete 和put 请求暂时未处理
        if ("POST".equals(meth)) {
            params = requestWrapper.getBody();
        } else {
            params = JSONObject.toJSONString(httpServletRequest.getParameterMap());
        }
        log.info(params);

       /* monitorData.setInterAddress(httpServletRequest.getRequestURL().toString());
        monitorData.setAccAccount("");
        monitorData.setAccIP(httpServletRequest.getRemoteAddr());
        monitorData.setAccParams(params);
        monitorData.setInterType("");*/

        byte[] bytes = responseWrapper.getBytes();
        val = new String(bytes, "UTF-8");
        //monitorData.setRetVal(JSONObject.toJSONString(val));

        log.info(val);

        //将数据 再写到 response 中
        servletResponse.getOutputStream().write(bytes);
        servletResponse.getOutputStream().flush();
        servletResponse.getOutputStream().close();

        //ihoauMonitorDataServiceImpl.addMonitorData(monitorData);

    }

    @Override
    public void destroy() {

    }
}
