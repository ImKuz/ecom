import { request } from '../helpers/request.js';

const ENDPOINTS = {
    emailSignUp: "/auth/email/sign-up",
    emailLogin: "/auth/email/login",
    logout: "/auth/logout",
};

export function emailSignUp(sessionId, password) {
    return request("POST", ENDPOINTS.emailSignUp,
        { body: { sessionId, password } }
    );
}

export function emailLogin(email, password) {
    return request("POST", ENDPOINTS.emailLogin,
        { body: { email, password } }
    );
}

export function logout(accessToken) {
    return request("POST", ENDPOINTS.logout,
        { body: { accessToken } }
    );
}