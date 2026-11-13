# SEMANA 10 — TDD nos quatro projetos, com cobertura total

Implementação dos quatro projetos propostos em
[`aulas/SEMANA10`](https://github.com/JoaoChoma/teste_software_2026/tree/main/aulas/SEMANA10)
usando **TDD** (ciclo RED → GREEN → REFACTOR) e **cobertura de 100%** verificada
automaticamente pelo JaCoCo.

| Projeto | Classe sob teste | Testes | Cobertura (instr./linhas/desvios/métodos) |
|---|---|---:|:---:|
| `bubblesort-tdd` | `BubbleSort` | 8 | **100% / 100% / 100% / 100%** |
| `carrinho-compras-tdd` | `Carrinho`, `Produto` | 13 | **100% / 100% / 100% / 100%** |
| `conta-bancaria-tdd-maven` | `ContaBancaria` | 11 | **100% / 100% / 100% / 100%** |
| `estoque-tdd` | `Produto` | 12 | **100% / 100% / 100% / 100%** |
| **Total** | | **44** | **100%** |

> A cobertura não é apenas medida: o `pom.xml` de cada projeto tem uma regra
> `jacoco:check` que **reprova o build** (`mvn verify`) se qualquer contador —
> instruções, linhas, desvios (branches), métodos ou classes — ficar abaixo de
> 100%.

---

## Como executar

```bash
# dentro de qualquer um dos quatro projetos
mvn test      # executa os testes e gera o relatório de cobertura
mvn verify    # executa os testes e REPROVA se a cobertura não for 100%
```

Os quatro de uma vez, a partir da raiz do repositório:

```bash
for p in semana10-tdd/*/; do (cd "$p" && mvn -q verify) || echo "FALHOU: $p"; done
```

Relatório HTML de cobertura (após executar): `<projeto>/target/site/jacoco/index.html`.

---

## Evidências do ciclo TDD

A pasta [`evidencias/`](evidencias) guarda a saída real do Maven em cada ponto do
ciclo, e o histórico de commits foi feito na ordem do TDD (commit de teste
primeiro, commit de implementação depois):

| Arquivo | Momento | Resultado registrado |
|---|---|---|
| `red-bubblesort-tdd.txt` | antes da implementação | 3 testes, **2 falhas** |
| `red-conta-bancaria-tdd-maven.txt` | antes da implementação | 4 testes, **4 falhas** |
| `red-estoque-tdd.txt` | antes da implementação | 6 testes, **4 falhas** |
| `red-carrinho-ciclo1.txt` | só com os testes dos requisitos 1–3 | **falha de compilação** (`adicionar`, `getTotal` inexistentes) |
| `green-carrinho-ciclo1.txt` | após a implementação mínima | 3 testes, 0 falhas |
| `red-carrinho-ciclo2.txt` | só com os testes dos requisitos 4–5 | **falha de compilação** (`remover`, `getQuantidadeDeItens`) |
| `green-carrinho-ciclo2.txt` | após a implementação | 7 testes, 0 falhas |
| `red-carrinho-ciclo3.txt` | só com os testes do requisito 6 | **falha de compilação** (`getSubtotal`, `getDesconto`, `getProdutos`) |
| `green-carrinho-ciclo3.txt` | após a implementação + refactor | 13 testes, 0 falhas, **cobertura 100%** |
| `green-*.txt` (demais) | após a implementação | 0 falhas e `All coverage checks have been met` |

---

## 1. `bubblesort-tdd`

**Situação inicial:** testes prontos (3), método `ordenar()` vazio → RED com
`array contents differ at index [0], expected: <1> but was: <5>`.

| Ciclo | Requisito | O que foi feito |
|---|---|---|
| 1 | R1 ordenar vetor desordenado | implementação do laço duplo com troca de posições |
| 1 | R2 manter vetor ordenado | atendido pela mesma implementação |
| 1 | R3 valores repetidos | atendido pela comparação `>` (estável para iguais) |
| REFACTOR | — | extração do método `trocar()` e **parada antecipada**: se uma passagem completa não troca nada, o vetor já está ordenado |

**Testes complementares** (`BubbleSortCoberturaTest`, escritos pela equipe para
fechar a cobertura): vetor vazio, vetor de um elemento, vetor em ordem
decrescente, números negativos e verificação de que a ordenação é *in-place*.

Os testes fornecidos pelo professor **não foram alterados**, conforme a restrição
de `REQUISITOS.md`.

## 2. `carrinho-compras-tdd`

Único projeto em que **os testes também precisavam ser escritos**. Foram três
ciclos completos, cada um com evidência de RED antes da implementação:

| Ciclo | Requisitos | Testes escritos | Implementação mínima |
|---|---|---|---|
| 1 | R1, R2, R3 | carrinho vazio, um produto, vários produtos | lista interna, `adicionar()`, `getTotal()` somando preços |
| 2 | R4, R5 | remoção por nome, remoção de inexistente, preço negativo, fronteira preço zero | `remover(nome)` devolvendo `boolean`, validação em `adicionar()` |
| 3 | R6 + refactor | desconto em 600,00 e nas fronteiras 499,99 / 500,00 / 500,01, desconto sobre a soma, lista imutável | `getSubtotal()`, `getDesconto()`, `getTotal()` = subtotal − desconto, `getProdutos()` somente leitura |

**Decisão de projeto no R5** (o enunciado pede para discutir se o sistema deve
ignorar a operação ou lançar exceção): optou-se por **lançar
`IllegalArgumentException`**, porque preço negativo é um dado inválido de
entrada, e ignorar silenciosamente deixaria o chamador acreditando que o produto
entrou no carrinho. O teste verifica a exceção, a mensagem e que o total
permanece zerado. Preço **zero** continua válido (brinde), e isso está fixado em
um teste de fronteira.

**Fronteiras do R6** exigidas pelo enunciado, todas testadas: `499,99` (sem
desconto), `500,00` (com desconto — o limite é inclusivo) e `500,01` (com
desconto).

## 3. `conta-bancaria-tdd-maven`

**Situação inicial:** 4 testes prontos, `depositar()` e `sacar()` vazios → RED
com `expected: <100.0> but was: <0.0>` nos quatro.

| Ciclo | Comportamento | Implementação |
|---|---|---|
| 1 | depositar e acumular depósitos | `saldo += valor` |
| 2 | sacar | `saldo -= valor` |
| 3 | não permitir saque maior que o saldo | guarda `valor > saldo` → operação ignorada |

**Decisão derivada do teste:** o teste `naoDevePermitirSaqueMaiorQueSaldo`
apenas verifica que o saldo **não muda** — não espera exceção. Por isso a
operação inválida é **ignorada**, e não lançada como erro. Nota didática: é o
teste que define o contrato, e aqui ele é diferente do contrato escolhido no
carrinho de compras.

**Testes complementares** (`ContaBancariaCoberturaTest`): saldo inicial,
depósito zero, depósito negativo, saque zero, saque negativo e as fronteiras
"saque igual ao saldo" (permitido) e "um centavo acima do saldo" (recusado).

## 4. `estoque-tdd`

**Situação inicial:** 6 testes prontos, `adicionarEstoque()` e
`retirarEstoque()` vazios → RED em 4 deles (os dois que esperavam estoque `0`
passavam por acidente, já que o campo começa em zero — exemplo de **teste que
passa sem implementação**, discutido no relatório).

| Ciclo | Requisito | Implementação |
|---|---|---|
| 1 | R1 estoque inicial zero | já atendido pelo construtor |
| 2 | R2 e R3 adicionar / acumular | `this.quantidade += quantidade` |
| 3 | R4 retirar | `this.quantidade -= quantidade` |
| 4 | R5 não permitir estoque negativo | guarda `quantidade > this.quantidade` |
| 5 | R6 não permitir entrada negativa | guarda `quantidade <= 0` |

**Testes complementares** (`ProdutoCoberturaTest`): `getNome()` (não exercitado
por nenhum teste fornecido), entrada igual a zero, retirada zero e negativa, e
as fronteiras "retirar exatamente todo o estoque" e "retirar uma unidade acima".

---

## Observações sobre cobertura total

1. **Cobertura de desvios (branch) é o que realmente obriga a testar as regras.**
   A cobertura de linhas chegaria a 100% sem testar, por exemplo, o saque de
   valor negativo; a cobertura de desvios não, porque o `if` precisa ser
   exercitado nos dois resultados. Por isso a regra do JaCoCo exige `BRANCH` a
   100%, e não só `LINE`.
2. **100% de cobertura não significa ausência de defeitos.** Significa apenas que
   todo o código foi executado por algum teste. Os testes de fronteira
   (499,99 / 500,00 / 500,01; saque igual ao saldo; retirada igual ao estoque)
   foram escritos pensando no comportamento, não no percentual.
3. Os projetos usam `double` para valores monetários, como no código-base
   fornecido; as asserções usam `delta = 0,001` por causa do erro de
   arredondamento de ponto flutuante. Em um sistema real, o tipo adequado seria
   `BigDecimal`.
