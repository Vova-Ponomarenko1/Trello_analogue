package org.example;

import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FlywayMigrationRunner implements CommandLineRunner {
    private final Flyway flyway;

    public FlywayMigrationRunner(Flyway flyway) {
        this.flyway = flyway;
    }

    @Override
    public void run(String... args) throws Exception {
        flyway.migrate();
    }
}
