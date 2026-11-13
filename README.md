# Avaliação Prática — Prova 02 · Teste e Qualidade de Software

Repositório único com a solução das três atividades da avaliação prática
(Semanas 10, 11 e 14), a partir do material da disciplina em
[JoaoChoma/teste_software_2026](https://github.com/JoaoChoma/teste_software_2026).

**Aluno:** Gustavo Pasquini · **Curso:** Engenharia de Software — 6º semestre
**Entrega:** 22/11/2026

---

## Atividades

| Semana | Atividade | Pasta | Situação |
|---|---|---|---|
| **10** | TDD nos quatro projetos, até a implementação e **cobertura total** | [`semana10-tdd/`](semana10-tdd) | ✅ 44 testes · **100% de cobertura** nos 4 projetos |
| **11** | Teste de integração no projeto Spring Boot | [`semana11-integracao-springboot/`](semana11-integracao-springboot) | ✅ 10 testes de integração · CT01 a CT05 + 5 complementares |
| **14** | Executar o teste de usuário para o problema proposto | [`semana14-teste-usuario/`](semana14-teste-usuario) | ✅ instrumento completo + sessão piloto executada com evidências |

---

## Semana 10 — TDD com cobertura total

Os quatro projetos foram desenvolvidos pelo ciclo **RED → GREEN → REFACTOR**, com
a saída real do Maven guardada em `semana10-tdd/evidencias/` e o histórico de
commits na ordem do TDD (commit de teste antes do commit de implementação).

| Projeto | Testes | Instruções | Desvios | Linhas | Métodos |
|---|---:|:-:|:-:|:-:|:-:|
| `bubblesort-tdd` | 8 | 100% | 100% | 100% | 100% |
| `carrinho-compras-tdd` | 13 | 100% | 100% | 100% | 100% |
| `conta-bancaria-tdd-maven` | 11 | 100% | 100% | 100% | 100% |
| `estoque-tdd` | 12 | 100% | 100% | 100% | 100% |
| **Total** | **44** | **100%** | **100%** | **100%** | **100%** |

A cobertura é **verificada automaticamente**: cada `pom.xml` tem uma regra
`jacoco:check` que reprova `mvn verify` se qualquer contador ficar abaixo de
100%. Detalhes dos ciclos, decisões de projeto e evidências no
[README da Semana 10](semana10-tdd/README.md).

```bash
for p in semana10-tdd/*/; do (cd "$p" && mvn -q verify) || echo "FALHOU: $p"; done
```

## Semana 11 — Testes de integração com Spring Boot

Todos os `TODO` da lista foram preenchidos, **sem mocks** para
`ProdutoRepository` ou `ProdutoService`:

- **Exercício 1** (`@SpringBootTest` + `@Transactional`): CT01 (cadastro
  válido), CT02 (nome em branco não persiste) e o desafio CT03 (atualização de
  preço persistida), com `flush()` + `clear()` no `EntityManager` para que a
  consulta leia de fato do H2.
- **Exercício 2** (MockMvc): CT04 (`POST /produtos`) e CT05 (`POST` → extrair o
  ID com `ObjectMapper` → `GET /produtos/{id}`).
- **5 casos complementares** fecham 100% dos desvios de `ProdutoService`.

Resultado: **10 testes, 0 falhas**. Evidência, cobertura e as discussões pedidas
no enunciado (papel do `@Transactional`, por que o MockMvc dispensa servidor
real, diferença para o teste unitário com mocks) no
[README da Semana 11](semana11-integracao-springboot/README.md).

```bash
cd semana11-integracao-springboot && mvn test
```

## Semana 14 — Teste de usuário final

Teste de usabilidade do Mercado Livre (busca → filtros → detalhes → carrinho →
início da compra), com **plano de teste, roteiro de sessão, planilha de
observação, questionário SUS** e relatório final.

A **sessão piloto** foi executada em 12/11/2026 no site em produção: a tarefa 1
foi concluída e a tarefa 2 foi bloqueada por uma tela de verificação de conta do
próprio site — a sessão foi encerrada **sem login e sem contornar o mecanismo**,
como manda o plano de teste. Foram registrados dois achados de usabilidade com
captura de tela.

As métricas que dependem de pessoas (taxa de sucesso, tempo médio e SUS) estão
**explicitamente marcadas como pendentes de coleta**: nenhum dado de
participante foi inventado. Os instrumentos estão prontos para as cinco sessões.
Ver o [README da Semana 14](semana14-teste-usuario/README.md).

---

## Requisitos para executar

- **Java 17+** (testado com OpenJDK 21)
- **Maven 3.9+**
- Conexão com a internet na primeira execução (download das dependências)

```bash
# tudo de uma vez
for p in semana10-tdd/*/; do (cd "$p" && mvn -q verify); done
(cd semana11-integracao-springboot && mvn -q test)
```

## Estrutura

```text
.
├── semana10-tdd/
│   ├── bubblesort-tdd/              # BubbleSort            — 8 testes
│   ├── carrinho-compras-tdd/        # Carrinho + Produto    — 13 testes (TDD do zero)
│   ├── conta-bancaria-tdd-maven/    # ContaBancaria         — 11 testes
│   ├── estoque-tdd/                 # Produto (estoque)     — 12 testes
│   └── evidencias/                  # saída do Maven em cada RED e GREEN
├── semana11-integracao-springboot/  # Spring Boot 3.3 + JPA + H2 + MockMvc
│   └── evidencias/
└── semana14-teste-usuario/
    ├── 01-plano-de-teste.md … 06-relatorio-final.md
    └── evidencias/                  # capturas de tela e tempos
```
