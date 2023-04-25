package cart.controller;

import cart.controller.dto.ItemRequest;
import cart.controller.dto.ItemResponse;
import cart.service.ItemService;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ResponseEntity<ItemResponse> addItem(@RequestBody @Valid ItemRequest itemRequest) {
        ItemResponse itemResponse = itemService.add(itemRequest);
        return ResponseEntity.created(URI.create("/items/" + itemResponse.getId()))
                .body(itemResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponse> updateItem(@RequestBody @Valid ItemRequest itemRequest, @PathVariable Long id) {
        ItemResponse itemResponse = itemService.update(id, itemRequest);
        return ResponseEntity.ok(itemResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        itemService.delete(id);
        return ResponseEntity.ok().build();
    }
}