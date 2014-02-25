package pw.swordfish.poker;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import pw.swordfish.main.Program;

/**
 * This is a a source file belonging to the solpoker project.
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
public class DealerTest {
    @Test
    public void simpleMaximumEqualsChipDistributionForBuyIn() {
        String expected =
                "$2.00 - 0\n" +
                "$1.00 - 1\n" +
                "$0.50 - 10\n" +
                "$0.25 - 10\n" +
                "$0.10 - 10\n" +
                "$0.05 - 10";
        String actual = Program.pokerChipDistribution(
                "100/$0.05,100/$0.10,100/$0.25,100/$0.50,50/$1.00,50/$2.00",
                "10",
                "$10.00"
        );
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deepTreeMaximumEqualsChipDistributionForBuyIn() {
        String expected =
                "$106 - 1\n" +
                "$100 - 0\n" +
                "$99 - 0\n" +
                "$4 - 0";

        String actual = Program.pokerChipDistribution(
                "20/$4,10/$99,10/$100,10/$106",
                "10",
                "106.00"
        );
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void complexMaximumEqualsChipDistributionForBuyIn() {
        String expected =
                "$2.00 - 5\n" +
                "$1.00 - 4\n" +
                "$0.41 - 5\n" +
                "$0.13 - 4\n" +
                "$0.11 - 4\n" +
                "$0.07 - 2";
        String actual = Program.pokerChipDistribution(
                "30/$0.07,70/$0.11,50/$0.13,50/$0.41,50/$1.00,50/$2.00",
                "10",
                "17.15"
        );
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void simpleOutOfOrderMaximumEqualsChipDistributionForBuyIn() {
        String expected =
                "$2.00 - 0\n" +
                "$1.00 - 1\n" +
                "$0.50 - 10\n" +
                "$0.25 - 10\n" +
                "$0.10 - 10\n" +
                "$0.05 - 10";
        String actual = Program.pokerChipDistribution(
                "50/$2.00,50/$1.00,100/$0.50,100/$0.25,100/$0.10,100/$0.05",
                "10",
                "$10.00"
        );
        Assert.assertEquals(expected, actual);
    }
}
