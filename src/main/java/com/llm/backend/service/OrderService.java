package com.llm.backend.service;

import com.llm.backend.domain.item.Item;
import com.llm.backend.repository.ItemRepository;
import com.llm.backend.repository.MemberRepository;
import com.llm.backend.repository.OrderRepository;
import com.llm.backend.repository.OrderSearch;
import com.llm.backend.domain.Delivery;
import com.llm.backend.domain.Member;
import com.llm.backend.domain.Order;
import com.llm.backend.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    //주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count); //*OrderItem은 왜 주입받지 않고 사용하나 //빌더로도 대체 가능 할듯
        //생성자로 할경우 사용하는 측마다 로직이 분산되기 때문에 protect 로 인스턴스 생성을 막고 static 메서드만으로 공통화

        //주문생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문저장
        orderRepository.save(order);

        return order.getId();
    }

    //취소
    @Transactional
    public void cancelOrder(Long orderId) {
        //주문 엔티티조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel(); //orderId 정보를 안넘겨줘도 되는건 debugger를 통해 확인해 보자! 클래스 내부에서
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }

    //검색
}

