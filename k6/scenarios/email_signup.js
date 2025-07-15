import { emailSignUp } from '../flows/email_signup';
import { group } from 'k6';

export let options = {
    stages: [
        { duration: '5s', target: 10 }, 
        { duration: '5s', target: 20 },
        { duration: '5s', target: 0 },
    ],
    thresholds: {
        http_req_duration: ['p(95)<300'],
    },
};

export default function() {
    group('Sign up flow', function () {
        emailSignUp(__VU, __ITER);
    })
}