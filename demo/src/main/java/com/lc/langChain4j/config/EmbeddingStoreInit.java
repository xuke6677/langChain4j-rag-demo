package com.lc.langChain4j.config;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class EmbeddingStoreInit {

    final PgConfig pgConfig;
    final EmbeddingModel embeddingModel;

    @Bean
    public EmbeddingStore<TextSegment> initEmbeddingStore() {
        return PgVectorEmbeddingStore.builder()
            .table(pgConfig.getTable())
            .dropTableFirst(true) //先删除表
            .createTable(true)//再重启创建表
            .host(pgConfig.getHost())
            .port(pgConfig.getPort())
            .user(pgConfig.getUser())
            .password(pgConfig.getPassword())
                // .dimension(384)
               .dimension(1536)//要与嵌入模型输出维度一致
            .database(pgConfig.getDatabase())
            .build();

    }
}