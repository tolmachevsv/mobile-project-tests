package com.tolmachevsv.tests.local;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.appium.java_client.AppiumBy.*;
import static io.qameta.allure.Allure.step;

@Tag("local_selenide_android")
public class LocalSearchTest extends LocalTestBase {

    @Test
    void successfulSearchTest() {
        step("Skip onboarding screen", () ->
                $(id("org.wikipedia.alpha:id/fragment_onboarding_skip_button")).click());
        step("Type search", () -> {
            $(accessibilityId("Search Wikipedia")).click();
            $(id("org.wikipedia.alpha:id/search_src_text")).sendKeys("Java (programming language)");
        });
        step("Verify content found", () ->
                $$(id("org.wikipedia.alpha:id/page_list_item_title"))
                        .shouldHave(sizeGreaterThan(0)));
    }

    @Test
    @DisplayName("Check if button's name is 'Explore'")
    void checkButtonName() {
        step("Skip onboarding screen", () ->
                $(id("org.wikipedia.alpha:id/fragment_onboarding_skip_button")).click());
        step("Check name", () -> {
            $(id("org.wikipedia.alpha:id/largeLabel")).shouldHave(text("Explore"));
        });
    }

    @Test
    void AddToFavorites() {
        step("Search 'Java (programming language)'", () -> {
            successfulSearchTest();
            $$(id("org.wikipedia.alpha:id/page_list_item_title")).get(0).click();
        });
        step("Save the article", () -> {
            $(id("org.wikipedia.alpha:id/article_menu_bookmark")).click();
        });
        step("Back to the main page", () -> {
            $(className("android.widget.ImageButton")).click();
            $(className("android.widget.ImageButton")).click();
        });
        step("Go to the saved articles", () -> {
            $(xpath("//android.widget.FrameLayout[@content-desc=\"Saved\"]")).click();
        });
        step("Check if the article was added in saved articles", () -> {
            $(id("org.wikipedia.alpha:id/negativeButton")).click();
            $$(className("android.view.ViewGroup")).get(2).click();
            $(id("org.wikipedia.alpha:id/page_list_item_title"))
                    .shouldHave(text("Java (programming language)"));
        });
    }
}
