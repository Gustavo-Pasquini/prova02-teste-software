# SEMANA 11 — Testes de integração com Spring Boot

Resolução da lista
[`exercicios_integracao_springboot`](https://github.com/JoaoChoma/teste_software_2026/tree/main/aulas/SEMANA11/exercicios_integracao_springboot).
Todos os `TODO` das classes de teste foram preenchidos, **sem substituir
`ProdutoRepository` ou `ProdutoService` por mocks**, como exige o enunciado.

**Tecnologias:** Java 17, Maven, Spring Boot 3.3.13, Spring Data JPA, H2 em
memória, JUnit 5 e MockMvc.

---

## Resultado da execução

```bash
mvn test
```

```text
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0 -- ProdutoControllerIntegrationTest
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0 -- ProdutoServiceIntegrationTest
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

Saída completa do Maven: [`evidencias/mvn-test.txt`](evidencias/mvn-test.txt).

### Cobertura alcançada pelos testes de integração

| Classe | Instruções | Desvios | Linhas |
|---|---:|---:|---:|
| `service.ProdutoService` | 100% | 100% | 100% |
| `controller.ProdutoController` | 100% | — | 100% |
| `controller.ProdutoRequest` | 100% | — | 100% |
| `model.Produto` | 100% | — | 100% |
| `Aplicacao` | 38% | — | 100% |
| **Total** | **96%** | **100%** | **100%** |

Todas as classes de negócio ficam 100% cobertas **pelos testes de integração**,
sem nenhum teste unitário. O que falta em `Aplicacao` é o `main()`, que só é
executado quando a aplicação sobe de verdade — os testes carregam o contexto por
`@SpringBootTest`, e não pelo `main`. Relatório HTML:
`target/site/jacoco/index.html`.

---

## Exercício 1 — Service + Repository + H2

Classe: [`ProdutoServiceIntegrationTest`](src/test/java/br/edu/exemplo/ProdutoServiceIntegrationTest.java)
(`@SpringBootTest`, `@Transactional`).

| Caso | Teste | Entrada | Verificação |
|---|---|---|---|
| **CT01** | `deveCadastrarERecuperarProduto` | `Teclado`, 150.0 | produto recebe ID; `repository.findById()` encontra o registro com nome e preço corretos |
| **CT02** | `naoDevePersistirProdutoComNomeEmBranco` | nome `"   "`, 80.0 | `IllegalArgumentException` com mensagem `Nome obrigatório`; `repository.count()` não aumenta |
| **CT03** (desafio) | `deveAtualizarPrecoPersistido` | `Mouse` 80.0 → 95.0 | consulta posterior ao repositório retorna preço `95.0` e nome inalterado |
| CT06 (complementar) | `naoDevePersistirProdutoComPrecoNaoPositivo` | `Monitor`, 0.0 | `Preço deve ser positivo`; contagem inalterada |
| CT07 (complementar) | `deveFalharAoBuscarProdutoInexistente` | id `999999` | `Produto não encontrado` |
| CT09 (complementar) | `naoDevePersistirProdutoComNomeNulo` | nome `null` | `Nome obrigatório`; contagem inalterada |
| CT10 (complementar) | `naoDeveAtualizarPrecoComValorInvalido` | `Monitor` 700.0 → −1.0 | exceção e preço **continua** 700.0 no banco |

Os quatro casos complementares foram acrescentados pela equipe para cobrir todos
os desvios de validação de `ProdutoService` (daí os 100% de *branch coverage*).

### Detalhe de implementação: `flush()` + `clear()`

Em cada verificação o teste chama um método auxiliar antes de consultar:

```java
private void sincronizarComOBanco() {
    entityManager.flush();   // envia ao H2 o que está pendente na transação
    entityManager.clear();   // esvazia o contexto de persistência
}
```

Sem o `clear()`, o `findById()` devolveria **a mesma instância Java** que já
estava no contexto de persistência (cache de primeiro nível do JPA), sem ir ao
banco. O teste passaria mesmo que o `INSERT`/`UPDATE` nunca tivesse sido
executado. Com `flush()` + `clear()`, a consulta realmente lê a linha do H2 — a
evidência fica muito mais forte.

### Discussão pedida no enunciado

**Por que consultar com `repository.findById()` é uma evidência diferente de
verificar apenas o objeto devolvido pelo serviço?**
Porque o objeto devolvido pelo serviço é apenas um objeto em memória: ele
provaria somente que o método retornou algo coerente. Ao consultar o
repositório, verifica-se que a integração com a camada de persistência
funcionou — que o Hibernate gerou o `INSERT`, que o banco gerou a chave
primária e que os dados estão recuperáveis depois. É exatamente a diferença
entre testar o *componente* e testar a *integração* entre componentes.

**O que `@Transactional` faz ao término de cada teste?**
Em classes de teste do Spring, cada método roda dentro de uma transação que é
**desfeita (rollback) no final**, e não confirmada. Isso dá isolamento entre os
testes: o `Teclado` cadastrado no CT01 não existe mais quando o CT02 conta os
registros. É o que permite usar `repository.count()` como verificação sem
depender da ordem de execução dos testes. Em produção, a mesma anotação teria o
efeito oposto (commit ao final) — o rollback é um comportamento específico do
contexto de teste.

---

## Exercício 2 — Integração HTTP com MockMvc

Classe: [`ProdutoControllerIntegrationTest`](src/test/java/br/edu/exemplo/ProdutoControllerIntegrationTest.java)
(`@SpringBootTest`, `@AutoConfigureMockMvc`, `@Transactional`).

| Caso | Teste | Requisição | Verificação |
|---|---|---|---|
| **CT04** | `deveCadastrarProdutoPelaApi` | `POST /produtos` `{"nome":"Mouse","preco":80.0}` | HTTP `200`; `$.id` é número; `$.nome` = `Mouse`; `$.preco` = `80.0` |
| **CT05** | `deveBuscarProdutoPelaApi` | `POST /produtos` (Teclado, 150.0) → extrai o ID → `GET /produtos/{id}` | HTTP `200` nas duas; o JSON do `GET` corresponde ao produto cadastrado |
| CT08 (complementar) | `deveRecusarCadastroComNomeEmBrancoPelaApi` | `POST /produtos` com nome `"  "` | a requisição falha e a causa raiz é `Nome obrigatório` |

No CT05 o ID é extraído do corpo da resposta com `ObjectMapper`/`JsonNode`,
como sugere o enunciado:

```java
JsonNode corpo = objectMapper.readTree(resultado.getResponse().getContentAsString());
return corpo.get("id").asLong();
```

Sobre o **CT08**: a aplicação-base não tem tratamento de exceção
(`@ControllerAdvice` / `@ResponseStatus`), então a `IllegalArgumentException`
lançada pelo serviço atravessa a camada web e o MockMvc a repropaga para o
teste. O teste **documenta o comportamento real** em vez de supor um `400 Bad
Request` que a aplicação não produz. É um achado útil: numa aplicação real, essa
validação deveria virar `HTTP 400`, e a correção seria acrescentar um
`@ControllerAdvice` — fora do escopo desta lista, que pede para não alterar a
aplicação.

### Discussão pedida no enunciado

**Quais componentes são efetivamente exercitados?**
Toda a pilha da aplicação: desserialização do JSON pelo Jackson no
`ProdutoRequest`, roteamento e validação de `Content-Type` pelo Spring MVC,
`ProdutoController`, `ProdutoService` (com suas regras), `ProdutoRepository`
(proxy do Spring Data), Hibernate, o banco H2 em memória e a serialização da
entidade de volta para JSON. O único componente ausente é o servidor HTTP
(Tomcat) e a pilha de rede.

**Por que o MockMvc não precisa iniciar um servidor HTTP real?**
Porque ele chama diretamente o `DispatcherServlet` com objetos
`MockHttpServletRequest`/`MockHttpServletResponse`, simulando o contrato de
Servlet. O fluxo de requisição do Spring MVC é executado de verdade, mas sem
socket, sem porta e sem serialização de rede — o que deixa o teste muito mais
rápido e determinístico. (A alternativa, com servidor real, seria
`@SpringBootTest(webEnvironment = RANDOM_PORT)` + `TestRestTemplate`.)

**Qual é a diferença em relação a um teste unitário do controller com
dependências simuladas?**
Com mocks (`@WebMvcTest` + `@MockBean ProdutoService`), o teste verificaria
apenas se o controller chama o serviço e traduz a resposta; um erro de mapeamento
JPA, uma coluna errada, uma regra de validação quebrada ou uma chave primária
não gerada **passariam despercebidos**, porque o dublê sempre responde o que foi
programado. O teste de integração troca velocidade por confiança: é mais lento
(sobe o contexto do Spring), porém detecta justamente os defeitos que aparecem
*entre* os componentes.

---

## Isolamento entre os testes

Três mecanismos combinados garantem que os 10 testes não interfiram entre si:

1. **`@Transactional` na classe de teste** → rollback automático ao fim de cada
   método.
2. **Banco H2 em memória** (`jdbc:h2:mem:produtos`) com
   `spring.jpa.hibernate.ddl-auto=create-drop` → o esquema é criado na subida do
   contexto e descartado no encerramento; nada fica em disco.
3. **Nenhum teste depende de ID fixo nem da ordem de execução** → os IDs são
   sempre lidos do que o banco gerou, e as verificações de contagem usam o valor
   medido antes da operação (`registrosAntes`), não um número absoluto.

## Como executar

```bash
mvn test                  # executa os 10 testes de integração
mvn test -Dtest=ProdutoServiceIntegrationTest   # apenas o exercício 1
mvn test -Dtest=ProdutoControllerIntegrationTest # apenas o exercício 2
mvn spring-boot:run       # sobe a aplicação em http://localhost:8080
```
