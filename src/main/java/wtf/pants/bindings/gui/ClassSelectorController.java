package wtf.pants.bindings.gui;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import wtf.pants.bindings.gui.entries.ClassTreeEntry;
import wtf.pants.bindings.gui.entries.PathTreeEntry;
import wtf.pants.bindings.gui.entries.TreeEntry;
import wtf.pants.bindings.mappings.ClassMap;
import wtf.pants.bindings.util.Functional;

import java.util.Arrays;

public class ClassSelectorController {

    protected final TreeView<TreeEntry> importTreeView = new TreeView<>(new TreeItem<>(new PathTreeEntry("Imported")));
    protected final TreeView<TreeEntry> exportTreeView = new TreeView<>(new TreeItem<>(new PathTreeEntry("Exported")));

    public void onImportTreeItemClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            final var clickedItem = importTreeView.getSelectionModel().getSelectedItem();

            if (clickedItem.getValue() instanceof ClassTreeEntry) {
                final ClassMap classMap = ((ClassTreeEntry)clickedItem.getValue()).getClassMap();
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
}
