package com.github.vedenin.atoms.xml;

import com.github.vedenin.atoms.io.FileAndIOUtilsAtom;
import com.github.vedenin.atoms.xml.exceptions.XMLAtomException;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

/**
 * Returns object from xml
 *
 * Created by slava on 03.03.18.
 */
@SuppressWarnings("WeakerAccess")
public class JAXBXmlReaderAtom {
    private final FileAndIOUtilsAtom fileAndIOUtilsAtom;

    @Inject
    public JAXBXmlReaderAtom(FileAndIOUtilsAtom fileAndIOUtilsAtom) {
        this.fileAndIOUtilsAtom = fileAndIOUtilsAtom;
    }

    @SuppressWarnings("unchecked")
    public <T> T getEntity(String text, Class<T> cls) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(cls);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            InputStream io = fileAndIOUtilsAtom.stringToInputStream(text);
            return (T) jaxbUnmarshaller.unmarshal(io);
        } catch (Exception exp) {
            throw new XMLAtomException("Exception in JAXBXmlReaderAtom.getEntity ", exp);
        }
    }

    public static JAXBXmlReaderAtom create() {
        return new JAXBXmlReaderAtom(FileAndIOUtilsAtom.create());
    }
}
