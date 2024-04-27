package com.example.common.enums;

/**
 * 订单枚举类
 *
 * @author Arrebol
 * @date 2024/4/27
 */
public enum OrderStatusEnum {
    CANCEL("已取消"),
    UNPAID("待支付"),
    UNSEND("待发货"),
    UNRECEIVE("待收货"),
    FINISH("已完成");

    public String value;

    OrderStatusEnum(String value) {
        this.value = value;
    }
}
