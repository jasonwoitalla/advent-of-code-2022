package utils;

import java.io.FilenameFilter;
import java.util.ArrayList;

public class FileNode {
    
    private FileNode parent;
    private ArrayList<FileNode> children;

    private int size = 0;
    private String fileName = "";

    public FileNode(int size, String fileName, FileNode parent, ArrayList<FileNode> children) {
        this.size = size;
        this.fileName = fileName;
        this.parent = parent;
        this.children = children;
    }

    public FileNode(int size, String fileName, FileNode parent) {
        this(size, fileName, parent, new ArrayList<>());
    }

    public void setParent(FileNode parent) {
        this.parent = parent;
    }

    public void addChild(FileNode child) {
        children.add(child);
    }

    // Recursively get the size of all the nodes in the tree
    public int getSize() {
        int mySize = 0;
        if(size >= 0)
            mySize = size;
        for(FileNode node : children)
            mySize += node.getSize();
        return mySize;
    }

    public FileNode getDir(String fileName) {
        for(FileNode child : children)
            if(child.fileName.equals(fileName) && child.size == -1)
                return child;
        return null;
    }

    public FileNode getFile(String fileName) {
        for(FileNode child : children)
            if(child.fileName.equals(fileName) && child.size >= 0)
                return child;
        return null;
    }

    public FileNode getParent() {
        return parent;
    }

    public int getLimitedSum(int limit) {
        int sum = 0;

        int mySize = getSize();
        if(mySize <= limit && size < 0) {
            sum += mySize;
        }

        for(FileNode child : children)
            sum += child.getLimitedSum(limit);

        return sum;
    }

    public FileNode getSmallestDir(int availableSpace, int neededSpace, FileNode smallest) {
        if(size >= 0) // this is a file, so can't update the smallest
            return smallest;
        
        int smallestSize = smallest.getSize();
        int mySize = getSize();

        if(availableSpace+mySize >= neededSpace) { // this directory can be deleted
            if(mySize < smallestSize)
                smallest = this;
            
            // Check if my children can accomplish this with a smaller size
            for(FileNode child : children) {
                smallest = child.getSmallestDir(availableSpace, neededSpace, smallest);
            }
        }

        return smallest;
    }

    @Override
    public String toString() {
        return toString(0);
    }

    // Print the tree
    public String toString(int indent) {
        String output = " ".repeat(indent) + "- " + fileName + " ";
        if(size < 0) {
            output += String.format("(dir, size=%d)\n", getSize());
        } else {
            output += String.format("(file, size=%d)\n", size);
        }
        for(FileNode child : children) {
            output += child.toString(indent+4);
        }
        return output;
    }
}

// 5655407 (too high)
// 5655442 (too high)
// 1749646
