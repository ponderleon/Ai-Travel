package de.xxcd.aitravel.utils.database.mongo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * MongoDB枚举类
 */
@Schema(description = "MongoDB工具类")
@Getter
public enum MongoCollectionsEnum {

    /**
     * 聊天记忆持久化集合：前缀chat_messages_ 分区数量 1024
     */
    CHAT_MESSAGES("chat_messages_", "聊天记忆持久化集合",1024),;

    /**
     * 聊天记忆持久化集合前缀
     */
    private String collectionPrefix;

    /**
     * 集合描述
     */
    private String description;

    /**
     * 分区最大记录数，默认1000
     */
    private int collectionSize;


    /**
     * 构造函数
     *
     * @param collectionPrefix 集合前缀
     * @param description      集合描述
     */
    MongoCollectionsEnum(String collectionPrefix, String description,int collectionSize) {
        this.collectionPrefix = collectionPrefix;
        this.description = description;
        this.collectionSize = collectionSize;
    }
}


