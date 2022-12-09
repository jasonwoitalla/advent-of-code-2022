package utils;

public class SingleTree {

    public int height;
    public boolean edge;
    // 0: up | 1: left | 2: down | 3: right
    public int[] tallestTrees = new int[4];

    public SingleTree(int height, boolean edge) {
        this.height = height;
        this.edge = edge;
        this.tallestTrees[0] = 0;
        this.tallestTrees[1] = 0;
        this.tallestTrees[2] = 0;
        this.tallestTrees[3] = 0;
    }

    public boolean isVisible(int i, int j) {
        if(edge) {
            //System.out.println(String.format("Checking if this tree is visible at (%d, %d), my height is %d with heights of (%d, %d, %d, %d) -> %b", i, j, height, tallestTrees[0], tallestTrees[1], tallestTrees[2], tallestTrees[3], true));
            return true;
        }
        
        boolean visible = height > tallestTrees[0] || height > tallestTrees[1] || height > tallestTrees[2] || height > tallestTrees[3];
        System.out.println(String.format("Checking if this tree is visible at (%d, %d), my height is %d with heights of (%d, %d, %d, %d) -> %b", i, j, height, tallestTrees[0], tallestTrees[1], tallestTrees[2], tallestTrees[3], visible));
        return visible;
    }

    // @Override
    // public String toString() {
    //     return String.format("SingleTree(height = %d blocked at [%d, %d by %d] [%d, %d by %d] [%d, %d by %d] [%d, %d by %d])", 
    //         height,
    //         blockedPairs[0].x(), blockedPairs[0].y(), blockedPairs[0].height(),
    //         blockedPairs[1].x(), blockedPairs[1].y(), blockedPairs[1].height(),
    //         blockedPairs[2].x(), blockedPairs[2].y(), blockedPairs[2].height(),
    //         blockedPairs[3].x(), blockedPairs[3].y(), blockedPairs[3].height());
    // }
}
