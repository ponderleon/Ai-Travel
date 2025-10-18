package de.xxcd.aitravel.utils.database.mongo.partition.impl;

import de.xxcd.aitravel.utils.database.mongo.MongoCollectionsEnum;
import de.xxcd.aitravel.utils.database.mongo.partition.CollectionRoute;
import org.springframework.stereotype.Component;


/**
 * MongoDB分区路由实现类
 *  实现了PartitionRoute接口，提供了基于MongoDB的分区路由功能
 */
@Component
public class MongoCollectionRoute implements CollectionRoute {

    /**
     * 计算集合后缀
     * @param route          路由值
     * @param collectionSize 集合数量，必须为2的幂
     * @return 集合后缀
     */
    @Override
    public int calculateCollectionSuffix(Long route, int collectionSize) {
        // 计算集合后缀
        return (int)(route & (normalizeToPowerOfTwo(collectionSize) -1));
    }

    /**
     * 获取具体的集合名称
     * @param mongoCollectionsEnum 枚举类型
     * @param route                路由值
     * @return 集合名称
     */
    @Override
    public String getCollectionName(MongoCollectionsEnum mongoCollectionsEnum, Long route) {
        // 拼接集合名称
        return mongoCollectionsEnum.getCollectionPrefix() + calculateCollectionSuffix(route, mongoCollectionsEnum.getCollectionSize());
    }
}
