package xyz.skettios.dungeonmaster.command.impl;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.audio.AudioPlayer;
import xyz.skettios.dungeonmaster.DungeonMaster;
import xyz.skettios.dungeonmaster.command.CommandContext;
import xyz.skettios.dungeonmaster.command.ICommand;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class CommandAudio implements ICommand
{
    @Override
    public String[] getAliases()
    {
        return new String[]{"join_voice", "leave_voice", "play_sound"};
    }

    @Override
    public void execute(CommandContext ctx)
    {
        IUser user = ctx.getAuthor();
        IUser self = ctx.getClient().getOurUser();

        switch (ctx.getCommand())
        {
            case "join_voice":
                IVoiceChannel vc = user.getVoiceStateForGuild(ctx.getGuild()).getChannel();
                vc.join();
                break;
            case "leave_voice":
                IVoiceChannel selfVC = self.getVoiceStateForGuild(ctx.getGuild()).getChannel();
                selfVC.leave();
                break;
            case "play_sound":
                playSound(ctx.getArgs().get(0), ctx.getGuild(), ctx.getChannel());
            default:
                break;
        }
    }

    private void playSound(String name, IGuild guild, IChannel channel)
    {
        AudioPlayer player = AudioPlayer.getAudioPlayerForGuild(guild);
        File[] soundDir = new File("sounds").listFiles(file -> file.getName().contains(name));
        for (File file : soundDir)
            System.out.println(file.getName());

        try
        {
            player.queue(soundDir[0]);
        }
        catch (IOException ioe)
        {
            DungeonMaster.getInstance().LOGGER.error(ioe.getMessage());
        }
        catch (UnsupportedAudioFileException ue)
        {
            DungeonMaster.getInstance().LOGGER.error(ue.getMessage());
            channel.sendMessage("Unsupported Format");
        }
    }
}
