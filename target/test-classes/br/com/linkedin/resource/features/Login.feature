# language: pt
# Este é um arquivo Feature do Cucumber.
# Ele descreve a funcionalidade de "Login" em um formato legível por humanos (Gherkin).
# Cada funcionalidade é dividida em Cenários, que representam casos de teste específicos.

Funcionalidade: Login de Usuário
  Como um usuário registrado
  Eu quero poder fazer login na aplicação
  Para acessar minha conta e funcionalidades restritas

  # O Bloco "Contexto" (ou Background) define passos que são executados ANTES de CADA Cenário nesta feature.
  # É útil para configurações comuns, como abrir o navegador e navegar para a página de login.
  Contexto: Usuário está na página de login
    Dado que o usuário está na página de login

  # --- Cenários de Teste ---

  # Cenário 1: Testa o fluxo de login bem-sucedido.
  # Usamos "Esquema do Cenário" (Scenario Outline) para poder rodar o mesmo cenário com diferentes dados.
  # Os valores entre <> são variáveis que serão preenchidas pelos exemplos na tabela "Exemplos".
  # **IMPORTANTE:** Você precisará substituir "seu_email_valido@exemplo.com" e "sua_senha_correta" pelos dados reais.
  Esquema do Cenário: Login bem-sucedido com credenciais válidas
    Quando o usuário insere o email "<Email>"
    E o usuário insere a senha "<Senha>"
    E o usuário clica no botão de login
    Então o usuário deve ser logado com sucesso

    Exemplos:
      | Email                       | Senha         |
      | ale.contato.tech@gmail.com  | Al1011985632! |
      # Você pode adicionar mais linhas aqui para testar outros usuários válidos, se necessário.

  # Cenário 2: Testa o fluxo de login com senha incorreta.
  # Espera-se que uma mensagem de erro seja exibida.
  # **IMPORTANTE:** Substitua "seu_email_valido@exemplo.com" pelo email válido e use uma senha заведомо incorreta.
  Esquema do Cenário: Tentativa de login com senha incorreta
    Quando o usuário insere o email "<Email>"
    E o usuário insere a senha "<SenhaIncorreta>"
    E o usuário clica no botão de login
    Então o usuário deve ver uma mensagem de erro de login
    # Adicionalmente, você pode querer verificar o texto exato da mensagem de erro:
    # E a mensagem de erro deve conter "Credenciais inválidas" (ajuste o texto conforme sua aplicação)

    Exemplos:
      | Email                        | SenhaIncorreta     |
      | ale.contato.tech@gmail.com   | incorreta123 |

  # Cenário 3: Testa o fluxo de login com email incorreto (ou não cadastrado).
  # Espera-se também uma mensagem de erro.
  # **IMPORTANTE:** Use um email заведомо inválido ou não existente no sistema.
  Esquema do Cenário: Tentativa de login com email incorreto
    Quando o usuário insere o email "<EmailIncorreto>"
    E o usuário insere a senha "<QualquerSenha>"
    E o usuário clica no botão de login
    Então o usuário deve ver uma mensagem de erro de login
    # Adicionalmente, você pode querer verificar o texto exato da mensagem de erro:
    # E a mensagem de erro deve conter "Usuário não encontrado" (ajuste o texto conforme sua aplicação)

    Exemplos:
      | EmailIncorreto              | QualquerSenha |
      | emailnaoexiste@gmail.com  | qualquercoisa123|
