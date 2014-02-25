package pw.swordfish.parser;

import pw.swordfish.contracts.Contract;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class Option {
    private final String argument;
    Pattern regex;
    private boolean isRequired;
    private String description;
    private LinkedList<String> values;

    /**
     * Creates a new option with the specified regular expression.
     * @param regex the regular expression to match.
     */
    public Option(String regex, String argument) {
        Contract.requires(argument != null, "Description cannot be null");
        Contract.requires(regex != null, "Regex cannot be null");
        this.argument = argument;
        this.regex = Pattern.compile(regex);
    }

    /**
     * Gets a value indicating whether this option is required.
     * @return true if it is, false otherwise.
     */
    public boolean isRequired() {
        return this.isRequired;
    }

    /**
     * Sets a value indicating whether this option is required.
     * @param isRequired true if it is, false otherwise
     */
    public void setRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }

    /**
     * Gets the description of this option.
     * @return the description of this option or null if there isn't one.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description of this option.
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets a value indicating whether this option has a description or not
     * @return true if the option has a description, false otherwise.
     */
    public boolean hasDescription() {
        return this.description != null;
    }

    /**
     * Try to parse the input argument.
     * @param input the input argument.
     * @return true if this option parsed successfully, false otherwise.
     */
    public boolean tryParse(String input) {
        Matcher matcher = this.regex.matcher(input);

        // If the regular expression doesn't match then
        // we can't parse this input.
        if (! matcher.find())
            return false;

        // Setup the values and add all of the regex
        // groups to the value array.
        this.values = new LinkedList<String>();
        int groupCount = matcher.groupCount();

        for (int group = 0; group < groupCount; group++) {
            this.values.add(matcher.group(group));
        }

        return true;
    }

    /**
     * Gets an enumeration of the parsed values.
     * @return the enumeration of parsed values.
     */
    public LinkedList<String> getValues() {
        return this.values;
    }

    /**
     * Gets the first value of the values set.
     * Typically used when we only expect one value.
     * @return the value in the values set or null if there isn't one.
     */
    public String getValue() {
        return this.hasValue() ? this.values.get(0) : null;
    }

    /**
     * Gets a value indicating whether the parse succeeded or not.
     * @return true if this parsed successfully and has a value.
     */
    public boolean hasValue() {
        return this.values != null && this.values.size() > 0;
    }

    /**
     * Gets the argument name.
     * @return the name of the argument.
     */
    public String getArgument() {
        return argument;
    }
}
