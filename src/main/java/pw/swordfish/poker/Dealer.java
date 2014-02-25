package pw.swordfish.poker;

import pw.swordfish.contracts.Contract;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
public class Dealer {
    private static final int NO_SOLUTION = -1;
    //private TreeSet<ChipRoll> selectedChips;
    private HashMap<BigDecimal, Integer> selectedChips;
    private ChipRoll[] chipRolls;
    // private HashMap<BigDecimal, Integer>[] opt;
    private int calls;

    /**
     * Gets the number of calls on the last invocation of
     * maximumEqualChipDistributionForBuyIn.
     *
     * @return the number of calls.
     */
    public int getCalls() {
        return this.calls;
    }

    /**
     * Gets the maximum chip distribution for the number of people
     * and buy in specified.
     *
     * @param people the number of people to give chipRolls to.
     * @param buyIn  the exact chip total that each person should receive.
     * @return null if there is no solution, otherwise the maximum number of
     *         chipRolls that total to the buy in can can be allocated to each person.
     */
    public HashMap<BigDecimal, Integer> maximumEqualChipDistributionForBuyIn(ChipRoll[] chips, int people, BigDecimal buyIn) {
        HashMap<BigDecimal, Integer> emptySet = new HashMap<BigDecimal, Integer>();
        Contract.requires(people >= 0, "People must be >= 0");
        Contract.requires(buyIn.compareTo(BigDecimal.ZERO) >= 0, "Buy in must be >= $0.00");
        // implicitly the denomination is >= 0.01 and the quantity is >= 0 since a chip cannot
        // be created with an invalid denomination or quantity so we don't need to check it
        Contract.requires(chips != null, "ChipRolls must not be null");

        if (people == 0)
            return emptySet;
        if (buyIn.compareTo(BigDecimal.ZERO) == 0)
            return emptySet;
        // we know that there are people and the buy in is positive
        // so if we have no chipRolls then this is impossible
        if (chips.length == 0)
            return emptySet;

        // reduce the quantity by people, that way we
        // can just worry about one person instead of multiple ones
        ChipRoll[] reducedChipRolls = new ChipRoll[chips.length];
        for (int i = 0; i < chips.length; i++) {
            ChipRoll chipRoll = chips[i];
            reducedChipRolls[i] = new ChipRoll(chipRoll.getQuantity() / people, chipRoll.getDenomination());
        }
        // normally I wouldn't set any state in this class because it could
        // possibly destroy referential transparency, but this makes our recursive
        // calls easier
        this.chipRolls = reducedChipRolls;

        // now select all chip rolls and sort them by denomination
        this.selectedChips = new HashMap<BigDecimal, Integer>();
        //TreeSet<ChipRoll>(ChipRoll.compareByDenomination());
        for (ChipRoll chip : reducedChipRolls)
            this.selectedChips.put(chip.getDenomination(), 0);

        // enable us to keep track of the number of calls.
        this.calls = 0;

        // TODO: this is really bad, definitely remove this...
        this.chipRolls = linearizeChipRolls(chipRolls);

        this.maximumEqualChipDistributionForBuyIn(0, buyIn);

        if (!verifySolution(selectedChips, buyIn))
            return emptySet;

        return selectedChips;
    }

    /**
     * Verify the solution...
     */
    private boolean verifySolution(HashMap<BigDecimal, Integer> hashMap, BigDecimal buyIn) {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<BigDecimal, Integer> chip : hashMap.entrySet())
            total = total.add(chip.getKey().multiply(BigDecimal.valueOf(chip.getValue())));
        return total.compareTo(buyIn) == 0;
    }

    /**
     * Creates a new chip for each chip quantity.
     * Example: Chip($1,5) would become
     * Chip($1,1)
     * Chip($1,1)
     * Chip($1,1)
     * Chip($1,1)
     * Chip($1,1)
     *
     * @param chipRolls the chip rolls to linearize.
     */
    private ChipRoll[] linearizeChipRolls(ChipRoll[] chipRolls) {
        Arrays.sort(chipRolls, ChipRoll.compareByDenomination());
        ArrayList<ChipRoll> linearChips = new ArrayList<ChipRoll>();
        // TODO: this can be done faster, but I doubt this would be the bottle-neck...
        for (ChipRoll chipRoll : chipRolls)
            for (int i = 0; i < chipRoll.getQuantity(); i++)
                linearChips.add(new ChipRoll(1, chipRoll.getDenomination()));

        return linearChips.toArray(new ChipRoll[linearChips.size()]);
    }

    /**
     * Gets the maximum number of chips that exactly equal the specified buy in
     *
     * @param index     the start index for chips.
     * @param remaining the remaining buy in.
     * @return the maximum number of chips.
     */
    private int maximumEqualChipDistributionForBuyIn(int index, BigDecimal remaining) {
        // base case 0, have we already selected the maximum number
        // of chips?
        if (remaining.compareTo(BigDecimal.ZERO) == 0)
            return 0;

        // if there are no more chips then there is no solution in
        // this chain
        if (index >= chipRolls.length)
            return NO_SOLUTION;

        calls++;
        BigDecimal chip = chipRolls[index].getDenomination();
        // do we have chips remaining that are less than the
        // remaining amount?
        if (remaining.compareTo(chip) < 0)
            return NO_SOLUTION;

        int add = maximumEqualChipDistributionForBuyIn(index + 1, remaining.subtract(chip));
        if (add != NO_SOLUTION) {
            int quantity =
                    selectedChips.containsKey(chip) ?
                            (selectedChips.get(chip) + 1) :
                            1;
            selectedChips.put(chip, quantity);

            return add + 1;
        }

        int skip = maximumEqualChipDistributionForBuyIn(index + 1, remaining);
        if (skip != NO_SOLUTION) {
            return skip;
        }

        return NO_SOLUTION;
    }
}
