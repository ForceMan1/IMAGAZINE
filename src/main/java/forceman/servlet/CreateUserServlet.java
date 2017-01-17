package forceman.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Igor on 17.01.2017.
 */
public class CreateUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void processCreateUser(HttpServletRequest request, HttpServletResponse response)
                                                                throws ServletException, IOException {
        String txtFio = request.getParameter("txtFio");
        String txtBirthday = request.getParameter("txtBirthday");
        String txtLogin = request.getParameter("txtLogin");
        String txtPassword = request.getParameter("txtPassword");
        boolean isActive = request.getParameter("cbActive") != null ? true : false;


    }
}
