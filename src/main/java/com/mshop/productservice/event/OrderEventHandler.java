package com.mshop.productservice.event;

import com.mshop.productservice.dto.event.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventHandler {

    private final SimpMessagingTemplate messagingTemplate;


    @EventListener
    public void handleProductEvent(OrderEvent orderEvent) {
        // neu user dat hang thi gui thong bao cho admin
        // /topic/admin
        if (OrderEvent.Event.USER_ORDER.equals(orderEvent.getEvent())) {
            messagingTemplate.convertAndSend("/topic/order/admin", orderEvent);
        }

        // admin xac nhan, tu choi don hang thi gui cho user
        // /topic/<userId>/admin
        if (OrderEvent.Event.ADMIN_ACCEPTED.equals(orderEvent.getEvent())
                || OrderEvent.Event.ADMIN_CANCELED.equals(orderEvent.getEvent())
                || OrderEvent.Event.ADMIN_PAID_CONFIRM.equals(orderEvent.getEvent())) {
            messagingTemplate.convertAndSend("/topic/" + orderEvent.getUserId() + "/order", orderEvent);
        }

    }

}
