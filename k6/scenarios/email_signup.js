import {initiateVerification, resendCode, confirmVerification,} from '../flows/verificaiton.js';
import { emailSignUp } from '../flows/auth.js';
import { checkedCall } from '../helpers/request.js';
import { userDetails } from '../helpers/user.js';
import { isOK } from '../helpers/checks.js';
import { group } from 'k6';

export default function() {
    group('Sign up flow', function () {
        const user = userDetails(__VU, __ITER);

        const initVerifRes = checkedCall(initiateVerification("LOCAL", user.email), isOK);
        const id = initVerifRes.data.sessionId

        checkedCall(confirmVerification(id, "000000"), isOK);

        checkedCall(emailSignUp(id, user.password), isOK);
    })
}