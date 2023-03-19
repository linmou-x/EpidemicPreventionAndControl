package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 用于分页
 */
public class PageUserDTO {
    Integer currentPage;
    Integer pageSize;
    UserDTO userDTO;
}
