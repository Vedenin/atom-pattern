package com.github.vedenin.atoms.htmlparser;

import com.github.vedenin.atom.annotations.Atom;
import com.github.vedenin.atom.annotations.BoilerPlate;
import com.github.vedenin.atom.annotations.Contract;
import com.github.vedenin.atom.annotations.Molecule;
import com.github.vedenin.atoms.collections.ListAtom;
import com.github.vedenin.atoms.htmlparser.exceptions.HTMPParserAtomException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This Atom pattern (pattern that extends a Proxy/Facade pattern that have only minimal methods from original class)
 * This using to light-reference to another third party open-source libraries
 * <p>
 * Created by Slava Vedenin on 12/16/2016.
 */
@Atom(Document.class)
@Molecule({ElementAtom.class, ListAtom.class})
@Contract("Provide information about HTML pages")
@SuppressWarnings({"unused", "WeakerAccess"})
public class DocumentAtom {
    private final Document original;

    @Contract("Should returns elements according this CSS Query")
    public ListAtom<ElementAtom> select(String cssQuery) {
        try {
            ListAtom<ElementAtom> result = ListAtom.create();
            result.addAll(original.select(cssQuery).stream().map(ElementAtom::getAtom).collect(ListAtom.getCollector()));
            return result;
        } catch (Exception exp) {
            throw new HTMPParserAtomException("DocumentAtom.select: ", exp);
        }
    }

    @Contract("Should returns text from elements according this CSS Query")
    public String selectText(String cssQuery) {
        try {
            return original.select(cssQuery).text();
        } catch (Exception exp) {
            throw new HTMPParserAtomException("DocumentAtom.selectText: ", exp);
        }
    }

    @Contract("Should returns text from html without any tags")
    public String getText() {
        try {
            return original.text();
        } catch (Exception exp) {
            throw new HTMPParserAtomException("DocumentAtom.getText: ", exp);
        }
    }

    @Contract("Should returns base url for this html")
    public String getBaseUrl() {
        try {
            return original.baseUri();
        } catch (Exception exp) {
            throw new HTMPParserAtomException("DocumentAtom.getBaseUrl: ", exp);
        }
    }

    @Contract("Should resturn all elements for this original")
    public ListAtom<ElementAtom> getElements() {
        try {
            ListAtom<ElementAtom> result = ListAtom.create();
            result.addAll(original.children().stream().map(ElementAtom::getAtom).collect(ListAtom.getCollector()));
            return result;
        } catch (Exception exp) {
            throw new HTMPParserAtomException("DocumentAtom.getElements: ", exp);
        }
    }

    @Contract("Should return html without changing")
    public String getHtml() {
        try {
            return original.html();
        } catch (Exception exp) {
            throw new HTMPParserAtomException("DocumentAtom.getHtml: ", exp);
        }
    }

    public ListAtom<ElementAtom> getAllElements() {
        try {
            ListAtom<ElementAtom> result = ListAtom.create();
            addAllElementToSet(result, original.children());
            return result;
        } catch (Exception exp) {
            throw new HTMPParserAtomException("DocumentAtom.getAllElements: ", exp);
        }
    }

    private static void addAllElementToSet(ListAtom<ElementAtom> result, Elements elements) {
        for (Element element : elements) {
            result.add(ElementAtom.getAtom(element));
            addAllElementToSet(result, element.children());
        }
    }

    // -------------- Just boilerplate code for Atom -----------------
    @BoilerPlate
    private DocumentAtom(Document original) {
        this.original = original;
    }

    @BoilerPlate
    static DocumentAtom getAtom(Document document) {
        return new DocumentAtom(document);
    }

}
