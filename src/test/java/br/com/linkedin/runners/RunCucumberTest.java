package br.com.linkedin.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

/**
 * Esta é a classe Runner (Executora) para rodar os testes Cucumber com JUnit 4.
 * Ela usa anotações para configurar como o Cucumber deve encontrar e executar os testes.
 *
 * @RunWith(Cucumber.class): Indica ao JUnit para usar o executor do Cucumber.
 * @CucumberOptions: Configura o Cucumber.
 *   - features = "src/test/resources/features": Especifica o diretório onde estão os arquivos .feature.
 *   - glue = "com.example.steps": Especifica o pacote onde estão as classes com as definições de Steps (@Dado, @Quando, @Então).
 *   - plugin = {"pretty", "html:target/cucumber-reports.html"}: Define os formatos de relatório. 
 *     - "pretty": Imprime a saída formatada no console (o log que estava faltando!).
 *     - "html:target/cucumber-reports.html": Gera um relatório HTML na pasta 'target' após a execução.
 *   - monochrome = true: Melhora a legibilidade do output no console, removendo caracteres de cor estranhos em alguns terminais.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "com.example.steps",
    plugin = {"pretty", "html:target/cucumber-reports.html"},
    monochrome = true
)
public class RunCucumberTest {
    // Esta classe geralmente fica vazia.
    // As anotações fazem todo o trabalho de configuração.
}
