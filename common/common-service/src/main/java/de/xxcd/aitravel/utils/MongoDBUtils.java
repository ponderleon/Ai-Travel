package de.xxcd.aitravel.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "MongoDB工具类")
public class MongoDBUtils {

    /**
     * 集合初始化接口
     */
    @FunctionalInterface
    public interface CollectionInitializer{
        /**
         * 确保集合已经创建 [from,end)
         * @param collectionPrefix 集合前缀
         * @param from 开始
         * @param end 结束
         */
        void ensureCollections(String collectionPrefix,Integer from,Integer end);
    }


    @Getter

    public enum MongoDBCollectionsEnum {

        /**
         * 聊天记忆持久化集合：前缀chat_messages_ 分区数量 1024
         */
        CHAT_MESSAGES("chat_messages_", "聊天记忆持久化集合", 1024);

        /**
         * 聊天记忆持久化集合前缀
         */
        private String collectionPrefix;

        /**
         * 集合描述
         */
        private String description;

        /**
         * 分区数量
         */
        private Integer partitionCount;

        /**
         * 分区扩容因子 80%
         */
        private final double expansionFactor = 0.8;

        /**
         * 最大分区数量 65536
         */
        private final int maxPartitionCount = Integer.MAX_VALUE ;

        /**
         * 构造函数
         *
         * @param collectionPrefix 集合前缀
         * @param description      集合描述
         * @param partitionCount   分区数量
         */
        MongoDBCollectionsEnum(String collectionPrefix, String description, Integer partitionCount) {
            this.collectionPrefix = collectionPrefix;
            this.description = description;
            this.partitionCount =normalizeToPowerOfTwo(partitionCount);
        }

        /**
         * 验证分区数量是否为2的幂次方
         * @param number 分区数量
         * @return boolean true 是  false 否
         */
        private boolean isPowerOfTwo(int number){
            return number > 0 && (number & (number -1)) == 0;
        }

        /**
         * 将分区数量规范化为2的幂次方
         * @param number 分区数量
         * @return int 规范化后的分区数量
         */
        private int normalizeToPowerOfTwo(int number){
            // 如果已经是2的幂次方，直接返回
            if(isPowerOfTwo(number)) return number;

            // 创建临时存储变量
            int temp = 1;

            // 左移操作，直到大于等于number
            while(temp < number) temp <<= 1;

            return temp;
        }

        /**
         * 确保分区容量
         * @param route 路由值，可以为用户ID或者其他唯一标识
         */
        public void ensureCapacity(Long route,CollectionInitializer collectionInitializer){
            // 获取当前分区数量
            int oldPartitionCount = this.partitionCount;
            // 计算扩容阈值
            int expansionThreshold =(int) Math.floor(this.expansionFactor * oldPartitionCount);
            // 计算当前路由所在分区
            int currentPartition = (int) (route & (oldPartitionCount - 1));

            // 如果当前分区超过扩容阈值，则进行扩容
            if(currentPartition > expansionThreshold) return;

            // 加入同步锁，进行扩容操作
            synchronized (this) {
                // 再次获取当前分区数量，防止线程交叉
                int oldPartition  =  this.getPartitionCount();
                // 计算分区扩容阈值
                int expansion = (int)Math.floor(this.expansionFactor * oldPartition);
                // 计算当前路由所在分区
                int partition = (int) (route & (oldPartition -1));


                // 如果当前分区仍然超过扩容阈值，则进行扩容
                if(partition > expansion) return;

                // 进行扩容，扩容为原来的2倍
                int newPartitionCount = oldPartition << 1;

                // 规范化分区数量为2的幂次方
                if(newPartitionCount <= 0) newPartitionCount = oldPartition;

                // 判断是否超过最大的分区数量
                if(newPartitionCount > maxPartitionCount) return;

                // 如果有集合初始化接口，则进行集合初始化
                if(collectionInitializer != null) collectionInitializer.ensureCollections(this.collectionPrefix,oldPartition,newPartitionCount);

                // 进行扩容
                this.partitionCount = newPartitionCount;

            }

        }

        /**
         * 结算集合名称
         * @param route 路由值
         * @param collectionInitializer 集合初始化函数
         * @return String 集合名称
         */
        private String calculateCollectionName(Long route,CollectionInitializer collectionInitializer){
            // 确保分区容量
            //this.ensureCapacity(route,collectionInitializer);
            // 返回集合名称
            return this.collectionPrefix + (route & (this.partitionCount  -1));
        }
    }




    /**
     * 计算集合名称
     *
     * @param mongoDBCollectionsEnum 枚举-集合信息
     * @param route                  路由值，通过路由进行分区
     * @return String
     */
    public static String calculateCollectionName(MongoDBCollectionsEnum mongoDBCollectionsEnum, Long route) {

        // 返回集合名称
        return mongoDBCollectionsEnum.calculateCollectionName(route,null);

    }

    /**
     * 计算集合名称
     * @param mongoDBCollectionsEnum 枚举-集合信息
     * @param route 路由值，通过路由进行分区
     * @param collectionInitializer 集合初始化函数
     * @return String 集合名称
     */
    public static String calculateCollectionName(MongoDBCollectionsEnum mongoDBCollectionsEnum,Long route,CollectionInitializer collectionInitializer){
        // 返回集合名称
        return mongoDBCollectionsEnum.calculateCollectionName(route,collectionInitializer);
    }


}
