package ca.uhn.fhir.jpa.empi.dao;

/*-
 * #%L
 * HAPI FHIR JPA Server - Enterprise Master Patient Index
 * %%
 * Copyright (C) 2014 - 2020 University Health Network
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import ca.uhn.fhir.empi.api.IEmpiSettings;
import ca.uhn.fhir.jpa.entity.EmpiLink;
import org.springframework.beans.factory.annotation.Autowired;

public class EmpiLinkFactory {
	private final IEmpiSettings myEmpiSettings;

	@Autowired
	public EmpiLinkFactory(IEmpiSettings theEmpiSettings) {
		myEmpiSettings = theEmpiSettings;
	}

	/**
	 * Create a new EmpiLink, populating it with the version of the ruleset used to create it.
	 *
	 * @return the new {@link EmpiLink}
	 */
	public EmpiLink newEmpiLink() {
		return new EmpiLink(myEmpiSettings.getRuleVersion());
	}
}
