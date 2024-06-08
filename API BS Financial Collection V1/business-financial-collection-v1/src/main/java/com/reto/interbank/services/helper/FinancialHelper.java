package com.reto.interbank.services.helper;

import com.reto.interbank.services.util.AesEncryptor;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.Optional;

import static com.reto.interbank.services.util.Constants.PATTERN_FORMAT_THOUSAND_SEPARATOR;

@Component
public class FinancialHelper {

    public String formatThousandSeparator(Double balanceAmount) {
        return Optional.ofNullable(balanceAmount)
                .map(number -> new DecimalFormat(PATTERN_FORMAT_THOUSAND_SEPARATOR)
                        .format(balanceAmount))
                .orElse(String.valueOf(balanceAmount));
    }

}