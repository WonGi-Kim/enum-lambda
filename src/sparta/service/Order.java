package sparta.service;

import sparta.enumtype.OrderStatus;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Predicate;

public class Order {
    private String productName;
    private OrderStatus status;


    private static final Map<OrderStatus, Predicate<OrderStatus>> TRANSITION = new EnumMap<>(OrderStatus.class);

    static {
        // 상품 주문 -> 주문 접수
        TRANSITION.put(OrderStatus.PRODUCT_ORDER, nextStatus -> nextStatus == OrderStatus.ORDER_RECEIVED);
        // 주문 접수 -> 상품 발송 or 주문 취소
        TRANSITION.put(OrderStatus.ORDER_RECEIVED, nextStatus ->
                nextStatus == OrderStatus.PRODUCT_SHIPPED || nextStatus == OrderStatus.ORDER_CANCLE);
        // 주문 취소 -> false
        TRANSITION.put(OrderStatus.ORDER_CANCLE, nextStatus -> false);
        // 상품 발송 -> 배송 완료
        TRANSITION.put(OrderStatus.PRODUCT_SHIPPED, nextStatus -> nextStatus == OrderStatus.DELIVERY_COMPLETE);
        // 배송 완료 -> 구매 결정 or 교환 or 반품
        TRANSITION.put(OrderStatus.DELIVERY_COMPLETE, nextStatus ->
                nextStatus == OrderStatus.PURCHASE_DECIDED || nextStatus == OrderStatus.EXCHANGE_REQUESTED || nextStatus == OrderStatus.REFUND_REQUESTED);
        // 구매 결정 -> false
        TRANSITION.put(OrderStatus.PURCHASE_DECIDED, nextStatus -> false); // 전환 불가
        // 교환 요청 -> 재배송
        TRANSITION.put(OrderStatus.EXCHANGE_REQUESTED, nextStatus -> nextStatus == OrderStatus.EXCHANGE_SHIPPED);
        // 재배송 -> 배송 완료
        TRANSITION.put(OrderStatus.EXCHANGE_SHIPPED, nextStatus -> nextStatus == OrderStatus.DELIVERY_COMPLETE);
        // 반품 -> 환불
        TRANSITION.put(OrderStatus.REFUND_REQUESTED, nextStatus -> nextStatus == OrderStatus.REFUND_PRICE);
        // 환불 -> false
        TRANSITION.put(OrderStatus.REFUND_PRICE, nextStatus -> false);
    }

    public Order(String productName, OrderStatus orderStatus) {
        this.productName = productName;
        this.status = orderStatus;
    }

    // 생성 시점에 넘어온 this.status = orderStatus; 와 메서드 호출 시 넘어오는 OrderStatus nextStatus 비교
    public boolean isChangable(OrderStatus nextStatus) {
        return TRANSITION.getOrDefault(this.status, next -> false).test(nextStatus);
    }
}
