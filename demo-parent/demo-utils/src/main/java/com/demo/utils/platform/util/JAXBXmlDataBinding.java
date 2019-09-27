/**
 *
 */
package com.demo.utils.platform.util;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

/**
 * @author hjs
 * 2012
 */
public class JAXBXmlDataBinding {

    private final static String DEFAULT_CHARSET = "UTF-8";

    private final static boolean JAXB_FRAGMENT = false;

    private static ValidationEventHandler validationEventHandler;

    public static String marshal(Object dataObject) throws JAXBException {
        return marshal(dataObject, false, true);
    }

    public static String marshal(Object dataObject, Boolean formatted, Boolean jaxbFragment) throws JAXBException {
        if (dataObject == null) {
            return null;
        }

        StringWriter sw = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(dataObject.getClass());
        Marshaller m = jaxbContext.createMarshaller();
        m.setProperty(Marshaller.JAXB_ENCODING, DEFAULT_CHARSET);
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatted == null ? false : formatted);
        m.setProperty(Marshaller.JAXB_FRAGMENT, jaxbFragment == null ? JAXB_FRAGMENT : jaxbFragment);
        m.marshal(dataObject, sw);
        return sw.toString();
    }

    @SuppressWarnings("unchecked")
    public static <T> T unmarshal(String xml, Class<T> objectType) throws JAXBException {
        if (xml == null) {
            return null;
        }

        StringReader sr = new StringReader(xml);
        JAXBContext jaxbContext = JAXBContext.newInstance(objectType);
        Unmarshaller um = jaxbContext.createUnmarshaller();
        return (T) um.unmarshal(sr);
    }

    public static Object unmarshal(String xml, String objectType) throws Exception {
        return unmarshal(xml, Class.forName(objectType));
    }

    public static <T> T unmarshal(String xml, Class<T> objectType, String schema) throws Exception {
        return unmarshal(xml, objectType, new File(schema));
    }

    @SuppressWarnings("unchecked")
    public static <T> T unmarshal(String xml, Class<T> objectType, File schema) throws Exception {
        StringReader sr = new StringReader(xml);
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schemaObj = sf.newSchema(schema);

        JAXBContext jc = JAXBContext.newInstance(objectType);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        unmarshaller.setSchema(schemaObj);
        unmarshaller.setEventHandler(validationEventHandler);

        return (T) unmarshaller.unmarshal(sr);
    }

    @SuppressWarnings("unchecked")
    public static <T> T unmarshal(String xml, Class<T> objectType, URL schema) throws Exception {
        StringReader sr = new StringReader(xml);

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schemaObj = sf.newSchema(schema);

        JAXBContext jc = JAXBContext.newInstance(objectType);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        unmarshaller.setSchema(schemaObj);
        unmarshaller.setEventHandler(validationEventHandler);

        return (T) unmarshaller.unmarshal(sr);
    }

    /**
     * @return the validationEventHandler
     */
    public static ValidationEventHandler getValidationEventHandler() {
        return validationEventHandler;
    }

    /**
     * @param validationEventHandler the validationEventHandler to set
     */
    public static void setValidationEventHandler(ValidationEventHandler validationEventHandler) {
        JAXBXmlDataBinding.validationEventHandler = validationEventHandler;
    }

}
