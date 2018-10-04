package com.nowcoder;

import com.nowcoder.dao.QuestionDAO;
import com.nowcoder.dao.UserDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;

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
        Random random = new Random();
//        for(int i = 0;i < 11;i++){
//            User user = new User();
//            user.setId(i + 1);
//            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
//            user.setName(String.format("User%d", i));
//            user.setPassword("");
//            user.setSalt("");
//            userDAO.addUser(user);
//        }

//        for(int i = 0;i < 11;i++){
//            Question question = new Question();
//            Date date = new Date();
//            date.setTime(date.getTime() + 100 * 3600 * i);
//            question.setCreatedDate(date);
//            question.setCommentCount(i);
//            question.setUserId(i + 1);
//            question.setTitle(String.format("Title%d", i + 1));
//            question.setContent(String.format("content%d:balabala", i + 1));
//            questionDAO.addQuestion(question);
//        }
    }
}
