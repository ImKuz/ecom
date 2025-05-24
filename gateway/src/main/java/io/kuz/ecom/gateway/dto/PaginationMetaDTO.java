package io.kuz.ecom.gateway.dto;

public class PaginationMetaDTO {

    private Long total;
    private Long limit;
    private Long offset;

    public PaginationMetaDTO(Long total, Long limit, Long offset) {
        this.total = total;
        this.limit = limit;
        this.offset = offset;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }
}
