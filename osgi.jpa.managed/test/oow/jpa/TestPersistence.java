/*
 * Copyright (c) OSGi Alliance (2013). All Rights Reserved.
 * 
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
 */

package oow.jpa;

import java.io.File;
import javax.xml.bind.JAXB;
import junit.framework.TestCase;
import v2_0.Persistence;
import v2_0.Persistence.PersistenceUnit;

public class TestPersistence extends TestCase {

	static class MyP extends Persistence {
		String	location;

	}

	public void testA() {
		MyP persistence = JAXB.unmarshal(new File("testresources/schema_a.xml"), MyP.class);
		for (PersistenceUnit pu : persistence.getPersistenceUnit()) {
			System.out.println(pu.getJtaDataSource());
			System.out.println(pu.getClazz());
		}
	}
}
