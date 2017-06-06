package comet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 长连接
 * @author n004881
 */
@WebServlet(name = "MessageServlet", urlPatterns = {
    "/servlet/MessageServlet"
}, loadOnStartup = 2)
public class MessageServlet extends HttpServlet {

    private static final long serialVersionUID = -1L;

    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    public MessageServlet() {
        super();
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = request.getParameter("message");
        String user = request.getParameter("user");
        String from = request.getParameter("from");
        // ChatServlet.messageSender.login(from);
        if ("all".equals(user)) {
            log(from + " send message: " + message + " to everyone");
            ChatServlet.send("*", from + ": " + message + " [" + sdf.format(new Date()) + "]");
        } else {
            ChatServlet.send(user, from + " said to me: " + message + " [" + sdf.format(new Date()) + "]");
            log(from + " send message: " + message + " to " + user);
        }
    }

}
