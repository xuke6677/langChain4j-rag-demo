package com.lc.langChain4j.controller;

import com.lc.langChain4j.service.Assistant;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentByLineSplitter;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/rag")
@RequiredArgsConstructor
public class RagController {

    final EmbeddingStore<TextSegment> embeddingStore;

    final EmbeddingModel embeddingModel;

    /**
     * 加载文件到向量数据库
     *
     * @return
     */
    @GetMapping("/load")
    public Object load() {
        //Document documents = ClassPathDocumentLoader.loadDocument("documents/2025年中国房地产市场发展趋势分析.txt", new TextDocumentParser());
        List<Document> documents = FileSystemDocumentLoader.loadDocuments("E:\\code\\AI\\langChain4j-demo\\demo\\target\\classes\\documents");
        EmbeddingStoreIngestor.builder().embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .documentSplitter(new DocumentByLineSplitter(30, 20))
                .build().ingest(documents);
        return "success";
    }

    final Assistant assistant;

    /**
     * 对话
     *
     * @return
     */
    @GetMapping("/chat")
    public Object chat(@RequestParam(value = "message") String message) {
        TextSegment textSegment = TextSegment.from("test text");
        float[] embedding = embeddingModel.embed(textSegment.text()).content().vector();
        log.info("chat Embedding dimension: {}", embedding.length);

        return assistant.chat(message);
    }

}