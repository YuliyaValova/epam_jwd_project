package com.jwd.dao.domain;

import java.util.ArrayList;
import java.util.List;

public class PageableOrder<T> {

    private int pageNumber;
    private long totalElements;
    private int limit;
    private String status;
    private long customerId;
    private List<T> elements = new ArrayList<>();
    private String sortBy = "id";
    private String direction = "ASC";

    public PageableOrder() {
    }

    public PageableOrder(int pageNumber, long totalElements, int limit, String status, long customerId, List<T> elements, String sortBy, String direction) {
        this.pageNumber = pageNumber;
        this.totalElements = totalElements;
        this.limit = limit;
        this.status = status;
        this.customerId = customerId;
        this.elements = elements;
        this.sortBy = sortBy;
        this.direction = direction;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PageableOrder<?> that = (PageableOrder<?>) o;

        if (pageNumber != that.pageNumber) return false;
        if (totalElements != that.totalElements) return false;
        if (limit != that.limit) return false;
        if (customerId != that.customerId) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (elements != null ? !elements.equals(that.elements) : that.elements != null) return false;
        if (sortBy != null ? !sortBy.equals(that.sortBy) : that.sortBy != null) return false;
        return direction != null ? direction.equals(that.direction) : that.direction == null;
    }

    @Override
    public int hashCode() {
        int result = pageNumber;
        result = 31 * result + (int) (totalElements ^ (totalElements >>> 32));
        result = 31 * result + limit;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (int) (customerId ^ (customerId >>> 32));
        result = 31 * result + (elements != null ? elements.hashCode() : 0);
        result = 31 * result + (sortBy != null ? sortBy.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }
}
