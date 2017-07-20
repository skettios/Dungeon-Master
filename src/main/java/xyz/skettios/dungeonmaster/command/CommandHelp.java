package xyz.skettios.dungeonmaster.command;

import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.EmbedBuilder;
import xyz.skettios.dungeonmaster.DungeonMaster;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

@CommandAlias(aliases = {"help"})
public class CommandHelp implements ICommand
{
    public CommandHelp()
    {
    }

    @Override
    public void execute(IMessage message, String... args)
    {
        try
        {
            EmbedBuilder builder = new EmbedBuilder();

            File imgFile = File.createTempFile("img", ".png", new File("cache"));

            BufferedImage avatar = ImageIO.read(new URL(message.getAuthor().getAvatarURL()));
            BufferedImage foreground = ImageIO.read(new File("kevin_hug.png"));
            BufferedImage newImg = new BufferedImage(foreground.getWidth(), foreground.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics g = newImg.getGraphics();
            g.drawImage(avatar, 40, 70, null);
            g.drawImage(foreground, 0, 0, null);
            ImageIO.write(newImg, "png", imgFile);
            g.dispose();

            /*
            BufferedImage srcImg = ImageIO.read(new URL(message.getAuthor().getAvatarURL()));
            BufferedImage newImg = new BufferedImage(srcImg.getWidth(), srcImg.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D g2 = (Graphics2D) newImg.getGraphics();
            g2.drawImage(srcImg, 0, 0, null);
            g2.setStroke(new BasicStroke(5));
            g2.setColor(Color.black);
            g2.drawString("hey guys", 20, 20);
            g2.setColor(Color.red);
            g2.draw(new Line2D.Float(0, 0, newImg.getWidth(), newImg.getHeight()));
            ImageIO.write(newImg, "png", imgFile);
            */

            builder.withImage("attachment://" + imgFile.getName());
            message.getChannel().sendFile(builder.build(), imgFile);

            if (!imgFile.delete())
                DungeonMaster.getInstance().LOGGER.error("Could not delete file {}!", imgFile.getPath());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
