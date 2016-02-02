package com.monkeybusiness.jaaar.objectClasses;

import com.monkeybusiness.jaaar.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yalantis
 */
public class DemoDataFlipImageViewer {

    public static final List<Friend> friends = new ArrayList<>();

    static {
        friends.add(new Friend(R.drawable.anastasia, "ANASTASIA", R.color.purple, "A", "P", "A", "P", "A","P","A", "P", "A", "P", "A","P"));
        friends.add(new Friend(R.drawable.irene, "IRENE", R.color.saffron, "A", "P", "A", "P", "A","P","A", "P", "A", "P", "A","P"));
        friends.add(new Friend(R.drawable.kate, "KATE", R.color.purple, "A", "P", "A", "P", "A","P","A", "P", "A", "P", "A","P"));
        friends.add(new Friend(R.drawable.paul, "PAUL", R.color.saffron, "A", "P", "A", "P", "A","P","A", "P", "A", "P", "A","P"));
        friends.add(new Friend(R.drawable.daria, "DARIA", R.color.purple, "A", "P", "A", "P", "A","P","A", "P", "A", "P", "A","P"));
        friends.add(new Friend(R.drawable.kirill, "KIRILL", R.color.saffron, "A", "P", "A", "P", "A","P","A", "P", "A", "P", "A","P"));
        friends.add(new Friend(R.drawable.julia, "JULIA", R.color.purple, "A", "P", "A", "P", "A","P","A", "P", "A", "P", "A","P"));
        friends.add(new Friend(R.drawable.yalantis, "YALANTIS", R.color.saffron, "A", "P", "A", "P", "A","P","A", "P", "A", "P", "A","P"));
    }
}
