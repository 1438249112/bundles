//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b01 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.08.22 at 11:20:46 AM CEST 
//

package v2_0;

import javax.xml.bind.annotation.*;

/**
 * <p>
 * Java class for persistence-unit-caching-type.
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="persistence-unit-caching-type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}token">
 *     &lt;enumeration value="ALL"/>
 *     &lt;enumeration value="NONE"/>
 *     &lt;enumeration value="ENABLE_SELECTIVE"/>
 *     &lt;enumeration value="DISABLE_SELECTIVE"/>
 *     &lt;enumeration value="UNSPECIFIED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 */
@XmlType(name = "persistence-unit-caching-type")
@XmlEnum
public enum PersistenceUnitCachingType {

	ALL,
	NONE,
	ENABLE_SELECTIVE,
	DISABLE_SELECTIVE,
	UNSPECIFIED;

	public String value() {
		return name();
	}

	public static PersistenceUnitCachingType fromValue(String v) {
		return valueOf(v);
	}

}
