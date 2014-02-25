package pw.swordfish.contracts;

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
public class Contract {
    /**
     * Validates a required contract and throws an illegal argument exception if the predicate is
     * false.
     *
     * @param predicate the predicate to use in our evaluation.
     * @param s         the message to pass to format before the exception.
     * @param objects   the objects to pass to String.format for the message.
     */
    public static void requires(boolean predicate, String s, Object... objects) {
        if (!predicate) {
            throw new IllegalArgumentException(String.format(s, objects));
        }
    }

    /**
     * Validates a required contract and throws the exception if the predicate is false.
     *
     * @param predicate the predicate to use in our evaluation.
     * @param e         the exception to throw.
     * @param <T>       the type of exception.
     * @throws T thrown when the contract is invalidated.
     */
    public static <T extends Exception> void requires(boolean predicate, T e) throws T {
        if (!predicate) {
            throw e;
        }
    }
}
