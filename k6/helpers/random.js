export function randomInt(n, k) {
    return Math.floor(Math.random() * (k - n + 1)) + n;
}

export function randomBool() {
    return Math.random() < 0.5;
}