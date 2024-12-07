package com.llm.backend.domain;

import com.llm.backend.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

@Entity
@BatchSize(size = 100)
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)// 생성자를 통한 item 셋팅을 막기 위해 protected 로 생성//jpa는 protected까지 생성자로 허용함
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id") //외래키
    private Order order;

    private int orderPrice; // 주문가격 (주문 당시 가격)
    private int count; //수량

    //@NoArgsConstructor(access = AccessLevel.PROTECTED) // 해당 어노테이션으로 대체
    /*protected OrderItem() { // 생성자를 통한 item 셋팅을 막기 위해 protected 로 생성//jpa는 protected까지 생성자로 허용함
    }*/

    //==생성메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }


    //==비지니스로직==//
    public void cancel() {
        getItem().addStock(count);
    }

    //==조회로직==//
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
