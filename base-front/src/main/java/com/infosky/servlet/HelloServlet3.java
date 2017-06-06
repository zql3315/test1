package com.infosky.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * servlet3的最新特性，不需要在web.xml里面配置
 	name == <servlet-name>
 	urlPatterns == <url-pattern>,
 	loadOnStartup == <load-on-startup>
 	initParam == <init-param>
 	name == <param-name>
 	value == <param-value>
 * @author n004881
 *
 */
@WebServlet(name = "HelloServlet3", urlPatterns = {
    "/servlet/helloServlet3"
}, asyncSupported = true, loadOnStartup = 1, initParams = {
        @WebInitParam(name = "name", value = "xiazdong"), @WebInitParam(name = "age", value = "20")
})
public class HelloServlet3 extends HttpServlet {

    private static final long serialVersionUID = 132757044432513852L;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("GBK");
        ServletConfig config = getServletConfig();
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<body>");
        out.println("Hello world" + "<br />");
        out.println(config.getInitParameter("name"));
        out.println("</body>");
        out.println("</html>");
    }
}
