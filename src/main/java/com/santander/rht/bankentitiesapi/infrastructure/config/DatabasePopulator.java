package com.santander.rht.bankentitiesapi.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * Database population component that runs on application startup.
 * Populates the banks table with sample data if it's empty.
 */
@Component
@Slf4j
public class DatabasePopulator {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public DatabasePopulator() {
        log.info("DatabasePopulator component created!");
    }

    @PostConstruct
    public void populateDatabase() {
        log.info("DatabasePopulator.populateDatabase() called - Starting database population check...");

        try {
            // Check if banks table has data
            Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM banks", Integer.class);

            if (count == null || count == 0) {
                log.info("Banks table is empty. Populating with sample data...");
                populateBanks();
            } else {
                log.info("Banks table already contains {} records. Skipping population.", count);
            }
        } catch (Exception e) {
            log.error("Error during database population: {}", e.getMessage(), e);
        }
    }

    private void populateBanks() {
        // Use H2-compatible MERGE syntax instead of PostgreSQL's ON CONFLICT
        String[] banks = {
            "('SANDESMMXXX', 'Banco Santander', 'Paseo de la Castellana 83-85', 'Madrid', 'Spain', 'ES', '+34915123000', 'info@santander.es', 'https://www.santander.es', 'COMMERCIAL', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)",
            "('BBVAESMM', 'Banco Bilbao Vizcaya Argentaria', 'Plaza de San Nicolás 4', 'Bilbao', 'Spain', 'ES', '+34944876000', 'info@bbva.es', 'https://www.bbva.es', 'COMMERCIAL', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)",
            "('CAIXESBB', 'CaixaBank', 'Avenida Diagonal 621-629', 'Barcelona', 'Spain', 'ES', '+34935046000', 'info@caixabank.es', 'https://www.caixabank.es', 'COMMERCIAL', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)",
            "('DEUTDEFF', 'Deutsche Bank', 'Taunusanlage 12', 'Frankfurt', 'Germany', 'DE', '+4969910000', 'info@db.com', 'https://www.db.com', 'INVESTMENT', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)",
            "('BNPAFRPP', 'BNP Paribas', '16 Boulevard des Italiens', 'Paris', 'France', 'FR', '+33142980000', 'info@bnpparibas.fr', 'https://www.bnpparibas.fr', 'COMMERCIAL', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)"
        };

        try {
            int totalInserted = 0;
            for (String bankData : banks) {
                String sql = "MERGE INTO banks (swift_code, name, address, city, country, country_code, phone_number, email, website, bank_type, active, created_at, updated_at) VALUES " + bankData;
                try {
                    int rowsAffected = jdbcTemplate.update(sql);
                    totalInserted += rowsAffected;
                } catch (Exception e) {
                    log.debug("Bank already exists, skipping: {}", e.getMessage());
                }
            }
            
            log.info("Successfully inserted {} sample banks into the database.", totalInserted);

            // Verify the insertion
            Integer finalCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM banks", Integer.class);
            log.info("Total banks in database after population: {}", finalCount);

        } catch (Exception e) {
            log.error("Failed to populate banks table: {}", e.getMessage(), e);
            throw e;
        }
    }
}
