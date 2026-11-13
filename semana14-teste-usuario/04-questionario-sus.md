# Questionário SUS — System Usability Scale

Aplicado **logo após** as tarefas, antes de qualquer conversa sobre o site.
Pode ser impresso ou transposto para um Google Forms (uma pergunta por item, em
escala linear de 1 a 5).

> **Instrução ao participante:** "Marque a resposta que primeiro vier à cabeça,
> sem pensar muito. Se não souber responder, marque o centro (3)."

Escala: **1 = discordo totalmente** … **5 = concordo totalmente**

| # | Afirmação | 1 | 2 | 3 | 4 | 5 |
|---|---|:-:|:-:|:-:|:-:|:-:|
| Q1 | Eu usaria esse site com frequência para comprar. | ( ) | ( ) | ( ) | ( ) | ( ) |
| Q2 | Achei o site desnecessariamente complexo. | ( ) | ( ) | ( ) | ( ) | ( ) |
| Q3 | Achei o site fácil de usar. | ( ) | ( ) | ( ) | ( ) | ( ) |
| Q4 | Eu precisaria de ajuda de alguém para conseguir usar esse site. | ( ) | ( ) | ( ) | ( ) | ( ) |
| Q5 | As funções do site estão bem integradas umas às outras. | ( ) | ( ) | ( ) | ( ) | ( ) |
| Q6 | Havia muita inconsistência no site. | ( ) | ( ) | ( ) | ( ) | ( ) |
| Q7 | Imagino que a maioria das pessoas aprenderia a usar esse site rapidamente. | ( ) | ( ) | ( ) | ( ) | ( ) |
| Q8 | Achei o site confuso/trabalhoso de usar. | ( ) | ( ) | ( ) | ( ) | ( ) |
| Q9 | Senti confiança ao usar o site. | ( ) | ( ) | ( ) | ( ) | ( ) |
| Q10 | Precisei aprender muita coisa antes de conseguir usar o site. | ( ) | ( ) | ( ) | ( ) | ( ) |

---

## Como calcular o escore

1. Itens **ímpares** (Q1, Q3, Q5, Q7, Q9): contribuição = `resposta − 1`.
2. Itens **pares** (Q2, Q4, Q6, Q8, Q10): contribuição = `5 − resposta`.
3. Some as 10 contribuições (0 a 40) e **multiplique por 2,5** → escore de 0 a 100.

```text
SUS = 2,5 × [ (Q1−1)+(Q3−1)+(Q5−1)+(Q7−1)+(Q9−1)
            + (5−Q2)+(5−Q4)+(5−Q6)+(5−Q8)+(5−Q10) ]
```

### Interpretação de referência

| Escore SUS | Leitura |
|---|---|
| < 51 | inaceitável — usabilidade ruim |
| 51 – 67 | abaixo da média de mercado |
| **68** | **média de mercado (linha de corte usual)** |
| 68 – 80 | bom |
| > 80 | excelente |

O escore SUS **não é uma porcentagem**: 68 não significa "68% de acerto", e sim
a média histórica de centenas de sistemas avaliados.

### Planilha de apuração

As respostas vão para [`04-questionario-sus.csv`](04-questionario-sus.csv), uma
linha por participante. Fórmula pronta para a coluna `escore_SUS`
(considerando Q1 em `B2` … Q10 em `K2`):

```text
=2,5*((B2-1)+(D2-1)+(F2-1)+(H2-1)+(J2-1)+(5-C2)+(5-E2)+(5-G2)+(5-I2)+(5-K2))
```
