/*
 * Copyright 2018 Crown Copyright
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.gchq.gaffer.operation.util.join;

import com.google.common.collect.ImmutableMap;

import uk.gov.gchq.gaffer.operation.util.matcher.MatchKey;
import uk.gov.gchq.gaffer.operation.util.matcher.Matcher;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OuterJoin implements JoinFunction {
    @Override
    public Iterable join(final List left, final List right, final Matcher matcher, final MatchKey matchKey) {
        Set resultSet = new HashSet<>();
        if (matchKey.equals(MatchKey.LEFT)) {
            left.stream().filter(listObj -> matcher.matching(listObj, right).isEmpty()).forEach(listObj -> resultSet.add(ImmutableMap.of(listObj, new ArrayList<>())));
        } else if (matchKey.equals(MatchKey.RIGHT)) {
            right.stream().filter(listObj -> matcher.matching(listObj, left).isEmpty()).forEach(listObj -> resultSet.add(ImmutableMap.of(listObj, new ArrayList<>())));
        }
        return resultSet;
    }
}
