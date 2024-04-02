package org.reggie.app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String number;

    private Integer status;

    private Long userId;

    private Integer payMethod;

    private BigDecimal amount;

    private Long addressBookId;

    private String remark;

    private String phone;

    private String address;

    private String userName;

    private String consignee;

    private LocalDateTime orderTime;

    private LocalDateTime checkoutTime;

}
