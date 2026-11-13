# Relatório do teste de usuário final — Mercado Livre

**Disciplina:** Teste e Qualidade de Software — 2026/2 · **Semana 14**
**Produto avaliado:** `www.mercadolivre.com.br` (web desktop)
**Período:** 12/11/2026
**Documento-base:** `TESTE USUARIO FINAL.pdf` (enunciado da atividade)

---

## 1. Sumário executivo

Foi planejado e instrumentado um teste de usuário final com **8 tarefas**,
**3 métricas** (tempo, taxa de sucesso e erros) e **questionário SUS**, conforme
o enunciado. A **sessão piloto** do roteiro foi executada em 12/11/2026 e
produziu dois achados de usabilidade com evidência em tela, mas **foi
interrompida na tarefa 2** por uma tela de verificação de conta imposta pelo
site, que a equipe optou por **não contornar**.

Em consequência:

- o **instrumento está completo, validado e corrigido** pelo piloto;
- os **achados qualitativos** estão documentados com captura de tela;
- as **métricas quantitativas** (taxa de sucesso, tempo médio, SUS) **dependem
  da coleta com participantes humanos**, que não foi realizada e **não foi
  simulada**.

---

## 2. Contexto teórico (conteúdo da Semana 14)

| Tipo de teste | Quem executa | O que valida | Neste trabalho |
|---|---|---|---|
| Teste de sistema | equipe de teste | o sistema integrado contra os requisitos | — |
| Teste de aceitação do cliente | cliente / *stakeholder* | requisitos de negócio | — |
| **Teste de usuário final** | **usuários reais** | **facilidade de uso percebida no uso real** | **objeto deste relatório** |

Pontos da aula que orientaram as decisões:

- **Usuário não é cliente.** Quem executa as tarefas é o *usuário final*
  (comprador), não quem encomendou o sistema. Por isso o participante é um
  colega na posição de comprador, e não o professor.
- **O ambiente deve se assemelhar ao de produção.** Aqui o teste roda sobre o
  site em produção, em modo somente-navegação, sem concluir pagamento.
- **O objetivo é coletar feedback direto do usuário**, e não encontrar defeitos
  de código: o que se mede é comportamento (tempo, erro, hesitação) e percepção
  (SUS).

---

## 3. Execução

Descrição completa em [`05-execucao-e-resultados.md`](05-execucao-e-resultados.md).
Resumo:

| Tarefa | Resultado |
|---|---|
| T1 — Acessar o site | concluída |
| T2 — Buscar "fone de ouvido Bluetooth" | **bloqueada** por tela de verificação de conta |
| T3a a T7 | não executadas |

Evidências em [`evidencias/`](evidencias): home com o aviso de cookies, modal de
configuração de cookies vazio, e as duas telas de verificação de conta.

---

## 4. Achados

| ID | Achado | Severidade | Heurística violada | Evidência |
|---|---|:-:|---|---|
| A1 | "Configurar cookies" abre um painel sem opções; só resta "Salvar" ou aceitar tudo | Alta | Controle e liberdade do usuário | `02-modal-configurar-cookies-vazio.jpg` |
| A2 | Busca de usuário anônimo levada a uma parede de login antes de qualquer produto | Alta no contexto observado | Flexibilidade e eficiência de uso | `03-…jpg`, `04-…jpg` |
| A3 | Primeira dobra da home disputada entre publicidade e aviso de cookies | Baixa | Estética e design minimalista | `01-home-com-banner-de-cookies.jpg` |

**Sobre o A2** — há forte indício de que a tela seja uma **proteção
antiautomação** disparada pelo perfil da sessão (navegador automatizado, sem
histórico), e não a experiência de um visitante humano comum. O achado é
relatado como ocorrência real e como **restrição de ambiente**, não como
conclusão sobre todos os usuários; a confirmação depende das sessões com
participantes.

---

## 5. Resultados frente aos critérios de sucesso

| ID | Critério | Meta | Resultado | Situação |
|---|---|---|---|---|
| CS1 | Participantes que adicionam o produto ao carrinho | ≥ 90% | sem dado | **não avaliado** |
| CS2 | Tempo médio para concluir o teste | ≤ 4 min | sem dado | **não avaliado** |
| CS3 | Escore SUS médio | ≥ 68 | sem dado | **não avaliado** |

**Conclusão parcial:** o teste **não pode ser declarado aprovado nem reprovado**.
Declarar qualquer das duas coisas com base em uma sessão piloto interrompida
seria um erro metodológico — e inventar números de participantes seria pior.

---

## 6. Recomendações

### Ao produto (Mercado Livre)

1. **(A1)** Exibir as categorias de cookies com interruptores e oferecer
   "Rejeitar opcionais" com o mesmo peso visual de "Aceitar cookies".
2. **(A2)** Garantir que a busca e a listagem de produtos sejam acessíveis sem
   conta; se houver verificação antifraude, explicar o motivo e oferecer um
   caminho de retorno à navegação ("continuar sem conta").
3. **(A3)** Reduzir a concorrência visual na primeira dobra, preservando o campo
   de busca como elemento dominante.

### Ao processo de teste

4. Executar as cinco sessões no **navegador do próprio participante**, em janela
   anônima, conforme a correção do piloto.
5. Aplicar a **regra de descarte**: sessão barrada por verificação de conta é
   descartada e refeita, com a ocorrência registrada.
6. Registrar sempre as **falas literais**: a métrica diz *onde* o usuário
   travou; a fala diz *por quê*.
7. Confirmar o A1 em pelo menos dois navegadores antes de classificá-lo como
   defeito permanente do produto.

---

## 7. Lições aprendidas

- **O piloto se pagou.** Uma sessão de poucos minutos revelou que o ambiente
  escolhido inviabilizaria as cinco sessões seguintes. Sem ele, cinco pessoas
  teriam sido chamadas para um teste impossível.
- **Teste de usuário não é teste automatizado.** As Semanas 10 e 11 deste mesmo
  repositório mostram o oposto: lá, repetibilidade e isolamento são virtudes
  (JUnit, H2, `@Transactional`). Aqui, o que se procura é justamente o
  comportamento humano imprevisível — e o ambiente automatizado não serve como
  substituto.
- **Relatar o que não foi medido faz parte do resultado.** Um relatório com
  lacunas explícitas é mais útil — e mais honesto — do que um relatório completo
  com números fabricados.

---

## 8. Anexos

| Documento | Conteúdo |
|---|---|
| [`01-plano-de-teste.md`](01-plano-de-teste.md) | objetivo, escopo, tarefas, métricas, critérios, riscos, ética |
| [`02-roteiro-participante.md`](02-roteiro-participante.md) | script da sessão, briefing, regras do avaliador |
| [`03-planilha-observacao.md`](03-planilha-observacao.md) / [`.csv`](03-planilha-observacao.csv) | registro por tarefa e consolidação |
| [`04-questionario-sus.md`](04-questionario-sus.md) / [`.csv`](04-questionario-sus.csv) | SUS e fórmula de apuração |
| [`05-execucao-e-resultados.md`](05-execucao-e-resultados.md) | o que foi executado, achados e pendências |
| [`evidencias/`](evidencias) | capturas de tela e tempos brutos |
