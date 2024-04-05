// Initialize Stripe.js
const stripe = Stripe('pk_test_51Ozj7i00KXfzHYtMS0anKahBQMVdRQRiwWDREAiJTb5ZePEbsarELB237wrQWKo9ztPimulSElNeZtksJpdOOdeq006lbef5y0');

initialize();
const RequestToPay = {
    amount:"200"
}

// Fetch Checkout Session and retrieve the client secret
async function initialize() {
    const fetchClientSecret = async () => {
        const response = await fetch("/create-checkout-session", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(RequestToPay),
        });
        const { clientSecret } = await response.json();
        return clientSecret;
    };

    // Initialize Checkout
    const checkout = await stripe.initEmbeddedCheckout({
        fetchClientSecret,
    });

    // Mount Checkout
    checkout.mount('#checkout');
}