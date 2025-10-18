package de.xxcd.aitravel.assistant;

import dev.langchain4j.data.image.Image;

/**
 * 图像生成模型不支持AI Service功能
 */
public interface ImageModelAssistant {

    Image generateImage(String message);

}
