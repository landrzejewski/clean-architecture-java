package pl.training.payments.integration;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import pl.training.Application;
import pl.training.payments.CardTestFixtures;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.core.Is.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.training.payments.CardTestFixtures.testCurrency;
import static pl.training.payments.CardTestFixtures.testMoney;

@WithMockUser(roles = "ADMIN")
@SpringBootTest(classes = Application.class, webEnvironment = DEFINED_PORT)
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class AddCardTransactionUseCaseTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EntityManager entityManager;

    @Transactional
    @Test
    void addInflowTransaction() throws Exception {
        var cardEntity = CardTestFixtures.validCardEntity();
        entityManager.persist(cardEntity);
        entityManager.flush();

        String payload = """
                    {
                      "type": "IN",
                      "amount": 100.0,
                      "currencyCode": "PLN"
                    }
                """;

        var url = "/api/cards/" + cardEntity.getNumber() + "/transactions";

        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(payload))
                .andExpect(status().isNoContent());
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].type", is("IN")))
                .andExpect(jsonPath("$[0].amount", closeTo(testMoney.amount().doubleValue(), 0.1)))
                .andExpect(jsonPath("$[0].currencyCode", is(testCurrency.getCurrencyCode())));
    }

}
