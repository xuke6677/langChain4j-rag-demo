package com.lc.langChain4j.config;

import com.lc.langChain4j.service.Assistant;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AssistantConfig {

    final ChatLanguageModel chatLanguageModel;


    @Bean
    public Assistant assistant(EmbeddingStore<TextSegment> embeddingStore) {
        return AiServices.builder(Assistant.class)
                //.chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(15))
                .contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore))
                .chatLanguageModel(chatLanguageModel)
                .build();
    }

}