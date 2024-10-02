package com.example.chromadbrag.controller;

import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AIController {

    @Value("classpath:input.txt")
    Resource resourceFile;

    VectorStore vectorStore;

    public AIController(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @GetMapping("/load")
    public String load() throws IOException {
        List<Document> documents = Files.lines(resourceFile.getFile().toPath())
                .map(Document::new)
                .toList();
        TextSplitter textSplitter = new TokenTextSplitter();
        for(int i=0;i<documents.size();i++){
            List<Document> splittedDocs = textSplitter.split(documents.get(i));
            vectorStore.add(splittedDocs);
            System.out.println("Document "+documents.get(i).getContent()+" added to vector store");
        }
        return resourceFile.getFilename();
    }


    @GetMapping("/search")
    public String search(@RequestParam(value = "message", defaultValue = "Big Little Lies by Liane Moriarty") String message) {
        List<Document> documents = vectorStore.similaritySearch(SearchRequest.query(message).withTopK(2));
        return documents.stream().
                map(Document::getContent).
                collect(Collectors.joining(", "));
    }
}
