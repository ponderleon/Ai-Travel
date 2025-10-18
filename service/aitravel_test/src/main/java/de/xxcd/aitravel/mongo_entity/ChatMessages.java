package de.xxcd.aitravel.mongo_entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="chat_messages")
public class ChatMessages {

    /**
     * 主键ID
     */
    @Id
    private ObjectId id;

    /**
     * 记忆ID
     */
    private Long memoryId;

    /**
     * 消息内容
     */
    private String content;

}
