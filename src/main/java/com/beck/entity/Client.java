package com.beck.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Client {
    private Integer clientId;
    private String name;
    private String address;
    private String city;
    private String state;
    private String phone;
}
