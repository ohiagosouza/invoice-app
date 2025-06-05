package com.hiagosouza.api.quoted.services;

import com.hiagosouza.api.quoted.model.AuthRequest;
import com.hiagosouza.api.quoted.model.AuthResponse;

public interface AuthLoginService {
    AuthRequest loginAuthentication(String email, String password);
    AuthResponse getLoginAuthentication();
}
