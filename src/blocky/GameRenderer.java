package blocky;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameRenderer extends JComponent implements MouseListener, KeyListener
{
    private Image image;
    private JFrame frame;
    private Game game;
    
    public GameRenderer(Game inGame)
    {
        game = inGame;
        setUpRenderer();
    }
    
    public void display()
    {
        paintBlocks(game.getRoot(), 0, 0);
        paintHighlightedBlock();
        repaint();
    }
    
    private void setUpRenderer()
    {
        image = new BufferedImage(Block.MAX_SIZE, Block.MAX_SIZE, BufferedImage.TYPE_INT_RGB);

        frame = new JFrame("Blocky");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.LINE_AXIS));
        frame.getContentPane().add(topPanel);

        setPreferredSize(new Dimension(Block.MAX_SIZE, Block.MAX_SIZE));
        addMouseListener(this);
        frame.addKeyListener(this);
        topPanel.add(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.PAGE_AXIS));
        topPanel.add(buttonPanel);
        
        frame.pack();
        frame.addWindowListener(new WindowAdapter() { 
          @Override
          public void windowClosing(WindowEvent e) {System.exit(0);} 
        });
        frame.setVisible(true);
    }
    
    private void paintHighlightedBlock()
    {
        Block highlightedBlock = game.getHighlightedBlock();
        
        if (highlightedBlock != null)
        {
            // Set up the graphics object and paint the border the highlight color.
            Graphics graphics = image.getGraphics();
            graphics.setColor(Block.HIGHLIGHT_COLOR);
            graphics.drawRect(highlightedBlock.getXCoordinate(), highlightedBlock.getYCoordinate(), 
                    highlightedBlock.getSize(), highlightedBlock.getSize());
            graphics.drawRect(highlightedBlock.getXCoordinate() + 1, highlightedBlock.getYCoordinate() + 1, 
                    highlightedBlock.getSize() - 2, highlightedBlock.getSize() - 2);
        }
    }
    
    private void paintBlocks(Block block, int xCoordinate, int yCoordinate)
    {
        // Set up the graphics object and paint the solid rectangle the appropriate color.
        Graphics graphics = image.getGraphics();
        graphics.setColor(block.getColor());
        graphics.fillRect(xCoordinate, yCoordinate, block.getSize(), block.getSize());
        
        // Set the border color.
        if (block.isHighlighted())
        {
            
            game.getHighlightedBlock().setXCoordinate(xCoordinate);
            game.getHighlightedBlock().setYCoordinate(yCoordinate);
        }
        else
        {
            graphics.setColor(Color.BLACK);
            graphics.drawRect(xCoordinate, yCoordinate, block.getSize(), block.getSize());
            graphics.drawRect(xCoordinate + 1, yCoordinate + 1, block.getSize() - 2, block.getSize() - 2);
        }
        
        List<Block> children = block.getChildren();
        
        if (!children.isEmpty())
        {
            Block childBlock;
            
            // Upper left.
            childBlock = children.get(0);
            paintBlocks(childBlock, xCoordinate, yCoordinate);
            
            // Upper right.
            childBlock = children.get(1);
            paintBlocks(childBlock, xCoordinate + childBlock.getSize(), yCoordinate);
            
            // Lower left.
            childBlock = children.get(2);
            paintBlocks(childBlock, xCoordinate, yCoordinate + childBlock.getSize());
            
            // Lower right.
            childBlock = children.get(3);
            paintBlocks(childBlock, xCoordinate + childBlock.getSize(), yCoordinate + childBlock.getSize());
        }
    }
    
    private void highlightBlock(Block block, int row, int column)
    {
        List<Block> children;
        Block child;
        int halfBlockSize = block.getSize() / 2;
        
        if(!block.getChildren().isEmpty())
        {
            children = block.getChildren();
            
            // First child
            if(row < halfBlockSize && column < halfBlockSize)
            {
                child = children.get(0);
                
//                if(!child.getChildren().isEmpty())
//                {
//                    highlightBlock(child, row, column);
//                }
                if(game.getHighlightedBlock() != null)
                {
                    game.getHighlightedBlock().setHighlighted(false);
                    game.setHighlightedBlock(null);
                }
                child.setHighlighted(true);
                game.setHighlightedBlock(child);
            }
            
            // Second child
            else if(row < halfBlockSize && column > halfBlockSize)
            {
                child = children.get(1);
                
                if(game.getHighlightedBlock() != null)
                {
                    game.getHighlightedBlock().setHighlighted(false);
                    game.setHighlightedBlock(null);
                }
                child.setHighlighted(true);
                game.setHighlightedBlock(child);
            }
            
            // Third child
            else if(row > halfBlockSize && column < halfBlockSize)
            {
                child = children.get(2);
                
                if(game.getHighlightedBlock() != null)
                {
                    game.getHighlightedBlock().setHighlighted(false);
                    game.setHighlightedBlock(null);
                }
                child.setHighlighted(true);
                game.setHighlightedBlock(child);
            }
            
            // Fourth child
            else if(row > halfBlockSize && column > halfBlockSize)
            {
                child = children.get(3);
                
                if(game.getHighlightedBlock() != null)
                {
                    game.getHighlightedBlock().setHighlighted(false);
                    game.setHighlightedBlock(null);
                }
                child.setHighlighted(true);
                game.setHighlightedBlock(child);
            }
        }
        
    }
    
    private void highlightBlock(MouseEvent e)
    {
        int row = e.getY();
        int column = e.getX();
        highlightBlock(game.getRoot(), row, column);
    }
  
    @Override
    public void paintComponent(Graphics g)
    {
        g.drawImage(image, 0, 0, null);
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_UP)
        {
            Block highlightedBlock = game.getHighlightedBlock();
            
            if (highlightedBlock != null)
            {
                // Unhighlight block
                highlightedBlock.setHighlighted(false);
                
                // Find parent and set highlight
                Block parent = highlightedBlock.getParent();
                parent.setHighlighted(true);
                
                // Set game highlight
                game.setHighlightedBlock(parent);
                
                display();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        highlightBlock(e);
        display();
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        
    }
}
