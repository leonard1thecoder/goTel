package org.airpenthouse.GoTel.util.payments;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.model.PaymentMethod;
import com.stripe.net.RequestOptions;
import com.stripe.param.ChargeCreateParams;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentMethodCreateParams;
import org.springframework.stereotype.Component;

@Component
public class SponsorsDonateUsingStripe {

    RequestOptions requestOptions;
    public SponsorsDonateUsingStripe() {
        requestOptions = RequestOptions.builder()
                .setApiKey("key")
                .build();
    }

    public Customer setCustomer(String sponsorName
            , String sponsorSurname
            , String sponsorEmailAddress
            , String sponsorCellphoneNumber)
            throws StripeException {
        CustomerCreateParams params =
                CustomerCreateParams.builder()
                        .setName(sponsorName + " " + sponsorSurname)
                        .setEmail(sponsorEmailAddress)
                        .setPhone(sponsorCellphoneNumber)
                        .build();
        return Customer.create(params,requestOptions);
    }

    public PaymentMethod setPaymentMethod(Customer customer
            , String sponsorCardNumber
            , String sponsorCvc
            , Long expMonth
            , Long expYear)
            throws StripeException {

        PaymentMethodCreateParams params =
                PaymentMethodCreateParams.builder()
                        .setType(PaymentMethodCreateParams.Type.CARD)
                        .setCustomer(customer.getId())
                        .setCard(PaymentMethodCreateParams
                                .CardDetails
                                .builder()
                                .setNumber(sponsorCardNumber)
                                .setCvc(sponsorCvc)
                                .setExpMonth(expMonth)
                                .setExpYear(expYear)
                                .build())
                        .setBillingDetails(
                                PaymentMethodCreateParams.BillingDetails.builder().setName("John Doe").build()
                        )
                        .build();

        return PaymentMethod.create(params,requestOptions);
    }

    public boolean sponsorDonate(Customer customer
            , Long amount
            , String currency)
            throws StripeException {
        ChargeCreateParams params =
                ChargeCreateParams.builder()
                        .setCustomer(customer.getId())
                        .setAmount(amount)
                        .setCurrency(currency)
                        .setSource("tok_visa")
                        .build();
        Charge charge = Charge.create(params,requestOptions);
        return charge.getPaid();
    }


}
