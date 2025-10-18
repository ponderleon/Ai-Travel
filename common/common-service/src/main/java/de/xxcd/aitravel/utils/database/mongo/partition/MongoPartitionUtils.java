package de.xxcd.aitravel.utils.database.mongo.partition;


import de.xxcd.aitravel.mongo_entity.ChatMessages;

/**
 *  MongoDB 逻辑分区工具类
 *    将类进行不可修改化设置，防止被继承
 */
public final class MongoPartitionUtils {

    /**
     * 私有化构造函数，防止实例化
     */
    private MongoPartitionUtils(){}

    /**
     * 判断一个数是否是2的幂
     * @param number 待判断的数
     * @return 如果是2的幂返回true，否则返回false
     */
    public static boolean isPowerOfTwo(int number){
        return number > 0 && (number & (number -1)) == 0;
    }

    /**
     * 将一个数规范化为2的幂
     * @param number 待规范化的数
     * @return 规范化后的2的幂
     * @throws IllegalArgumentException 如果输入的数字小于等于0
     */
    public static int normalizeToPowerOfTwo(int number){
        // 判断数字是否正整数
        if(number <= 0) throw new IllegalArgumentException("分区数量必须为正整数");

        // 如果已经是2的幂次方，直接返回
        if(isPowerOfTwo(number)) return number;

        // 使用位运算找到大于number的最小2的幂
        return Integer.highestOneBit(number - 1 ) << 1;
    }

    /**
     * 计算路由的基础分区
     * @param route 路由值
     *     聊天记忆 {@link ChatMessages#getMemoryId()}
     *     用户ID {@link ChatMessages#getUserId()}
     * @param partitionCount 分区数量，必须为2的幂
     * @return 基础分区
     */
    public static int basePartitionOf(long route,int partitionCount){
        return (int) (route & (partitionCount - 1));
    }

}
