package com.github.vedenin.atoms.htmlparser;

import com.github.vedenin.atom.annotations.Atom;
import com.github.vedenin.atom.annotations.BoilerPlate;
import com.github.vedenin.atom.annotations.Molecule;
import com.github.vedenin.atoms.collections.MultimapAtom;
import com.github.vedenin.atoms.collections.SetAtom;
import org.jsoup.parser.Tag;

import static com.github.vedenin.atoms.collections.MultimapAtom.Entry.entity;

/**
 * This Atom pattern (pattern that extends a Proxy/Facade pattern that have only minimal methods from original class)
 * This using to light-reference to another third party open-source libraries
 *
 * Created by Slava Vedenin on 12/19/2016.
 */
@Atom(Tag.class)
@Molecule({ElementAtom.class})
@SuppressWarnings({"unused", "WeakerAccess"})
public class TagAtom {
    private final Tag tag;

    public static TagAtom valueOf(String tagString) {
        return new TagAtom(Tag.valueOf(tagString));
    }

    private static final MultimapAtom<String, String> KNOWN_TAGS = MultimapAtom.create(
            entity("BLOCK", "html", "head", "body", "frameset", "script", "noscript", "style", "meta", "link", "title", "frame",
                    "noframes", "section", "nav", "aside", "hgroup", "header", "footer", "p", "h1", "h2", "h3", "h4", "h5", "h6",
                    "ul", "ol", "pre", "div", "blockquote", "hr", "address", "figure", "figcaption", "form", "fieldset", "ins",
                    "del", "s", "dl", "dt", "dd", "li", "table", "caption", "thead", "tfoot", "tbody", "colgroup", "col", "tr", "th",
                    "td", "video", "audio", "canvas", "details", "menu", "plaintext", "template", "article", "main",
                    "svg", "math", "center", "cut", "include-fragment"),
            entity("INLINE", "object", "base", "font", "tt", "i", "b", "u", "big", "small", "em", "strong", "dfn", "code", "samp", "kbd",
                    "var", "cite", "abbr", "time", "acronym", "mark", "ruby", "rt", "rp", "a", "img", "br", "wbr", "map", "q",
                    "sub", "sup", "bdo", "iframe", "embed", "span", "input", "select", "textarea", "label", "button", "optgroup",
                    "option", "legend", "datalist", "keygen", "output", "progress", "meter", "area", "param", "source", "track",
                    "summary", "command", "device", "area", "basefont", "bgsound", "menuitem", "param", "source", "track",
                    "data", "bdi", "relative-time", "local-time", "time-until", "time-ago", "local-time"),
            entity("WITHOUT_TEXT", "meta", "link", "base", "frame", "img", "br", "wbr", "embed", "hr", "input", "keygen", "col", "command",
                    "device", "area", "basefont", "bgsound", "menuitem", "param", "source", "track", "path", "lineargradient", "stop"),
            entity("FORMAT_INLINE", "title", "a", "p", "h1", "h2", "h3", "h4", "h5", "h6", "pre", "address", "li", "th", "td", "script", "style",
                    "ins", "del", "s", "relative-time"),
            entity("PRESERVE_WHITESPACE", "pre", "plaintext", "title", "textarea"),
            entity("FORM_LISTED", "button", "fieldset", "input", "keygen", "object", "output", "select", "textarea"),
            entity("FORM_SUBMIT", "input", "keygen", "object", "select", "textarea")
    );

    public Boolean isKnownTag() {
        return KNOWN_TAGS.containsValue(tag.getName());
    }

    public SetAtom<String> getCategory() {
        return KNOWN_TAGS.getKeysByValue(tag.getName());
    }

    // Just boilerplate code for Atom
    @Override
    @BoilerPlate
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TagAtom tagAtom = (TagAtom) o;

        return !(tag != null ? !tag.equals(tagAtom.tag) : tagAtom.tag != null);

    }

    @Override
    @BoilerPlate
    public int hashCode() {
        return tag != null ? tag.hashCode() : 0;
    }

    @BoilerPlate
    private TagAtom(Tag tag) {
        this.tag = tag;
    }

    @BoilerPlate
    static TagAtom getAtom(Tag tag) {
        return new TagAtom(tag);
    }

    @Override
    public String toString() {
        return tag.getName();
    }
}
