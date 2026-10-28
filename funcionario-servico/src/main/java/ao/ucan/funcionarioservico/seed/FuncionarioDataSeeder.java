package ao.ucan.funcionarioservico.seed;

import ao.ucan.funcionarioservico.model.Funcionario;
import ao.ucan.funcionarioservico.repositorio.FuncionarioRepositorio;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
public class FuncionarioDataSeeder {

    @Bean
    CommandLineRunner gerarFuncionarios(FuncionarioRepositorio repository) {
        return args -> {

            // Evita inserir novamente os dados em cada arranque.
            if (repository.count() > 0) {
                System.out.println(
                    "Já existem funcionários na base de dados. Seeder ignorado."
                );
                return;
            }

            Faker faker = new Faker(new Locale("pt"));

            String[] cargos = {
                "Analista de Sistemas",
                "Programador Java",
                "Técnico de Redes",
                "Técnico de Suporte",
                "Administrador de Sistemas",
                "Analista de Segurança",
                "Gestor de Projetos"
            };

            for (int i = 0; i < 20; i++) {

                Funcionario funcionario = new Funcionario();

                funcionario.setNome(faker.name().fullName());

                funcionario.setEmail(
                    "funcionario" + (i + 1) + "@example.com"
                );

                funcionario.setCargo(
                    cargos[ThreadLocalRandom.current()
                        .nextInt(cargos.length)]
                );

                int salario = ThreadLocalRandom.current()
                    .nextInt(150000, 601000);

                funcionario.setSalarioBase(
                    BigDecimal.valueOf(salario)
                );

                repository.save(funcionario);
            }

            System.out.println(
                "Seeder concluído: 20 funcionários inseridos no MongoDB."
            );
        };
    }
}