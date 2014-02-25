package pw.swordfish.parser;

import java.util.HashSet;
import java.util.LinkedList;

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
public class UsageBuilder {
    private StringBuilder usage;

    /**
     * Creates a new command-line parser.
     * @param progName the program name.
     * @param description the description of the application.
     * @param argsExample an example of the arguments.
     */
    public UsageBuilder(String progName, String argsExample, String description) {
        this.usage = new StringBuilder()
                .append("Usage: java -jar ")
                .append(progName)
                .append(argsExample)
                .append("\n")
                .append(description)
                .append("\n\n");
    }

    /**
     * Creates a new usage builder without example arguments.
     * @param progName the program name.
     * @param description the description of the program.
     */
    public UsageBuilder(String progName, String description) {
        this(progName, "", description);
    }

    /**
     * Adds the set of options to the usage builder
     * @param optionSet the options to add
     * @return the usage builder containing the options.
     */
    public UsageBuilder addOptionSet(OptionSet optionSet) {
         int maxArgumentLength = 0;
        for (Option o : optionSet)
            maxArgumentLength = Math.max(maxArgumentLength, o.getArgument().length());

        String alignLeft = "%-" + maxArgumentLength + "s";

        // TODO: this is a little hacky...
        StringBuilder spaces = new StringBuilder();
        for (int i = 0; i < maxArgumentLength; i++)
            spaces.append(' ');
        String alignRight = spaces
                .append("\t\t\t").toString();

        for (Option o : optionSet) {
            String argument = String.format(alignLeft, o.getArgument());
            usage.append("\t")
                    .append(argument)
                    .append("\t\t");
            if (o.hasDescription()) {
                String description = o.getDescription();
                String[] lines = description.split("\n");
                usage.append(lines[0]);
                for (int i = 1; i < lines.length - 1; i++) {
                    usage.append("\n");
                    String line = lines[i];
                    usage.append(alignRight);
                    usage.append(line);
                }
            }
            usage.append("\n");
        }
        return this;
    }

    /**
     * Adds a custom string to the usage.
     * @param custom the custom string to append.
     * @return the usage builder containing the custom string.
     */
    public UsageBuilder addString(String custom) {
        this.usage.append(custom);
        return this;
    }

    /**
     * Gets the built usage string.
     * @return the usage string.
     */
    public String getUsage() {
        return usage.toString();
    }
}
