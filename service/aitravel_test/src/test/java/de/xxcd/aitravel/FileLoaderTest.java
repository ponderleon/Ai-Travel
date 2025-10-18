package de.xxcd.aitravel;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class FileLoaderTest {

    @Test
    public void testFileLoader() {

        /**
         * 读取一个文件，文件类型可以是：
         */
        //Document document = FileSystemDocumentLoader.loadDocument("/Users/miniserver/Desktop/knowledge/测试.txt");

        //System.out.println(document.text());

        /**
         * 读取目标目录下的所有文件，不包含子目录，默认支持解析的文件类型有：TXT, HTML, MD
         */
        List<Document> documents = FileSystemDocumentLoader.loadDocuments("/Users/miniserver/Desktop/knowledge");


        documents.forEach(document -> {

            System.out.println("===============================");

            System.out.println(document.metadata());

            System.out.println(document.text());

        });

        /**
         * 递归读取目标目录下的所有文件，还会读取子目录下的文件，默认支持解析的文件类型有：TXT, HTML, MD
         */
        List<Document> documentList = FileSystemDocumentLoader.loadDocumentsRecursively("/Users/miniserver/Desktop/knowledge");


    }

    @Test
    public void testDocumentParser() {

        /**
         * 读取目标目录下的所有文件，不包含子目录，使用自定义的DocumentParser，支持解析的文件类型有：PDF
         */
        //List<Document> documents = FileSystemDocumentLoader.loadDocuments("/Users/miniserver/Desktop/knowledge", new ApachePdfBoxDocumentParser());
        //
        //documents.forEach(document -> {
        //
        //    System.out.println("===============================");
        //
        //    System.out.println(document.metadata());
        //
        //    System.out.println(document.text());
        //
        //});

        /**
         * 递归读取目标目录下的所有文件，还会读取子目录下的文件，使用自定义的DocumentParser，支持解析的文件类型有：PDF
         */
        //List<Document> documents = FileSystemDocumentLoader.loadDocumentsRecursively("/Users/miniserver/Desktop/knowledge", new ApachePdfBoxDocumentParser());
        //
        //documents.forEach(document -> {
        //    System.out.println("===============================");
        //
        //    System.out.println(document.metadata());
        //
        //    System.out.println(document.text());
        //});

        /**
         * 递归读取目标目录下的所有文件，还会读取子目录下的文件，使用自定义的DocumentParser，支持解析的文件类型有：DOC, DOCX, XLS, XLSX, PPT, PPTX
         */
        //List<Document> documents = FileSystemDocumentLoader.loadDocumentsRecursively("/Users/miniserver/Desktop/knowledge",new ApachePoiDocumentParser());
        //
        //documents.forEach(document -> {
        //
        //    System.out.println("===============================");
        //
        //    System.out.println(document.metadata());
        //
        //    System.out.println(document.text());
        //
        //});

        /**
         * 递归读取目标目录下的所有文件，还会读取子目录下的文件，使用自定义的DocumentParser，支持解析的文件类型有：PDF, DOC, DOCX, XLS, XLSX, PPT, PPTX, TXT, HTML, MD
         */
        List<Document> documents = FileSystemDocumentLoader.loadDocumentsRecursively("/Users/miniserver/Desktop/knowledge",new ApacheTikaDocumentParser());

        documents.forEach(document -> {
            System.out.println("===============================");

            System.out.println(document.metadata());

            System.out.println(document.text());
        });

    }


}
