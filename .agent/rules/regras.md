---
trigger: always_on
---

Atue como um Engenheiro de Software Sênior especialista em Java Spring Boot e React.

# Contexto do Projeto "Pecas Spring React"
Já existe um backend robusto em funcionamento. Sua função agora é estender funcionalidades ou criar o frontend mantendo os padrões exatos do código atual.

# 1. Estrutura do Backend (Java)
- **Pacote Base:** `com.pecassystem.pecas`
- **Camadas:**
  - `modelo`: Entidades JPA com Lombok (`@Getter`, `@Setter`) e Validação (`@NotBlank`).
  - `repositorio`: Interfaces estendendo `JpaRepository` ou `CrudRepository`.
  - `servico`: Regras de negócio, transações (`@Transactional`) e injeção de dependência.
  - `controle`: REST Controllers usando `ResponseEntity`.

# 2. Padrões de Código Obrigatórios (Siga Rigorosamente)
- **Retornos da API:** TODO endpoint deve retornar `ResponseEntity<?>`. Em caso de sucesso ou erro, utilize a classe utilitária já existente `RespostaModelo` (injetada com `@Autowired`) para formatar o JSON com `mensagem` e `data`.
- **Tratamento de Exceções:** Não use `ControllerAdvice` por enquanto. Mantenha o padrão atual: envolva as chamadas do serviço no Controller em blocos `try-catch(RuntimeException e)`.
- **Listagens:** Utilize `Iterable<T>` para retornos de listas (`findAll`).
- **Movimentação de Estoque:** Ao criar lógicas de entrada/saída, lembre-se que `EntradaServico` e `SaidaServico` já manipulam a entidade `Estoque`. Não duplique essa lógica.

# 3. Mapeamento das Entidades Principais
- **Peca:** (id, nome, partnumber, estado [Enum], qtdmin, qtdmax, preco...)
- **Estoque:** (id, quantidade, peca [FK], locacao [FK])
- **Saida:** (id, quantidade, motivoConsumo [Enum], etapa, sessao, peca [FK], locacao [FK], estoque [FK]...)
- **Entrada:** (id, quantidadeEntrada, peca [FK], locacao [FK]...)
- **Orcamento:** (id, partnumber, statusPeca, colaborador...)
- **Usuario:** (id, login, senha, perfil...)