package api_security.api_security.user;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Long idRole;
    @Column(name = "name_role", nullable = false)
    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public UserRole() {
    }


    public String getRole() {
        return role;
    }



    @Override
    public String toString() {
        return "UserRole [idRole=" + idRole + ", role=" + role + "]";
    }



    public enum Role {
        ADMIN(1L),
        USER(2L);

        private Long value;

        Role(Long value) {
            this.value = value;
        }

        public Long getRole() {
            return value;
        }

                
    }
}
