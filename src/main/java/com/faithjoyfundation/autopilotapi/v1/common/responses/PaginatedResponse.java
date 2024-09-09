package com.faithjoyfundation.autopilotapi.v1.common.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResponse<T> {
    private List<T> data;
    private int currentPage;
    private int perPage;
    private int totalCurrentPageElements;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean hasNext;
    private boolean last;

    public PaginatedResponse(Page<T> paginatedData) {
        this.data = paginatedData.getContent();
        this.currentPage = paginatedData.getNumber();
        this.perPage = paginatedData.getSize();
        this.totalCurrentPageElements = paginatedData.getNumberOfElements();
        this.totalElements = paginatedData.getTotalElements();
        this.totalPages = paginatedData.getTotalPages();
        this.first = paginatedData.isFirst();
        this.hasNext = paginatedData.hasNext();
        this.last = paginatedData.isLast();
    }
}