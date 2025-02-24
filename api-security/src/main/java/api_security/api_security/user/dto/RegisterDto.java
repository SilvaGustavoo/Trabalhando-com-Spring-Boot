package api_security.api_security.user.dto;

import java.util.Set;

import api_security.api_security.user.UserRole;

public record RegisterDto(String login, String password, Set<UserRole> role) {
    
}
