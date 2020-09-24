package wtf.pants.bindings.gui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import wtf.pants.bindings.generators.v1.ClassIndex;
import wtf.pants.bindings.gui.entries.ClassTreeEntry;
import wtf.pants.bindings.gui.entries.PathTreeEntry;
import wtf.pants.bindings.gui.entries.TreeEntry;
import wtf.pants.bindings.mappings.ClassMap;
import wtf.pants.bindings.util.Functional;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassSelectorController {

    protected final MenuItem saveExportConfig;
    protected final MenuItem generateBindings;

    protected final MenuBar menuBar = new MenuBar(
            new Menu(
                    "File", null,
                    saveExportConfig = new MenuItem("Save Configuration"),
                    generateBindings = new MenuItem("Generate Bindings")
            )
    );

    protected final TreeView<TreeEntry> importTreeView = new TreeView<>(new TreeItem<>(new PathTreeEntry("Imported")));
    protected final TreeView<TreeEntry> exportTreeView = new TreeView<>(new TreeItem<>(new PathTreeEntry("Exported")));

    public void onImportTreeItemClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            final var clickedItem = importTreeView.getSelectionModel().getSelectedItem();

            if (clickedItem.getValue() != null && clickedItem.getValue() instanceof ClassTreeEntry) {
                final ClassMap classMap = ((ClassTreeEntry) clickedItem.getValue()).getClassMap();
                TreeItem<TreeEntry> newItem = addToTree(exportTreeView, classMap);
            }
        }
    }

    protected TreeItem<TreeEntry> addToTree(final TreeView<TreeEntry> treeView, final ClassMap classMap) {
        final String[] splitPath = classMap.getCleanName().split("/");
        final String[] path = Arrays.copyOfRange(splitPath, 0, splitPath.length - 1);

        final TreeItem<TreeEntry> root = treeView.getRoot();

        final TreeItem<TreeEntry> topLevel = Functional.foldLeft(path, root, (prev, next) -> {
            var stream = prev.getChildren().stream();
            var filter = stream.filter(t -> t.getValue().toString().equals(next));
            var first = filter.findFirst();

            return first.orElseGet(() -> {
                final TreeItem<TreeEntry> item = new TreeItem<>(new PathTreeEntry(next));
                prev.getChildren().add(item);
                return item;
            });
        });

        final TreeItem<TreeEntry> classItem = new TreeItem<>(new ClassTreeEntry(classMap));
        topLevel.getChildren().add(classItem);

        return classItem;
    }

    private List<ClassMap> walkTreeViewChildren(ObservableList<TreeItem<TreeEntry>> children) {
        return children.stream().flatMap(child -> {
            if (child.getValue() instanceof ClassTreeEntry) {
                ClassTreeEntry classTreeEntry = (ClassTreeEntry) child.getValue();
                return Stream.of(classTreeEntry.getClassMap());
            } else {
                return walkTreeViewChildren(child.getChildren()).stream();
            }
        }).collect(Collectors.toList());
    }

    public void onGenerateBindingsClicked(ActionEvent actionEvent) {

    }
}
