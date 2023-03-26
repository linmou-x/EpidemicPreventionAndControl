package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageServiceDTO {
    Integer currentPage;
    Integer pageSize;
    ServiceDTO serviceDTO;
}
