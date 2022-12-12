package test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static data.DataHelper.*;
import static sql.RestApiHelper.*;

class ApiTest {

        @Test
        void shouldGetStatusValidApprovedCardPayment() {
            CardInfo validApprovedCard = getValidApprovedCardDataAPI();
            String status = PaymentPageForm(validApprovedCard);
            assertTrue(status.contains("APPROVED"));
        }

        @Test
        void shouldGetStatusValidDeclinedCardPayment() {
            CardInfo validDeclinedCardNumber = getValidDeclinedCardDataAPI();
            String status = PaymentPageForm(validDeclinedCardNumber);
            assertTrue(status.contains("DECLINED"));
        }

        @Test
        void shouldGetStatusValidApprovedCardCreditRequest() {
            CardInfo validApprovedCard = getValidApprovedCardDataAPI();
            String status = CreditRequestPageForm(validApprovedCard);
            assertTrue(status.contains("APPROVED"));
        }

        @Test
        void shouldGetStatusValidDeclinedCardCreditRequest() {
            CardInfo validDeclinedCard = getValidDeclinedCardDataAPI();
            String status = CreditRequestPageForm(validDeclinedCard);
            assertTrue(status.contains("DECLINED"));
        }
    }