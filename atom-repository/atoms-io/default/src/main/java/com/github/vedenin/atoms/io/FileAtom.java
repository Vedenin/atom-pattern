package com.github.vedenin.atoms.io;

import com.github.vedenin.atom.annotations.Atom;
import com.github.vedenin.atom.annotations.AtomException;
import com.github.vedenin.atom.annotations.BoilerPlate;
import com.github.vedenin.atoms.io.exceptions.IOAtomException;

import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * Atom to proxy working with original
 *
 * Created by vvedenin on 4/19/2017.
 */
@Atom(File.class)
@AtomException(IOAtomException.class)
@SuppressWarnings({"unused", "WeakerAccess"})
public class FileAtom {
    private final File original;

    public boolean isExists() {
        return original.exists();
    }

    public void createDir() {
        boolean flag = original.mkdirs();
        if(!flag) {
            throw new IOAtomException("Can't create dir with name " + original.getPath() + " " + original.getName());
        }
    }

    public String getPath() {
        return original.getPath();
    }

    public boolean createNewFile() {
        try {
            return original.createNewFile();
        } catch (IOException exp) {
            throw new IOAtomException(exp);
        }
    }

    public String getAbsolutePath() {
        return original.getAbsolutePath();
    }

    // Just boilerplate code for Atom
    @BoilerPlate
    private FileAtom(File original) {
        this.original = original;
    }
    @BoilerPlate
    public File getOriginal() {
        return original;
    }

    @BoilerPlate
    public static FileAtom getAtom(File file) {
        return new FileAtom(file);
    }

    @BoilerPlate
    public static FileAtom create(String fileName) {
        try {
            return new FileAtom(new File(fileName));
        } catch (Exception exp) {
            throw new IOAtomException(exp);
        }
    }

    @BoilerPlate
    public static FileAtom create(URI uri) {
        try {
            return new FileAtom(new File(uri));
        } catch (Exception exp) {
            throw new IOAtomException(exp);
        }
    }
}
