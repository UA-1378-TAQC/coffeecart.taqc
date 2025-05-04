package com.coffeecart.ui.data;

public enum PaymentFormTexts {
    HEADER("Payment details"),
    PARAGRAPH("We will send you a payment link via email."),
    NAME_LABEL("Name"),
    EMAIL_LABEL("Email"),
    CHECKBOX_LABEL("I would like to receive order updates and promotional messages."),
    SUBMIT_BUTTON("Submit");

    private final String text;

    PaymentFormTexts(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
