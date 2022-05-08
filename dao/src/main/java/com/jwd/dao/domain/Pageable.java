package com.jwd.dao.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pageable<T> {

    private int pageNumber;
    private long totalElements;
    private int limit;
    private List<T> elements = new ArrayList<>();
    private String sortBy = "id";
    private String direction = "ASC";
    private String filter = "(1,2,3,4)";

    public Pageable() {
    }

    public Pageable(int pageNumber, long totalElements, int limit, List<T> elements) {
        this.pageNumber = pageNumber;
        this.totalElements = totalElements;
        this.limit = limit;
        this.elements = elements;
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

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pageable<?> pageable = (Pageable<?>) o;
        return pageNumber == pageable.pageNumber && totalElements == pageable.totalElements && limit == pageable.limit && Objects.equals(elements, pageable.elements) && Objects.equals(sortBy, pageable.sortBy) && Objects.equals(direction, pageable.direction) && Objects.equals(filter, pageable.filter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageNumber, totalElements, limit, elements, sortBy, direction, filter);
    }
}