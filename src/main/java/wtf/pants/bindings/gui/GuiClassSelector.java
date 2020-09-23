package wtf.pants.bindings.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import wtf.pants.bindings.mappings.ClassMap;
import wtf.pants.bindings.mappings.loaders.MappingLoader;
import wtf.pants.bindings.mappings.loaders.TinyMappingsLoader;

import java.io.File;
import java.util.*;

public class GuiClassSelector extends Application {

    private final ClassSelectorController controller = new ClassSelectorController();

    public List<ClassMap> loadMappings() {
        final File mappingsFile = new File("mappings.tiny");

        if (!mappingsFile.exists()) {
            System.out.println("Mapping file not found");
        }

        MappingLoader mappingLoader = new TinyMappingsLoader();
        return mappingLoader.loadMappingFiles(mappingsFile);
    }

    private void setupEvents() {
        controller.importTreeView.setOnMouseClicked(controller::onImportTreeItemClicked);
    }

    @Override
    public void start(Stage stage) throws Exception {
        HBox hbox = new HBox();

        TreeItem<String> treeItem = new TreeItem<>("Classes");

        Map<String, TreeItem<String>> treeItemMap = new HashMap<>();

        for (ClassMap classMap : loadMappings()) {
            controller.addToTree(controller.importTreeView, classMap);
        }

        hbox.getChildren().add(controller.importTreeView);
        hbox.getChildren().add(controller.exportTreeView);

        HBox.setHgrow(controller.importTreeView, Priority.ALWAYS);
        HBox.setHgrow(controller.exportTreeView, Priority.ALWAYS);

        setupEvents();

        Scene scene = new Scene(hbox, 640, 480);
        stage.setScene(scene);
        stage.show();
    }
}
