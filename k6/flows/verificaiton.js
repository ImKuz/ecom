import { request } from '../helpers/request.js';

const ENDPOINTS = {
    initiateVerification: "/auth/verify/start",
    resendCode: "/auth/verify/resend",
    confirmVerification: "/auth/verify/confirm",
};

export function initiateVerification(provider, identifier) {
    return request("POST", ENDPOINTS.initiateVerification, 
        { body: { provider, identifier } }
    );
}

export function resendCode(sessionId) {
    return request("POST", ENDPOINTS.resendCode,
        { body: { sessionId } }
    );
}

export function confirmVerification(sessionId, code) {
    return request("POST", ENDPOINTS.confirmVerification,
        { body: { sessionId, code } }
    );
}
