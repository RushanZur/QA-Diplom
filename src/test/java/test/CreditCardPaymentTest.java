package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import data.DataHelper;
import page.MainPage;
import page.PaymentPage;
import sql.SqlHelper;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditCardPaymentTest {

    MainPage mainPage;

    @BeforeAll
    public static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    public static void tearDownAll() {
        SelenideLogger.removeListener("allure");
        SqlHelper.cleanDb();
    }

    @BeforeEach
    public void setUp() {
        mainPage = open(System.getProperty("sut.url"), MainPage.class);
    }

    // POSITIVE SCENARIOS

    @Test
    public void shouldDoPaymentByCreditCardWithStatusApproved() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getValidApprovedCardData();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkSuccessNotification();
        String paymentStatus = SqlHelper.getStatusCreditRequestEntity();
        assertEquals("APPROVED", paymentStatus);
    }

    @Test
    public void shouldNotDoPaymentByCreditCardWithStatusDeclined() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getValidDeclinedCardData();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkErrorNotification();
        String paymentStatus = SqlHelper.getStatusCreditRequestEntity();
        assertEquals("DECLINED", paymentStatus);
    }

    // NEGATIVE SCENARIOS

    // Card Number

    @Test
    public void shouldNotDoPaymentByCreditCardWhenFieldWithInvalidCardNumber() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getInvalidCardNumberIfFieldAllZeros();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkErrorNotification();
    }

    @Test
    public void shouldNotDoPaymentByCreditCardWhenCardNumberFieldAnotherBankCard() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getAnotherBankCardNumber();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkErrorNotification();
    }

    @Test
    public void shouldNotDoPaymentByCreditCardWhenFieldCardNumberWithOneDigit() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info= DataHelper.getInvalidCardNumberWithOneDigit();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByCreditCardWhenFieldCardNumberWithFifteenDigits() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getInvalidCardNumberWithFifteenDigits();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByCreditCardWhenCardNumberFieldEmpty() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getCardNumberFieldEmpty();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }


    // Month

    @Test
    public void shouldNotDoPaymentByCreditCardWhenFieldWithInvalidMonth() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getInvalidMonthWithIrrelevantValue();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkInvalidCardExpirationDate();
    }

    @Test
    public void shouldNotDoPaymentByCreditCardWhenMonthFieldIsZeros() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getInvalidMonthWithZeros();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkInvalidCardExpirationDate();
    }

    @Test
    public void shouldNotDoPaymentByCreditCardWhenFieldWithMonthExpiredCurrentYear() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getInvalidMonthIsCurrentYear();
        paymentPage.fillPaymentFormat(info);
        paymentPage.verifyCardExpired();
    }

    @Test
    public void shouldNotDoPaymentByCreditCardWhenFieldMonthOneDigit() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getInvalidMonthWithOneDigit();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test

    public void shouldNotDoPaymentByCreditCardWhenFieldMonthEmpty() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getFieldMonthEmpty();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    //  Year

    @Test
    public void shouldNotDoPaymentByCreditCardWhenFieldWithExpiredYear() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getCardWithExpiredYear();
        paymentPage.fillPaymentFormat(info);
        paymentPage.verifyCardExpired();
    }

    @Test
    public void shouldNotDoPaymentByCreditCardWhenFieldWithInvalidYearExpirationDate() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getInvalidYearExceedingCardExpirationDate();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkInvalidCardExpirationDate();
    }

    @Test
    public void shouldNotDoPaymentByCreditCardWhenYearFieldWithZeros() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getInvalidYearWithZeros();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkInvalidCardExpirationDate();
    }

    @Test
    public void shouldNotDoPaymentByCreditCardWhenYearFieldWithOneDigit() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getInvalidYearWithOneDigit();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByCreditCardWhenYearFieldEmpty() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getFieldYearEmpty();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    // Owner

    @Test
    public void shouldNotDoPaymentByCreditCardWhenOwnerFieldFilledCyrillic() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getInvalidOwnerWithCyrillic();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByCreditCardWhenOwnerFieldWithOneLatinWord() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getInvalidOwnerWithOneWord();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByCreditCardWhenOwnerFieldWithThreeLatinWords() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getInvalidOwnerWithThreeWords();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByCreditCardWhenOwnerFieldWithLowerCase() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getInvalidOwnerWithLowerCase();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByCreditCardWhenOwnerFieldWithUpperCase() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getInvalidOwnerWithUpperCase();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByCreditCardIfOwnerFieldWithOneLetter() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getInvalidOwnerWithOneLetter();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByCreditCardIfOwnerFieldWithLargeNumberLetters() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getInvalidOwnerWithLotsNumberOfLetters();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByCreditCardIfOwnerFieldWithDigits() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getInvalidOwnerWithDigits();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByCreditCardIfOwnerFieldWithSymbols() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getInvalidOwnerWithSymbols();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByCreditCardIfOwnerFieldEmpty() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getOwnerFieldEmpty();
        paymentPage.fillPaymentFormat(info);
        paymentPage.verifyEmptyField();
    }

    //  CVC/CVV

    @Test
    public void shouldNotDoPaymentByCreditCardWhenCVCIsOneDigit() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getInvalidCVCWithOneDigit();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByCreditCardWhenCVCIsTwoDigits() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getInvalidCVCWithTwoDigits();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByCreditCardIfCVCWithThreeZeros() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getInvalidCVCWithZeros();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByCreditCardIfCVCFieldEmpty() {
        PaymentPage paymentPage = mainPage.getPaymentByCreditCard();
        DataHelper.CardInfo info = DataHelper.getEmptyCVCField();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }
}
