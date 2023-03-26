package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageGoodDTO {
    Integer currentPage;
    Integer pageSize;
    GoodDTO goodDTO;
}
