package tech.hephaestusforge.messaging.antenna.configuration.persistence;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import jakarta.persistence.spi.ClassTransformer;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import tech.hephaestusforge.messaging.antenna.configuration.ApplicationProperty;
import tech.hephaestusforge.messaging.antenna.model.datasource.ConversationModel;
import tech.hephaestusforge.messaging.antenna.model.datasource.ConversationParticipantModel;
import tech.hephaestusforge.messaging.antenna.model.datasource.MessageModel;
import tech.hephaestusforge.messaging.antenna.model.datasource.UserModel;
import tech.hephaestusforge.messaging.antenna.model.datasource.derived.ConversationJoinParticipantModel;

import javax.sql.DataSource;
import java.net.URL;
import java.util.List;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class ApplicationPersistenceUnit implements PersistenceUnitInfo {
    private final ApplicationProperty property;

    @Override
    public String getPersistenceUnitName() {
        return this.property.getName();
    }

    @Override
    public String getPersistenceProviderClassName() {
        return org.hibernate.jpa.HibernatePersistenceProvider.class.getName();
    }

    @Override
    public PersistenceUnitTransactionType getTransactionType() {
        // use Jta if Jndi (application container defined)
        return PersistenceUnitTransactionType.RESOURCE_LOCAL;
    }

    @Override
    public DataSource getJtaDataSource() {
        // use Jta if Jndi (application container defined)
        return null;
    }

    @Override
    public DataSource getNonJtaDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(this.property.getDriverClassName());
        hikariDataSource.setJdbcUrl(this.property.getUrl());
        hikariDataSource.setUsername(this.property.getUsername());
        hikariDataSource.setPassword(this.property.getPassword());

        return hikariDataSource;
    }

    @Override
    public List<String> getMappingFileNames() {
        return null;
    }

    @Override
    public List<URL> getJarFileUrls() {
        return null;
    }

    @Override
    public URL getPersistenceUnitRootUrl() {
        return null;
    }

    @Override
    public List<String> getManagedClassNames() {
        // @Todo programmatically fetch all classes in package
        // checkout google guava ClassPath
        return List.of(
                ConversationModel.class.getCanonicalName(),
                ConversationParticipantModel.class.getCanonicalName(),
                ConversationJoinParticipantModel.class.getCanonicalName(),
                MessageModel.class.getCanonicalName(),
                UserModel.class.getCanonicalName()
        );
    }

    @Override
    public boolean excludeUnlistedClasses() {
        return false;
    }

    @Override
    public SharedCacheMode getSharedCacheMode() {
        return null;
    }

    @Override
    public ValidationMode getValidationMode() {
        return null;
    }

    @Override
    public Properties getProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.allow_update_outside_transaction", "true");

        return properties;
    }

    @Override
    public String getPersistenceXMLSchemaVersion() {
        return null;
    }

    @Override
    public ClassLoader getClassLoader() {
        return null;
    }

    @Override
    public void addTransformer(ClassTransformer classTransformer) {

    }

    @Override
    public ClassLoader getNewTempClassLoader() {
        return null;
    }
}
