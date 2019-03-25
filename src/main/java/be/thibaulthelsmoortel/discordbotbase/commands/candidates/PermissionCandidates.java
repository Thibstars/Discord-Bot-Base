/*
 * Copyright (c) 2019 Thibault Helsmoortel.
 *
 * This file is part of Discord Bot Base.
 *
 * Discord Bot Base is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Discord Bot Base is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Discord Bot Base.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package be.thibaulthelsmoortel.discordbotbase.commands.candidates;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.dv8tion.jda.core.Permission;

/**
 * List of all existing {@link Permission}s.
 *
 * @author Thibault Helsmoortel
 */
public class PermissionCandidates extends ArrayList<String> {

    PermissionCandidates() {
        super(Stream.of(Permission.values()).map(Enum::toString).collect(Collectors.toList()));
    }
}
