package dao;

import forceman.dao.DAOException;
import forceman.dao.IUserDAO;
import forceman.dao.jdbc.impl.MysqlJdbcUserDAO;
import forceman.entity.User;
import forceman.security.IPasswordHash;
import forceman.security.PasswordHash;
import org.junit.*;
import org.junit.runners.MethodSorters;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Created by Igor on 22.12.2016.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JdbcUserDAOJUnitTest  extends Assert {
    private Connection conn = null;
    private IPasswordHash pswdHashImpl = new PasswordHash("SHA-256");
    private IUserDAO<Integer> userDAO = null;

    @Before
    public void initTest() throws SQLException, NamingException
    {
        /**
        String url = "jdbc:mysql://localhost:3306/magazine";
        String username = "magazine";
        String password = "magazine";

        conn = DriverManager.getConnection(url, username, password);
        userDAO = new MysqlJdbcUserDAO(conn, pswdHashImpl);
        */

        Context initContext = new InitialContext();
        Context webContext = (Context)initContext.lookup("java:/comp/env");

        DataSource ds = (DataSource) webContext.lookup("jdbc/DSMagazine");

    }

    // Удаление всех созданных пользователей
    @Test
    public void _0000_delete_all_users() throws DAOException {
        int count = userDAO.getCount();
        List<User> users = userDAO.getList(count, 0);

        Iterator<User> iterUser = users.iterator();
        while (iterUser.hasNext()) {
            userDAO.deleteById(iterUser.next().getId());
        }

        System.out.println(userDAO.getCount());
        assertTrue(userDAO.getCount() == 0);


        /************ Создание пользователя *****/
    }

    @Test(expected = DAOException.class)
    public void _0001_create_null_user() throws DAOException {
       userDAO.create(null);
    }

    @Test(expected = DAOException.class)
    public void _0002_create_user_null_fields() throws DAOException {
        //SecureRandom
        Date date = null;
       // try {
            //date = new SimpleDateFormat("yyyy-MM-dd").parse("1980-11-24");
        //} catch (ParseException e) {
         //   e.printStackTrace();
        //}
        User user = new User(null, date, null, true);
        userDAO.create(user);
        //conn.commit();
    }

    @Test()
    public void _0003_create_user() throws DAOException, SQLException {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse("1980-11-24");
        } catch (ParseException e) {
           e.printStackTrace();
        }
        User user = new User("Ivanov Sergey Vladimirovich", date, "ivanov", "qwerty", true);
        userDAO.create(user);
        //conn.commit();
    }

    /***** Изменение пользователя *********/
    @Test(expected = DAOException.class)
    public void _1000_update_null_user() throws DAOException{
        userDAO.update(null);
     }

     @Test(expected = DAOException.class)
     public void _1001_update_user_null_fields() throws DAOException {
         User user = new User(null, null, null, true);
         userDAO.update(user);
     }

     // обновление без изменения пароля



    @Test(expected = DAOException.class)
    public void _1002_update_user_with_null_password() throws DAOException {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse("1983-05-21");
        }catch(ParseException exc){

        }
        User user = new User("ivanov", date, "ivanoff", null,true);
        userDAO.updateWithPassword(user);
    }

    // проверка существования записи с логином
    @Test
    public void _2000_find_user_with_login() throws DAOException {
        Assert.assertFalse( userDAO.isLoginExists("konev") );
    }

    // проверка существования записи с логином, имеющимся в таблице
    @Test
    public void _2001_find_user_with_existing_login() throws DAOException {
        Assert.assertTrue( userDAO.isLoginExists("ivanov") );

    }

    // Удаление всех созданных пользователей
    @Test
    public void _3000_delete_all_users() throws DAOException {
        int count = userDAO.getCount();
        List<User> users = userDAO.getList(count, 0);

        Iterator<User> iterUser = users.iterator();
        while( iterUser.hasNext() ) {
            userDAO.deleteById(iterUser.next().getId());
        }

        System.out.println( userDAO.getCount() );
        assertTrue( userDAO.getCount() == 0 );

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
