package com.michelesalvucci.prenations.web.rest.responses;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Data;

@Data
public class PNPageResponse implements Serializable {
    
    private Long totalElements;
    private Integer totalPages;
    private Integer pageNumber;
    private Integer pageSize;
    private List<?> content;

    public PNPageResponse(Page<?> page) {
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.content = page.getContent();
    }
}
