# Execução do teste — sessão piloto e resultados

**Data:** 12/11/2026, às 22:03
**Ambiente:** navegador isolado, **janela sem login e sem cookies de sessão**,
viewport 1280×800, site em produção (`www.mercadolivre.com.br`)
**Papel executado:** sessão piloto (*dry run*) do roteiro, conduzida pela equipe
de teste antes da coleta com participantes
**Participante:** não se aplica (piloto do instrumento, não medição de usuário)

> **Por que um piloto?** Antes de gastar o tempo de cinco participantes, roda-se
> o roteiro uma vez para validar o script, o cronômetro, a planilha e o
> ambiente. É o momento em que se descobre se alguma tarefa é impossível de
> executar como escrita — e foi exatamente o que aconteceu aqui.

---

## 1. O que foi executado

| Tarefa | Situação | Tempo medido | Evidência |
|---|---|---:|---|
| T1 — Acessar o site | **Concluída** | — | [`01-home-com-banner-de-cookies.jpg`](evidencias/01-home-com-banner-de-cookies.jpg) |
| — Tratar o aviso de cookies | **Concluída com defeito observado** | — | [`02-modal-configurar-cookies-vazio.jpg`](evidencias/02-modal-configurar-cookies-vazio.jpg) |
| T2 — Buscar "fone de ouvido Bluetooth" | **Bloqueada** — o site exigiu conta | 36 s até o bloqueio | [`03-busca-interrompida-por-verificacao-de-conta.jpg`](evidencias/03-busca-interrompida-por-verificacao-de-conta.jpg) |
| T2 (2ª via) — abrir a listagem pela URL direta | **Bloqueada** — mesma exigência | — | [`04-listagem-direta-tambem-exige-conta.jpg`](evidencias/04-listagem-direta-tambem-exige-conta.jpg) |
| T3a, T3b, T4, T5, T6, T7 | **Não executadas** | — | — |

Registro bruto de tempos: [`evidencias/tempos-piloto.csv`](evidencias/tempos-piloto.csv).

### O que aconteceu na T2

Ao digitar `fone de ouvido Bluetooth` no campo de busca e pressionar Enter, o
site não exibiu a lista de resultados: redirecionou para

```text
https://www.mercadolivre.com.br/gz/account-verification?go=https%3A%2F%2Flista.mercadolivre.com.br%2Ffone-de-ouvido-bluetooth
```

com a mensagem **"Olá! Para continuar, acesse sua conta"** e dois botões:
*Sou novo* e *Já tenho conta*. Abrir a URL da listagem diretamente levou à mesma
tela.

### Decisão de conduta da equipe (e por que ela importa)

Nesse ponto a sessão foi **encerrada por decisão de protocolo**, sem:

- criar conta ou fazer login;
- inserir qualquer dado pessoal;
- tentar contornar o mecanismo de verificação.

O plano de teste (`01-plano-de-teste.md`, seção 10) proíbe explicitamente
credenciais e pagamento, e a seção 9 já previa esse risco. Um teste de usuário
que precisa burlar um controle do produto para continuar deixa de ser um teste
válido: os dados coletados não representariam o uso real.

---

## 2. Achados do piloto

### A1 — Painel "Configurar cookies" abre sem opções (severidade: alta)

O aviso de cookies cobre o rodapé da home com dois botões: **Aceitar cookies** e
**Configurar cookies**. Ao escolher *Configurar cookies*, o modal abre com o
título, o "X" de fechar e um botão **Salvar** — **sem nenhuma opção de categoria
de cookies para marcar ou desmarcar** (evidência 02).

- **Heurística violada (Nielsen):** *Controle e liberdade do usuário* e
  *Visibilidade do estado do sistema*.
- **Impacto:** o usuário que quer recusar cookies não encontra o que recusar; a
  única ação disponível é "Salvar" um conjunto de escolhas invisível. Na
  prática, o caminho de menor esforço vira "Aceitar cookies" — um padrão que a
  literatura de IHC classifica como *dark pattern* de consentimento.
- **Recomendação:** listar as categorias (necessários, desempenho, publicidade)
  com interruptores visíveis e um botão explícito "Rejeitar opcionais" no mesmo
  nível visual de "Aceitar cookies".
- **Ressalva honesta:** o painel pode depender de um script de terceiros que não
  carregou nesta sessão. Como o usuário final não tem como saber disso, a falha
  continua sendo uma falha **de experiência** — mas o relatório final deve
  verificar se ela se repete em outros navegadores antes de classificá-la como
  defeito permanente do produto.

### A2 — Usuário anônimo barrado antes de ver qualquer produto (severidade: alta no contexto observado)

A busca, que é a porta de entrada do e-commerce, levou a uma parede de login
(evidência 03 e 04).

- **Heurística violada:** *Correspondência entre o sistema e o mundo real* e
  *Flexibilidade e eficiência de uso* — um visitante que ainda está decidindo o
  que comprar é forçado a se cadastrar antes de ver preços.
- **Impacto, se atingir usuários reais:** abandono imediato. Em e-commerce, o
  cadastro obrigatório antes da vitrine é uma das causas clássicas de perda de
  conversão.
- **Ressalva honesta e importante:** esta tela é, com alta probabilidade, uma
  **proteção antiautomação** disparada pelo perfil da sessão (navegador
  automatizado, sem histórico e sem cookies), e **não** o comportamento que um
  visitante humano comum encontra — o Mercado Livre normalmente permite buscar e
  navegar sem conta. O achado é relatado porque foi o que de fato ocorreu e
  porque define o ambiente válido do teste, **não** como prova de que todo
  usuário anônimo é bloqueado. Verificar isso com os participantes humanos é
  item obrigatório do relatório final.

### A3 — O aviso de cookies compete com o conteúdo logo na entrada (severidade: baixa)

A faixa de cookies ocupa a base da janela e convive com um carrossel
publicitário no topo; na primeira dobra da home, a parcela da tela dedicada ao
que o usuário veio fazer (buscar um produto) é pequena. Vale cronometrar, com os
participantes, quanto tempo levam até o primeiro clique no campo de busca.

---

## 3. Resultados quantitativos

| Métrica | Meta | Resultado do piloto |
|---|---|---|
| Taxa de sucesso até o carrinho (CS1) | ≥ 90% | **não medida** — fluxo interrompido na T2 |
| Tempo médio total (CS2) | ≤ 240 s | **não medido** |
| Escore SUS médio (CS3) | ≥ 68 | **não medido** — sem participantes |

> Estes campos estão deliberadamente vazios. **Nenhum dado de participante foi
> inventado.** Taxa de sucesso, tempo médio e SUS só existem a partir de pessoas
> reais executando as tarefas; preencher essas linhas sem coleta transformaria
> o relatório em ficção e invalidaria a atividade.

---

## 4. O que o piloto mudou no instrumento

1. **Ambiente de execução corrigido:** as sessões com participantes devem ser
   feitas em **navegador comum do próprio participante**, em janela anônima, e
   não em navegador automatizado — este dispara a verificação de conta e
   inviabiliza o teste.
2. **Nova regra de parada:** se a tela de verificação de conta aparecer durante
   uma sessão, a sessão é **descartada** (não convertida em falha do
   participante) e reiniciada em outra máquina/rede, registrando a ocorrência.
3. **Nova linha na planilha:** "o site exigiu conta? (S/N)" — se vários
   participantes forem barrados, o achado A2 deixa de ser artefato do ambiente e
   passa a ser um defeito real de produto, com severidade crítica.
4. **Confirmação do achado A1:** cada participante deve ser observado no aviso
   de cookies antes de começar a T1 (é a primeira interação real com o site).

---

## 5. Coleta pendente

| Item | Responsável | Situação |
|---|---|---|
| 5 participantes × roteiro completo (T1–T7) | equipe | **pendente** |
| Planilha de observação preenchida | avaliador de cada sessão | **pendente** |
| Questionário SUS de cada participante | participante | **pendente** |
| Consolidação e cálculo de CS1, CS2 e CS3 | equipe | **pendente** |
| Conclusão sobre aprovação/reprovação do teste | equipe | **pendente** |

Os instrumentos estão prontos e prontos para uso:
[`02-roteiro-participante.md`](02-roteiro-participante.md),
[`03-planilha-observacao.csv`](03-planilha-observacao.csv) e
[`04-questionario-sus.csv`](04-questionario-sus.csv).
