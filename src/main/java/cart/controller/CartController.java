package cart.controller;

import cart.auth.Authentication;
import cart.controller.dto.CartResponse;
import cart.controller.dto.ItemResponse;
import cart.controller.dto.MemberDto;
import cart.controller.dto.MemberResponse;
import cart.service.CartService;
import cart.service.MemberService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/carts")
@RestController
public class CartController {

    private final CartService cartService;
    private final MemberService memberService;

    public CartController(CartService cartService, MemberService memberService) {
        this.cartService = cartService;
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<ItemResponse>> findAllCarts(@Authentication MemberDto memberDto) {
        MemberResponse findMember = memberService.findByEmailAndPassword(memberDto.getEmail(),
                memberDto.getPassword());

        List<ItemResponse> response = cartService.findAllByMemberId(findMember.getId());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{itemId}")
    public ResponseEntity<CartResponse> addItem(@PathVariable Long itemId,
                                                @Authentication MemberDto memberDto) {
        MemberResponse findMember = memberService.findByEmailAndPassword(memberDto.getEmail(),
                memberDto.getPassword());

        CartResponse cartResponse = cartService.save(findMember.getId(), itemId);
        return ResponseEntity.created(URI.create("/carts/" + cartResponse.getCartId()))
                .body(cartResponse);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId, @Authentication MemberDto memberDto) {
        MemberResponse findMember = memberService.findByEmailAndPassword(memberDto.getEmail(),
                memberDto.getPassword());

        cartService.delete(findMember.getId(), itemId);
        return ResponseEntity.noContent().build();
    }
}
