package com.sadat.service.general;

import com.sadat.dto.general.*;
import com.sadat.model.User;

public interface AuthenticationService {

    User register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
    LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
    void forgetPassword(EmailRequest request);
    void resetPassword(ResetPasswordRequest request);
    boolean checkEmailIfExists(EmailRequest request);
    boolean checkUsernameIfExists(UsernameRequest request);
}
