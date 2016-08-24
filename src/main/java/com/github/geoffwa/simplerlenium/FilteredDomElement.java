/**
 * Copyright (C) 2013-2015 all@code-story.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
 /**
 * NOTE: modified from original
 */
package com.github.geoffwa.simplerlenium;

import com.google.common.base.Predicate;

import java.util.regex.Pattern;

public interface FilteredDomElement {
  // Modifiers

  FilteredDomElement not();

  // Matchers

  DomElement beingNull();

  DomElement beingEmpty();

  DomElement equalTo(String text);

  DomElement containing(String text);

  DomElement containing(Pattern regex);

  DomElement containingWord(String word);

  DomElement startingWith(String text);

  DomElement endingWith(String text);

  DomElement matching(Pattern regex);

  DomElement matching(Predicate<String> predicate);
}