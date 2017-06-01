package component.user_interface.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ercan on 24.05.2017.
 */
@WebServlet(name = "InitServlet", urlPatterns = {"/init"})
public class InitServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*Socket pythonSocket = null;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(5555);
                    Socket pythonSocket = serverSocket.accept();

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
        }).start();
        String command = "/home/ercan/anaconda2/bin/python /home/ercan/ideaprojects/turquas/predict.py";
        Process p = Runtime.getRuntime().exec(command);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ServletContext context = getServletContext();
        context.setAttribute("socket", pythonSocket);*/

        response.sendRedirect("index.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
