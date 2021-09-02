package br.gov.es.pm.sbp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Sbp.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
    private String chaveToken;
    private String fileDatabasePath;
    private String batchFilesPath;

    public String getChaveToken() {
        return chaveToken;
    }

    public void setChaveToken(String chaveToken) {
        this.chaveToken = chaveToken;
    }

    public String getFileDatabasePath() {
        return fileDatabasePath;
    }

    public void setFileDatabasePath(String fileDatabasePath) {
        this.fileDatabasePath = fileDatabasePath;
    }

    public String getBatchFilesPath() {
        return batchFilesPath;
    }

    public void setBatchFilesPath(String batchFilesPath) {
        this.batchFilesPath = batchFilesPath;
    }
}
