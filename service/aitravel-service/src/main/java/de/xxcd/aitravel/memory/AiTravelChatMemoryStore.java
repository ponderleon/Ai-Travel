package de.xxcd.aitravel.memory;

import de.xxcd.aitravel.mongo_entity.ChatMessages;
import de.xxcd.aitravel.utils.database.mongo.MongoCollectionsEnum;
import de.xxcd.aitravel.utils.database.mongo.partition.impl.MongoCollectionRoute;
import de.xxcd.aitravel.utils.general.StringUtils;
import de.xxcd.aitravel.utils.user.UserMessageHandler;
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

    @Autowired
    private MongoCollectionRoute mongoCollectionRoute;



    /**
     * 通过聊天记忆ID获取消息列表
     *
     * @param memoryId 聊天记忆ID
     * @return 消息列表
     */
    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        // 获取用户ID
        Long userId = UserMessageHandler.getUserId();

        // 判断memoryId是否为空
        if (memoryId == null || StringUtils.isEmpty(String.valueOf(memoryId))) {
            // 返回空列表
            return new LinkedList<>();
        }

        // 创建查询对象
        Query query = new Query();
        // 添加查询条件
        query.addCriteria(Criteria.where("memoryId").is(memoryId));
        // 执行查询
        ChatMessages chatMessages = mongoTemplate
                .findOne(query,
                        ChatMessages.class,
                        mongoCollectionRoute.getCollectionName(MongoCollectionsEnum.CHAT_MESSAGES, userId));

        // 判断是否为空，以及是否存在消息
        if (chatMessages == null || chatMessages.getContent() == null || chatMessages.getContent().trim().isEmpty()) {
            // 返回空列表
            return new LinkedList<>();
        }

        return ChatMessageDeserializer.messagesFromJson(chatMessages.getContent());
    }

    /**
     * 更新指定聊天记忆的消息列表
     *
     * @param memoryId 聊天记忆ID
     * @param messages 这是聊天中的消息，即沟通的会话 {@link ChatMemory}.
     *                 可以将聊天数据转化JSON格式字符串方便存储 {@link ChatMessageSerializer}.
     *                 可以将JSON格式字符串转化为聊天消息列表 {@link ChatMessageDeserializer}
     */
    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {


        // 获取用户ID
        Long userId = UserMessageHandler.getUserId();

        // 判断memoryId和messages是否为空
        if (StringUtils.isEmpty(String.valueOf(memoryId))
                || messages == null
                || messages.isEmpty()) {

            // 抛出异常
            throw new NullPointerException("聊天记忆ID或消息列表不能为空");
        }

        // 创建查询对象
        Query query = new Query();

        // 添加查询条件
        query.addCriteria(Criteria.where("memoryId").is(memoryId));

        // 创建更新对象
        Update update = new Update();
        // 设置聊天记忆ID
        update.set("memoryId", memoryId);
        // 设置消息内容，转换为JSON格式字符串进行存储
        update.set("content", ChatMessageSerializer.messagesToJson(messages));
        // 这里为了方便测试，统一设置为0L,后期直接获取用户ID
        update.set("userId", userId);

        // 执行更新操作，如果不存在则插入
        mongoTemplate.upsert(query, update, ChatMessages.class, mongoCollectionRoute.getCollectionName(MongoCollectionsEnum.CHAT_MESSAGES, userId));
    }

    /**
     * 通过聊天记忆ID删除消息
     *
     * @param memoryId 聊天记忆ID.
     */
    @Override
    public void deleteMessages(Object memoryId) {

        // 获取用户ID
        Long userId = UserMessageHandler.getUserId();
        // 判断memoryId是否为空，以及是否为Long类型
        if (memoryId == null || StringUtils.isEmpty(String.valueOf(memoryId))) {
            // 抛出异常
            throw new NullPointerException("聊天记忆ID或消息列表不能为空");
        }

        // 执行删除操作
        mongoTemplate.remove(Query.query(Criteria.where("memoryId").is(memoryId)),
                ChatMessages.class,
                mongoCollectionRoute.getCollectionName(MongoCollectionsEnum.CHAT_MESSAGES, userId));

    }
}
