
package com.lunaltas.ListaUsuarios.config;

import org.javalite.activejdbc.Base;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Ligando à base de dados SQLite...");

        boolean conectado = false;

        try {
            Base.open(
                    "org.sqlite.JDBC",
                    "jdbc:sqlite:meu_app.db",
                    "",
                    ""
            );

            conectado = true;

            String sqlCreate = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name VARCHAR(56),
                    arroba VARCHAR(56),
                    cpf VARCHAR(14)
                )
                """;

            Base.exec(sqlCreate);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("erro", e);

        } finally {
            if (conectado) {
                Base.close();
                System.out.println("Ligação encerrada.");
            }
        }
    }
}
