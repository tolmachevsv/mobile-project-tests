package com.tolmachevsv.tests.browserstack;

import com.tolmachevsv.annotations.Layer;
import io.qameta.allure.AllureId;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static io.appium.java_client.AppiumBy.accessibilityId;
import static io.appium.java_client.AppiumBy.id;
import static io.qameta.allure.Allure.step;

@Feature("Wikipedia")
@Story("BrowserStack")
@Owner("tolmachevsv")
@Layer("mobile")
@Tag("browserstack_selenide_android")
public class BrowserStackSearchTests extends BrowserStackTestBase {

    @Test
    @AllureId("23338")
    @DisplayName("Checking the efficiency of the search string")
    @Tag("critical")
    void successfulSearchTest() {
        step("Enter 'java' in search field", () -> {
            $(accessibilityId("Search Wikipedia")).click();
            $(id("org.wikipedia.alpha:id/search_src_text")).sendKeys("java");
        });
        step("Verify content found", () ->
                $$(id("org.wikipedia.alpha:id/page_list_item_title"))
                        .shouldHave(sizeGreaterThan(0)));
    }
}