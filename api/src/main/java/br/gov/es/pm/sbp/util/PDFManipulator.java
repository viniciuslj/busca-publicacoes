package br.gov.es.pm.sbp.util;

/**
 * https://github.com/viniciuslj/split-ingest-pdf/blob/master/src/main/java/com/github/viniciuslj/pdf/PDFManipulator.java
 */

import org.apache.logging.log4j.util.BiConsumer;
import org.apache.pdfbox.multipdf.PageExtractor;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Component
public class PDFManipulator {
    private PDFTextStripper stripper;
    private Splitter splitter;

    public PDFManipulator() throws IOException {
        stripper = new PDFTextStripper();
        splitter = new Splitter();
    }

    public PDDocument load(Path path) throws IOException {
        return PDDocument.load(new File(path.toString()));
    }

    public List<PDDocument> split(Path path) throws IOException {
        PDDocument pdDocument = load(path);
        List<PDDocument> pdDocuments = splitter.split(pdDocument);
        pdDocument.close();
        return pdDocuments;
    }

    public String getText(PDDocument document) throws IOException {
        return stripper.getText(document);
    }

    public int processPages(List<PDDocument> pages, BiConsumer<Integer, String> consumer) throws IOException {
        for (int i = 0; i < pages.size(); i++) {
            consumer.accept(i + 1, stripper.getText(pages.get(i)));
        }

        return pages.size();
    }

    public PDDocument extract(PDDocument document, int start, int end) throws IOException {
        PageExtractor extractor = new PageExtractor(document, start, end);
        return extractor.extract();
    }

    public PDDocument extract(Path path, int start, int end) throws IOException {
        PageExtractor extractor = new PageExtractor(load(path), start, end);
        return extractor.extract();
    }

    public byte[] toByteArray(PDDocument document) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        document.save(stream);
        document.close();
        return stream.toByteArray();
    }
}
