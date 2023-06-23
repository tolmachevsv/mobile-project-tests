package com.tolmachevsv.tests.local;

import com.tolmachevsv.annotations.Layer;
import io.qameta.allure.AllureId;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static io.appium.java_client.AppiumBy.*;
import static io.qameta.allure.Allure.step;

@Feature("Wikipedia")
@Story("Local")
@Owner("tolmachevsv")
@Layer("mobile")
@Tag("local_selenide_android")
public class LocalSearchTest extends LocalTestBase {

    @Test
    @AllureId("23339")
    @DisplayName("Checking the efficiency of the search string")
    @Tag("critical")
    void successfulSearchTest() {
        step("Skip on-boarding screen", () ->
                $(id("org.wikipedia.alpha:id/fragment_onboarding_skip_button")).click());
        step("Click on the search box and enter 'Java (programming language)'", () -> {
            $(accessibilityId("Search Wikipedia")).click();
            $(id("org.wikipedia.alpha:id/search_src_text")).sendKeys("Java (programming language)");
        });
        step("Verify content found", () ->
                $$(id("org.wikipedia.alpha:id/page_list_item_title"))
                        .shouldHave(sizeGreaterThan(0)));
    }

    @Test
    @AllureId("23341")
    @DisplayName("Checking the button name: 'Explore'")
    void checkButtonName() {
        step("Skip on-boarding screen", () ->
                $(id("org.wikipedia.alpha:id/fragment_onboarding_skip_button")).click());
        step("Verify the name of the button - 'Explore'", () -> {
            $(id("org.wikipedia.alpha:id/largeLabel")).shouldHave(text("Explore"));
        });
    }

    @Test
    @AllureId("23340")
    @DisplayName("Article is successfully added to Favorites")
    @Tag("critical")
    void AddToFavorites() {
        step("Find in the search bar 'Java (programming language)'", () -> {
            successfulSearchTest();
            $$(id("org.wikipedia.alpha:id/page_list_item_title")).get(0).click();
        });
        step("Save the article", () -> {
            $(id("org.wikipedia.alpha:id/article_menu_bookmark")).click();
        });
        step("Go back to the main page", () -> {
            $(className("android.widget.ImageButton")).click();
            $(className("android.widget.ImageButton")).click();
        });
        step("Go to saved articles", () -> {
            $(xpath("//android.widget.FrameLayout[@content-desc=\"Saved\"]")).click();
        });
        step("Verify the article was added to the saved", () -> {
            $(id("org.wikipedia.alpha:id/negativeButton")).click();
            $$(className("android.view.ViewGroup")).get(2).click();
            $(id("org.wikipedia.alpha:id/page_list_item_title"))
                    .shouldHave(text("Java (programming language)"));
        });
    }
}
