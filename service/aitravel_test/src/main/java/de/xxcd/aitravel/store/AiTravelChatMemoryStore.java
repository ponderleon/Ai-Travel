package de.xxcd.aitravel.store;

import de.xxcd.aitravel.mongo_entity.ChatMessages;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class AiTravelChatMemoryStore implements ChatMemoryStore {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 根据ID获取消息列表
     * @param memoryId 记忆ID
     * @return 消息列表
     */
    @Override
    public List<ChatMessage> getMessages(Object memoryId) {

        System.out.println("当前线程ID：====================================" + Thread.currentThread().getId());

        // 如果memoryId为空，返回空的列表
        if(memoryId == null){
            // 返回空的列表
            return new LinkedList<>();
        }

        // 查询数据
        ChatMessages chatMessages  = mongoTemplate
                .findOne(Query.query(Criteria.where("memoryId").is(memoryId)), ChatMessages.class);

        // 判断是否查询到数据
        if(chatMessages == null){
            // 返回空的列表
            return new LinkedList<>();
        }
        // 返回消息列表
        return ChatMessageDeserializer.messagesFromJson(chatMessages.getContent());
    }


    /**
     *  更新消息列表
     * @param memoryId The ID of the chat memory.
     * @param messages List of messages for the specified chat memory, that represent the current state of the {@link ChatMemory}.
     *                 Can be serialized to JSON using {@link ChatMessageSerializer}.
     */
    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {

        System.out.println("当前线程ID：====================================" + Thread.currentThread().getId());

        // 判断是否有消息ID，以及消息列表是否为空
        if(memoryId == null || messages == null || messages.isEmpty()){
            return;
        }

        Update update = new Update();
        // 设置memoryId
        update.set("memoryId",memoryId);
        // 设置消息内容
        update.set("content",ChatMessageSerializer.messagesToJson(messages));
        // 更新数据
        mongoTemplate.upsert(Query.query(Criteria.where("memoryId").is(memoryId)),update,ChatMessages.class);

    }

    /**
     * 删除消息列表
     * @param memoryId The ID of the chat memory.
     */
    @Override
    public void deleteMessages(Object memoryId) {

        System.out.println("当前线程ID：====================================" + Thread.currentThread().getId());

        // 如果memoryId为空，直接返回
        if(memoryId == null){
            return;
        }
        // 进行数据的删除
        mongoTemplate.remove(Query.query(Criteria.where("memoryId").is(memoryId)),ChatMessages.class);

    }
}
