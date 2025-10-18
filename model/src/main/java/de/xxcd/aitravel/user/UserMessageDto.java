package de.xxcd.aitravel.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户消息传输对象")
public class UserMessageDto {

    @Schema(description = "聊天记忆ID")
    private Long memoryId;

    @Schema(description = "用户消息")
    private String message;

}
