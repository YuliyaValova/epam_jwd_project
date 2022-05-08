package com.jwd.service.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Page<T> {
    private int pageNumber;
    private long totalElements;
    private int limit;
    private List<T> elements = new ArrayList<>();
    private String sortBy = "id";
    private String direction = "ASC";
    private String filter = "(1,2,3,4)";

    public Page() {}

    public Page(int pageNumber, long totalElements, int limit, List<T> elements, String sortBy, String direction, String filter) {
        this.pageNumber = pageNumber;
        this.totalElements = totalElements;
        this.limit = limit;
        this.elements = elements;
        this.sortBy = sortBy;
        this.direction = direction;
        this.filter = filter;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }


    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page<?> page = (Page<?>) o;
        return pageNumber == page.pageNumber && totalElements == page.totalElements && limit == page.limit && Objects.equals(elements, page.elements) && Objects.equals(sortBy, page.sortBy) && Objects.equals(direction, page.direction) && Objects.equals(filter, page.filter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageNumber, totalElements, limit, elements, sortBy, direction, filter);
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNumber=" + pageNumber +
                ", totalElements=" + totalElements +
                ", limit=" + limit +
                ", elements=" + elements +
                ", sortBy='" + sortBy + '\'' +
                ", direction='" + direction + '\'' +
                ", filter='" + filter + '\'' +
                '}';
    }
}
