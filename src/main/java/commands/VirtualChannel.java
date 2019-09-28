package commands;

import java.awt.Color;
import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;





public class VirtualChannel {

	public static void createvchannel(GuildMessageReceivedEvent event) {
		// TODO Auto-generated method stub
		User author=event.getAuthor();
		Guild g=event.getGuild();
		List<Member> mems=event.getMessage().getMentionedMembers();
		if(g.getVoiceChannelsByName(author.getId(), false).isEmpty()) {
			g.createVoiceChannel(author.getId()).setParent(g.getCategoriesByName("Private Channels", true).get(0)).complete();
			VoiceChannel vc= g.getVoiceChannelsByName(author.getId(), false).get(0);

			hide(event.getMember(), vc,mems);
			vclog(author,event.getChannel(),vc,mems);
			vc.createPermissionOverride(event.getMember()).setAllow(Permission.VIEW_CHANNEL,Permission.VOICE_CONNECT).queue();
			if(event.getMember().getVoiceState().inVoiceChannel()) {
			g.moveVoiceMember(event.getMember(), vc).queue();
			}
		}else {event.getChannel().sendMessage("You have already a channel").queue();}
		
		
	}
	
	public static void removevchannel(GuildMessageReceivedEvent event) {

		event.getGuild().getVoiceChannelsByName(event.getAuthor().getId(), false).get(0).delete().queue();
		delvlog(event.getAuthor(),event.getChannel());
	}
	public static void adduservchannel(GuildMessageReceivedEvent event) {

		VoiceChannel vc=event.getGuild().getVoiceChannelsByName(event.getAuthor().getId(), false).get(0);
		show(vc, event.getMessage().getMentionedMembers());
		adduvlog(event.getMember(),event.getMessage().getMentionedMembers(),event.getChannel());
		
	}
	
	
	public static void hide(Member author,VoiceChannel vc,List<Member> canvisual) {
		
		vc.createPermissionOverride(author.getGuild().getPublicRole()).setDeny(Permission.VIEW_CHANNEL,Permission.VOICE_CONNECT).queue();
		for(Member m:canvisual) {
		vc.createPermissionOverride(m).setAllow(Permission.VIEW_CHANNEL,Permission.VOICE_CONNECT).queue();
		}
	}
	public static void show(VoiceChannel channel,List<Member> canvisual) {
		for(Member m:canvisual) {
		channel.createPermissionOverride(m).setAllow(Permission.VIEW_CHANNEL,Permission.VOICE_CONNECT).queue();
		}
	}
	public static void vclog(User authorofvc,TextChannel botchannel, VoiceChannel created,List<Member> mems) {
		EmbedBuilder builder= new EmbedBuilder();
		builder.setTitle("Virtual Channel Created");
		builder.getDescriptionBuilder().append("Can join: ");
		for(Member m:mems) {
			builder.getDescriptionBuilder().append(m.getAsMention());
		}
		builder.setColor(Color.GREEN);
		builder.addField("Channel Owner:", authorofvc.getAsMention(), false);
		builder.addField("Usage:", "§createvchannel @user1 @user2 .....\n"
				+ "crea un canale vocale temporaneo visibile solo agli utenti indicati che si autoelimina quando l'ultimo utente è uscito dal canale", true);
		builder.addField("Instant invite: "+created.createInvite().complete().getUrl(), "`mention @UtopyBot#9388 for the help page`", true);
		
		
		

		botchannel.sendMessage(builder.build()).complete();
	}
	
	public static void delvlog(User author,TextChannel c) {
		EmbedBuilder builder= new EmbedBuilder();
		builder.setTitle("Virtual Channel Deleted");
		builder.setColor(Color.RED);
		builder.addField("Channel Owner:", author.getAsMention(), false);
		

		c.sendMessage(builder.build()).complete();
	}
	public static void adduvlog(Member author,List<Member> mems,TextChannel c) {
		EmbedBuilder builder= new EmbedBuilder();
		builder.setTitle("Private channel modified");
		builder.getDescriptionBuilder().append("User added: ");
		for(Member m:mems) {
			builder.getDescriptionBuilder().append(m.getAsMention());
		}
		builder.setColor(Color.BLUE);
		builder.addField("Channel Owner:", author.getAsMention(), false);
		

		c.sendMessage(builder.build()).complete();
	}



	
}
