package api_security.api_security.user.dto;

import java.util.HashSet;
import java.util.Set;

import api_security.api_security.model.CarrinhoItem;
import api_security.api_security.model.Produto;

public record ItemDto(Produto produto, Double quantidade) {
    
    public static ItemDto parseItemDto(CarrinhoItem item) {
        return new ItemDto(item.getProduto(), item.getQuantidade());
    }

    public static Set<ItemDto> parseItemDto(Set<CarrinhoItem> items) {
        Set<ItemDto> listItem = new HashSet<>();

        for (CarrinhoItem item : items) {
            listItem.add(new ItemDto(item.getProduto(), item.getQuantidade()));
        }

        return listItem;
    }
}
