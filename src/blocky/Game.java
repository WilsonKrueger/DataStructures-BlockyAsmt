package blocky;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Game
{
    //=== Public Attributes ===
    //root:
    //    The Blocky board on which this game will be played.
    private Block root;
    
    //highlightedBlock:
    //    The block the user has selected.  This will be colored specially when rendered.
    private Block highlightedBlock;
    
    public Game()
    {
        root = createTestBoard();
        //root = new Block();
        //createRandomChildren(root);
        highlightedBlock = null;
    }
    
    public void createRandomChildren(Block parent)
    {
        // TODO - complete this method.
    }
    
    public Block createTestBoard()
    {
        Block root = new Block();
        root.setSize(Block.MAX_SIZE);
        
        List<Block> rootChildren = new ArrayList<>();
        rootChildren.add(new Block(Block.DAFFODIL_DELIGHT, 1, 320, root));
        rootChildren.add(new Block(Block.OLD_OLIVE, 1, 320, root));
        rootChildren.add(new Block(Block.PACIFIC_POINT, 1, 320, root));
        rootChildren.add(new Block(Block.REAL_RED, 1, 320, root));
        
        root.setChildren(rootChildren);
        Block child = root.getChildren().get(0);
        
        rootChildren = new ArrayList<>();
        rootChildren.add(new Block(Block.DAFFODIL_DELIGHT, 2, 160, child));
        rootChildren.add(new Block(Block.OLD_OLIVE, 2, 160, child));
        rootChildren.add(new Block(Block.PACIFIC_POINT, 2, 160, child));
        rootChildren.add(new Block(Block.REAL_RED, 2, 160, child));
        
        child.setChildren(rootChildren);
        
        return root;
    }

    public Block getRoot()
    {
        return root;
    }

    public Block getHighlightedBlock()
    {
        return highlightedBlock;
    }

    public void setRoot(Block root)
    {
        this.root = root;
    }

    public void setHighlightedBlock(Block highlightedBlock)
    {
        this.highlightedBlock = highlightedBlock;
    }
}
