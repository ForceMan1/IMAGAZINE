package forceman.servlet;

import forceman.entity.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Igor on 13.01.2017.
 */
public class ListUsersServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String fio = request.getParameter("txtFio");
        String login = request.getParameter("txtLogin");
        String password = request.getParameter("txtPassword");
        String txtBirthday = request.getParameter("txtBirthday");
        boolean isActive = request.getParameter("cbActive") != null ? true : false;

        SimpleDateFormat dtf = new SimpleDateFormat("yyyy.MM.dd");
        Date birthday = null;
        try {
            birthday = dtf.parse(txtBirthday);
        }catch(ParseException excParse){

        }
        User user = new User(login, birthday, login, password, isActive);

    }
}
