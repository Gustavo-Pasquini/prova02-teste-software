# SEMANA 14 — Teste de usuário final (Mercado Livre)

Execução da atividade proposta em
[`TESTE USUARIO FINAL.pdf`](https://github.com/JoaoChoma/teste_software_2026/blob/main/aulas/SEMANA14/TESTE%20USUARIO%20FINAL.pdf):
avaliar facilidade de uso, eficiência e clareza da interface do Mercado Livre
nas etapas de busca, comparação e simulação de compra.

## Documentos

| # | Documento | Conteúdo |
|---|---|---|
| 1 | [`01-plano-de-teste.md`](01-plano-de-teste.md) | objetivo, escopo, cenário, 8 tarefas, métricas, critérios de sucesso, papéis, riscos e ética |
| 2 | [`02-roteiro-participante.md`](02-roteiro-participante.md) | script da sessão: preparação, briefing, tarefas e regras do avaliador |
| 3 | [`03-planilha-observacao.md`](03-planilha-observacao.md) · [`.csv`](03-planilha-observacao.csv) | planilha de observação por tarefa e consolidação dos participantes |
| 4 | [`04-questionario-sus.md`](04-questionario-sus.md) · [`.csv`](04-questionario-sus.csv) | questionário SUS, fórmula e interpretação do escore |
| 5 | [`05-execucao-e-resultados.md`](05-execucao-e-resultados.md) | o que foi executado na sessão piloto, achados e pendências |
| 6 | [`06-relatorio-final.md`](06-relatorio-final.md) | relatório consolidado, recomendações e lições aprendidas |
| — | [`evidencias/`](evidencias) | capturas de tela e registro bruto de tempos |

## Resumo do que foi feito

- **Instrumento completo e pronto para uso:** plano de teste, roteiro de sessão,
  planilha de observação (com definição operacional de "erro") e questionário
  SUS com fórmula de apuração.
- **Sessão piloto executada em 12/11/2026** no site em produção, em navegação
  anônima: a tarefa 1 foi concluída e a tarefa 2 foi **bloqueada** por uma tela
  de verificação de conta do próprio site. A sessão foi encerrada sem login e
  sem tentar contornar o mecanismo.
- **Dois achados de usabilidade com evidência em tela**, sendo o principal o
  painel "Configurar cookies" que abre sem nenhuma opção de escolha.
- **Nenhum dado de participante foi inventado.** Taxa de sucesso, tempo médio e
  SUS estão explicitamente marcados como pendentes de coleta com pessoas reais.

## Para concluir a coleta (5 participantes)

1. Abrir o navegador **do próprio participante** em janela anônima (não usar
   navegador automatizado — foi o que disparou a verificação de conta no piloto).
2. Conduzir a sessão com [`02-roteiro-participante.md`](02-roteiro-participante.md)
   à mão, cronometrando cada tarefa.
3. Preencher uma linha por tarefa em
   [`03-planilha-observacao.csv`](03-planilha-observacao.csv).
4. Aplicar o SUS e lançar as respostas em
   [`04-questionario-sus.csv`](04-questionario-sus.csv).
5. Consolidar CS1, CS2 e CS3 e completar as seções 5 e 6 do
   [`06-relatorio-final.md`](06-relatorio-final.md).
