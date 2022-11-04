package blocky;

public class Blocky
{
    public static void main(String[] args)
    {
        Game game = new Game();
        GameRenderer gameRenderer = new GameRenderer(game);
        gameRenderer.display();
    }
}
