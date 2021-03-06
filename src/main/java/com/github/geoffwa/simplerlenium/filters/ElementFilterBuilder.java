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
package com.github.geoffwa.simplerlenium.filters;

import com.github.geoffwa.simplerlenium.FilteredDomElement;
import com.github.geoffwa.simplerlenium.text.Text;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import org.openqa.selenium.WebElement;

import java.util.regex.Pattern;

class ElementFilterBuilder implements FilteredDomElement {
  private final LazyDomElement domElement;
  private final String description;
  private final Function<WebElement, String> toValue;
  private final boolean ok;

  ElementFilterBuilder(LazyDomElement domElement, String description, Function<WebElement, String> toValue, boolean ok) {
    this.domElement = domElement;
    this.description = description;
    this.toValue = toValue;
    this.ok = ok;
  }

  // Modifiers

  @Override
  public ElementFilterBuilder not() {
    return new ElementFilterBuilder(domElement, description, toValue, !ok);
  }

  // Matchers

  @Override
  public LazyDomElement beingEmpty() {
    return build(isOrNot("empty"), null, StringPredicates.isEmpty());
  }

  @Override
  public LazyDomElement beingNull() {
    return build(isOrNot("null"), null, StringPredicates.isNull());
  }

  @Override
  public LazyDomElement equalTo(String text) {
    return build(isOrNot("equal to"), text, StringPredicates.equalsTo(text));
  }

  @Override
  public LazyDomElement containing(String text) {
    return build(doesOrNot("contain"), text, StringPredicates.contains(text));
  }

  @Override
  public LazyDomElement containing(Pattern regex) {
    return build(doesOrNot("contain"), regex, StringPredicates.contains(regex));
  }

  @Override
  public LazyDomElement containingWord(String word) {
    return build(hasOrNot("word"), word, StringPredicates.containsWord(word));
  }

  @Override
  public LazyDomElement startingWith(String text) {
    return build(doesOrNot("start with"), text, StringPredicates.startsWith(text));
  }

  @Override
  public LazyDomElement endingWith(String text) {
    return build(doesOrNot("end with"), text, StringPredicates.endsWith(text));
  }

  @Override
  public LazyDomElement matching(Pattern regex) {
    return build(doesOrNot("match"), regex, StringPredicates.matches(regex));
  }

  @Override
  public LazyDomElement matching(Predicate<String> predicate) {
    return build(doesOrNot("match"), predicate, predicate);
  }

  // Internal

  private String doesOrNot(String verb) {
    return Text.doesOrNot(!ok, verb);
  }

  private String isOrNot(String state) {
    return Text.isOrNot(!ok, state);
  }

  private String hasOrNot(String what) {
    return Text.hasOrNot(!ok, what);
  }

  private LazyDomElement build(String word, Object details, final Predicate<String> predicate) {
    String fullDescription = " with " + description + " that " + word;
    if (details != null) {
      fullDescription += " [" + details + "]";
    }

    final Predicate<String> okPredicate = ok ? predicate : Predicates.not(predicate);

    Function<Iterable<WebElement>, Iterable<WebElement>> filter = new Function<Iterable<WebElement>, Iterable<WebElement>>() {
      @Override
      public Iterable<WebElement> apply(final Iterable<WebElement> elements) {
        return Iterables.filter(elements, new Predicate<WebElement>() {
          @Override
          public boolean apply(WebElement webElement) {
            return okPredicate.apply(toValue.apply(webElement));
          }
        });
      }
    };

    return domElement.with(new ElementFilter(fullDescription, filter));
  }
}
