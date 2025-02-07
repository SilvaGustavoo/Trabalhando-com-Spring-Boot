package api_security.api_security.user.dto;

import api_security.api_security.user.UserRole;

public record RegisterDto(String login, String password, UserRole role) {
    
}
