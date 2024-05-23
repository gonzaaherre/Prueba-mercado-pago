const mp = new MercadoPago('TEST-fad4162a-3c89-4217-a1fa-10956a4f1fa4', {
    locale: 'es-AR'
});

const MP = async () => {
    try {
        let miArticulo = {
            title: 'Producto de ejemplo',
            quantity: 1,
            unit_price: 100.0
        };

        const response = await fetch('http://localhost:8080/apiMp', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(miArticulo)
        });

        const preference = await response.text();
        createCheckoutButton(preference);
        console.log("preferencia: " + preference);
    } catch (e) {
        alert("error: " + e);
    }
};

const createCheckoutButton = (preferenceId_OK) => {
    const bricksBuilder = mp.bricks();
    if (window.checkoutButton) window.checkoutButton.unmount();
    window.checkoutButton = bricksBuilder.create("wallet", "wallet_container", {
        initialization: {
            preferenceId: preferenceId_OK,
        },
    });
};

// Llama a
