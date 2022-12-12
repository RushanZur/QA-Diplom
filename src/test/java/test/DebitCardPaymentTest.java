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

public class DebitCardPaymentTest {

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
    public void shouldDoPaymentByDebitCardWithStatusApproved() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getValidApprovedCardData();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkSuccessNotification();
        String paymentStatus = SqlHelper.getStatusPaymentEntity();
        assertEquals("APPROVED", paymentStatus);
    }

    @Test
    public void shouldNotDoPaymentByDebitCardWithStatusDeclined() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getValidDeclinedCardData();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkErrorNotification();
        String paymentStatus = SqlHelper.getStatusPaymentEntity();
        assertEquals("DECLINED", paymentStatus);
    }

    // NEGATIVE SCENARIOS

    // Card Number

    @Test
    public void shouldNotDoPaymentByDebitCardWhenFieldWithInvalidCardNumber() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getInvalidCardNumberIfFieldAllZeros();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkErrorNotification();
    }

    @Test
    public void shouldNotDoPaymentByDebitCardWhenCardNumberFieldAnotherBankCard() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getAnotherBankCardNumber();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkErrorNotification();
    }

    @Test
    public void shouldNotDoPaymentByDebitCardWhenFieldCardNumberWithOneDigit() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getInvalidCardNumberWithOneDigit();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByDebitCardWhenFieldCardNumberWithFifteenDigits() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getInvalidCardNumberWithFifteenDigits();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByDebitCardWhenCardNumberFieldEmpty() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getCardNumberFieldEmpty();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    // Month

    @Test
    public void shouldNotDoPaymentByDebitCardWhenFieldWithInvalidMonth() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getInvalidMonthWithIrrelevantValue();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkInvalidCardExpirationDate();
    }

    @Test
    public void shouldNotDoPaymentByDebitCardWhenMonthFieldIsZeros() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getInvalidMonthWithZeros();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkInvalidCardExpirationDate();
    }

    @Test
    public void shouldNotDoPaymentByDebitCardWhenFieldWithMonthExpiredCurrentYear() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getInvalidMonthIsCurrentYear();
        paymentPage.fillPaymentFormat(info);
        paymentPage.verifyCardExpired();
    }

    @Test
    public void shouldNotDoPaymentByDebitCardWhenFieldMonthOneDigit() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getInvalidMonthWithOneDigit();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByDebitCardWhenFieldMonthEmpty() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getFieldMonthEmpty();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    //  Year

    @Test
    public void shouldNotDoPaymentByDebitCardWhenFieldWithExpiredYear() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getCardWithExpiredYear();
        paymentPage.fillPaymentFormat(info);
        paymentPage.verifyCardExpired();
    }

    @Test
    public void shouldNotDoPaymentByDebitCardWhenFieldWithInvalidYearExpirationDate() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getInvalidYearExceedingCardExpirationDate();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkInvalidCardExpirationDate();
    }

    @Test
    public void shouldNotDoPaymentByDebitCardWhenYearFieldWithZeros() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getInvalidYearWithZeros();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkInvalidCardExpirationDate();
    }

    @Test
    public void shouldNotDoPaymentByDebitCardWhenYearFieldWithOneDigit() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getInvalidYearWithOneDigit();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByDebitCardWhenYearFieldEmpty() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getFieldYearEmpty();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    // Owner

    @Test
    public void shouldNotDoPaymentByDebitCardWhenOwnerFieldFilledCyrillic() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getInvalidOwnerWithCyrillic();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByDebitCardWhenOwnerFieldWithOneLatinWord() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getInvalidOwnerWithOneWord();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByDebitCardWhenOwnerFieldWithThreeLatinWords() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getInvalidOwnerWithThreeWords();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByDebitCardWhenOwnerFieldWithLowerCase() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getInvalidOwnerWithLowerCase();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByDebitCardWhenOwnerFieldWithUpperCase() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getInvalidOwnerWithUpperCase();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByDebitCardIfOwnerFieldWithOneLetter() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getInvalidOwnerWithOneLetter();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByDebitCardIfOwnerFieldWithLargeNumberLetters() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getInvalidOwnerWithLotsNumberOfLetters();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByDebitCardIfOwnerFieldWithDigits() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getInvalidOwnerWithDigits();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByDebitCardIfOwnerFieldWithSymbols() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getInvalidOwnerWithSymbols();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByDebitCardIfOwnerFieldEmpty() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getOwnerFieldEmpty();
        paymentPage.fillPaymentFormat(info);
        paymentPage.verifyEmptyField();
    }

    //  CVC/CVV

    @Test
    public void shouldNotDoPaymentByDebitCardWhenCVCIsOneDigit() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getInvalidCVCWithOneDigit();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByDebitCardWhenCVCIsTwoDigits() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getInvalidCVCWithTwoDigits();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByDebitCardIfCVCWithThreeZeros() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getInvalidCVCWithZeros();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }

    @Test
    public void shouldNotDoPaymentByDebitCardIfCVCFieldEmpty() {
        PaymentPage paymentPage = mainPage.getDebitCardPayment();
        DataHelper.CardInfo info = DataHelper.getEmptyCVCField();
        paymentPage.fillPaymentFormat(info);
        paymentPage.checkWrongFormat();
    }
}
