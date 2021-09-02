package br.gov.es.pm.sbp.filedatabase;

import br.gov.es.pm.sbp.config.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public abstract class FileDatabase {
    private final Logger log = LoggerFactory.getLogger(DocumentoFileDatabase.class);
    private String rootDirectory;

    public FileDatabase(ApplicationProperties applicationProperties) {
        this.rootDirectory = applicationProperties.getFileDatabasePath() + File.separator;
        createDirectories();
    }

    public String getRootDirectory() {
        return rootDirectory;
    }

    protected abstract Stream<String> directories();

    private void createDirectories() {
        directories().forEach(directory ->
            (new File(rootDirectory + directory)).mkdirs()
        );
    }

    protected String getAbsolutePath(String relativePath) {
        return rootDirectory + relativePath;
    }

    public boolean fileSilentDelete(String relativePath) {
        return (new File(getAbsolutePath(relativePath))).delete();
    }

    public boolean fileDelete(String relativePath) throws IOException {
        return Files.deleteIfExists(Paths.get(getAbsolutePath(relativePath)));
    }
}
