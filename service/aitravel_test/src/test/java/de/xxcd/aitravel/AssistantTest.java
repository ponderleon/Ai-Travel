package de.xxcd.aitravel;

import de.xxcd.aitravel.assistant.*;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.image.ImageModel;
import dev.langchain4j.model.output.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AssistantTest {

    @Autowired
    private ChatModelAssistant chatModelAssistant;

    @Autowired
    private ChatModelSystemAssistant chatModelSystemAssistant;

    @Autowired
    private ChatModelMemoryAssistant chatModelMemoryAssistant;

    @Autowired
    private ImageModel imageModel;

    /**
     * 简单对话测试
     */
    @Test
    public void testChat() {

        String message = chatModelAssistant.chat("你好，请问你是谁？");

        System.out.println(message);

    }

    /**
     * 测试生成图片
     */
    @Test
    public void testImage() {

       //Response<Image> response = imageModel.generate("""
       //         奇幻森林精灵：在一片弥漫着轻柔薄雾的古老森林深处，阳光透过茂密枝叶洒下金色光斑。
       //         一位身材娇小、长着透明薄翼的精灵少女站在一朵硕大的蘑菇上。她有着海藻般的绿色长发，
       //         发间点缀着蓝色的小花，皮肤泛着珍珠般的微光。身上穿着由翠绿树叶和白色藤蔓编织而成的连衣裙，
       //         手中捧着一颗散发着柔和光芒的水晶球，周围环绕着五彩斑斓的蝴蝶，脚下是铺满苔藓的地面，
       //         蘑菇和蕨类植物丛生，营造出神秘而梦幻的氛围。
       //         """);

       // https://dashscope-result-wlcb-acdr-1.oss-cn-wulanchabu-acdr-1.aliyuncs.com/7d/5f/20250928/c1a4ba8a/dc8a8e5b-42e1-46b4-a8be-afff57b1dc26-1.png?Expires=1759652872&OSSAccessKeyId=LTAI5tKPD3TMqf2Lna1fASuh&Signature=EzLNOA8kY9jdgrek%2FBOd7Fs8eZQ%3D
       //Response<Image> response = imageModel.generate("""
       //         奇幻森林精灵：在一片弥漫着轻柔薄雾的古老森林深处，阳光透过茂密枝叶洒下金色光斑。
       //         一位身材娇小、长着透明薄翼的精灵男孩飞在空中。他有着阳光般的橙色长发，
       //         头上戴着一个彩色花朵的花圈，皮肤泛着珍珠般的微光。身上穿着由翠绿树叶和白色藤蔓编织而成的衣服，
       //         手中捧着一束漂亮的花朵，周围环绕着五彩斑斓的蝴蝶，脚下是铺满苔藓的地面，
       //         蘑菇和蕨类植物丛生，营造出神秘而梦幻的氛围。
       //         """);
       //Response<Image> response = imageModel.generate("""
       //         蒸汽朋克城市：巨大的齿轮和管道纵横交错，覆盖着整个城市的建筑。高耸入云的烟囱中喷出浓浓的黑烟，
       //         天空被染成了暗灰色。街道上，蒸汽驱动的机械车辆穿梭往来，发出嘈杂的轰鸣声。人们穿着皮质的长风衣、
       //         戴着护目镜和金属头盔，手中拿着各种机械工具和武器。一座巨大的钟楼矗立在城市中央，齿轮在钟楼上飞速转动，
       //         钟声沉闷而悠远。城市边缘，巨大的蒸汽动力飞行器缓缓升空，准备开始新的旅程。
       //         """);
       //Response<Image> response = imageModel.generate("""
       //         古风庭院雅集：一座宁静优美的中式庭院，亭台楼阁错落有致。池塘中，荷花盛开，
       //         荷叶田田，红色的锦鲤在水中悠然游动。庭院中央的石桌上摆放着笔墨纸砚和茶具，
       //         几位身着古装的文人雅士围坐在一起，或挥毫泼墨，或品茗吟诗。一位身着淡蓝色长袍的书生正低头沉思，
       //         旁边的一位女子手持团扇，微笑着看向他。庭院四周，翠竹环绕，石径通幽，空气中弥漫着淡淡的花香，营造出一种闲适雅致的氛围。
       //         """);
       //Response<Image> response = imageModel.generate("""
       //         未来科技都市夜景：城市的天际线被无数闪烁的霓虹灯光和全息投影照亮，高楼大厦的外墙上不断变换着各种绚丽的图案和广告。
       //         巨大的悬浮列车在城市上空呼啸而过，留下一道道光影。街道上，行人穿着带有科技感的服饰，衣服上的发光装置闪烁着不同的颜色。
       //         机器人在人群中穿梭，执行着各种任务。城市的中心是一座巨大的能源塔，塔顶喷射出蓝色的光芒，照亮了周围的夜空。
       //         远处的桥梁连接着不同的建筑，桥梁上也布满了各种灯光和显示屏，整个城市充满了未来感和科技感。
       //         """);
       Response<Image> response = imageModel.generate("""
                旅行助手：这是一个可爱乖巧的旅行助手，它帮助用户解决旅行难题，提供旅行服务与计划和建议，充满智慧，像一个贴心的朋友。
                它有着圆润的身体，柔和的线条，整体设计简洁现代。它的头部是一个大大的屏幕，可以显示各种信息和表情。
                它的眼睛是两个圆形的摄像头，能够识别用户的面部表情和动作。它的嘴巴是一个扬声器，可以发出清晰的声音。
                它的身体上有几个按钮和触摸屏，用户可以通过它们与旅行助手互动。它的底部有一个圆形的底座，可以360度旋转，方便用户随时查看信息。
                它的颜色主要是蓝色和白色，给人一种科技感和信赖感。整体形象友好，充满活力，像一个随时准备帮助用户的旅行伙伴。
                图片风格：卡通插画，明亮的色彩，简洁的线条，充满科技感和未来感。图片是圆形的，只显示助手部分，不添加背景，适合用作应用程序图标。
                """);

        System.out.println(response.content().url());

    }


    /**
     * 系统消息测试
     */
    @Test
    public void testSystemChat() {

        String message = chatModelSystemAssistant.chat("你是谁？");

        //String message = chatModelSystemAssistant.chatFile("请问Java中的单例模式有哪几种实现方式");

        System.out.println(message);

    }


    /**
     * 测试聊天记忆
     */
    @Test
    public void testMemory() {

        String message = chatModelMemoryAssistant.chat("你好，我是小中");

        System.out.println(message);

        message = chatModelMemoryAssistant.chat("我是谁");

        System.out.println(message);

    }

    @Autowired
    private ChatModelMemoryProviderAssistant chatModelMemoryProviderAssistant;

    @Test
    public void testMemoryProvider() {

        System.out.println("当前线程ID：====================================" + Thread.currentThread().getId());
        String message = chatModelMemoryProviderAssistant.chat(1, "你好，我是小中");

        System.out.println(message);

        message = chatModelMemoryProviderAssistant.chat(1, "我是谁");

        System.out.println(message);

        message = chatModelMemoryProviderAssistant.chat(2, "我是谁");

        System.out.println(message);
        System.out.println("当前线程ID：====================================" + Thread.currentThread().getId());


    }

    @Autowired
    private ChatModelCustomMemoryProviderAssistant chatModelCustomMemoryProviderAssistant;

    @Test
    public void testCustomMemoryProvider() {

        System.out.println("当前线程ID：====================================" + Thread.currentThread().getId());

        String message = chatModelCustomMemoryProviderAssistant.chat(1L, "你好我是小智");

        System.out.println(message);

        message = chatModelCustomMemoryProviderAssistant.chat(1L, "你好，请问我是谁");

        System.out.println(message);

        message = chatModelCustomMemoryProviderAssistant.chat(2L,"你好，请问我是谁");

        System.out.println(message);

        System.out.println("当前线程ID：====================================" + Thread.currentThread().getId());
    }




}
