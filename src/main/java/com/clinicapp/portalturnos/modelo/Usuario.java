package com.clinicapp.portalturnos.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mindrot.jbcrypt.BCrypt;

@Setter
@Getter
@NoArgsConstructor
@Entity(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //No permite null, ni textos vacíos "", ni solo espacios " ", se usa en strings
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;
    private String apellido;
    @Email(message = "Formato de email inválido")
    @Column(unique = true, nullable = false)
    private String email;
    //columna obligatoria en base de datos
    @Column(nullable = false)
    private String password;
    private Long numero;

    public Usuario(String username, String email, String passwordPlana, Long numero) {
        this.nombre = username;
        this.email = email;
        this.encriptarPassword(passwordPlana);
        this.numero = numero;
    }


    public void encriptarPassword(String passwordPlana) {
        if (passwordPlana != null && !passwordPlana.isEmpty()) {
            this.password = BCrypt.hashpw(passwordPlana, BCrypt.gensalt());
        }
    }

    public boolean validarPassword(String passwordIngresada) {
        // Compara el texto plano con el hash guardado en la BD
        return BCrypt.checkpw(passwordIngresada, this.password);
    }
}
