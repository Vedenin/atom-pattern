package com.github.vedenin.atoms.downloader;

import com.github.vedenin.atom.annotations.Atom;
import com.github.vedenin.atom.annotations.BoilerPlate;
import com.github.vedenin.atom.annotations.Molecule;
import com.github.vedenin.atoms.collections.MultimapAtom;
import com.github.vedenin.atoms.collections.SetAtom;
import com.github.vedenin.atoms.downloader.exceptions.DownloaderAtomException;
import com.github.vedenin.atoms.htmlparser.DocumentAtom;
import com.github.vedenin.atoms.htmlparser.HTMLParserAtom;
import com.github.vedenin.atoms.io.FileAndIOUtilsAtom;
import com.github.vedenin.atoms.io.FileAtom;
import com.github.vedenin.atoms.io.ResourceAtom;
import org.apache.commons.io.IOUtils;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URL;
import java.nio.charset.Charset;


/**
 * Atom to download different resources
 * <p>
 * Created by Slava Vedenin on 6/6/2016.
 */
@SuppressWarnings("WeakerAccess")
@Atom
@Molecule({ResourceAtom.class, TrustManagerAtom.class, DocumentAtom.class})
@Singleton
public class DownloaderAtom {
    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36";
    private static final String FILE_URL = "file:";
    private static final int TIMEOUT = 30000;
    private static final int MAX_FILE_NAME_SIZE = 130;
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private boolean isSSLEnabled = false;
    private final TrustManagerAtom trustManagerAtom;
    private final ResourceAtom resourceAtom;
    private final FileAndIOUtilsAtom fileAndIOUtilsAtom;

    @Inject
    public DownloaderAtom(TrustManagerAtom trustManagerAtom,
                          ResourceAtom resourceAtom,
                          FileAndIOUtilsAtom fileAndIOUtilsAtom) {
        this.trustManagerAtom = trustManagerAtom;
        this.resourceAtom = resourceAtom;
        this.fileAndIOUtilsAtom = fileAndIOUtilsAtom;
    }

    private void initHTTPSDownload() {
        if (!isSSLEnabled) {
            trustManagerAtom.activate();
            isSSLEnabled = true;
        }
    }

    public String getText(String url) {
        try {
            return IOUtils.toString(new URL(url), UTF_8);
        } catch (Exception e) {
            throw new DownloaderAtomException("getText: Can't download url = " + url, e);
        }
    }

    public DocumentAtom getHTMLPage(String url) {
        try {
            Thread.sleep(200);
            initHTTPSDownload();
            if (url.startsWith(FILE_URL)) {
                FileAtom file = FileAtom.create(url.replace(FILE_URL, ""));
                String text = fileAndIOUtilsAtom.readFileToString(file);
                return HTMLParserAtom.parseString(text);
            } else {
                return HTMLParserAtom.parseUrl(url, USER_AGENT, TIMEOUT);
            }
        } catch (Exception e) {
            throw new DownloaderAtomException("getHTMLPage: Can't download url = " + url, e);
        }
    }

    public DocumentAtom getHTMLPageWithCache(String url, String cachePath) {
        String cacheName = getCacheName(url);
        if(cacheName.length() > MAX_FILE_NAME_SIZE) {
            cacheName = cacheName.substring(0, MAX_FILE_NAME_SIZE) + ".html";
        }
        String fullName = cachePath + cacheName;
        if(resourceAtom.isResourceExist(fullName)) {
            String text = resourceAtom.getResourceAsString(cachePath + cacheName);
            return HTMLParserAtom.parseString(text);
        } else {
            DocumentAtom documentAtom = getHTMLPage(url);
            resourceAtom.saveAsResource(cachePath, cacheName, documentAtom.getHtml());
            return documentAtom;
        }
    }

    public SetAtom<DocumentAtom> getHTMLPagesWithCache(SetAtom<String> urls, String cachePath) {
        SetAtom<DocumentAtom> result = SetAtom.create();
        for(String url: urls) {
            result.add(getHTMLPageWithCache(url, cachePath));
        }
        return result;
    }

    public MultimapAtom<String, DocumentAtom> getHTMLPagesWithCache(MultimapAtom<String, String> groupAndUrls, String cacheDir) {
        MultimapAtom<String, DocumentAtom> result = MultimapAtom.create();
        for (String group: groupAndUrls.keySet()) {
            for(String url: groupAndUrls.get(group).getSet()) {
                result.put(group, getHTMLPageWithCache(url, cacheDir));
            }
        }
        return result;
    }

    private String getCacheName(String url) {
        return url.replaceAll("https://", "").replaceAll("http://","").
                    replace('.','_').replace('\\','_').replaceAll("/", "_").replaceAll("%","").
                    replaceAll("__","_") + ".html";
    }


    // -------------- Just boilerplate code for Atom -----------------
    private static DownloaderAtom download;

    @BoilerPlate
    public static DownloaderAtom create() {
        return create(TrustManagerAtom.create(), ResourceAtom.create(), FileAndIOUtilsAtom.create());
    }

    @BoilerPlate
    public static DownloaderAtom create(TrustManagerAtom trustManagerAtom,
                                        ResourceAtom resourceAtom,
                                        FileAndIOUtilsAtom fileAndIOUtilsAtom) {
        if (download == null) {
            download = new DownloaderAtom(trustManagerAtom, resourceAtom, fileAndIOUtilsAtom);
        }
        return download;
    }
}
