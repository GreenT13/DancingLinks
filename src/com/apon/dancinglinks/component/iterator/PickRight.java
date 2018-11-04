package com.apon.dancinglinks.component.iterator;

import com.apon.dancinglinks.component.Node;

public class PickRight implements PickCommand {

    @Override
    public Node pickDirection(Node node) {
        return node.right;
    }
}
