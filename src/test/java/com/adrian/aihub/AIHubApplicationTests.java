package com.adrian.aihub;

import com.adrian.aihub.utils.VectorDistanceUtils;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class AIHubApplicationTests {

    @Autowired
    private OpenAiEmbeddingModel embeddingModel;

    @Autowired
    private VectorStore vectorStore;

    @Test
    void contextLoads() {
    }

    @Test
    public void testEmbedding() {
        // 1.测试数据
        // 1.1.用来查询的文本
        String query = "global conflicts";

        // 1.2.用来做比较的文本
        String[] texts = new String[] {
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

    @Test
    public void testVectorStore() {
        Resource resource = new FileSystemResource("src/main/resources/static/面试宝典.pdf");
        // 1.创建PDF的读取器
        PagePdfDocumentReader reader = new PagePdfDocumentReader(
                resource, // 文件源
                PdfDocumentReaderConfig.builder()
                        .withPageExtractedTextFormatter(ExtractedTextFormatter.defaults())
                        .withPagesPerDocument(1) // 每1页PDF作为一个Document
                        .build());
        // 2.读取PDF文档，拆分为Document
        List<Document> documents = reader.read();
        // 3.写入向量库
        vectorStore.add(documents);
        // 4.搜索
        SearchRequest request = SearchRequest.builder()
                .query("什么是分布式事务啊")
                .topK(1)
                .similarityThreshold(0.6)
                .filterExpression("file_name == '面试宝典.pdf'")
                .build();
        List<Document> docs = vectorStore.similaritySearch(request);
        if (docs == null) {
            System.out.println("没有搜索到任何内容");
            return;
        }
        for (Document doc : docs) {
            System.out.println(doc.getId());
            System.out.println(doc.getScore());
            System.out.println(doc.getText());
        }
    }
}