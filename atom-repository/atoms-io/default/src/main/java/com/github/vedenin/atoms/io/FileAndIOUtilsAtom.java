package com.github.vedenin.atoms.io;

import com.github.vedenin.atom.annotations.AtomException;
import com.github.vedenin.atom.annotations.AtomUtils;
import com.github.vedenin.atom.annotations.Molecule;

import com.github.vedenin.atoms.collections.ListAtom;
import com.github.vedenin.atoms.io.exceptions.IOAtomException;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;


/**
 * This Atom pattern (pattern that extends a Proxy/Facade pattern that have only minimal methods from original class)
 * This using to light-reference to another third party open-source libraries
 * <p>
 * Created by Slava Vedenin on 12/16/2016.
 */
@AtomUtils({FileUtils.class, Files.class, IOUtils.class})
@Molecule(ListAtom.class)
@AtomException(IOAtomException.class)
@SuppressWarnings({"unused", "WeakerAccess"})
public class FileAndIOUtilsAtom {
    private static final Charset FILE_CODE_PAGE = Charsets.UTF_8;

    public static ListAtom<FileAtom> listFiles(final FileAtom directory) {
        return FileUtils.listFiles(directory.getOriginal(),
                TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE).stream().map(FileAtom::getAtom).
                collect(ListAtom.getCollector());
    }

    public static void writeStringToFile(final FileAtom file, final String data) {
        try {
            FileUtils.writeStringToFile(file.getOriginal(), data, FILE_CODE_PAGE);
        } catch (IOException exp) {
            throw new IOAtomException(exp);
        }
    }

    public static void writeStringToProcessOutputStream(final Process process, String text) {
        try (OutputStream outputStream = process.getOutputStream()){
            outputStream.write(text.getBytes(FILE_CODE_PAGE));
            outputStream.flush();
        } catch (IOException exp) {
            throw new IOAtomException(exp);
        }
    }

    public static String readFileToString(final FileAtom file) {
        try {
            return FileUtils.readFileToString(file.getOriginal(), FILE_CODE_PAGE);
        } catch (IOException exp) {
            throw new IOAtomException(exp);
        }
    }

    public static ListAtom<String> readLines(final FileAtom file) {
        try {
            return ListAtom.getAtom(Files.readLines(file.getOriginal(), FILE_CODE_PAGE));
        } catch (IOException exp) {
            throw new IOAtomException(exp);
        }
    }

    public static String readIOtoString(InputStream inputStream) {
        try {
            return inputStream != null? IOUtils.toString(inputStream, FILE_CODE_PAGE): null;
        } catch (IOException exp) {
            throw new IOAtomException(exp);
        }
    }

    public static FileAtom getFileFromURL(String name, Object cls) throws URISyntaxException {
        URL url = cls.getClass().getClassLoader().getResource(name);
        return url != null? FileAtom.create(url.toURI()): null;
    }

    public static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("win");
    }
}
