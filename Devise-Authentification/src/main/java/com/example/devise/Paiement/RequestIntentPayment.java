package com.example.devise.Paiement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestIntentPayment {

    private Long amount;

    private String email;
}
