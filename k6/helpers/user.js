export const userDetails = (id, iter) => ({
    email: `test_user${id}_${iter}@example.com`,
    password: `foo_${id}_${iter}`,
});