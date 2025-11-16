package com.fxdidier.trading_portal.app.web.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String password;
    private boolean admin;   // si quieres marcar desde aquí si es admin
}
