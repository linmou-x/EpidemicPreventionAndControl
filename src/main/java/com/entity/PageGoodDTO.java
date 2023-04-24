package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageGoodDTO implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;
    Integer currentPage;
    Integer pageSize;
    GoodDTO goodDTO;
}
