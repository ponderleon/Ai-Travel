package de.xxcd.aitravel;

import de.xxcd.aitravel.mongo_entity.ChatMessages;
import de.xxcd.aitravel.utils.database.mongo.MongoCollectionsEnum;
import de.xxcd.aitravel.utils.database.mongo.partition.impl.MongoCollectionRoute;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class MongoPartitionRouteTest {

    @Autowired
    private MongoCollectionRoute mongoCollectionRoute;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Test
    public void insert() {

        int core = Runtime.getRuntime().availableProcessors() * 2;

        Executor executor = new ThreadPoolExecutor(core, core, 0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), (r, e) -> {
            try {
                Thread.sleep(1000);
                if (!e.isShutdown()) {
                    e.execute(r);
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });



        for (long i = 6000; i <= 10000; i++) {

            long finalI = i;

            executor.execute(() -> {

                ChatMessages messages = new ChatMessages();

                messages.setMemoryId(finalI);

                messages.setUserId(finalI);

                messages.setContent("test" + finalI);

                mongoTemplate.insert(messages, mongoCollectionRoute.getCollectionName(MongoCollectionsEnum.CHAT_MESSAGES,finalI));
            });

        }


    }

    @Test
    public void test(){

        System.out.println(MongoCollectionsEnum.CHAT_MESSAGES.name());

    }


}
