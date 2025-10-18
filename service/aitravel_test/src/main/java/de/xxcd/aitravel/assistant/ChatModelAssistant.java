package de.xxcd.aitravel.assistant;


import dev.langchain4j.service.spring.AiService;
import dev.langchain4j.service.spring.AiServiceWiringMode;


/**
 *
 * 使用langchain4j高级功能AI Service实现编程的解耦
 *
 *1.wiringMode() - 控制组件注入模式
 * 默认值：AUTOMATIC（自动模式）
 *      功能：决定 LangChain4j 组件如何被注入到 AI 服务中
 *      AUTOMATIC：自动从 Spring 上下文中查找并注入所有可用的组件
 *      EXPLICIT：需要显式指定要使用的组件 bean 名称
 * 2.chatModel() - 聊天模型 bean 名称
 *      类型：String，默认空字符串
 *      功能：指定要使用的 ChatModel bean 的名称，用于处理常规的聊天对话
 * 3.streamingChatModel() - 流式聊天模型 bean 名称
 *      类型：String，默认空字符串
 *      功能：指定要使用的 StreamingChatModel bean 的名称，用于处理流式响应的聊天对话
 * 4.chatMemory() - 聊天记忆 bean 名称
 *      类型：String，默认空字符串
 *      功能：指定要使用的 ChatMemory bean 的名称，用于存储和管理对话历史
 * 5.chatMemoryProvider() - 聊天记忆提供者 bean 名称
 *      类型：String，默认空字符串
 *      功能：指定要使用的 ChatMemoryProvider bean 的名称，用于提供聊天记忆实例
 * 6.contentRetriever() - 内容检索器 bean 名称
 *      类型：String，默认空字符串
 *      功能：指定要使用的 ContentRetriever bean 的名称，用于 RAG（检索增强生成）中的内容检索
 * 7.retrievalAugmentor() - 检索增强器 bean 名称
 *      类型：String，默认空字符串
 *      功能：指定要使用的 RetrievalAugmentor bean 的名称，用于增强检索功能
 * 8.moderationModel() - 内容审核模型 bean 名称
 *      类型：String，默认空字符串
 *      功能：指定要使用的 ModerationModel bean 的名称，用于对输入或输出内容进行审核
 * 9.tools() - 工具 bean 名称数组
 *      类型：String[]，默认空数组
 *      功能：指定包含 @Tool 注解方法的 bean 名称列表，这些工具可以被 AI 服务调用来执行特定功能
 */

/**
 * 1️⃣ 如果没有指定 wiringMode属性，则默认会自动在容器中进行装配，如果有多个模型则需要手动指定
 * 2️⃣ 手动指定则需要将 wiringMode属性指定为 EXPLICIT
 */
@AiService(
        wiringMode = AiServiceWiringMode.EXPLICIT, // 指定手动指定模型
        chatModel = "openAiChatModel"
)
public interface ChatModelAssistant {

    /**
     * 简单对话功能
     * @param message 用户输入的信息
     * @return AI的回复信息
     */
    String chat(String message);

}
