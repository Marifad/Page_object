package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private final SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(DataHelper.Card card) {
        // TODO: перебрать все карты и найти по атрибуту data-test-id
        var text = cards.findBy(attribute("data-test-id", card.getTestId())).getText();
        return extractBalance(text);
    }

    public CardReplenishmentPage selectCard(DataHelper.Card card) {
        cards.findBy(text(card.getNumber().substring(15))).$("button").click();
        return new CardReplenishmentPage();
    }

    public int extractBalance(String text) {
        var beginIndex = text.indexOf(balanceStart);
        var endIndex = text.indexOf(" р.");
        var balance = text.substring(beginIndex + balanceStart.length(), endIndex);
        return Integer.parseInt(balance);
    }
}

