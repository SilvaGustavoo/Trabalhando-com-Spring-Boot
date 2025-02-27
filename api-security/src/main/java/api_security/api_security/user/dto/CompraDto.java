package api_security.api_security.user.dto;

import java.util.List;

import api_security.api_security.model.Produto;
import api_security.api_security.user.Users;

public record CompraDto(Double valor, List<Produto> produtos, Users user) {
    
}
