package de.xxcd.aitravel;

import dev.langchain4j.model.openai.OpenAiChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ModelTest {

    @Autowired
    private OpenAiChatModel openAiChatModel;

    @Test
    public void test01(){
        //String message = openAiChatModel.chat("请告诉我深圳今天的天气怎么样？");

        String message = openAiChatModel.chat("你是谁？");

        System.out.println(message);
    }

}
