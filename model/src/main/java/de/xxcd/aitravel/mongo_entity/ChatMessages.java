package de.xxcd.aitravel.mongo_entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document
@Schema(description = "聊天记忆持久化实体化对象")
public class ChatMessages {

    /**
     * 主键ID
     */
    @Id
    private ObjectId id;

    /**
     * 聊天记忆ID
     */
    @Schema(description = "聊天记忆ID")
    private String memoryId;

    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Long userId;

    /**
     * 聊天内容
     *  JSON格式进行存储，包含角色和内容
     */
    @Schema(description = "聊天内容，JSON格式进行存储，包含角色和内容")
    private String content;

}
