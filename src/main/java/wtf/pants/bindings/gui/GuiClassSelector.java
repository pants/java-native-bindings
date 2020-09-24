package wtf.pants.bindings.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
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
        controller.generateBindings.setOnAction(controller::onGenerateBindingsClicked);
    }

    @Override
    public void start(Stage stage) throws Exception {
        final VBox mainLayout = new VBox();

        mainLayout.getChildren().add(controller.menuBar);

        //Add class selector tree views
        final HBox classSelectorHBox = new HBox();

        for (ClassMap classMap : loadMappings()) {
            controller.addToTree(controller.importTreeView, classMap);
        }

        classSelectorHBox.getChildren().add(controller.importTreeView);
        classSelectorHBox.getChildren().add(controller.exportTreeView);

        HBox.setHgrow(controller.importTreeView, Priority.ALWAYS);
        HBox.setHgrow(controller.exportTreeView, Priority.ALWAYS);

        mainLayout.getChildren().add(classSelectorHBox);
        VBox.setVgrow(classSelectorHBox, Priority.ALWAYS);

        setupEvents();

        Scene scene = new Scene(mainLayout, 1280, 720);
        stage.setScene(scene);
        stage.show();
    }
}
