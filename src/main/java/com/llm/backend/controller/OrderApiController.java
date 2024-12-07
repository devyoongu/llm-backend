package com.llm.backend.controller;

import com.llm.backend.repository.OrderRepository;
import com.llm.backend.repository.OrderSearch;
import com.llm.backend.repository.order.query.OrderFlatDto;
import com.llm.backend.repository.order.query.OrderItemQueryDto;
import com.llm.backend.repository.order.query.OrderQueryDto;
import com.llm.backend.repository.order.query.OrderQueryRepository;
import com.llm.backend.domain.Address;
import com.llm.backend.domain.Order;
import com.llm.backend.domain.OrderItem;
import com.llm.backend.domain.OrderStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll(new OrderSearch());

        for (Order order : all) {
            order.getMember().getName(); //Lazy 강제 초기화
            order.getDelivery().getAddress(); //Lazy 강제 초기환
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName()); //Lazy 강제 초기화
        }
        return all;
    }

    @GetMapping("/api/v2/orders")
    public List<OrderDto> orderV2() {
        List<Order> orders = orderRepository.findAll(new OrderSearch());

        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());

        return result;
    }

    @GetMapping("/api/v3/orders") //fetch 조인 - 1:다 관계에서는 페이징이 불가능
    public List<OrderDto> orderV3() {
        List<Order> orders = orderRepository.findAllWithItem();

        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());

        return result;
    }

    /**
     * 1:N(컬렉션)에 대한 페이징 한계 돌파
     * 1. xToOne 관계는 fetchJoin
     * 2. coolection은 DTO 초기화로 호출 하여 hibernate.default_batch_fetch_size 통해 in 절 쿼리
     */

    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page (@RequestParam(value = "offset", defaultValue = "0") int offset
            ,@RequestParam(value = "limit", defaultValue = "100") int limit) {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);

        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());

        return result;
    }


    @GetMapping("/api/v4/orders") // jpa에서 DTO 직접 조회 - order, orderItem 별도 조회 후 setOrderItem
    public List<OrderQueryDto> ordersV4 () {
        return orderQueryRepository.findOrderQueryDtos();
    }

    @GetMapping("/api/v5/orders") // jpa에서 DTO 직접 조회 - 최적화
    public List<OrderQueryDto> ordersV5 () {
        return orderQueryRepository.findAllByDto_optimization();
    }

    @GetMapping("/api/v6/orders") // jpa에서 DTO 직접 조회 - 쿼리 1회 / 단점 : 페이징 불가능
    public List<OrderQueryDto> ordersV6 () {
        List<OrderFlatDto> flats = orderQueryRepository.findAllByDto_flat();


        List<OrderQueryDto> collect = flats.stream()
                .collect(groupingBy(o -> new OrderQueryDto(o.getOrderId(), o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()), mapping(o -> new OrderItemQueryDto(o.getOrderId(),
                        o.getItemName(), o.getOrderPrice(), o.getCount()), toList())
                )).entrySet().stream()
                .map(e -> new OrderQueryDto(e.getKey().getOrderId(), e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(),
                        e.getKey().getAddress(), e.getValue()))
                .collect(toList());

        return collect;
    }

    @Data
    static class OrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate; //주문시간
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;

        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            orderItems = order.getOrderItems().stream()
                    .map(orderItem -> new OrderItemDto(orderItem))
                    .collect(toList());
        }
    }

    @Data
    static class OrderItemDto {
        private String itemName;//상품 명
        private int orderPrice; //주문 가격
        private int count; //주문 수량

        public OrderItemDto(OrderItem orderItem) {
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }



}
