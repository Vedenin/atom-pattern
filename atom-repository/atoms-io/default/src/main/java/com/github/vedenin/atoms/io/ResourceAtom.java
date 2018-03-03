package com.github.vedenin.atoms.io;

import com.github.vedenin.atom.annotations.AtomException;
import com.github.vedenin.atom.annotations.AtomUtils;
import com.github.vedenin.atom.annotations.BoilerPlate;
import com.github.vedenin.atoms.collections.ListAtom;
import com.github.vedenin.atoms.collections.MapAtom;
import com.github.vedenin.atoms.collections.SetAtom;
import com.github.vedenin.atoms.io.exceptions.ResourceAtomException;
import com.github.vedenin.atoms.strings.StringUtilsAtom;

import javax.inject.Inject;
import java.io.InputStream;

import static java.util.stream.Collectors.toMap;


/**
 * Atom for working with Resource.
 */
@AtomUtils
@AtomException(ResourceAtomException.class)
@SuppressWarnings({"unused", "WeakerAccess"})
public class ResourceAtom {
    private final Class<?> thisClass;
    private final FileAndIOUtilsAtom fileAndIOUtilsAtom;
    private final StringUtilsAtom stringUtilsAtom;

    @Inject
    public ResourceAtom(FileAndIOUtilsAtom fileAndIOUtilsAtom, StringUtilsAtom stringUtilsAtom) {
        this.thisClass = this.getClass();
        this.fileAndIOUtilsAtom = fileAndIOUtilsAtom;
        this.stringUtilsAtom = stringUtilsAtom;
    }

    /**
     * Returns list from config file
     *
     * @param name - name of config file
     * @return list from config file
     */
    public ListAtom<String> getListFromConfig(String name) {
        return stringUtilsAtom.split(getResourceAsString(name).replaceAll("\r", ""), "\n").
                stream().map(String::trim).collect(ListAtom.getCollector());
    }

    /**
     * Returns list from file (every row is separate item in list)
     *
     * @param name -file name
     * @return list from file
     */
    public ListAtom<String> getListFromFile(String name) {
        return fileAndIOUtilsAtom.readLines(FileAtom.create(name));
    }

    /**
     * Return map from config file (template: key1 := value1 \n key2 := value2)
     *
     * @param name - name of config file
     * @return Map{key,value} from config file
     */
    public MapAtom<String, String> getMapFromConfig(String name) {
        return MapAtom.getAtom(
                stringUtilsAtom.split(getResourceAsString(name).replaceAll("\r", ""),
                        "\n").stream().map((s) -> s.split(" := "))
                        .collect(toMap((s) -> s[0], (s) -> s[1])));
    }

    /**
     * Returns String from resource file
     *
     * @param name - path and name of resource file
     * @return String from this resource file
     */
    public String getResourceAsString(String name) {
        try {
            return fileAndIOUtilsAtom.inputStreamToString(getResourceAsStream(name));
        } catch (Exception e) {
            throw new ResourceAtomException("Problem during open resource: " + name, e);
        }
    }

    public boolean isResourceExist(String name) {
        try(InputStream inputStream = thisClass.getResourceAsStream(name)) {
            return inputStream != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Return InputStream from resource file
     *
     * @param name - path and name of resource file
     * @return InputStream from this resource file
     */
    public InputStream getResourceAsStream(String name) {
        try {
            InputStream inputStream = thisClass.getResourceAsStream(name);
            if (inputStream == null) {
                throw new ResourceAtomException("Resource with name = " + name + " isn't found");
            } else {
                return thisClass.getResourceAsStream(name);
            }
        } catch (Exception e) {
            throw new ResourceAtomException("Problem during open resource: " + name, e);
        }
    }

    /**
     * Returns list of files path from all resources of this dir
     *
     * @param dirPath - dir path
     * @return list of files path
     */
    public ListAtom<String> getFiles(String dirPath) {
        FileAtom dir = getDir(dirPath);
        return fileAndIOUtilsAtom.listFiles(dir).stream().map(FileAtom::getPath).collect(ListAtom.getCollector());
    }

    /**
     * Returns list of sorted files from all resources of this dir
     *
     * @param dirPath - dir path
     * @return list of files path
     */
    public SetAtom<String> getSortedFiles(String dirPath) {
        return SetAtom.createTreeSet(getFiles(dirPath));
    }

    /**
     * Returns file from recourse's dir path
     *
     * @param dirPath - dir path
     * @return dir file
     */
    public FileAtom getDir(String dirPath) {
        java.net.URL url = thisClass.getResource(dirPath);
        if (url == null) {
            throw new ResourceAtomException("Dir with path " + dirPath + " isn't found");
        } else {
            return FileAtom.create(thisClass.getResource(dirPath).getPath());
        }
    }

    /**
     * Save text as resource in target directory
     *
     * @param dirPath  - path for dir
     * @param fileName - file name
     * @param text     - text that need to save
     */
    public void saveAsResource(String dirPath, String fileName, String text) {
        FileAtom dir = getDir(dirPath);
        if (!dir.isExists()) {
            dir.createDir();
        }
        FileAtom file = FileAtom.create(dir.getPath() + "/" + fileName);
        if (file.createNewFile()) {
            fileAndIOUtilsAtom.writeStringToFile(file, text);
        }
    }

    /**
     * Save text as resource in target directory
     *
     * @param dirPath  - path for dir
     * @param fileName - file name
     * @param lines    - List of lines that need to save
     */
    public void saveAsResource(String dirPath, String fileName, ListAtom<String> lines) {
        saveAsResource(dirPath, fileName, lines.stream().collect(ListAtom.getJoiningCollectors("\n\r")));
    }

    /**
     * Returns String from file
     *
     * @param name - path and name of file
     * @return String from this file
     */
    public  String getFileAsString(String name) {
        try {
            return fileAndIOUtilsAtom.readFileToString(FileAtom.create(name));
        } catch (Exception e) {
            throw new ResourceAtomException("Problem during open file: " + name, e);
        }
    }

    public String getNameFromResource(String name, String dir) {
        return name.replace(dir, "").replace("\\", "").replace("/", "").split("_")[0];
    }

    public String getNameFromResource(String name) {
        String tmp = name.replace("\\", "/");
        return tmp.substring(tmp.lastIndexOf("/") + 1);
    }

    public String getResourcePath(String name) {
        try {
            return thisClass.getResource(name).getPath();
        } catch (Exception e) {
            throw new ResourceAtomException("Problem during open resource: " + name, e);
        }
    }

    public java.net.URL getResourceURL(String name) {
        try {
            return thisClass.getResource(name);
        } catch (Exception e) {
            throw new ResourceAtomException("Problem during open resource: " + name, e);
        }
    }

    // -------------- Just boilerplate code for Atom -----------------
    @BoilerPlate
    public static ResourceAtom create(FileAndIOUtilsAtom fileAndIOUtilsAtom, StringUtilsAtom stringUtilsAtom) {
        return new ResourceAtom(fileAndIOUtilsAtom, stringUtilsAtom);
    }

    @BoilerPlate
    public static ResourceAtom create() {
        return new ResourceAtom(FileAndIOUtilsAtom.create(), StringUtilsAtom.create());
    }
}
