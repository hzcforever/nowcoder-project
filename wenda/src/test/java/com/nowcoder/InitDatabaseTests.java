package com.nowcoder;

import com.nowcoder.dao.QuestionDAO;
import com.nowcoder.dao.UserDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WendaApplication.class)
//@Sql("/init-schema.sql")
public class InitDatabaseTests {
    @Autowired
    UserDAO userDAO;

    @Autowired
    QuestionDAO questionDAO;

    @Test
    public void initDatabase(){
        userDAO.deleteById(1410313116);
    }
}
