package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.CardReplenishmentPage;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPageV1;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {
    LoginPageV1 loginPage;


    @BeforeEach
    void setup() {
        loginPage = open("http://localhost:9999", LoginPageV1.class);
    }

    @Test
    void shouldTransferMoneyFromFirstToSecond() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var dashboardPage = verificationPage.validVerify(DataHelper.getVerificationCodeFor(authInfo));
        var cardOne = DataHelper.getCardOne();
        var cardTwo = DataHelper.getCardTwo();
        var cardOneBalance = dashboardPage.getCardBalance(cardOne);
        var cardTwoBalance = dashboardPage.getCardBalance(cardTwo);
        var amount = DataHelper.generateValidAmount(cardOneBalance);
        var cardReplenishmentPage = dashboardPage.selectCard(cardTwo);
        dashboardPage = cardReplenishmentPage.validMoneyTransfer(String.valueOf(amount), cardOne);
        var expectedBalanceFirstCard = cardOneBalance - amount;
        var expectedBalanceSecondCard = cardTwoBalance + amount;
        var actualBalanceFirstCard = dashboardPage.getCardBalance(cardOne);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(cardTwo);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }

    @Test
    void shouldTransferMoneyFromSecondToFirst() {
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var dashboardPage = verificationPage.validVerify(DataHelper.getVerificationCodeFor(authInfo));
        var cardOne = DataHelper.getCardOne();
        var cardTwo = DataHelper.getCardTwo();
        var cardOneBalance = dashboardPage.getCardBalance(cardOne);
        var cardTwoBalance = dashboardPage.getCardBalance(cardTwo);
        var amount = DataHelper.generateValidAmount(cardTwoBalance);
        var cardReplenishmentPage = dashboardPage.selectCard(cardOne);
        dashboardPage = cardReplenishmentPage.validMoneyTransfer(String.valueOf(amount), cardTwo);
        var expectedBalanceFirstCard = cardOneBalance + amount;
        var expectedBalanceSecondCard = cardTwoBalance - amount;
        var actualBalanceFirstCard = dashboardPage.getCardBalance(cardOne);
        var actualBalanceSecondCard = dashboardPage.getCardBalance(cardTwo);
        assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }

}

