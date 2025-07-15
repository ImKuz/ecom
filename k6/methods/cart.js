import { request } from '../helpers/request.js';
import { HEADERS } from '../helpers/headers.js';

const ENDPOINTS = {
    getItems: "/cart/items",
    addItem: "/cart/add",
    removeItems: "/cart/remove",
    updateCount: "/cart/update-count",
    process: "/cart/process",
};

export function getCartItems(
    token,
    userId,
    limit,
    offset,
) {
    return request("GET", ENDPOINTS.getItems,
        {
            query: {
                limit,
                offset
            },
            headers: { [HEADERS.auth]: token },
        }
    );
}

export function addCartItem(
    token,
    item,
) {
    return request("POST", ENDPOINTS.addItem,
        {
            body: { item },
            headers: { [HEADERS.auth]: token },
        }
    );
}

export function removeCartItems(
    token,
    itemIds,
) {
    return request("POST", ENDPOINTS.removeItems,
        {
            body: { itemIds },
            headers: { [HEADERS.auth]: token },
        }
    );
}

export function updateCartItemCount(
    token,
    productId,
    count,
) {
    return request("POST", ENDPOINTS.removeItems,
        {
            body: { productId, count },
            headers: { [HEADERS.auth]: token },
        }
    );
}

export function processCart(
    token,
    items
) {
    return request("POST", ENDPOINTS.removeItems,
        {
            body: { items },
            headers: { [HEADERS.auth]: token },
        }
    );
}