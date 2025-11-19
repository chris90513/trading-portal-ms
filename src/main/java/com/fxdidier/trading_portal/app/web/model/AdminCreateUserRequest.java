package com.fxdidier.trading_portal.app.web.model;

import com.fxdidier.trading_portal.util.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminCreateUserRequest extends RegisterRequest{
    private Role role;
    private boolean enabled = true;
}
