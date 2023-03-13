package com.springdemo2fa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class UserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotEmpty
    private String username;

    @NotNull
    @NotEmpty
    private String password;

    @Column(name = "mfa_secret")
    private String mfaSecret;

    @Column(name = "mfa_description")
    private String mfaDescription;

    public String getMfaDescription() {
        return this.mfaDescription == null ? "conarf-2fa-demo" : this.mfaDescription;
    }

}
