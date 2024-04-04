package com.example.devise.Paiement;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Controller
public class PaymentIntentController {

    @Autowired
    private MobilePaiementService mobilePaiementService;

    @PostMapping("/create-checkout-session")
    public Map<String, String> createPaymentIntent(@RequestBody Long amount)
            throws StripeException {
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setUiMode(SessionCreateParams.UiMode.EMBEDDED)
                        .setReturnUrl("http://localhost:8099/return.html?session_id={CHECKOUT_SESSION_ID}")
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        .setPriceData(
                                                SessionCreateParams.LineItem.PriceData.builder()
                                                        .setCurrency("usd")
                                                        .setUnitAmount(amount)
                                                        .setProductData(
                                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                        .setName("Exchange")
                                                                        .build())
                                                        .build())
                                        .build())
                        .build();
        Session session = Session.create(params);

        Map<String, String> map = new HashMap();
        map.put("clientSecret", session.getClientSecret());

        return map;
    }

    @GetMapping("/session-status")
    public Map<String, String> createPaymentIntent(@RequestParam String sessionId) throws StripeException {
        Session session = Session.retrieve(sessionId);

        Map<String, String> map = new HashMap();
        map.put("status", session.getStatus());
        map.put("customer_email", session.getCustomerDetails().getEmail());

        return map;
    }

    @PostMapping("/pay-by-mobilemoney")
    public void createMobileMoney(@RequestBody RequestToPay requestToPay){
        var id = mobilePaiementService.sandBoxUserProvisionning();
        var key = mobilePaiementService.createApiKey(id);
        var token = mobilePaiementService.generateToken(id, key);
        mobilePaiementService.requestToPay(token);
    }

}
