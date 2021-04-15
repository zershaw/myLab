package com.czx.pojo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
public class People implements Serializable {
    private Integer id;
    private String name;

}
