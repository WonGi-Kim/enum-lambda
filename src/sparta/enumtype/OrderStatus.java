package sparta.enumtype;

public enum OrderStatus {
    PRODUCT_ORDER,      // 상품 주문
    ORDER_RECEIVED,     // 주문 접수
    ORDER_CANCLE,
    PRODUCT_SHIPPED,    // 상품 발송
    DELIVERY_COMPLETE,  // 배송 완료
    PURCHASE_DECIDED,   // 구매 결정
    EXCHANGE_REQUESTED, // 교환
    EXCHANGE_SHIPPED,   // 재발송
    REFUND_REQUESTED,   // 반품
    REFUND_PRICE        // 환불
}
