package com.mshop.productservice.dto.event;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProductEvent {

    private Long productId;

    private String productName;

    private Event event;


    public enum Event {

        CREATED,

        MODIFIED,

        REMOVED

    }

}
