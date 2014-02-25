package pw.swordfish.formatter;

import pw.swordfish.contracts.Contract;
import pw.swordfish.poker.ChipRoll;

import java.math.BigDecimal;
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
public class ChipFormatter implements Formatter<ChipRoll> {
    private CurrencyFormatter currencyFormatter;
    private static final String errorString = "Illegal chip specification: %1$s\n" +
            "Expected %2$s: %$3s, Actual %2$s: %3s";

    public ChipFormatter() {
        this.currencyFormatter = new CurrencyFormatter(Locale.CANADA);
    }

    public ChipFormatter(CurrencyFormatter bdf) {
        this.currencyFormatter = bdf;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChipRoll deserialize(String input) {
        String[] chip = input.split("/");
        int expected = 2;
        Contract.requires(chip.length == expected,
                errorString, input, "length", expected, chip.length);
        int quantity = Integer.parseInt(chip[0]);
        BigDecimal denomination = currencyFormatter.deserialize(chip[1]);
        Contract.requires(denomination.compareTo(BigDecimal.ZERO) > 0,
                errorString, input, "denomination", "> $0.00", currencyFormatter.serialize(denomination));

        return new ChipRoll(quantity, denomination);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String serialize(ChipRoll chipRoll) {
        return String.format("$%s - %s", currencyFormatter.serialize(
                chipRoll.getDenomination()),
                chipRoll.getQuantity());
    }
}
