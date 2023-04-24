package com.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Charles
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 用于分页
 */
public class PageUserDTO implements Serializable {
    private static final long serialVersionUID = -40356785423868312L;
    Integer currentPage;
    Integer pageSize;
    UserDTO userDTO;
}
