package br.gov.es.pm.sbp.filedatabase;

import br.gov.es.pm.sbp.config.ApplicationProperties;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
public class DocumentoFileDatabase extends FileDatabase {
    private static final String DIR = "private/documentos";

    private final Logger log = LoggerFactory.getLogger(DocumentoFileDatabase.class);

    public DocumentoFileDatabase(ApplicationProperties applicationProperties) {
        super(applicationProperties);
    }

    @Override
    protected Stream<String> directories() {
        return Stream.<String>builder()
            .add(DIR)
            .build();
    }

    @Override
    protected String getAbsolutePath(String diretorio) {
        return super.getAbsolutePath(DIR + File.separator + diretorio);
    }

    public boolean addDiretorio(String name) {
        File file = new File(getAbsolutePath(name));
        return file.mkdir();
    }

    public Path addDocumento(String diretorio, String nomeArquivo, MultipartFile arquivo) throws IOException {
        addDiretorio(diretorio);
        Path filepath = Paths.get(getAbsolutePath(diretorio), nomeArquivo);
        try(OutputStream outputStream = Files.newOutputStream(filepath)) {
            outputStream.write(arquivo.getBytes());
            return filepath;
        }
    }

    public Path addDocumento(String diretorio, String nomeArquivo, String nomeArquivoOrigem) throws IOException {
        addDiretorio(diretorio);
        return Files.move(Paths.get(nomeArquivoOrigem), Paths.get(getAbsolutePath(diretorio), nomeArquivo));
    }

    public byte[] getDocumento(String diretorio, String nomeArquivo) throws IOException {
        Path filepath = getPathDocumento(diretorio, nomeArquivo);
        try(InputStream inputStream = Files.newInputStream(filepath)) {
            return IOUtils.toByteArray(inputStream);
        }
    }

    public Path getPathDocumento(String diretorio, String nomeArquivo) throws IOException {
        return Paths.get(getAbsolutePath(diretorio), nomeArquivo);
    }

    public boolean deleteDocumento(String diretorio, String nomeArquivo) throws IOException {
        Path filepath = Paths.get(getAbsolutePath(diretorio), nomeArquivo);
        return Files.deleteIfExists(filepath);
    }
}
