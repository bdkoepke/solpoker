package pw.swordfish.formatter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * This is a a source file belonging to the Solium project.
 * Copyright (c) 2013 Brandon Koepke <bdkoepke@gmail.com>
 * <p/>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
public class CurrencyFormatter implements Formatter<BigDecimal> {
    private final NumberFormat numberFormat;

    /**
     * Creates a new CurrencyFormatter.
     *
     * @param numberFormat the number format to use for the BigDecimal.
     */
    public CurrencyFormatter(NumberFormat numberFormat) {
        // if a number format is injected then just
        // honour it.
        this.numberFormat = numberFormat;
    }

    /**
     * Creates a new CurrencyFormatter.
     *
     * @param locale the locale to use for the number format.
     */
    public CurrencyFormatter(Locale locale) {
        this(NumberFormat.getNumberInstance(locale));

        // We want our currency as $0.00, $0.05, $0.10...
        this.numberFormat.setMinimumFractionDigits(2);
        this.numberFormat.setMaximumFractionDigits(2);
    }

    /**
     * Creates a new CurrencyFormatter with the default
     * Canada locale.
     */
    public CurrencyFormatter() {
        this(Locale.CANADA);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal deserialize(String input) {
        if (input.charAt(0) == '$')
            input = input.substring(1, input.length());
        return new BigDecimal(input);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String serialize(BigDecimal item) {
        return numberFormat.format(item);
    }
}
