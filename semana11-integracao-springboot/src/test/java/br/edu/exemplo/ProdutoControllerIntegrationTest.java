package br.edu.exemplo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Exercício 2 — integração HTTP com MockMvc.
 *
 * <p>Exercita o fluxo completo Controller → Service → Repository → H2 por meio
 * de requisições HTTP simuladas, sem subir um servidor real e sem substituir
 * nenhuma dependência por mock.</p>
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProdutoControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("CT04 - POST /produtos cadastra o produto e devolve o JSON com ID")
    void deveCadastrarProdutoPelaApi() throws Exception {
        mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Mouse\",\"preco\":80.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nome").value("Mouse"))
                .andExpect(jsonPath("$.preco").value(80.0));
    }

    @Test
    @DisplayName("CT05 - GET /produtos/{id} devolve o produto cadastrado pela API")
    void deveBuscarProdutoPelaApi() throws Exception {
        MvcResult respostaDoCadastro = mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Teclado\",\"preco\":150.0}"))
                .andExpect(status().isOk())
                .andReturn();

        long idGerado = extrairId(respostaDoCadastro);

        mockMvc.perform(get("/produtos/{id}", idGerado))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(idGerado))
                .andExpect(jsonPath("$.nome").value("Teclado"))
                .andExpect(jsonPath("$.preco").value(150.0));
    }

    @Test
    @DisplayName("CT08 (complementar) - POST com nome em branco resulta em erro HTTP 500 do servidor")
    void deveRecusarCadastroComNomeEmBrancoPelaApi() {
        // A aplicação-base não possui tratamento de exceção (@ControllerAdvice),
        // portanto a IllegalArgumentException lançada pelo serviço atravessa a
        // camada web. O teste documenta esse comportamento real: a requisição
        // falha e o servlet propaga a exceção.
        Exception erro = org.junit.jupiter.api.Assertions.assertThrows(
                Exception.class,
                () -> mockMvc.perform(post("/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"  \",\"preco\":80.0}"))
        );

        assertTrue(mensagensDe(erro).contains("Nome obrigatório"),
                "a causa raiz deve ser a validação do serviço: " + mensagensDe(erro));
    }

    private long extrairId(MvcResult resultado) throws Exception {
        JsonNode corpo = objectMapper.readTree(resultado.getResponse().getContentAsString());
        return corpo.get("id").asLong();
    }

    private String mensagensDe(Throwable erro) {
        StringBuilder mensagens = new StringBuilder();
        for (Throwable atual = erro; atual != null; atual = atual.getCause()) {
            mensagens.append(atual.getMessage()).append(" | ");
        }
        return mensagens.toString();
    }
}
