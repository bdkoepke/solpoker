package pw.swordfish.parser;

import pw.swordfish.contracts.Contract;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
public class OptionSet implements Iterable<Option> {
    private ArrayList<Option> options = new ArrayList<Option>();

    /**
     * Creates an option set with the specified options.
     * @param options the options to set.
     */
    public OptionSet(Option... options) {
        Contract.requires(options != null, "Options cannot be null");
        for (Option o : options) {
            Contract.requires(o != null, "Option cannot be null");
            this.options.add(o);
        }
    }

    /**
     * Try to parse the input string with this option set
     * @param input the input to parse
     * @return true if the option set parsed successfully, false otherwise.
     */
    public boolean tryParse(String... input) {
        Contract.requires(input != null, "Input string cannot be null");
        if (input.length != options.size())
            return false;

        // for each option we have been given, in order try
        // to parse the input string, if it doesn't parse
        // then exit early
        for (int i = 0; i < input.length; i++)
            if (! options.get(i).tryParse(input[i]))
                return false;
        return true;
    }

    /**
     * The number of options in this set.
     */
    public int length() {
       return this.options.size();
    }

    @Override
    public Iterator<Option> iterator() {
        // TODO: this would enable someone else to modify the options...
        return this.options.iterator();
    }
}
