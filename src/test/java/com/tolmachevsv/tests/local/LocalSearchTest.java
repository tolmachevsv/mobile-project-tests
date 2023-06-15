package com.tolmachevsv.tests.local;

import com.tolmachevsv.annotations.Layer;
import io.qameta.allure.AllureId;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.appium.java_client.AppiumBy.*;
import static io.qameta.allure.Allure.step;

@Feature("Wikipedia")
@Owner("tolmachevsv")
@Layer("mobile")
@Tag("local_selenide_android")
public class LocalSearchTest extends LocalTestBase {

    @Test
    @AllureId("23339")
    @DisplayName("Проверка работоспособности поисковой строки")
    @Tag("critical")
    void successfulSearchTest() {
        step("Пропустить экран обучения", () ->
                $(id("org.wikipedia.alpha:id/fragment_onboarding_skip_button")).click());
        step("Нажать на поле поисковой строки и ввести 'Java (programming language)'", () -> {
            $(accessibilityId("Search Wikipedia")).click();
            $(id("org.wikipedia.alpha:id/search_src_text")).sendKeys("Java (programming language)");
        });
        step("Убедиться, что контент найден", () ->
                $$(id("org.wikipedia.alpha:id/page_list_item_title"))
                        .shouldHave(sizeGreaterThan(0)));
    }

    @Test
    @AllureId("23341")
    @DisplayName("Проверка наименования кнопки: 'Explore'")
    void checkButtonName() {
        step("Пропустить экран обучения", () ->
                $(id("org.wikipedia.alpha:id/fragment_onboarding_skip_button")).click());
        step("Убедиться, что наименование кнопки - 'Explore'", () -> {
            $(id("org.wikipedia.alpha:id/largeLabel")).shouldHave(text("Explore"));
        });
    }

    @Test
    @AllureId("23340")
    @DisplayName("Проверка функционала добавления в Избранное")
    @Tag("critical")
    void AddToFavorites() {
        step("Найти в поисковой строке 'Java (programming language)'", () -> {
            successfulSearchTest();
            $$(id("org.wikipedia.alpha:id/page_list_item_title")).get(0).click();
        });
        step("Сохранить статью", () -> {
            $(id("org.wikipedia.alpha:id/article_menu_bookmark")).click();
        });
        step("Вернуться на главную страницу", () -> {
            $(className("android.widget.ImageButton")).click();
            $(className("android.widget.ImageButton")).click();
        });
        step("Перейти к сохраненным статьям", () -> {
            $(xpath("//android.widget.FrameLayout[@content-desc=\"Saved\"]")).click();
        });
        step("Убедиться, что статья добавилась в сохраненные", () -> {
            $(id("org.wikipedia.alpha:id/negativeButton")).click();
            $$(className("android.view.ViewGroup")).get(2).click();
            $(id("org.wikipedia.alpha:id/page_list_item_title"))
                    .shouldHave(text("Java (programming language)"));
        });
    }
}
