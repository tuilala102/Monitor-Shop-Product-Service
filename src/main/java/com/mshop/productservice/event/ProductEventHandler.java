package com.mshop.productservice.event;

import com.mshop.productservice.dto.event.ProductEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductEventHandler {

    private final SimpMessagingTemplate messagingTemplate;


    @EventListener
    public void handleProductEvent(ProductEvent productEvent) {
        messagingTemplate.convertAndSend("/topic/product", productEvent);
    }

}
