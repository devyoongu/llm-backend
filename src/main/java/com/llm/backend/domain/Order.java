package com.llm.backend.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)// 생성자를 통한 item 셋팅을 막기 위해 protected 로 생성//jpa는 protected까지 생성자로 허용함
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY) // 여러주문건이 하나의 회원과 연관
    @JoinColumn(name = "member_id") //foregin key
    private Member member; //주문 회원

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)//서로 맴핑된 엔티티 클래스 한곳에 persist를 하면 맴핑된 엔티티에도 persist를 해준다.
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;//배송정보

    private LocalDateTime orderDate;//주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status;//주문상태[ORDER, CANCEL]

    //== 연관관계 편의 메서드??==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    //==생성메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        //static 제거했을때는? 생성 메서드이기 때문에 class 기반의 static 키워드 추가
        //생성메서드가 아닌 일반 생성자로 만들면 안될까??
        //-- 엔티티가 생성자를 만들지 않는 이유 ? 시퀀스 때문?

        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==비지니스로직==//
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소할 수 없습니다. ");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem:
             orderItems) {
            orderItem.cancel();
        }
    }

    //==조회로직==//
//    public int getTotalPrice() {  //OrderItem의 Lazy 강제 초기화로 임시 주석
//        /*int totalPrice = 0;
//        for (OrderItem orderItem : orderItems) {
//            totalPrice += orderItem.getTotalPrice();
//        }
//        return totalPrice;*/
//
//        return orderItems.stream()
//                .mapToInt(OrderItem::getTotalPrice)
//                .sum();
//    }


}
