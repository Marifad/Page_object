package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardReplenishmentPage {
    private final SelenideElement heading = $(byText("Пополнение карты"));
    private final SelenideElement amount = $("[data-test-id=amount] input");
    private final SelenideElement from = $("[data-test-id=from] input");
    private SelenideElement actionTransfer = $("[data-test-id=action-transfer]");
    private SelenideElement errorMessage = $("[data-test-id=error-notification]");

    public CardReplenishmentPage() {
        heading.shouldBe(visible);
    }

    public DashboardPage validMoneyTransfer(String transferAmount, DataHelper.Card card) {
        moneyTransfer(transferAmount, card);
        return new DashboardPage();
    }


    public void moneyTransfer(String transferAmount, DataHelper.Card card) {
        amount.setValue(transferAmount);
        from.setValue(card.getNumber());
        actionTransfer.click();
    }

    public void findErrorMessage(String errorMessageText) {
        errorMessage.shouldHave(exactText(errorMessageText), Duration.ofSeconds(15)).shouldBe(visible);
    }
}
