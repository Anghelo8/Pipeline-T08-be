package pe.edu.vallegrande.losreyes.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;

@Configuration
@EnableReactiveMongoAuditing
public class MongoConfig {
    // Esta clase vacía activa el motor de auditoría reactiva para MongoDB
}