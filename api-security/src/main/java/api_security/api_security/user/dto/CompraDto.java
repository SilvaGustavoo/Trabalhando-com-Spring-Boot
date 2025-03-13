package api_security.api_security.user.dto;

import api_security.api_security.model.Carrinho;
import api_security.api_security.model.Produto;

public record CompraDto(Carrinho carrinho, Produto produto) {
    
}
