package de.xxcd.aitravel.controller;

import de.xxcd.aitravel.assistant.AiTravelAssistant;
import de.xxcd.aitravel.user.UserMessageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Schema(description = "智能旅行")
@RestController
@RequestMapping("/api/aitravel")
public class AiTravelController {

    /**
     * 注入AiTravelAssistant
     */
    @Autowired
    private AiTravelAssistant aiTravelAssistant;


    /**
     * 流式输出聊天接口
     * @param userMessageDto 用户传递过来的信息对象
     * @return Flux<String> 流式输出聊天结果
     */
    @Operation(description = "聊天接口")
    @PostMapping(value = "/chat", produces = "text/stream;charset=utf-8")
    public Flux<String> chat(@Parameter(description = "用户传递过来的信息对象",required = true) @RequestBody UserMessageDto userMessageDto){
        //System.out.println("======================"+userMessageDto);
        return aiTravelAssistant.chat(userMessageDto.getMemoryId(),userMessageDto.getMessage());
    }





}
