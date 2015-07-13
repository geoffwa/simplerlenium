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
package net.codestory.simplelenium;

import net.codestory.simplelenium.driver.Browser;
import org.junit.After;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrentWebDriverTest {
  @After
  public void tearDown() {
    System.getProperties().remove("browser");
  }

  @Test
  public void default_to_phantomJs() {
    Browser browser = CurrentWebDriver.getTargetBrowser();

    assertThat(browser).isEqualTo(Browser.PHANTOM_JS);
  }

  @Test
  public void chrome() {
    System.setProperty("browser", "chrome");

    Browser browser = CurrentWebDriver.getTargetBrowser();

    assertThat(browser).isEqualTo(Browser.CHROME);
  }
}