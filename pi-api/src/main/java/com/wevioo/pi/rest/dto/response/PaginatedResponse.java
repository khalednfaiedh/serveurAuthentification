package com.wevioo.pi.rest.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
/**
 * PaginatedResponse class represents a generic paginated response containing paginated data.
 *
 * @param <T> Type parameter for the content in the paginated response.
 */
@Getter
@Setter
public class PaginatedResponse <T>{
    /**
     * Represents the current page number.
     */
    private Integer page;
    /**
     * Represents the number of items per page.
     */
    private Integer pageSize;
    /**
     * Represents the content of the paginated response.
     */
    private List<T> content;
    /**
     * Represents the total number of pages available.
     */
    private Integer totalPage;
    /**
     * Represents the total number of elements.
     */
    private long totalElement;
}
