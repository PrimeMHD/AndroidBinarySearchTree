package com.mhd.datastructure.View;

import com.mhd.datastructure.BTNode;

public class NodeOnTreeView {
    private BTNode btNode;
    private int layer;
    private int rank;

    private int posx;
    private int posy;

    public NodeOnTreeView(BTNode btNode, int layer, int rank) {
        this.btNode = btNode;
        this.layer = layer;
        this.rank = rank;
    }

    public BTNode getBtNode() {
        return btNode;
    }

    public void setBtNode(BTNode btNode) {
        this.btNode = btNode;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }


    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
    public void shiftNode(){

    }

    public int getPosx() {
        return posx;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public int getPosy() {
        return posy;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }
}
