package sample.FX;

import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import sample.model.Mothers.dialogableModelDB;

/**
 * Created by VAN on 10.10.2016.
 */
public interface ITreePattern{
    void buildMainTree();
    <M extends dialogableModelDB> void buildSubTree(TreeItem<? extends dialogableModelDB> rootItem, ObservableList<M> list);
    void removeChildrens(TreeItem<dialogableModelDB> rootTI);
    <M extends dialogableModelDB> void updateTreeItem(TreeItem<M> rootTI);
    <M extends dialogableModelDB> void updateParrentTI(TreeItem<M> rootTI);
    void info(TreeItem<? extends dialogableModelDB> selTreeItem);
    TreeItem<dialogableModelDB> getRoot();
}

