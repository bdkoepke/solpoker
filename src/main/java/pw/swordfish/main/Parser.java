package pw.swordfish.main;

import pw.swordfish.formatter.ChipFormatter;
import pw.swordfish.formatter.CurrencyFormatter;
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
public class Parser {
    CurrencyFormatter currencyFormatter = new CurrencyFormatter(Locale.CANADA);
    ChipFormatter chipFormatter = new ChipFormatter(currencyFormatter);

    public ChipRoll[] parseChipRolls(String chipRolls) {
        String[] chips = chipRolls.split(",");
        ChipRoll[] result = new ChipRoll[chips.length];
        for (int i = 0; i < chips.length; i++) {
            result[i] = chipFormatter.deserialize(chips[i]);
        }
        return result;
    }

    public int parsePeople(String people) {
        return Integer.parseInt(people);
    }

    public BigDecimal parseBuyIn(String buyIn) {
        return currencyFormatter.deserialize(buyIn);
    }
}
