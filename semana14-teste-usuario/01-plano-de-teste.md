# Plano de Teste de Usuário Final — Mercado Livre

**Disciplina:** Teste e Qualidade de Software — 2026/2
**Semana:** 14 — Teste de Sistema / Aceitação / Teste de Usuário
**Produto sob teste:** site de e-commerce Mercado Livre Brasil (`https://www.mercadolivre.com.br`)
**Tipo de teste:** teste de usuário final (usabilidade), moderado, com observação direta
**Data de execução:** 12/11/2026

---

## 1. Objetivo do teste

Avaliar a **facilidade de uso**, a **eficiência** e a **clareza da interface** do Mercado Livre
durante as etapas de **busca**, **comparação** e **simulação de compra** de um produto,
aplicando conceitos de IHC e de testes de usabilidade.

O teste **não** avalia o código do site: avalia o comportamento do usuário final diante da
interface e a capacidade da interface de conduzi-lo à conclusão da tarefa.

### Objetivos específicos

| # | Objetivo específico | Como é verificado |
|---|---|---|
| OE1 | Verificar se o usuário encontra o produto desejado pela busca | Tarefa 2 concluída sem ajuda |
| OE2 | Verificar se os filtros (Full e melhor avaliados) são descobertos e aplicados sem ajuda | Tarefa 3 concluída sem ajuda |
| OE3 | Verificar se a página de detalhes oferece informação suficiente para comparar opções | Observação + questão aberta |
| OE4 | Verificar se a adição ao carrinho é clara e reversível | Tarefa 5 concluída sem ajuda |
| OE5 | Medir a satisfação percebida da interface | Questionário SUS |

---

## 2. Escopo

### Dentro do escopo

- Fluxo de navegação anônima (sem login): home → busca → filtros → detalhes → carrinho → início da compra.
- Interface web desktop (navegador Chrome/Firefox/Edge em resolução ≥ 1280 px).
- Métricas de eficiência (tempo), eficácia (taxa de sucesso) e satisfação (SUS).

### Fora do escopo

- Conclusão do pagamento (explicitamente proibida pelo enunciado).
- Cadastro de conta, login e dados pessoais ou de cartão.
- Teste de carga, desempenho, segurança ou compatibilidade entre navegadores.
- Aplicativo mobile nativo.

---

## 3. Cenário de uso apresentado ao participante

> Você está procurando um **fone de ouvido sem fio** para uso diário e quer comprar pelo
> Mercado Livre. Seu objetivo é encontrar um modelo com boa avaliação, comparar opções e
> simular a compra até o carrinho — **sem finalizar o pagamento**.

O cenário é entregue ao participante por escrito (ver `02-roteiro-participante.md`) e lido em
voz alta pelo avaliador antes do início do cronômetro.

---

## 4. Tarefas

| ID | Tarefa | Critério de conclusão (observável) |
|---|---|---|
| T1 | Acessar `https://www.mercadolivre.com.br` | Home carregada |
| T2 | Pesquisar "fone de ouvido Bluetooth" | Página de resultados exibida para o termo |
| T3a | Filtrar apenas produtos com entrega **Full** | Chip/filtro "Full" ativo na listagem |
| T3b | Ordenar pelos **melhores avaliados** | Ordenação "Melhor avaliados" aplicada |
| T4 | Escolher um produto e abrir os detalhes | Página de detalhe do produto aberta |
| T5 | Adicionar o item ao carrinho | Item visível no carrinho |
| T6 | Simular o início da compra (sem concluir o pagamento) | Tela de início de compra/checkout alcançada |
| T7 | Retornar à tela inicial | Home novamente exibida |

> **Regra de parada:** a tarefa é encerrada em caso de (a) conclusão, (b) desistência do
> participante ou (c) estouro do tempo máximo de 120 s por tarefa. Em (b) e (c) a tarefa é
> registrada como **falha** e o avaliador fornece a dica mínima necessária para prosseguir
> (registrando "ajuda = S").

---

## 5. Métricas e instrumentos

| Métrica | Descrição | Instrumento | Tipo |
|---|---|---|---|
| Tempo de execução | Tempo total para completar todas as tarefas | Cronômetro | Eficiência |
| Tempo por tarefa | Tempo entre o início e a conclusão de cada tarefa | Cronômetro | Eficiência |
| Taxa de sucesso | % de participantes que concluem o fluxo sem ajuda | Planilha de observação | Eficácia |
| Erros cometidos | Cliques incorretos, perda do caminho, confusão nos filtros | Planilha de observação | Eficácia |
| Satisfação (SUS) | Escore 0–100 da System Usability Scale | Formulário SUS | Satisfação |
| Comentários | Verbalizações do participante (think aloud) | Campo "Observações" | Qualitativo |

### Contagem de erros — definição operacional

Para evitar divergência entre avaliadores, conta-se **1 erro** para cada:

- clique em elemento que não avança a tarefa (ex.: clicar em banner de publicidade);
- uso da navegação "voltar" do navegador para desfazer um caminho errado;
- aplicação de um filtro diferente do pedido (ex.: "Frete grátis" em lugar de "Full");
- pergunta ao avaliador sobre onde clicar (conta como erro **e** como ajuda).

---

## 6. Critérios de sucesso do teste

| ID | Critério | Meta |
|---|---|---|
| CS1 | Participantes que conseguem adicionar o produto ao carrinho | ≥ 90% |
| CS2 | Tempo médio para concluir o teste | ≤ 4 min (240 s) |
| CS3 | Escore SUS médio (critério complementar adotado pela equipe) | ≥ 68 (média de mercado) |

O teste é considerado **aprovado** quando CS1 e CS2 são atendidos simultaneamente.

---

## 7. Recursos e organização

### Papéis

| Papel | Quem | Responsabilidade |
|---|---|---|
| Usuário final | Aluno que executa as tarefas como comprador | Executar as tarefas pensando em voz alta |
| Avaliador | Colega que observa | Registrar tempo, erros e comentários; não ensinar o caminho |
| Instrutor | Professor | Orientar e supervisionar o teste |

### Ferramentas

- Navegador web (Google Chrome, Firefox ou Edge).
- Cronômetro ou celular para medir tempo.
- Planilha de observação (`03-planilha-observacao.md` / `.csv`).
- Formulário SUS (`04-questionario-sus.md`), em Google Forms ou impresso.
- Captura de tela para evidência (pasta `evidencias/`).

### Ambiente

Navegação **anônima/sem login**, janela de 1280×800 px, conexão doméstica de banda larga,
site em produção (`www.mercadolivre.com.br`). Conforme a aula, o teste de usuário é executado
em ambiente equivalente ao de produção — neste caso, o próprio ambiente de produção, em
modo somente-navegação.

---

## 8. Procedimento de execução

1. **Preparação (2 min):** abrir navegador em janela anônima, limpar o carrinho, posicionar a
   planilha de observação, apresentar o termo de consentimento.
2. **Briefing (1 min):** ler o cenário em voz alta; explicar que *o site está sendo avaliado,
   não o participante*; pedir que pense em voz alta.
3. **Execução (até 8 min):** avaliador inicia o cronômetro na T1 e registra tempo por tarefa,
   sucesso/falha, erros e falas. O avaliador **não** orienta o caminho.
4. **Pós-teste (3 min):** aplicação do questionário SUS e das três questões abertas.
5. **Encerramento:** agradecimento e registro das evidências (capturas de tela).

---

## 9. Riscos e restrições conhecidas

| Risco | Impacto | Tratamento |
|---|---|---|
| Checkout exige login | T6 não pode ser concluída sem conta | T6 é considerada concluída ao alcançar a tela de início da compra (parede de login); **não** se realiza login nem pagamento |
| Variação do catálogo entre sessões | Produtos diferentes entre participantes | O critério de sucesso é o *fluxo*, não o produto específico |
| Mecanismos antirrobô / verificação | Interrupção da sessão | Sessão descartada e reiniciada; nenhuma verificação é burlada |
| Publicidade e carrosséis dinâmicos | Erros não atribuíveis ao fluxo | Registrados como erro de interface, com observação |
| Viés do avaliador | Dados inconsistentes | Definição operacional de erro (seção 5) e script fixo de briefing |

---

## 10. Ética e privacidade

- Participação voluntária, com consentimento verbal registrado na planilha.
- Nenhum dado pessoal, credencial ou meio de pagamento é inserido no site.
- Nenhuma compra é concluída: o fluxo para antes do pagamento.
- As evidências em `evidencias/` contêm apenas telas públicas do site, sem dados de conta.
