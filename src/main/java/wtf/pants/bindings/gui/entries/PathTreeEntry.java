package wtf.pants.bindings.gui.entries;

public class PathTreeEntry implements TreeEntry {

    private final String path;

    public PathTreeEntry(String path){
        this.path = path;
    }

    @Override
    public String toString() {
        return path;
    }
}
