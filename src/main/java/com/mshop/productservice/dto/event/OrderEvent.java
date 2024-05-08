package com.mshop.productservice.dto.event;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderEvent {

    private Long orderId;

    private Long userId;

    private Event event;

    public enum Event {

        USER_ORDER,

        ADMIN_ACCEPTED,

        ADMIN_PAID_CONFIRM,

        ADMIN_CANCELED

    }

}
