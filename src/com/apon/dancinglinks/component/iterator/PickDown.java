package com.apon.dancinglinks.component.iterator;

import com.apon.dancinglinks.component.Node;

public class PickDown implements PickCommand {

    @Override
    public Node pickDirection(Node node) {
        return node.down;
    }
}
