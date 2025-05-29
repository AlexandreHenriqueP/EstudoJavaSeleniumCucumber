package br.com.linkedin.steps;

// Importações do Cucumber.
import io.cucumber.java.After; // Hook executado após cada cenário.
import io.cucumber.java.Before; // Hook executado antes de cada cenário.
import io.cucumber.java.Scenario; // Informações sobre o cenário atual.
import io.cucumber.java.pt.Dado; // Anotação para mapear passos 'Dado' (Given).
import io.cucumber.java.pt.Então; // Anotação para mapear passos 'Então' (Then).
import io.cucumber.java.pt.Quando; // Anotação para mapear passos 'Quando' (When).

// Importações do Selenium WebDriver.
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver; // Exemplo usando ChromeDriver.

// Você pode precisar de outros drivers como FirefoxDriver, EdgeDriver, etc.
// import org.openqa.selenium.firefox.FirefoxDriver;

// Importação da classe Page Object.
import br.com.linkedin.page.*;

// Importações do JUnit para asserções (verificações).
import static org.junit.Assert.fail;

/**
 * Esta classe contém as definições dos Steps (passos) do Cucumber para a funcionalidade de Login.
 * Cada método anotado (@Dado, @Quando, @Então) corresponde a um passo no arquivo .feature (login.feature).
 * Esta classe utiliza a LoginPage (Page Object) para interagir com a página web.
 */
public class LoginSteps {

    // Instância do WebDriver para controlar o navegador.
    // É declarada como static para ser compartilhada entre os steps do mesmo cenário,
    // mas uma gestão melhor (ex: usando injeção de dependência como PicoContainer) é recomendada em projetos maiores.
    private WebDriver driver;

    // Instância da nossa Page Object LoginPage.
    private LoginPage loginPage;

    // URL base da aplicação. **IMPORTANTE:** Substitua pela URL real da sua aplicação.
    private final String BASE_URL = "https://www.linkedin.com/checkpoint/rm/sign-in-another-account?fromSignIn=true&trk=guest_homepage-basic_nav-header-signin"; // <<< COLOQUE A URL BASE AQUI

    // --- Hooks do Cucumber (@Before e @After) ---

    /**
     * Método executado ANTES de cada cenário do Cucumber.
     * Responsável por inicializar o WebDriver e a Page Object.
     * Configura o ChromeDriver. Você pode precisar ajustar o caminho do chromedriver
     * ou usar um gerenciador de drivers como o WebDriverManager.
     */
    @Before
    public void setUp() {
        // Configurações do Chrome (ex: modo headless, ignorar erros de certificado, etc.)
        // ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless"); // Executa sem abrir a janela do navegador
        // options.addArguments("--disable-gpu");
        // options.addArguments("--window-size=1920,1080");
        // options.addArguments("--ignore-certificate-errors");

        // Inicializa o WebDriver para o Chrome.
        // Certifique-se de que o chromedriver está no PATH do sistema ou especifique o caminho:
        System.setProperty("webdriver.chrome.driver", "src/test/java/br/com/linkedin/resource/driver/chromedriver.exe");
        driver = new ChromeDriver(/*options*/); // Passa as opções se estiver usando
        driver.manage().window().maximize(); 

        // Inicializa a Page Object, passando a instância do driver para ela.
        loginPage = new LoginPage(driver);
    }

    /**
     * Método executado DEPOIS de cada cenário do Cucumber.
     * Responsável por fechar o navegador e limpar os recursos.
     * Também pode tirar um screenshot em caso de falha.
     *
     * @param scenario O cenário que acabou de ser executado.
     */
    @After
    public void tearDown(Scenario scenario) {
        // Verifica se o cenário falhou.
        if (scenario.isFailed()) {
            // Tira um screenshot se o cenário falhar.
            try {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                // Anexa o screenshot ao relatório do Cucumber.
                scenario.attach(screenshot, "image/png", scenario.getName() + "_falhou");
            } catch (Exception e) {
                System.err.println("Erro ao tirar screenshot: " + e.getMessage());
            }
        }

        // Fecha o navegador se o driver não for nulo.
        if (driver != null) {
            driver.quit(); // Fecha todas as janelas e encerra a sessão do WebDriver.
        }
    }

    // --- Definições dos Steps (@Dado, @Quando, @Então) ---

    /**
     * Implementação do passo "Dado que o usuário está na página de login".
     * Utiliza o método navigateToLoginPage da LoginPage.
     */
    @Dado("que o usuário está na página de login")
    public void que_o_usuário_está_na_página_de_login() {
        // Chama o método da Page Object para navegar até a página.
        // **IMPORTANTE:** Verifique se a URL base e o caminho "/login" estão corretos em LoginPage.java
        loginPage.navigateToLoginPage(BASE_URL);
        System.out.println("Navegou para a página de login: " + BASE_URL + "/login"); // Log simples
    }

    /**
     * Implementação do passo "Quando o usuário insere o email {string}".
     * Recebe o email como parâmetro (vindo da tabela Exemplos no .feature).
     * Utiliza o método enterEmail da LoginPage.
     *
     * @param email O email a ser inserido.
     */
    @Quando("o usuário insere o email {string}")
    public void o_usuário_insere_o_email(String email) {
        // Chama o método da Page Object para inserir o email.
        // **IMPORTANTE:** Substitua "seu_email_valido@exemplo.com" no arquivo .feature pelo email correto.
        loginPage.enterEmail(email);
        System.out.println("Inseriu email: " + email);
    }

    /**
     * Implementação do passo "E o usuário insere a senha {string}".
     * Recebe a senha como parâmetro.
     * Utiliza o método enterPassword da LoginPage.
     *
     * @param password A senha a ser inserida.
     */
    @Quando("o usuário insere a senha {string}")
    public void o_usuário_insere_a_senha(String password) {
        // Chama o método da Page Object para inserir a senha.
        // **IMPORTANTE:** Substitua "sua_senha_correta", "senha_incorreta123", etc., no .feature pelas senhas adequadas.
        loginPage.enterPassword(password);
        System.out.println("Inseriu senha: [PROTEGIDO]"); // Não logar a senha em produção!
    }

    /**
     * Implementação do passo "E o usuário clica no botão de login".
     * Utiliza o método clickLoginButton da LoginPage.
     */
    @Quando("o usuário clica no botão de login")
    public void o_usuário_clica_no_botão_de_login() {
        // Chama o método da Page Object para clicar no botão.
        loginPage.clickLoginButton();
        System.out.println("Clicou no botão de login");
    }

    /**
     * Implementação do passo "Então o usuário deve ser logado com sucesso".
     * Verifica se o login foi bem-sucedido usando o método isLoginSuccessful da LoginPage.
     * Usa assertTrue do JUnit para a asserção.
     */
    @Então("o usuário deve ser logado com sucesso")
    public void o_usuário_deve_ser_logado_com_sucesso() {
        // Chama o método da Page Object que verifica um indicador de sucesso na página.
        // **IMPORTANTE:** Certifique-se que o 'successIndicatorLocator' em LoginPage.java
        // realmente aponta para um elemento que SÓ aparece após o login bem-sucedido.
        boolean errorDisplayed = loginPage.isLoginSuccessful();

        if (!errorDisplayed) { // Verifica se errorDisplayed é falso
        // Se for falso, falha o teste.
        fail("Uma mensagem de erro de login era esperada, mas não foi exibida. O método isErrorMessageDisplayed() retornou false.");
    }
        
        System.out.println("Verificação: Login bem-sucedido.");
    }

    /**
     * Implementação do passo "Então o usuário deve ver uma mensagem de erro de login".
     * Verifica se a mensagem de erro é exibida usando isErrorMessageDisplayed da LoginPage.
     * Usa assertTrue do JUnit para a asserção.
     */
    @Então("o usuário deve ver uma mensagem de erro de login")
    public void o_usuário_deve_ver_uma_mensagem_de_erro_de_login() {
        // Chama o método da Page Object que verifica se a mensagem de erro está visível.
        // **IMPORTANTE:** Certifique-se que o 'errorMessageLocator' em LoginPage.java
        // aponta para o elemento correto da mensagem de erro.
        boolean errorDisplayed = loginPage.isErrorMessageDisplayed();

        if (!errorDisplayed) { // Verifica se errorDisplayed é falso
        // Se for falso, falha o teste.
        fail("Uma mensagem de erro de login era esperada, mas não foi exibida. O método isErrorMessageDisplayed() retornou false.");
    }
        System.out.println("Verificação: Mensagem de erro exibida.");

        // Opcional: Verificar o texto da mensagem de erro (descomente a linha no .feature também)
        // String actualErrorMessage = loginPage.getErrorMessageText();
        // String expectedErrorMessagePartial = "Credenciais inválidas"; // Ou "Usuário não encontrado"
        // assertTrue(actualErrorMessage.contains(expectedErrorMessagePartial),
        //            "A mensagem de erro não contém o texto esperado. Esperado: '" + expectedErrorMessagePartial +
        //            "' | Atual: '" + actualErrorMessage + "'");
        // System.out.println("Verificação: Texto da mensagem de erro contém '" + expectedErrorMessagePartial + "'.");
    }
}


