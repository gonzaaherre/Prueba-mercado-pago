package com.mp.prueba.Controller;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.mp.prueba.ModelEntity.UserBuyer;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/apiMp")
@CrossOrigin("*")
public class mpcpntroller {
    @PostMapping
    public String getList(@RequestBody UserBuyer userBuyer) {
        if (userBuyer == null) {
            return "error json";
        }
        String title = userBuyer.getTitle();
        int quantity = userBuyer.getQuantity();
        int price = userBuyer.getUnit_price();


        try {
            MercadoPagoConfig.setAccessToken("TEST-7646015955067723-052208-bbd41f2a9bbcc395124a6dc722a605c8-296809149");

            //Creamos la preferencia
            //PREFERENCIA DE VENTA
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .title(title)
                    .quantity(quantity)
                    .unitPrice(new BigDecimal(price))
                    .build();
            List<PreferenceItemRequest> items = new ArrayList<>();
            items.add(itemRequest);

            //preferencia de control de sucesos en el caso que toque lo redirecciona a otra pagna
            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("https://www.youtube.com/watch?v=mCdA4bJAGGk&ab_channel=sweetblue.")
                    .pending("https://www.youtube.com/watch?v=OiSo6jEEuqM&ab_channel=TheLaPlanta")
                    .failure("https://youtu.be/Flmg8uYMGZQ?si=5I1zl_P5n8IiSiiU")
                    .build();

            //preferencia que tendra todas las preferencias que se hayan creado
            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backUrls)
                    .build();

            // creo un cliente para comunicarme con mp
            PreferenceClient client = new PreferenceClient();
            //se crea una nueeva prefertencia que es igual a lla respuesta
            Preference preference = client.create(preferenceRequest);
            return preference.getId();


        } catch (MPException | MPApiException e) {
            return e.toString();
        }
    }
}

