export const isStatus = (expected) => ({
    name: `status is ${expected}`,
    fn: (r) => r.status === expected,
});

export const isOK = isStatus(200);

