package xyz.skettios.dungeonmaster;

import xyz.skettios.dungeonmaster.util.DatabaseHelper;

import java.io.File;

public class Main
{
    public static void main(String[] args)
    {
        // TODO(skettios): Temporary!
        DatabaseHelper.USER = args[1];
        DatabaseHelper.PASS = args[2];

        File cache = new File("cache/");
        if (!cache.mkdir() && !cache.exists())
        {
            DungeonMaster.getInstance().LOGGER.error("Could not make cache directory!");
            return;
        }

        System.loadLibrary("webp-imageio");
        System.setProperty("http.agent", "Chrome");

        DungeonMaster.getInstance().login(args[0]);

        if (cache.isDirectory() && cache.listFiles().length > 0)
        {
            for (File file : cache.listFiles())
            {
                if (!file.delete())
                    DungeonMaster.getInstance().LOGGER.error("Could not delete file {}!", file.getPath());
            }
            if (!cache.delete())
                DungeonMaster.getInstance().LOGGER.error("Could not delete directory {}!", cache.getPath());
        }
    }
}
