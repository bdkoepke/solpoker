package pw.swordfish.poker;

import pw.swordfish.contracts.Contract;

import java.math.BigDecimal;
import java.util.Comparator;

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
public final class ChipRoll {
    private static Comparator<ChipRoll> compareByDenomination =
            new Comparator<ChipRoll>() {
                @Override
                public int compare(ChipRoll chipRoll, ChipRoll chipRoll2) {
                    return chipRoll.getDenomination().compareTo(chipRoll2.getDenomination());
                }
            };
    private final BigDecimal denomination;
    private final int quantity;
    private BigDecimal total;

    /**
     * Creates a new roll of chips with a quantity and denomination.
     *
     * @param quantity     the number of chips of the specified denomination
     *                     that are available.
     * @param denomination the denomination of the chip.
     */
    public ChipRoll(int quantity, BigDecimal denomination) {
        Contract.requires(denomination.compareTo(BigDecimal.ZERO) >= 0.01, "Denomination of chips must be >= \\$0.01, Actual: \\$%s", denomination);
        BigDecimal cents = denomination.multiply(BigDecimal.valueOf(100));
        Contract.requires(
                cents.compareTo(BigDecimal.valueOf(cents.intValue())) == 0,
                "Denomination cannot include fraction less than 0.01");

        Contract.requires(quantity >= 0, "Quantity of chips must be >= 0, Actual: %s", quantity);
        this.denomination = denomination;
        this.quantity = quantity;
        this.total = denomination.multiply(BigDecimal.valueOf(quantity));
    }

    /**
     * Compare the chip by only denomination. In other words, a ChipRoll
     * containing 10 $5.00 chips is the same as a ChipRoll containing
     * 9 $5.00 chips.
     *
     * @return a comparator that compares chips by denomination only.
     */
    public static Comparator<ChipRoll> compareByDenomination() {
        return compareByDenomination;
    }

    /**
     * Gets the quantity of chips.
     *
     * @return the quantity of chips.
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Gets the denomination of the chip, e.g: $2.00.
     *
     * @return the denomination of the chip.
     */
    public BigDecimal getDenomination() {
        return this.denomination;
    }

    /**
     * Gets the product of the quantity and denomination.
     *
     * @return the product of the quantity and denomination.
     */
    public BigDecimal getTotal() {
        return this.total;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hashCode = 23;
        hashCode = hashCode * 37 + this.getDenomination().hashCode();
        hashCode = hashCode * 37 + this.getQuantity();
        return hashCode;
    }
}
