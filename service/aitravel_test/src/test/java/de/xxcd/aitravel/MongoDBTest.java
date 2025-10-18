package de.xxcd.aitravel;

import de.xxcd.aitravel.mongo_entity.ChatMessages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MongoDBTest {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Test
    public void test() {

        ChatMessages chatMessages = new ChatMessages();

        chatMessages.setMemoryId(2L);

        chatMessages.setContent("你好");

        ChatMessages insert = mongoTemplate.insert(chatMessages);

        System.out.println(insert);

    }

    @Test
    public void test2() {

        List<ChatMessages> messages = new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            ChatMessages chatMessages = new ChatMessages();

            if (i % 2 == 0) {

                chatMessages.setMemoryId(2L);

                chatMessages.setContent("你好" + i);
            } else {
                chatMessages.setMemoryId(Long.valueOf(i));

                chatMessages.setContent("哈哈哈" + i);
            }

            messages.add(chatMessages);
        }

        System.out.println(messages);


        mongoTemplate.insertAll(messages);


    }

    @Test
    public void test3() {

        //ChatMessages byId = mongoTemplate.findById("68d6653a9f48d55a0a6767b5", ChatMessages.class);
        //
        //System.out.println(byId);


        mongoTemplate.remove(Query.query(Criteria.where("memoryId").is(5L)), ChatMessages.class, "chat_messages");


    }

    @Test
    public void test4() {

        List<ChatMessages> messages = mongoTemplate.find(Query.query(Criteria.where("memoryId").is(2L)), ChatMessages.class);

        messages.forEach(System.out::println);

    }

    @Test
    public void test5() {

        Query query = new Query();

        query.addCriteria(Criteria.where("memoryId").is(2L));

        Update update = new Update();

        update.set("content", "你好，你会很辛运的");

        mongoTemplate.updateMulti(query, update, ChatMessages.class);

    }

    @Test
    public void test6() {

        Query query = new Query();

        query.addCriteria(Criteria.where("memoryId").is(2L));

        Update update = new Update();

        update.set("content", "加油努力，向钱看");

        mongoTemplate.updateFirst(query, update, "chat_messages");


    }


}
