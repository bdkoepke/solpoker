package pw.swordfish.main;

import pw.swordfish.parser.Option;
import pw.swordfish.parser.OptionBuilder;
import pw.swordfish.parser.OptionSet;
import pw.swordfish.parser.UsageBuilder;
import pw.swordfish.poker.ChipRoll;
import pw.swordfish.poker.Dealer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;

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
public class Program {
    /**
     * Constructs the main program.
     */
    public static void main(String... args) {
        // get the program name, args[0] isn't the program name :(
        String progName = new java.io.File(Program.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath())
                .getName();

        String onlyNumbers = "[0-9]+";
        String dollarAmount = "(\\$)?[0-9]+(\\.[0-9]{1,2})?";
        String csv = "(%1$s,)*(%1$s)";
        String quantityDenomination = onlyNumbers + "/" + dollarAmount;
        String quantityColours = String.format(onlyNumbers + "/", "[A-Z][a-z]*");
        String csvChips = String.format(csv, quantityDenomination);
        String csvColours = String.format(csv, quantityColours);
        OptionBuilder builder = new OptionBuilder();
        Option bonusOne = builder
                .withDescription(
                        "Calculates the breakdown if you require " +
                                "that each person receive at least one\n" +
                                "chip of each denomination.")
                .isRequired(false)
                .createExact("B1");
        Option bonusTwo = builder
                .withDescription(
                        "Based on purely the quantity of each type " +
                                "(colour) of chip inputted, calculates the\n" +
                                "optimal denomination to assign to each colour " +
                                "(from normal currency values) and the quantity\n" +
                                "of each chip that players should receive to " +
                                "maximize the number of chips while adding up\n" +
                                "to the buy-in amount.")
                .isRequired(false)
                .createExact("B2");
        Option chipsOption = builder
                .withDescription(
                        "A comma-separated list of chip quantities " +
                                "and denominations in the form qty/$denomination.")
                .isRequired(true)
                .createExact(csvChips, "Chips");
        Option coloursOption = builder
                .withDescription("A comma-separated list of chip quantities " +
                        "and quantity/colours.")
                .isRequired(true)
                .createExact(csvColours, "ChipColours");
        Option peopleOption = builder
                .withDescription("The number of players.")
                .isRequired(true)
                .createExact(onlyNumbers, "Players");
        Option buyInOption = builder
                .withDescription("The buy in, e.g. $10.00.")
                .isRequired(true)
                .createExact(dollarAmount, "BuyIn");

        OptionSet bonusOneOptionSet = new OptionSet(bonusOne, chipsOption, peopleOption, buyInOption);
        OptionSet bonusTwoOptionSet = new OptionSet(bonusTwo, coloursOption, peopleOption, buyInOption);
        OptionSet defaultSet = new OptionSet(chipsOption, peopleOption, buyInOption);

        // build the usage string from the options
        String usage = new UsageBuilder(
                progName,
                " < input.def",
                "Poker ChipRoll Distribution Calculator.")
                .addString("Where input.def is one of:\n")
                .addOptionSet(defaultSet)
                .addString("Or:\n")
                .addOptionSet(bonusOneOptionSet)
                .addString("Or:\n")
                .addOptionSet(bonusTwoOptionSet)
                .getUsage();

        if (args.length > 0) {
            System.err.println(usage);
            System.exit(-1);
        }

        args = readStdin(usage);

        // is the input the first question?
        if (defaultSet.tryParse(args)) {
            // print the chips
            System.out.println(pokerChipDistribution(
                    chipsOption.getValue(),
                    peopleOption.getValue(),
                    buyInOption.getValue()
            ));
            // success :)
            System.exit(0);
        }
        if (bonusOneOptionSet.tryParse(args))
            System.exit(pokerChipDistributionBonusOne());
        if (bonusTwoOptionSet.tryParse(args))
            System.exit(pokerChipDistributionBonusTwo());

        System.err.println(usage);
        System.exit(-1);
    }

    /**
     * Read in stdin or exit if it doesn't exist.
     * @param usage the usage string to print
     * @return
     */
    private static String[] readStdin(String usage) {
        ArrayList<String> stdin = new ArrayList<String>();
        try {
            if (System.in.available() == 0) {
                System.err.println(usage);
                System.exit(-1);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String input;
            while ((input = reader.readLine()) != null)
                stdin.add(input);
        } catch (IOException io) {
            io.printStackTrace();
        }

        return stdin.toArray(new String[stdin.size()]);
    }

    /**
     * Prints the selected chips.
     *
     * @param selectedChips the selected chips to print.
     */
    private static String hashMapToString(HashMap<BigDecimal, Integer> selectedChips) {
        SortedSet<Map.Entry<BigDecimal, Integer>> set = new TreeSet<Map.Entry<BigDecimal, Integer>>(
                new Comparator<Map.Entry<BigDecimal, Integer>>() {
                    @Override
                    public int compare(Map.Entry<BigDecimal, Integer> first, Map.Entry<BigDecimal, Integer> second) {
                        return second.getKey().compareTo(first.getKey());
                    }
                });
        set.addAll(selectedChips.entrySet());

        StringBuilder result = new StringBuilder();

        boolean first = true;
        for (Map.Entry<BigDecimal, Integer> entry : set) {
            if (first)
                first = false;
            else
                result.append("\n");
            result.append("$")
                    .append(entry.getKey())
                    .append(" - ")
                    .append(entry.getValue());
        }

        return result.toString();
    }

    /**
     * Poker chip distribution bonus round 1.
     *
     * @return 0 if successful, another otherwise.
     */
    private static int pokerChipDistributionBonusOne() {
        return -1;
    }

    /**
     * Poker chip distribution bonus round 2.
     *
     * @return 0 if successful, another otherwise.
     */
    private static int pokerChipDistributionBonusTwo() {
        return -1;
    }

    /**
     * Runs the default poker chip distribution question.
     *
     * @return 0 if successful, another otherwise.
     */
    public static String pokerChipDistribution(ChipRoll[] chips, int people, BigDecimal buyIn) {
        Dealer dealer = new Dealer();
        return hashMapToString(dealer.maximumEqualChipDistributionForBuyIn(chips, people, buyIn));
    }

    // TODO: duplicate...
    public static String pokerChipDistribution(String chips, String people, String buyIn) {
        Parser p = new Parser();
        return pokerChipDistribution(
                p.parseChipRolls(chips),
                p.parsePeople(people),
                p.parseBuyIn(buyIn)
        );
    }
}
