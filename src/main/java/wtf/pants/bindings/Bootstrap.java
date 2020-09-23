package wtf.pants.bindings;

import javafx.application.Application;
import wtf.pants.bindings.gui.GuiClassSelector;

import java.io.IOException;

public class Bootstrap {
    public static void main(String[] args) throws IOException {
        Application.launch(GuiClassSelector.class);
//        final File mappingsFile = new File("mappings.tiny");
//
//        if (!mappingsFile.exists()) {
//            System.out.println("Mapping file not found");
//            return;
//        }
//
//        MappingLoader mappingLoader = new TinyMappingsLoader();
//        List<ClassMap> classMapList = mappingLoader.loadMappingFiles(mappingsFile);
//
//        final MapProvider mapProvider = new MapProvider(mappingLoader.loadMappingFiles(mappingsFile));
//        System.out.println("Parsed: " + classMapList.size() + " classes");
    }
}
