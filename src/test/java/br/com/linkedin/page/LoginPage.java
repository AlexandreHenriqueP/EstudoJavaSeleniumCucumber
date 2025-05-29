package br.com.linkedin.page;

// Importações necessárias do Selenium WebDriver.
import org.openqa.selenium.By; // Para localizar elementos na página.
import org.openqa.selenium.WebDriver; // Interface principal para interação com o navegador.
import org.openqa.selenium.WebElement; // Representa um elemento HTML na página.
import org.openqa.selenium.support.ui.ExpectedConditions; // Para definir condições de espera explícitas.
import org.openqa.selenium.support.ui.WebDriverWait; // Para implementar esperas explícitas.
import java.time.Duration; // Para definir a duração das esperas.

/**
 * Esta classe representa a Página de Login (LoginPage) usando o padrão Page Object.
 * O padrão Page Object ajuda a organizar o código de automação, tornando-o mais legível,
 * manutenível e reutilizável. Cada página da aplicação web tem sua própria classe Page Object.
 *
 * Responsabilidades desta classe:
 * 1. Mapear os elementos da interface do usuário (UI) da página de login (campos, botões, etc.).
 * 2. Encapsular as ações que podem ser realizadas nesta página (digitar email, senha, clicar em login).
 */
public class LoginPage {

    // Instância do WebDriver que será usada para interagir com o navegador.
    // É 'private' para encapsulamento e 'final' porque não deve ser reatribuída após a inicialização.
    private final WebDriver driver;
    // Instância do WebDriverWait para gerenciar esperas explícitas.
    private final WebDriverWait wait;

    // --- Mapeamento dos Elementos da UI (WebElements) ---
    // Aqui você deve mapear os localizadores (By) para os elementos da página de login.
    // Use IDs, nomes, CSS Selectors ou XPath, preferencialmente nesta ordem de prioridade.
    // **IMPORTANTE:** Substitua os valores "seu_localizador_..." pelos localizadores reais da sua página.

    // Localizador para o campo de email/usuário.
    // Exemplo: By.id("username") ou By.cssSelector("#email-input")
    private final By emailInputLocator = By.id("username");

    // Localizador para o campo de senha.
    // Exemplo: By.name("password") ou By.xpath("//input[@type='password']")
    private final By passwordInputLocator = By.id("password");

    // Localizador para o botão de login.
    // Exemplo: By.tagName("button") ou By.cssSelector(".login-button")
    private final By loginButtonLocator = By.cssSelector("button[aria-label='Entrar']");

    // Localizador para uma mensagem de erro que aparece em caso de login inválido.
    // Este elemento só será visível após uma tentativa de login falha.
    // Exemplo: By.className("error-message") ou By.xpath("//div[contains(@class, 'alert-danger')]")
    private final By errorMessageLocator = By.id("error-for-password");

    // Localizador para um elemento que só aparece APÓS o login bem-sucedido (ex: nome do usuário no header).
    // Usado para verificar se o login foi realmente efetuado.
    // Exemplo: By.id("user-profile-link") ou By.cssSelector(".welcome-message")
    private final By successIndicatorLocator = By.id("ember36");

    // --- Construtor --- 
    /**
     * Construtor da classe LoginPage.
     * Recebe a instância do WebDriver do teste que a está utilizando.
     * Inicializa o WebDriverWait com um tempo limite (timeout) de 10 segundos.
     *
     * @param driver A instância do WebDriver.
     */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        // Define um tempo máximo de espera (10 segundos) para os elementos aparecerem.
        // Isso evita que o teste falhe imediatamente se um elemento demorar um pouco para carregar.
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // --- Métodos de Ação --- 
    // Estes métodos encapsulam as interações do usuário com a página de login.

    /**
     * Digita o endereço de email no campo correspondente.
     * Primeiro, espera até que o elemento esteja visível na página.
     * Depois, limpa qualquer texto existente e insere o email fornecido.
     *
     * @param email O endereço de email a ser digitado.
     */
    public void enterEmail(String email) {
        // Espera até que o campo de email esteja visível antes de interagir com ele.
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInputLocator));
        emailInput.clear(); // Limpa o campo caso haja algum texto preenchido.
        emailInput.sendKeys(email); // Digita o email.
    }

    /**
     * Digita a senha no campo correspondente.
     * Espera o campo ficar visível, limpa e insere a senha.
     *
     * @param password A senha a ser digitada.
     */
    public void enterPassword(String password) {
        // Espera até que o campo de senha esteja visível.
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInputLocator));
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    /**
     * Clica no botão de login.
     * Espera até que o botão esteja clicável antes de realizar a ação.
     */
    public void clickLoginButton() {
        // Espera até que o botão de login esteja pronto para ser clicado.
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(loginButtonLocator));
        loginButton.click();
    }

    /**
     * Método de conveniência que executa os passos de preencher email, senha e clicar em login.
     * Útil para simplificar os testes.
     *
     * @param email O email a ser usado no login.
     * @param password A senha a ser usada no login.
     */
    public void performLogin(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
    }

    // --- Métodos de Verificação (Assertions) ---
    // Estes métodos são usados para verificar o estado da página após uma ação.

    /**
     * Verifica se a mensagem de erro de login está visível na página.
     * Útil para confirmar que um login inválido resultou em erro.
     *
     * @return true se a mensagem de erro estiver visível, false caso contrário.
     */
    public boolean isErrorMessageDisplayed() {
        try {
            // Tenta localizar o elemento da mensagem de erro e verifica se está visível.
            // Usamos uma espera curta aqui, pois a mensagem deve aparecer rapidamente após o clique.
            WebElement errorMessage = new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOfElementLocated(errorMessageLocator));
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            // Se o elemento não for encontrado ou não estiver visível dentro do tempo, retorna false.
            return false;
        }
    }

    /**
     * Obtém o texto da mensagem de erro.
     * Primeiro, verifica se a mensagem está visível.
     *
     * @return O texto da mensagem de erro, ou uma string vazia se não estiver visível.
     */
    public String getErrorMessageText() {
        if (isErrorMessageDisplayed()) {
            // Se a mensagem estiver visível, localiza o elemento novamente e retorna seu texto.
            WebElement errorMessage = driver.findElement(errorMessageLocator);
            return errorMessage.getText();
        } else {
            return ""; // Retorna vazio se a mensagem não for encontrada.
        }
    }

    /**
     * Verifica se o indicador de sucesso de login está visível.
     * Este método confirma que o usuário foi redirecionado ou que um elemento esperado
     * após o login está presente na página.
     *
     * @return true se o indicador de sucesso estiver visível, false caso contrário.
     */
    public boolean isLoginSuccessful() {
        try {
            // Espera até que o elemento indicador de sucesso esteja visível.
            WebElement successIndicator = wait.until(ExpectedConditions.visibilityOfElementLocated(successIndicatorLocator));
            return successIndicator.isDisplayed();
        } catch (Exception e) {
            // Se o elemento não for encontrado ou não estiver visível, o login provavelmente falhou.
            return false;
        }
    }

    /**
     * Navega para a página de login.
     * **IMPORTANTE:** Substitua "URL_DA_SUA_PAGINA_DE_LOGIN" pela URL real.
     *
     * @param baseUrl A URL base da sua aplicação.
     */
    public void navigateToLoginPage(String baseUrl) {
        // Certifique-se de que a URL está correta!
        driver.get(baseUrl + "/login"); // Ou qualquer que seja o caminho para sua página de login
        // Você pode adicionar uma espera aqui para garantir que a página carregou completamente, se necessário.
        // Ex: wait.until(ExpectedConditions.visibilityOfElementLocated(emailInputLocator));
    }
}
