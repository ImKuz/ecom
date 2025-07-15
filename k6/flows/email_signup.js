import {initiateVerification, resendCode, confirmVerification,} from '../methods/verificaiton.js';
import { emailSignUp } from '../methods/auth.js';
import { apiCall } from '../helpers/request.js';
import { userDetails } from '../helpers/user.js';

export function emailSignUp(vu, iter) {
    const user = userDetails(vu, iter);

    const initVerifRes = apiCall(
        initiateVerification("LOCAL", user.email)
    );

    const id = initVerifRes.data.sessionId;

    apiCall(
        confirmVerification(id, "000000")
    );

    return apiCall(
        emailSignUp(id, user.password)
    );
}