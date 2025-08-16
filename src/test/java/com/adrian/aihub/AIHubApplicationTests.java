package com.adrian.aihub;

import com.adrian.aihub.utils.VectorDistanceUtils;
import org.junit.jupiter.api.Test;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class AIHubApplicationTests {

    @Autowired
    private OpenAiEmbeddingModel embeddingModel;

    @Test
    void contextLoads() {
    }

    @Test
    public void testEmbedding() {
        // 1.测试数据
        // 1.1.用来查询的文本
        String query = "global conflicts";

        // 1.2.用来做比较的文本
        String[] texts = new String[]{
                "哈马斯称加沙下阶段停火谈判仍在进行 以方尚未做出承诺",
                "土耳其、芬兰、瑞典与北约代表将继续就瑞典\"入约\"问题进行谈判",
                "日本航空基地水井中检测出有机氟化物超标",
                "国家游泳中心（水立方）：恢复游泳、嬉水乐园等水上项目运营",
                "我国首次在空间站开展舱外辐射生物学暴露实验",
        };

        // 2.向量化
        // 2.1.先将查询文本向量化
        float[] queryVector = embeddingModel.embed(query);

        // 2.2.再将比较文本向量化，放到一个数组
        List<float[]> textVectors = embeddingModel.embed(Arrays.asList(texts));

        // 3.输出格式化的相似度结果
        System.out.println("用户输入文本【" + query + "】");
        System.out.println();

        // 3.1.欧氏距离计算
        System.out.println("以下是欧氏距离，距离越小相似度越高：");
        // 查询文本与自己的距离
        System.out.println("文本【" + query + "】与用户输入的文本欧式距离为：" +
                VectorDistanceUtils.euclideanDistance(queryVector, queryVector));

        // 查询文本与其他文本的距离
        for (int i = 0; i < texts.length; i++) {
            float distance = (float) VectorDistanceUtils.euclideanDistance(queryVector, textVectors.get(i));
            System.out.println("文本【" + texts[i] + "】与用户输入的文本欧式距离为：" + distance);
        }
        System.out.println();

        // 3.2.余弦相似度计算
        System.out.println("以下是余弦相似度，值越接近1相似度越高：");
        // 查询文本与自己的相似度
        double cosineSimilarity = 1.0 - VectorDistanceUtils.cosineDistance(queryVector, queryVector);
        System.out.println("文本【" + query + "】与用户输入的余弦相似度为：" + cosineSimilarity);

        // 查询文本与其他文本的相似度
        for (int i = 0; i < texts.length; i++) {
            double similarity = 1.0 - VectorDistanceUtils.cosineDistance(queryVector, textVectors.get(i));
            System.out.println("文本【" + texts[i] + "】与用户输入的余弦相似度为：" + similarity);
        }
    }
}