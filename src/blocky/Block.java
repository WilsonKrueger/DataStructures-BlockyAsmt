package blocky;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

// A square block in the Blocky game.
public class Block
{
    public final static Color PACIFIC_POINT = new Color(1, 128, 181);
    public final static Color OLD_OLIVE = new Color(138, 151, 71);
    public final static Color REAL_RED = new Color(199, 44, 58);
    public final static Color DAFFODIL_DELIGHT = new Color(255, 211, 92);
    public final static Color[] COLORS = {DAFFODIL_DELIGHT, OLD_OLIVE, REAL_RED, PACIFIC_POINT};
    
    public final static Color MELON_MAMBO = new Color(234, 62, 112);
    public final static Color HIGHLIGHT_COLOR = new Color(75, 196, 213); //TEMPTING_TURQUOISE
    
    //=== Public Attributes ===
    //position:
    //    The (x, y) coordinates of the upper left corner of this Block.
    //    Note that (0, 0) is the top left corner of the window.
    private int xCoordinate;
    private int yCoordinate;
    
    //size:
    //    The height and width of this Block.  Since all blocks are square,
    //    we needn't represent height and width separately.
    private int size;
    
    //color:
    //    If this block is not subdivided, color stores its color.
    //    Otherwise, color is null and this block's sublocks store their
    //    individual colours.
    private Color color;
    
    //level:
    //    The level of this block within the overall block structure.
    //    The outermost block, corresponding to the root of the tree,
    //    is at level zero.  If a block is at level i, its children are at
    //    level i+1.
    private int level;
    
    //max_depth:
    //    The deepest level allowed in the overall block structure.
    public static int MAX_DEPTH = 5;
    
    // max_size:
    //    The size of the biggest Block, and thus, the entire game board.
    public final static int MAX_SIZE = 640;
    
    //highlighted:
    //    True if the user has selected this block for action.
    private boolean highlighted;
    
    //children:
    //    The blocks into which this block is subdivided.  The children are
    //    stored in this order: upper-left child, upper-right child,
    //    lower-left child, lower-right child.
    List<Block> children;
    
    //parent:
    //    The block that this block is directly within.
    Block parent;

    /*
    === Notes ===
    - children.size() == 0 or children.size() == 4
    - If this Block has children,
        - their max_depth is the same as that of this Block,
        - their size is half that of this Block,
        - their level is one greater than that of this Block,
        - their position is determined by the position and size of this Block
    - level <= max_depth
    */

    public Block()
    {
        this(Color.WHITE, 0, MAX_SIZE, null);
    }
    
    public Block(Color inColor, int inLevel, int inSize, Block inParent)
    {
        xCoordinate = 0;
        yCoordinate = 0;
        size = inSize;
        color = inColor;
        level = inLevel;
        highlighted = false;
        children = new ArrayList<>();
        parent = inParent;
    }
    
    // Method that swaps child elements horizontally or vertically
    public void swap(boolean isHorizontal)
    {
        if(!children.isEmpty())
        {
            // Retrieve the children
            Block tempChild0 = children.get(0);
            Block tempChild1 = children.get(1);
            Block tempChild2 = children.get(2);
            Block tempChild3 = children.get(3);

            // Swap the children horizontally
            if(isHorizontal)
            {
                children.set(0, tempChild1);
                children.set(1, tempChild0);
                children.set(2, tempChild3);
                children.set(3, tempChild2);
            }
            
            //Swap the children vertically
            else
            {
                children.set(0, tempChild2);
                children.set(1, tempChild3);
                children.set(2, tempChild0);
                children.set(3, tempChild1);
            }
        }
    }
    
    // Method to recreate the children of the block
    public void smash(Game game)
    {
        //Only smash if not at level 0 or the max depth
        if(level != 0 && level < MAX_DEPTH)
        {
            game.createRandomChildren(this);
        }
    }
    
    // Method to rotate the children clockwise or counterclockwise
    public void rotate(boolean clockwise)
    {
        if(!children.isEmpty())
        {
            // Retrieve the children
            Block tempChild0 = children.get(0);
            Block tempChild1 = children.get(1);
            Block tempChild2 = children.get(2);
            Block tempChild3 = children.get(3);
            
            // Rotate the blocks clockwise
            if(clockwise)
            {
                children.set(0, tempChild2);
                children.set(1, tempChild0);
                children.set(2, tempChild3);
                children.set(3, tempChild1);
            }
            
            // Rotate the blocks counterclockwise
            else
            {
                children.set(0, tempChild1);
                children.set(1, tempChild3);
                children.set(2, tempChild0);
                children.set(3, tempChild2);
            }
            
            // Rotate the children recursively
            for(Block child : children)
            {
                child.rotate(clockwise);
            }
        }
    }

    public int getXCoordinate()
    {
        return xCoordinate;
    }

    public int getYCoordinate()
    {
        return yCoordinate;
    }

    public int getSize()
    {
        return size;
    }

    public Color getColor()
    {
        return color;
    }

    public int getLevel()
    {
        return level;
    }

    public boolean isHighlighted()
    {
        return highlighted;
    }

    public List<Block> getChildren()
    {
        return children;
    }

    public Block getParent()
    {
        return parent;
    }

    public void setXCoordinate(int xCoordinate)
    {
        this.xCoordinate = xCoordinate;
    }

    public void setYCoordinate(int yCoordinate)
    {
        this.yCoordinate = yCoordinate;
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public void setColor(Color color)
    {
        this.color = color;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public void setHighlighted(boolean highlighted)
    {
        this.highlighted = highlighted;
    }

    public void setChildren(List<Block> children)
    {
        this.children = children;
    }

    public void setParent(Block parent)
    {
        this.parent = parent;
    }
}

