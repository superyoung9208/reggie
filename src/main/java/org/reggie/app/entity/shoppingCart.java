package org.reggie.app.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class shoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private Long dishId;

    private Long setmealId;

    private String name;

    private String image;

    private Integer number;

    private BigDecimal amount;

    private Integer isDeleted;

    private LocalDateTime createTime;

}
