import http from 'k6/http';
import { check, fail } from 'k6';

export const DEFAULT_HEADERS = {
    'Content-Type': 'application/json',
};

export function request(
    method,
    path,
    { 
        body = {}, 
        headers = {}, 
        query = {}, 
        timeout = '15s' 
    } = {}
) {
    // console.log(`[request call] ${method} ${path}`);

    const url = `${__ENV.URL}${path}`;
    const finalHeaders = { ...DEFAULT_HEADERS, ...headers };

    const hasBody = !['GET', 'HEAD'].includes(method.toUpperCase());
    const payload = hasBody ? JSON.stringify(body) : undefined;

    return http.request(
        method,
        url,
        payload,
        {
            headers: finalHeaders,
            timeout,
            params: query,
            tags: {
                name: path
            }
        },
    );
}

export function runChecks(res, ...checks) {
    const obj = {};
    for (const checkObj of checks) {
        obj[checkObj.name] = checkObj.fn;
    }
    return check(res, obj);
}

export function checkedCall(res, ...checks) {
    if (!runChecks(res, ...checks)) {
        fail('Check failed');
    }

    const body = res.json()

    if (body == null) {
        fail('No response body');
    }

    return {
        res: res, 
        data: body.data,
        message: body.message,
        isSuccess: body.isSuccess
    }
}