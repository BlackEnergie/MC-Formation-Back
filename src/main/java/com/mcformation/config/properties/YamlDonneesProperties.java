package com.mcformation.config.properties;

import com.mcformation.model.database.Domaine;
import com.mcformation.model.database.Role;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "data")
@PropertySource(value = "classpath:data/application.data.yml", factory = YamlPropertySourceFactory.class)
public class YamlDonneesProperties {

    private List<Role> roles;
    private List<Domaine> domaines;

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Domaine> getDomaines() {
        return domaines;
    }

    public void setDomaines(List<Domaine> domaines) {
        this.domaines = domaines;
    }
}
