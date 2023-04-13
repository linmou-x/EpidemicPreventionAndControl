package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Charles
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageOrderDTO {
    Integer currentPage;
    Integer pageSize;
    OrderDTO orderDTO;
}
