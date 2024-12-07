package com.llm.backend.repository.order.query;

import com.llm.backend.domain.Address;
import com.llm.backend.domain.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(of = "orderId") // 다른 객체와 묶기 위해 orderId 기준으로 묶어준다. groupby 기준
public class OrderQueryDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate; //주문시간
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemQueryDto> orderItems;

    public OrderQueryDto(Long orderId, String name, LocalDateTime orderDate,
                         OrderStatus orderStatus, Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }


    public OrderQueryDto(Long orderId, String name, LocalDateTime orderDate,
    OrderStatus orderStatus, Address address, List<OrderItemQueryDto> orderItems) {
     this.orderId = orderId;
     this.name = name;
     this.orderDate = orderDate;
     this.orderStatus = orderStatus;
     this.address = address;
     this.orderItems = orderItems;
    }

}
