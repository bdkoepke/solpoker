package pw.swordfish.parser;

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
public class OptionBuilder {
    private String description = null;
    private boolean isRequired = false;

    /**
     * Sets the description of the option for the usage text.
     *
     * @param description the description of the option.
     */
    public OptionBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * Sets a value indicating whether the option is required.
     *
     * @param isRequired true if the option is required, false otherwise.
     */
    public OptionBuilder isRequired(boolean isRequired) {
        this.isRequired = isRequired;
        return this;
    }

    /**
     * Creates a new option with the specified regular expression.
     *
     * @param match the string to match
     * @return the option as constructed.
     */
    public Option create(String match, String argument) {
        Option o = new Option(match, argument);
        o.setRequired(this.isRequired);
        o.setDescription(this.description);
        this.clear();
        return o;
    }

    /**
     * Creates a simple option with the same match string
     * and argument.
     * @param argument the argument to match.
     * @return the option as specified.
     */
    public Option createExact(String argument) {
        return createExact(argument, argument);
    }

    /**
     * Create an exact pattern matcher that will only match the
     * specified regex and nothing else. (I.e. if the regex was
     * [0-9]+ then the string 88878888a would not match.)
     *
     * @param match the string to match
     * @return the option as constructed.
     */
    public Option createExact(String match, String argument) {
        return this.create("^(" + match + ")$", argument);
    }

    /**
     * Clears the option values.
     */
    private void clear() {
        this.description = null;
        this.isRequired = false;
    }
}
