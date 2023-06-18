package pl.mholovchak.sales;

import pl.mholovchak.sales.cart.Cart;
import pl.mholovchak.sales.cart.CartStorage;
import pl.mholovchak.sales.offering.EveryNItemLineDiscountPolicy;
import pl.mholovchak.sales.offering.Offer;
import pl.mholovchak.sales.offering.OfferCalculator;
import pl.mholovchak.sales.offering.TotalDiscountPolicy;
import pl.mholovchak.sales.product.NoSuchProductException;
import pl.mholovchak.sales.product.ProductDetails;
import pl.mholovchak.sales.product.ProductDetailsProvider;

import java.math.BigDecimal;
import java.util.Optional;

public class Sales {
    private CartStorage cartStorage;
    private ProductDetailsProvider productDetailsProvider;
    private final OfferCalculator offerCalculator;

    public Sales(CartStorage cartStorage, ProductDetailsProvider productDetailsProvider, OfferCalculator offerCalculator) {
        this.cartStorage = cartStorage;
        this.productDetailsProvider = productDetailsProvider;
        this.offerCalculator = offerCalculator;
    }

    public void addToCart(String customerId, String productId) {
        Cart customerCart = loadForCustomer(customerId)
                .orElse(Cart.empty());

        ProductDetails product = getProductDetails(productId)
                .orElseThrow(() -> new NoSuchProductException());

        customerCart.add(product.getId());

        cartStorage.save(customerId, customerCart);
    }

    private Optional<ProductDetails> getProductDetails(String productId) {
        return productDetailsProvider.loadForProduct(productId);
    }

    private Optional<Cart> loadForCustomer(String customerId) {
        return cartStorage.load(customerId);
    }

    public Offer getCurrentOffer(String customerId) {
        Cart customerCart = loadForCustomer(customerId)
                .orElse(Cart.empty());

        Offer offer = this.offerCalculator.calculateOffer(
                customerCart.getCartItems(),
                new TotalDiscountPolicy(BigDecimal.valueOf(500), BigDecimal.valueOf(50)),
                new EveryNItemLineDiscountPolicy(5)
        );

        return offer;
    }

    public PaymentData acceptOffer(String customerId, AcceptOffer request) {
//
//        Offer offer = this.getCurrentOffer(customerId);
//
//        Reservation reservation = Reservation.of(offer);
//
//        String paymentUr1 = paymentGateway.register();
//
//        reservationStorage.save(reservation);
//
//        return new PaymentData(paymentId, paymentUr1);

        return null;

    }
}