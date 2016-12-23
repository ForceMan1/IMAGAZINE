package dao;

import forceman.dao.DAOException;
import forceman.dao.jdbc.impl.MysqlJdbcUserDAO;
import forceman.entity.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Igor on 22.12.2016.
 */
public class JdbcUserDAOJUnitTest  extends Assert {
    private Connection conn = null;

    @Before
    public void initTest(){
        String url = "jdbc:mysql://localhost:3306/magazine";
        String username = "magazine";
        String password = "magazine";

        try {
            conn = DriverManager.getConnection(url, username, password);
        }catch(SQLException sqlExc){

        }
    }

    @Test
    public void createuser() throws DAOException {
        //SecureRandom
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse("1980-11-24");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        User user = new User("Ivanov Nickolay Petrovich", date, "ivanov", true);
        MysqlJdbcUserDAO userDAO = new MysqlJdbcUserDAO(conn);
        userDAO.createUser(user);
        //conn.commit();
    }


    @After
    public void destroyTest(){
        try {
            if (conn != null)
                conn.close();
        }catch(SQLException sqlExc){
            }
    }

}
