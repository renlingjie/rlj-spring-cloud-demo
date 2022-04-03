package com.rlj.springcloud;

import lombok.Data;

/**
 * @author Renlingjie
 * @name
 * @date 2022-01-26
 */
@Data //lombok自动生成get/set/toString
public class Friend {
    private String name;
    private String address;
}
