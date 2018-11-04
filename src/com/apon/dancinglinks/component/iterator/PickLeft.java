package com.apon.dancinglinks.component.iterator;

import com.apon.dancinglinks.component.Node;

public class PickLeft implements PickCommand {

    @Override
    public Node pickDirection(Node node) {
        return node.left;
    }
}
