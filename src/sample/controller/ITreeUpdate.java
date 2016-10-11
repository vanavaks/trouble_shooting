package sample.controller;

import javafx.scene.control.TreeItem;

/**
 * Created by VAN on 28.09.2016.
 */
public interface ITreeUpdate extends ITableController{
    /*

     */
    void updateParrentTreeitem(TreeItem treeItem);
    void updateTreeItem(TreeItem treeItem);
}