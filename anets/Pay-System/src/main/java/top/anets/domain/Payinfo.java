package top.anets.domain;

import java.util.Date;

import lombok.Data;

@Data
public class Payinfo {
    private Long id;

    private String orderId;

    private Double amount;

    private String payType;

    private Byte status;
    
    private Date createTime;

    private Date finishTime;

    
}