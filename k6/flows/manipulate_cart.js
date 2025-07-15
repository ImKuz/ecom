import { apiCall } from '../helpers/request.js';
import { fail } from 'k6';
import { addCartItem, updateCartItemCount } from '../methods/cart.js';


export function addCartItem(
    token,
    productId,
    changeCountTo,
) {
    if (token == null) {
        fail("Unauthorized")
    }

    apiCall(
        addCartItem(
            token,
            {
                productId,
                quantity: 1
            }
        )
    );

    if (changeCountTo != null) {
        updateCartItemCount(
            token, 
            productId, 
            changeCountTo
        );
    }
}