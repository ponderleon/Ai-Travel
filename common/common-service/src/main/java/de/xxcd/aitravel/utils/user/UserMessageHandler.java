package de.xxcd.aitravel.utils.user;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "用户消息处理类")
public class UserMessageHandler {
    /**
     * 用户信息线程变量
     *    主要是存储用户的ID
     */
    private static final ThreadLocal<Long> USER_THREADLOCAL = new ThreadLocal<>();

    /**
     * 获取用户ID
     * @return Long 用户ID
     */
    public static Long getUserId(){

        //return USER_THREADLOCAL.get();
        return 1L;

    }

    /**
     * 设置用户ID进入到线程中
     * @param userId 用户ID
     */
    public static void setUserId(Long userId){
        USER_THREADLOCAL.set(userId);
    }

    /**
     * 移除用户ID，防止内存泄露
     */
    public static void removeUserId(){
        // 判断线程变量中是否有数据
        if(USER_THREADLOCAL.get() != null){
            // 移除数据
            USER_THREADLOCAL.remove();
        }
    }


}
