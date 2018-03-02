package com.github.vedenin.atoms.htmlparser;

import com.github.vedenin.atom.annotations.AtomException;
import com.github.vedenin.atom.annotations.AtomUtils;
import com.github.vedenin.atom.annotations.Molecule;
import com.github.vedenin.atoms.htmlparser.exceptions.HTMPParserAtomException;
import com.github.vedenin.atoms.io.FileAtom;
import com.google.common.base.Charsets;
import org.jsoup.Jsoup;

import java.io.IOException;

/**
 * This Atom pattern (pattern that extends a Proxy/Facade pattern that have only minimal methods from original class)
 * This using to light-reference to another third party open-source libraries
 *
 * Created by Slava Vedenin on 12/16/2016.
 */
@AtomUtils(HTMLParserAtom.class)
@Molecule({DocumentAtom.class, FileAtom.class})
@AtomException(HTMPParserAtomException.class)
@SuppressWarnings({"unused", "WeakerAccess"})
public class HTMLParserAtom {
    private static String CHARSET_NAME = Charsets.UTF_8.name();

    public static DocumentAtom parseFile(FileAtom file)  {
        try {
            return DocumentAtom.getAtom(Jsoup.parse(file.getOriginal(), CHARSET_NAME));
        } catch (IOException exp) {
            throw new HTMPParserAtomException(exp);
        }
    }

    public static DocumentAtom parseString(String text)  {
        try {
            return DocumentAtom.getAtom(Jsoup.parse(text));
        } catch (Exception exp) {
            throw new HTMPParserAtomException(exp);
        }
    }

    public static DocumentAtom parseUrl(String url, String userAgent, int timeout) throws IOException {
        try {
            return DocumentAtom.getAtom(Jsoup.connect(url).userAgent(userAgent).timeout(timeout).get());
        } catch (IOException exp) {
            throw new HTMPParserAtomException(exp);
        }
    }
}
