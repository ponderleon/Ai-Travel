package de.xxcd.aitravel.utils.database.mongo.partition;

import de.xxcd.aitravel.utils.database.mongo.MongoCollectionsEnum;

/**
 * MongoDB 集合路由接口
 */
public interface CollectionRoute {


    /**
     * 计算集合前缀
     * 通过路由值和集合大小计算出具体的集合前缀
     *
     * @param route          路由值
     * @param collectionSize 集合数量，必须为2的幂
     * @return 集合后缀
     */
    int calculateCollectionSuffix(Long route, int collectionSize);

    /**
     * 获取具体的集合名称
     *
     * @param mongoCollectionsEnum 枚举类型
     * @param route                路由值
     * @return 集合名称
     */
    String getCollectionName(MongoCollectionsEnum mongoCollectionsEnum, Long route);


    /**
     * 判断一个数是否是2的幂
     *
     * @param number 待判断的数
     * @return 如果是2的幂返回true，否则返回false
     */
    default boolean isPowerOfTwo(int number) {
        return number > 0 && (number & (number - 1)) == 0;
    }

    /**
     * 将一个数规范化为2的幂
     *
     * @param number 待规范化的数
     * @return 规范化后的2的幂
     */
    default int normalizeToPowerOfTwo(int number) {
        // 判断数字是否正整数
        if(number <= 0) throw new IllegalArgumentException("集合数量必须为正整数");
        // 判断数字是否正整数
        if(isPowerOfTwo(number)) return number;
        // 使用位运算找到大于number的最小2的幂
        return Integer.highestOneBit(number - 1) << 1;
    }

}
