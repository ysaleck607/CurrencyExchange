package com.example.devise.Paiement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseIntentPayment {
    private String intentID;
    private String clientSecret;
}
