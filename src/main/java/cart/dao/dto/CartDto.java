package cart.dao.dto;

public class CartDto {

    private final Long id;
    private final Long memberId;
    private final Long itemId;

    public CartDto(Long id, Long memberId, Long itemId) {
        this.id = id;
        this.memberId = memberId;
        this.itemId = itemId;
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public Long getItemId() {
        return itemId;
    }
}