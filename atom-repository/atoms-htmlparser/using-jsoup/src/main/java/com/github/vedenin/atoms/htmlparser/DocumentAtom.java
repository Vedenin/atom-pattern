package com.github.vedenin.atoms.htmlparser;

import com.github.vedenin.atom.annotations.Atom;
import com.github.vedenin.atom.annotations.BoilerPlate;
import com.github.vedenin.atom.annotations.Contract;
import com.github.vedenin.atom.annotations.Molecule;
import com.github.vedenin.atoms.collections.ListAtom;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This Atom pattern (pattern that extends a Proxy/Facade pattern that have only minimal methods from original class)
 * This using to light-reference to another third party open-source libraries
 *
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
        ListAtom<ElementAtom> result = ListAtom.create();
        result.addAll(original.select(cssQuery).stream().map(ElementAtom::getAtom).collect(ListAtom.getCollector()));
        return result;
    }

    @Contract("Should returns text from elements according this CSS Query")
    public String selectText(String cssQuery) {
        return original.select(cssQuery).text();
    }

    @Contract("Should returns text from html without any tags")
    public String getText() {
        return original.text();
    }

    @Contract("Should returns base url for this html")
    public String getBaseUrl() {
        return original.baseUri();
    }

    @Contract("Should resturn all elements for this original")
    public ListAtom<ElementAtom> getElements() {
        ListAtom<ElementAtom> result = ListAtom.create();
        result.addAll(original.children().stream().map(ElementAtom::getAtom).collect(ListAtom.getCollector()));
        return result;
    }

    @Contract("Should return html without changing")
    public String getHtml() {
        return original.html();
    }

    public ListAtom<ElementAtom> getAllElements() {
        ListAtom<ElementAtom> result = ListAtom.create();
        addAllElementToSet(result, original.children());
        return result;
    }

    private static void addAllElementToSet(ListAtom<ElementAtom> result, Elements elements) {
        for(Element element: elements) {
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
